package commands.utlaan

import domain.Verktoy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import org.mockito.kotlin.any
import repositories.BrukerRepository
import repositories.UtlaanRepository
import repositories.VerktoyRepository
import kotlin.test.assertTrue

/**
 * STEG 3 — Command-split (Single Responsibility)
 *
 * DEMONSTRERER: Forbedring fra god-service — klassen har kun én jobb (lån verktøy).
 *
 * FORBEDRING vs steg-2:
 * - Vi tester nå en klasse med ETT ansvar. Ingen soekTilgjengelige() eller
 *   returnerVerktoy() som «bor» i samme klasse og kan påvirke testoppsettet.
 * - Hvis ReturnerVerktoyCommand får en ny avhengighet, påvirker det IKKE denne testen.
 * - Enklere å forstå: testen handler om én ting, klassen handler om én ting.
 *
 * GJENSTÅENDE PROBLEM:
 * - Alle avhengigheter er fortsatt konkrete klasser (VerktoyRepository,
 *   BrukerRepository, StubVaermeldingService). Vi MÅ fortsatt bruke Mockito
 *   for å kontrollere oppførselen deres i tester.
 * - Konkrete klasser er skjøre å mocke: Endring i implementasjonen
 *   (f.eks. final-metoder, nye konstruktørparametere) kan brekke testene
 *   selv om forretningslogikken er uendret.
 *
 * → Se steg-5 for hvordan interfaces (ports) løser dette problemet.
 */
class LaanVerktoyCommandTest {

    // Fortsatt 4 konkrete klasser som må mockes — men nå er det KUN de
    // avhengighetene LaanVerktoyCommand faktisk trenger. Ingen «ekstra bagasje».
    private val utlaanRepository: UtlaanRepository = mock()
    private val verktoyRepository: VerktoyRepository = mock()
    private val brukerRepository: BrukerRepository = mock()
    private val vaerService: StubVaermeldingService = mock()

    private val command = LaanVerktoyCommand(utlaanRepository, verktoyRepository, brukerRepository, vaerService)

    @Test
    fun `positiv vaermelding og tilgjengelig verktoy gir suksess`() {
        val verktoy = Verktoy(id = "v1", navn = "Sirkelsag", beskrivelse = "Skjærer", taalerRegn = false)
        whenever(verktoyRepository.finnMedId("v1")).thenReturn(verktoy)
        whenever(brukerRepository.finnes("b1")).thenReturn(true)
        whenever(vaerService.hentVarsel()).thenReturn(Vaermelding(blirRegn = false, beskrivelse = "Sol"))

        val resultat = command.execute(LaanVerktoyCommand.Command("v1", "b1"))

        assertTrue(resultat is LaanVerktoyCommand.LaanResultat.Suksess)
        verify(utlaanRepository).lagre(any())
        verify(verktoyRepository).lagre(any())
    }

    @Test
    fun `regn meldt for verktoy som ikke taaler regn gir feil`() {
        // Fortsatt nødt til å mocke en konkret klasse (StubVaermeldingService)
        // for å teste regn-scenariet. Uten Mockito kunne vi ikke styrt returverdien.
        val verktoy = Verktoy(id = "v1", navn = "Sirkelsag", beskrivelse = "Skjærer", taalerRegn = false)
        whenever(verktoyRepository.finnMedId("v1")).thenReturn(verktoy)
        whenever(brukerRepository.finnes("b1")).thenReturn(true)
        whenever(vaerService.hentVarsel()).thenReturn(Vaermelding(blirRegn = true, beskrivelse = "Kraftig regn"))

        val resultat = command.execute(LaanVerktoyCommand.Command("v1", "b1"))

        assertTrue(resultat is LaanVerktoyCommand.LaanResultat.Feil)
        assertTrue(resultat.grunn.contains("regn"))
    }
}

