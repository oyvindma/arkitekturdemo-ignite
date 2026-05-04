package controllers

import commands.utlaan.LaanVerktoyCommand
import commands.utlaan.ReturnerVerktoyCommand
import commands.utlaan.SoekVerktoyCommand
import commands.utlaan.UtlaanDto
import commands.utlaan.VerktoyOppslagDto

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