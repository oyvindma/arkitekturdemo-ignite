package services

import domain.Vaermelding
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
 * STEG 2 — Package-by-layer / God-service
 *
 * DEMONSTRERER: Testbarhetsproblem med god-services og konkrete avhengigheter.
 *
 * Vi tester kun «Lån verktøy»-logikken, men UtlaanService inneholder også
 * soekTilgjengelige() og returnerVerktoy(). Alle 4 avhengigheter må mockes
 * selv om de fleste er irrelevante for denne testen.
 *
 * PROBLEMER:
 * 1. Vi må mocke ALLE avhengigheter — inkludert de som kun brukes av andre metoder.
 *    Hvis returnerVerktoy() får en ny avhengighet, brekker alle testene — også denne.
 * 2. Alle avhengigheter er konkrete klasser (ikke interfaces), så vi MÅ bruke
 *    et mocking-rammeverk (Mockito). Vi kan ikke lage enkle fakes.
 * 3. Mocking av konkrete klasser er skjørt: endringer i repository-implementasjonen
 *    (f.eks. nye metoder, endret intern tilstand) kan brekke tester selv om
 *    forretningslogikken er uendret.
 *
 * Sammenlign med steg-3 (Single Responsibility) og steg-5 (ports/interfaces) for å se forbedringene.
 */
class UtlaanServiceLaanVerktoyTest {

    // Alle 4 avhengigheter må mockes — selv om vi bare tester laanVerktoy().
    private val utlaanRepository: UtlaanRepository = mock()
    private val verktoyRepository: VerktoyRepository = mock()
    private val brukerRepository: BrukerRepository = mock()
    private val vaerService: StubVaermeldingService = mock()

    private val service = UtlaanService(utlaanRepository, verktoyRepository, brukerRepository, vaerService)

    @Test
    fun `laanVerktoy - godvaer og tilgjengelig verktoy gir suksess`() {
        val verktoy = Verktoy(id = "v1", navn = "Sirkelsag", beskrivelse = "Skjærer", taalerRegn = false)
        whenever(verktoyRepository.finnMedId("v1")).thenReturn(verktoy)
        whenever(brukerRepository.finnes("b1")).thenReturn(true)
        whenever(vaerService.hentVarsel()).thenReturn(Vaermelding(blirRegn = false, beskrivelse = "Sol"))

        val resultat = service.laanVerktoy("v1", "b1")

        assertTrue(resultat is UtlaanService.LaanResultat.Suksess)
        verify(utlaanRepository).lagre(any())
        verify(verktoyRepository).lagre(any())
    }

    @Test
    fun `laanVerktoy - regn meldt for verktoy som ikke taaler regn gir feil`() {
        val verktoy = Verktoy(id = "v1", navn = "Sirkelsag", beskrivelse = "Skjærer", taalerRegn = false)
        whenever(verktoyRepository.finnMedId("v1")).thenReturn(verktoy)
        whenever(brukerRepository.finnes("b1")).thenReturn(true)
        whenever(vaerService.hentVarsel()).thenReturn(Vaermelding(blirRegn = true, beskrivelse = "Kraftig regn"))

        val resultat = service.laanVerktoy("v1", "b1")

        assertTrue(resultat is UtlaanService.LaanResultat.Feil)
        assertTrue(resultat.grunn.contains("regn"))
    }
}

