import application.*
import infrastructure.*
import presentation.*
/**
 * Fabrikkklasse som kobler sammen alle lag.
 * Infrastruktur-implementasjoner injiseres inn i application og presentation via konstruktorar.
 */
class ApplicationFactory {
    private val brukerRepository = InMemoryBrukerRepository()
    private val verktoyRepository = InMemoryVerktoyRepository()
    private val utlaanRepository = InMemoryUtlaanRepository()
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
    // ACL-adaptere (ports)
    private val verktoyQueryPort = VerktoyQueryAdapter(listVerktoyUseCase)
    private val brukerQueryPort = BrukerQueryAdapter(hentBrukerUseCase)
    private val verktoyStatusPort = VerktoyStatusAdapter(verktoyRepository)
    private val vaerPort = StubVaermeldingAdapter()
    // Use cases - utlaan
    private val soekVerktoyUseCase = SoekVerktoyUseCase(verktoyQueryPort)
    private val laanVerktoyUseCase = LaanVerktoyUseCase(utlaanRepository, verktoyQueryPort, brukerQueryPort, vaerPort, verktoyStatusPort)
    private val returnerVerktoyUseCase = ReturnerVerktoyUseCase(utlaanRepository, verktoyStatusPort)
    // Controllers
    val brukerController = BrukerController(
        registrerBrukerUseCase = registrerBrukerUseCase,
        listBrukereUseCase = listBrukereUseCase,
        hentBrukerUseCase = hentBrukerUseCase,
        oppdaterBrukerUseCase = oppdaterBrukerUseCase,
        slettBrukerUseCase = slettBrukerUseCase
    )
    val verktoyController = VerktoyController(
        leggTilVerktoyUseCase = leggTilVerktoyUseCase,
        listVerktoyUseCase = listVerktoyUseCase,
        slettVerktoyUseCase = slettVerktoyUseCase,
        oppdaterVerktoyUseCase = oppdaterVerktoyUseCase
    )
    val utlaanController = UtlaanController(
        soekVerktoyUseCase = soekVerktoyUseCase,
        laanVerktoyUseCase = laanVerktoyUseCase,
        returnerVerktoyUseCase = returnerVerktoyUseCase
    )
}
