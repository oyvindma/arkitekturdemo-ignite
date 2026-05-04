import application.bruker.HentBrukerCommand
import application.bruker.ListBrukereCommand
import application.bruker.OppdaterBrukerCommand
import application.bruker.RegistrerBrukerCommand
import application.bruker.SlettBrukerCommand
import application.utlaan.LaanVerktoyCommand
import application.utlaan.ReturnerVerktoyCommand
import application.utlaan.SoekVerktoyCommand
import application.verktoy.LeggTilVerktoyCommand
import application.verktoy.ListVerktoyCommand
import application.verktoy.OppdaterVerktoyCommand
import application.verktoy.SlettVerktoyCommand
import infrastructure.bruker.BrukerQueryAdapter
import infrastructure.bruker.BrukerRepositoryAdapter
import infrastructure.utlaan.StubVaermeldingAdapter
import infrastructure.utlaan.UtlaanRepositoryAdapter
import infrastructure.utlaan.VerktoyQueryAdapter
import infrastructure.verktoy.VerktoyRepositoryAdapter
import infrastructure.verktoy.VerktoyStatusAdapter
import presentation.BrukerController
import presentation.UtlaanController
import presentation.VerktoyController

/**
 * Fabrikkklasse som kobler sammen alle lag.
 * Infrastruktur-implementasjoner injiseres inn i application og presentation via konstruktorar.
 */
class ApplicationFactory {
    private val brukerRepository = BrukerRepositoryAdapter()
    private val verktoyRepository = VerktoyRepositoryAdapter()
    private val utlaanRepository = UtlaanRepositoryAdapter()

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

    // ACL-adaptere (ports)
    private val verktoyQueryPort = VerktoyQueryAdapter(listVerktoyCommand)
    private val brukerQueryPort = BrukerQueryAdapter(hentBrukerCommand)
    private val verktoyStatusPort = VerktoyStatusAdapter(verktoyRepository)
    private val vaerPort = StubVaermeldingAdapter()

    // Use cases - utlaan
    private val soekVerktoyCommand = SoekVerktoyCommand(verktoyQueryPort)
    private val laanVerktoyCommand =
        LaanVerktoyCommand(utlaanRepository, verktoyQueryPort, brukerQueryPort, vaerPort, verktoyStatusPort)
    private val returnerVerktoyCommand = ReturnerVerktoyCommand(utlaanRepository, verktoyStatusPort)

    // Controllers
    val brukerController = BrukerController(
        registrerBrukerCommand = registrerBrukerCommand,
        listBrukereCommand = listBrukereCommand,
        hentBrukerCommand = hentBrukerCommand,
        oppdaterBrukerCommand = oppdaterBrukerCommand,
        slettBrukerCommand = slettBrukerCommand
    )
    val verktoyController = VerktoyController(
        leggTilVerktoyCommand = leggTilVerktoyCommand,
        listVerktoyCommand = listVerktoyCommand,
        slettVerktoyCommand = slettVerktoyCommand,
        oppdaterVerktoyCommand = oppdaterVerktoyCommand
    )
    val utlaanController = UtlaanController(
        soekVerktoyCommand = soekVerktoyCommand,
        laanVerktoyCommand = laanVerktoyCommand,
        returnerVerktoyCommand = returnerVerktoyCommand
    )
}
