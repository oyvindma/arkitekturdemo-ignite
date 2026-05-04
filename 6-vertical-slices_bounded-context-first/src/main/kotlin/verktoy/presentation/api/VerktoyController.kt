package verktoy.presentation.api

import verktoy.application.LeggTilVerktoyCommand
import verktoy.application.ListVerktoyCommand
import verktoy.application.OppdaterVerktoyCommand
import verktoy.application.SlettVerktoyCommand
import verktoy.application.VerktoyDto

class VerktoyController(
    private val leggTilVerktoyCommand: LeggTilVerktoyCommand,
    private val listVerktoyCommand: ListVerktoyCommand,
    private val slettVerktoyCommand: SlettVerktoyCommand,
    private val oppdaterVerktoyCommand: OppdaterVerktoyCommand
) {

    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        val command = LeggTilVerktoyCommand.Command(
            navn = navn,
            beskrivelse = beskrivelse,
            taalerRegn = taalerRegn
        )
        return leggTilVerktoyCommand.execute(command)
    }

    fun listVerktoy(): List<VerktoyDto> {
        return listVerktoyCommand.execute()
    }

    fun slettVerktoy(verktoyId: String) {
        slettVerktoyCommand.execute(verktoyId)
    }

    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        val command = OppdaterVerktoyCommand.Command(
            verktoyId = verktoyId,
            navn = navn,
            beskrivelse = beskrivelse,
            taalerRegn = taalerRegn
        )
        return oppdaterVerktoyCommand.execute(command)
    }
}

