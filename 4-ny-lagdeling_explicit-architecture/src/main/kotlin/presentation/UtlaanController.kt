package presentation

import application.LaanVerktoyCommand
import application.ReturnerVerktoyCommand
import application.SoekVerktoyCommand
import application.UtlaanDto
import application.utlaan.VerktoyOppslagDto

class UtlaanController(
    private val soekVerktoyCommand: SoekVerktoyCommand,
    private val laanVerktoyCommand: LaanVerktoyCommand,
    private val returnerVerktoyCommand: ReturnerVerktoyCommand
) {
    fun soekTilgjengeligeVerktoy(navn: String? = null): List<VerktoyOppslagDto> =
        soekVerktoyCommand.execute(SoekVerktoyCommand.Command(navn = navn, kunTilgjengelige = true))

    fun laanVerktoy(verktoyId: String, brukerId: String): LaanVerktoyCommand.LaanResultat =
        laanVerktoyCommand.execute(LaanVerktoyCommand.Command(verktoyId = verktoyId, brukerId = brukerId))

    fun returnerVerktoy(utlaanId: String): UtlaanDto = returnerVerktoyCommand.execute(utlaanId)
}
