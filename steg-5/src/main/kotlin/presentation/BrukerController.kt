package presentation
import application.BrukerDto
import application.HentBrukerUseCase
import application.ListBrukereUseCase
import application.OppdaterBrukerUseCase
import application.RegistrerBrukerUseCase
import application.SlettBrukerUseCase
class BrukerController(
    private val registrerBrukerUseCase: RegistrerBrukerUseCase,
    private val listBrukereUseCase: ListBrukereUseCase,
    private val hentBrukerUseCase: HentBrukerUseCase,
    private val oppdaterBrukerUseCase: OppdaterBrukerUseCase,
    private val slettBrukerUseCase: SlettBrukerUseCase
) {
    fun registrerBruker(navn: String): BrukerDto {
        return registrerBrukerUseCase.execute(RegistrerBrukerUseCase.Command(navn = navn))
    }
    fun listBrukere(): List<BrukerDto> {
        return listBrukereUseCase.execute()
    }
    fun hentBruker(brukerId: String): BrukerDto {
        return hentBrukerUseCase.execute(brukerId)
    }
    fun oppdaterBruker(brukerId: String, navn: String): BrukerDto {
        return oppdaterBrukerUseCase.execute(OppdaterBrukerUseCase.Command(brukerId = brukerId, navn = navn))
    }
    fun slettBruker(brukerId: String) {
        slettBrukerUseCase.execute(brukerId)
    }
}
