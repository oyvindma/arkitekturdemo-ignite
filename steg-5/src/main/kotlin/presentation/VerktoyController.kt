package presentation
import application.verktoy.LeggTilVerktoyUseCase
import application.verktoy.ListVerktoyUseCase
import application.verktoy.OppdaterVerktoyUseCase
import application.verktoy.SlettVerktoyUseCase
import application.verktoy.VerktoyDto
class VerktoyController(
    private val leggTilVerktoyUseCase: LeggTilVerktoyUseCase,
    private val listVerktoyUseCase: ListVerktoyUseCase,
    private val slettVerktoyUseCase: SlettVerktoyUseCase,
    private val oppdaterVerktoyUseCase: OppdaterVerktoyUseCase
) {
    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        return leggTilVerktoyUseCase.execute(
            LeggTilVerktoyUseCase.Command(navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn)
        )
    }
    fun listVerktoy(): List<VerktoyDto> {
        return listVerktoyUseCase.execute()
    }
    fun slettVerktoy(verktoyId: String) {
        slettVerktoyUseCase.execute(verktoyId)
    }
    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        return oppdaterVerktoyUseCase.execute(
            OppdaterVerktoyUseCase.Command(verktoyId = verktoyId, navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn)
        )
    }
}
