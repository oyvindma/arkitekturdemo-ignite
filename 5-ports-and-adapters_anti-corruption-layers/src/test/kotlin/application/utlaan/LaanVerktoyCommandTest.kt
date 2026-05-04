package application.utlaan

import core.bruker.BrukerQueryPort
import core.utlaan.Utlaan
import core.utlaan.UtlaanRepositoryPort
import core.utlaan.Vaermelding
import core.utlaan.VaermeldingPort
import core.utlaan.VerktoyQueryPort
import core.utlaan.VerktoyVisning
import core.verktoy.VerktoyStatusPort
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * STEG 5 — Ports & Adapters / Anti-Corruption Layer
 *
 * DEMONSTRERER: Full testbarhet uten mocking-rammeverk.
 *
 * FORBEDRING vs steg-3:
 * - Alle avhengigheter er nå INTERFACES (ports). Vi kan lage enkle inline fakes
 *   med «object : Interface { ... }» — ingen Mockito nødvendig.
 * - Testene leser som spesifikasjoner: «gitt at verktøy finnes og er tilgjengelig,
 *   og bruker finnes, og det ikke regner → da er utlånet vellykket.»
 * - Regn-scenariet er trivielt: bare returner Vaermelding(blirRegn = true) fra faken.
 *   Ingen whenever().thenReturn()-kjeder.
 *
 * FORBEDRING vs steg-2:
 * - I tillegg til alt over: klassen har Single Responsibility (som steg-3), OG avhengighetene er
 *   abstrakte (ports). Dobbel gevinst: fokusert klasse + enkle fakes.
 *
 * EKSTRA FORDEL — Anti-Corruption Layer:
 * - LaanVerktoyCommand ser aldri «Verktoy» (core.verktoy) direkte. Den ser
 *   VerktoyVisning — en projeksjon definert i utlåns-konteksten. Dette betyr at
 *   endringer i Verktoy-entiteten IKKE brekker denne testen.
 */
class LaanVerktoyCommandTest {

    // Gjenbrukbare fakes — enkle, lesbare, ingen rammeverk
    private val lagretUtlaan = mutableListOf<Utlaan>()
    private var markerUtlaantKalt = false

    private val utlaanRepositoryPort = object : UtlaanRepositoryPort {
        override fun lagre(utlaan: Utlaan) { lagretUtlaan.add(utlaan) }
        override fun finnMedId(id: String): Utlaan? = null
        override fun finnAktiveMedBrukerId(brukerId: String): List<Utlaan> = emptyList()
        override fun finnAktivMedVerktoyId(verktoyId: String): Utlaan? = null
        override fun finnAlle(): List<Utlaan> = emptyList()
    }

    private val verktoyStatusPort = object : VerktoyStatusPort {
        override fun markerUtlaant(verktoyId: String, brukerId: String) { markerUtlaantKalt = true }
        override fun markerReturnet(verktoyId: String) {}
    }

    private fun lagVerktoyQueryPort(verktoy: VerktoyVisning?) = object : VerktoyQueryPort {
        override fun finnTilgjengeligeVerktoy(): List<VerktoyVisning> = emptyList()
        override fun finnVerktoyMedNavn(navn: String): List<VerktoyVisning> = emptyList()
        override fun finnVerktoyMedId(id: String): VerktoyVisning? = verktoy
    }

    private fun lagBrukerQueryPort(finnes: Boolean) = object : BrukerQueryPort {
        override fun finnBrukerMedId(id: String) = null
        override fun brukerFinnes(id: String) = finnes
    }

    private fun lagVaermeldingPort(blirRegn: Boolean) = object : VaermeldingPort {
        override fun hentVarsel() = Vaermelding(blirRegn = blirRegn, beskrivelse = if (blirRegn) "Regn" else "Sol")
    }

    @Test
    fun `godvaer og tilgjengelig verktoy gir suksess`() {
        val verktoy = VerktoyVisning("v1", "Sirkelsag", "Skjærer", taalerRegn = false, tilgjengelig = true)
        val command = LaanVerktoyCommand(
            utlaanRepositoryPort,
            lagVerktoyQueryPort(verktoy),
            lagBrukerQueryPort(finnes = true),
            lagVaermeldingPort(blirRegn = false),
            verktoyStatusPort
        )

        val resultat = command.execute(LaanVerktoyCommand.Command("v1", "b1"))

        assertTrue(resultat is LaanVerktoyCommand.LaanResultat.Suksess)
        assertTrue(lagretUtlaan.size == 1)
        assertTrue(markerUtlaantKalt)
    }

    @Test
    fun `regn meldt for verktoy som ikke taaler regn gir feil`() {
        // Trivielt å teste regn-scenario: bare sett blirRegn = true i faken.
        // Ingen mocking-rammeverk, ingen whenever().thenReturn()-kjeder.
        val verktoy = VerktoyVisning("v1", "Sirkelsag", "Skjærer", taalerRegn = false, tilgjengelig = true)
        val command = LaanVerktoyCommand(
            utlaanRepositoryPort,
            lagVerktoyQueryPort(verktoy),
            lagBrukerQueryPort(finnes = true),
            lagVaermeldingPort(blirRegn = true),
            verktoyStatusPort
        )

        val resultat = command.execute(LaanVerktoyCommand.Command("v1", "b1"))

        assertTrue(resultat is LaanVerktoyCommand.LaanResultat.Feil)
        assertTrue(resultat.grunn.contains("regn"))
    }
}

