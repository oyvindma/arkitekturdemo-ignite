package presentation

import application.verktoy.LeggTilVerktoyCommand
import application.verktoy.ListVerktoyCommand
import application.verktoy.OppdaterVerktoyCommand
import application.verktoy.SlettVerktoyCommand
import application.verktoy.VerktoyDto

class VerktoyController(
    private val leggTilVerktoyCommand: LeggTilVerktoyCommand,
    private val listVerktoyCommand: ListVerktoyCommand,
    private val slettVerktoyCommand: SlettVerktoyCommand,
    private val oppdaterVerktoyCommand: OppdaterVerktoyCommand
) {
    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        return leggTilVerktoyCommand.execute(
            LeggTilVerktoyCommand.Command(navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn)
        )
    }

    fun listVerktoy(): List<VerktoyDto> {
        return listVerktoyCommand.execute()
    }

    fun slettVerktoy(verktoyId: String) {
        slettVerktoyCommand.execute(verktoyId)
    }

    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        return oppdaterVerktoyCommand.execute(
            OppdaterVerktoyCommand.Command(
                verktoyId = verktoyId,
                navn = navn,
                beskrivelse = beskrivelse,
                taalerRegn = taalerRegn
            )
        )
    }
}
