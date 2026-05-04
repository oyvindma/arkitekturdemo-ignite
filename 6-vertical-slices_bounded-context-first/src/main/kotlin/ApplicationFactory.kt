import brukere.BrukereFactory
import brukere.application.HentBrukerCommand
import brukere.infrastructure.database.BrukerRepositoryAdapter
import brukere.presentation.api.BrukerController
import utlaan.UtlaanFactory
import utlaan.presentation.api.UtlaanController
import verktoy.VerktoyFactory
import verktoy.application.ListVerktoyCommand
import verktoy.infrastructure.database.VerktoyRepositoryAdapter
import verktoy.presentation.api.VerktoyController

/**
 * Toppnivaa-kobling: oppretter og kobler sammen alle bounded contexts.
 * Delte repository-instanser sendes paa tvers av kontekstgrenser
 * kun gjennom ACL-adaptere — aldri raa domeneobjekter.
 */
class ApplicationFactory {

    private val verktoyRepository = VerktoyRepositoryAdapter()
    private val brukerRepository = BrukerRepositoryAdapter()

    val verktoyController: VerktoyController = VerktoyFactory.opprettVerktoyController(verktoyRepository)
    val brukerController: BrukerController = BrukereFactory.opprettBrukerController(brukerRepository)

    val utlaanController: UtlaanController = UtlaanFactory.opprettUtlaanController(
        listVerktoyCommand = ListVerktoyCommand(verktoyRepository),
        hentBrukerCommand = HentBrukerCommand(brukerRepository),
        verktoyRepository = verktoyRepository
    )
}
