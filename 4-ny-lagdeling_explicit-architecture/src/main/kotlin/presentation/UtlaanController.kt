package presentation
import application.*
import application.utlaan.VerktoyResultatDto

class UtlaanController(
    private val soekVerktoyUseCase: SoekVerktoyUseCase,
    private val laanVerktoyUseCase: LaanVerktoyUseCase,
    private val returnerVerktoyUseCase: ReturnerVerktoyUseCase
) {
    fun soekTilgjengeligeVerktoy(navn: String? = null): List<VerktoyResultatDto> =
        soekVerktoyUseCase.execute(SoekVerktoyUseCase.Command(navn = navn, kunTilgjengelige = true))
    fun laanVerktoy(verktoyId: String, brukerId: String): LaanVerktoyUseCase.LaanResultat =
        laanVerktoyUseCase.execute(LaanVerktoyUseCase.Command(verktoyId = verktoyId, brukerId = brukerId))
    fun returnerVerktoy(utlaanId: String): UtlaanDto = returnerVerktoyUseCase.execute(utlaanId)
}
