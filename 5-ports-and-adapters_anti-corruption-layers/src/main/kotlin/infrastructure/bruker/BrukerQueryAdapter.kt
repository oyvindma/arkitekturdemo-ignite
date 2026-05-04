package infrastructure.bruker
import application.bruker.HentBrukerCommand
import core.bruker.BrukerQueryPort
import core.bruker.BrukerVisning
/**
 * ACL-adapter: oversetter brukere-modellen til utlaan-kontekstens BrukerVisning.
 */
class BrukerQueryAdapter(
    private val hentBrukerCommand: HentBrukerCommand
) : BrukerQueryPort {
    override fun finnBrukerMedId(id: String): BrukerVisning? {
        return try {
            val dto = hentBrukerCommand.execute(id)
            BrukerVisning(id = dto.id, navn = dto.navn)
        } catch (e: IllegalStateException) {
            null
        }
    }
    override fun brukerFinnes(id: String): Boolean = finnBrukerMedId(id) != null
}
