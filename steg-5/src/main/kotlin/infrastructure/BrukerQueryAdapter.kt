package infrastructure
import application.BrukerDto
import application.HentBrukerUseCase
import core.BrukerQueryPort
import core.BrukerVisning
/**
 * ACL-adapter: oversetter brukere-modellen til utlaan-kontekstens BrukerVisning.
 */
class BrukerQueryAdapter(
    private val hentBrukerUseCase: HentBrukerUseCase
) : BrukerQueryPort {
    override fun finnBrukerMedId(id: String): BrukerVisning? {
        return try {
            val dto = hentBrukerUseCase.execute(id)
            BrukerVisning(id = dto.id, navn = dto.navn)
        } catch (e: IllegalStateException) {
            null
        }
    }
    override fun brukerFinnes(id: String): Boolean = finnBrukerMedId(id) != null
}
