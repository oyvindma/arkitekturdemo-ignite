package presentation
import application.utlaan.LaanVerktoyCommand
import application.utlaan.ReturnerVerktoyCommand
import application.utlaan.SoekVerktoyCommand
import application.utlaan.UtlaanDto
import application.utlaan.VerktoyResultatDto
class UtlaanController(
    private val soekVerktoyCommand: SoekVerktoyCommand,
    private val laanVerktoyCommand: LaanVerktoyCommand,
    private val returnerVerktoyCommand: ReturnerVerktoyCommand
) {
    fun soekTilgjengeligeVerktoy(navn: String? = null): List<VerktoyResultatDto> {
        return soekVerktoyCommand.execute(SoekVerktoyCommand.Command(navn = navn, kunTilgjengelige = true))
    }
    fun laanVerktoy(verktoyId: String, brukerId: String): LaanVerktoyCommand.LaanResultat {
        return laanVerktoyCommand.execute(LaanVerktoyCommand.Command(verktoyId = verktoyId, brukerId = brukerId))
    }
    fun returnerVerktoy(utlaanId: String): UtlaanDto {
        return returnerVerktoyCommand.execute(utlaanId)
    }
}
