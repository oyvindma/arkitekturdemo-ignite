package presentation
import application.*
class VerktoyController(
    private val leggTilVerktoyUseCase: LeggTilVerktoyUseCase,
    private val listVerktoyUseCase: ListVerktoyUseCase,
    private val slettVerktoyUseCase: SlettVerktoyUseCase,
    private val oppdaterVerktoyUseCase: OppdaterVerktoyUseCase
) {
    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto =
        leggTilVerktoyUseCase.execute(LeggTilVerktoyUseCase.Command(navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn))
    fun listVerktoy(): List<VerktoyDto> = listVerktoyUseCase.execute()
    fun slettVerktoy(verktoyId: String) = slettVerktoyUseCase.execute(verktoyId)
    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto =
        oppdaterVerktoyUseCase.execute(OppdaterVerktoyUseCase.Command(verktoyId = verktoyId, navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn))
}
