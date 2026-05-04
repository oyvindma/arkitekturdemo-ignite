package utlaan.presentation.api

import utlaan.application.LaanVerktoyCommand
import utlaan.application.ReturnerVerktoyCommand
import utlaan.application.SoekVerktoyCommand
import utlaan.application.UtlaanDto
import utlaan.application.VerktoyOppslagDto

class UtlaanController(
    private val soekVerktoyCommand: SoekVerktoyCommand,
    private val laanVerktoyCommand: LaanVerktoyCommand,
    private val returnerVerktoyCommand: ReturnerVerktoyCommand
) {

    fun soekTilgjengeligeVerktoy(navn: String? = null): List<VerktoyOppslagDto> {
        return soekVerktoyCommand.execute(SoekVerktoyCommand.Command(navn = navn, kunTilgjengelige = true))
    }

    fun laanVerktoy(verktoyId: String, brukerId: String): LaanVerktoyCommand.LaanResultat {
        return laanVerktoyCommand.execute(LaanVerktoyCommand.Command(verktoyId = verktoyId, brukerId = brukerId))
    }

    fun returnerVerktoy(utlaanId: String): UtlaanDto {
        return returnerVerktoyCommand.execute(utlaanId)
    }
}

