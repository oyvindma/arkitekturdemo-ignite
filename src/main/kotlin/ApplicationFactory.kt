import brukere.BrukereFactory
import brukere.infrastructure.InMemoryUserRepository
import brukere.presentation.UserController
import nettbutikk.NettbutikkFactory
import nettbutikk.presentation.NettbutikkController
import verktoy.VerktoyFactory
import verktoy.infrastructure.InMemoryToolRepository
import verktoy.presentation.ToolController

/**
 * Top-level wiring: creates and connects all bounded contexts.
 * Shared repository instances are passed across context boundaries
 * only through ACL adapters — never raw domain objects.
 */
class ApplicationFactory {

    private val toolRepository = InMemoryToolRepository()
    private val userRepository = InMemoryUserRepository()

    val toolController: ToolController = VerktoyFactory.createToolController(toolRepository)
    val userController: UserController = BrukereFactory.createUserController(userRepository)
    val nettbutikkController: NettbutikkController =
        NettbutikkFactory.createNettbutikkController(toolRepository, userRepository)
}
