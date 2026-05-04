import application.*
import infrastructure.*
import infrastructure.bruker.InMemoryBrukerRepository
import infrastructure.utlaan.InMemoryUtlaanRepository
import infrastructure.verktoy.InMemoryVerktoyRepository
import presentation.*
class ApplicationFactory {
    private val brukerRepository = InMemoryBrukerRepository()
    private val verktoyRepository = InMemoryVerktoyRepository()
    private val utlaanRepository = InMemoryUtlaanRepository()
    private val vaerService = StubVaermeldingService()
    // Use cases - brukere
    private val registrerBrukerCommand = RegistrerBrukerCommand(brukerRepository)
    private val listBrukereCommand = ListBrukereCommand(brukerRepository)
    private val hentBrukerCommand = HentBrukerCommand(brukerRepository)
    private val oppdaterBrukerCommand = OppdaterBrukerCommand(brukerRepository)
    private val slettBrukerCommand = SlettBrukerCommand(brukerRepository)
    // Use cases - verktoy
    private val leggTilVerktoyCommand = LeggTilVerktoyCommand(verktoyRepository)
    private val listVerktoyCommand = ListVerktoyCommand(verktoyRepository)
    private val slettVerktoyCommand = SlettVerktoyCommand(verktoyRepository)
    private val oppdaterVerktoyCommand = OppdaterVerktoyCommand(verktoyRepository)
    // Use cases - utlaan (uses repos directly, no ports/ACL)
    private val soekVerktoyCommand = SoekVerktoyCommand(verktoyRepository)
    private val laanVerktoyCommand = LaanVerktoyCommand(utlaanRepository, verktoyRepository, brukerRepository, vaerService)
    private val returnerVerktoyCommand = ReturnerVerktoyCommand(utlaanRepository, verktoyRepository)
    // Controllers
    val brukerController = BrukerController(registrerBrukerCommand, listBrukereCommand, hentBrukerCommand, oppdaterBrukerCommand, slettBrukerCommand)
    val verktoyController = VerktoyController(leggTilVerktoyCommand, listVerktoyCommand, slettVerktoyCommand, oppdaterVerktoyCommand)
    val utlaanController = UtlaanController(soekVerktoyCommand, laanVerktoyCommand, returnerVerktoyCommand)
}
