import application.*
import infrastructure.*
import presentation.*
class ApplicationFactory {
    private val brukerRepository = InMemoryBrukerRepository()
    private val verktoyRepository = InMemoryVerktoyRepository()
    private val utlaanRepository = InMemoryUtlaanRepository()
    private val vaerService = StubVaerService()
    // Use cases - brukere
    private val registrerBrukerUseCase = RegistrerBrukerUseCase(brukerRepository)
    private val listBrukereUseCase = ListBrukereUseCase(brukerRepository)
    private val hentBrukerUseCase = HentBrukerUseCase(brukerRepository)
    private val oppdaterBrukerUseCase = OppdaterBrukerUseCase(brukerRepository)
    private val slettBrukerUseCase = SlettBrukerUseCase(brukerRepository)
    // Use cases - verktoy
    private val leggTilVerktoyUseCase = LeggTilVerktoyUseCase(verktoyRepository)
    private val listVerktoyUseCase = ListVerktoyUseCase(verktoyRepository)
    private val slettVerktoyUseCase = SlettVerktoyUseCase(verktoyRepository)
    private val oppdaterVerktoyUseCase = OppdaterVerktoyUseCase(verktoyRepository)
    // Use cases - utlaan (uses repos directly, no ports/ACL)
    private val soekVerktoyUseCase = SoekVerktoyUseCase(verktoyRepository)
    private val laanVerktoyUseCase = LaanVerktoyUseCase(utlaanRepository, verktoyRepository, brukerRepository, vaerService)
    private val returnerVerktoyUseCase = ReturnerVerktoyUseCase(utlaanRepository, verktoyRepository)
    // Controllers
    val brukerController = BrukerController(registrerBrukerUseCase, listBrukereUseCase, hentBrukerUseCase, oppdaterBrukerUseCase, slettBrukerUseCase)
    val verktoyController = VerktoyController(leggTilVerktoyUseCase, listVerktoyUseCase, slettVerktoyUseCase, oppdaterVerktoyUseCase)
    val utlaanController = UtlaanController(soekVerktoyUseCase, laanVerktoyUseCase, returnerVerktoyUseCase)
}
