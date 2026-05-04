package presentation
import application.utlaan.LaanVerktoyUseCase
import application.utlaan.ReturnerVerktoyUseCase
import application.utlaan.SoekVerktoyUseCase
import application.utlaan.UtlaanDto
import application.utlaan.VerktoyResultatDto
class UtlaanController(
    private val soekVerktoyUseCase: SoekVerktoyUseCase,
    private val laanVerktoyUseCase: LaanVerktoyUseCase,
    private val returnerVerktoyUseCase: ReturnerVerktoyUseCase
) {
    fun soekTilgjengeligeVerktoy(navn: String? = null): List<VerktoyResultatDto> {
        return soekVerktoyUseCase.execute(SoekVerktoyUseCase.Command(navn = navn, kunTilgjengelige = true))
    }
    fun laanVerktoy(verktoyId: String, brukerId: String): LaanVerktoyUseCase.LaanResultat {
        return laanVerktoyUseCase.execute(LaanVerktoyUseCase.Command(verktoyId = verktoyId, brukerId = brukerId))
    }
    fun returnerVerktoy(utlaanId: String): UtlaanDto {
        return returnerVerktoyUseCase.execute(utlaanId)
    }
}
