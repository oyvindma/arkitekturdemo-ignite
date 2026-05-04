package presentation
import application.*
class BrukerController(
    private val registrerBrukerUseCase: RegistrerBrukerUseCase,
    private val listBrukereUseCase: ListBrukereUseCase,
    private val hentBrukerUseCase: HentBrukerUseCase,
    private val oppdaterBrukerUseCase: OppdaterBrukerUseCase,
    private val slettBrukerUseCase: SlettBrukerUseCase
) {
    fun registrerBruker(navn: String): BrukerDto = registrerBrukerUseCase.execute(RegistrerBrukerUseCase.Command(navn = navn))
    fun listBrukere(): List<BrukerDto> = listBrukereUseCase.execute()
    fun hentBruker(brukerId: String): BrukerDto = hentBrukerUseCase.execute(brukerId)
    fun oppdaterBruker(brukerId: String, navn: String): BrukerDto = oppdaterBrukerUseCase.execute(OppdaterBrukerUseCase.Command(brukerId = brukerId, navn = navn))
    fun slettBruker(brukerId: String) = slettBrukerUseCase.execute(brukerId)
}
