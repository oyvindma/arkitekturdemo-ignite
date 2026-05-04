package presentation

import application.LeggTilVerktoyCommand
import application.ListVerktoyCommand
import application.OppdaterVerktoyCommand
import application.SlettVerktoyCommand
import application.VerktoyDto

class VerktoyController(
    private val leggTilVerktoyCommand: LeggTilVerktoyCommand,
    private val listVerktoyCommand: ListVerktoyCommand,
    private val slettVerktoyCommand: SlettVerktoyCommand,
    private val oppdaterVerktoyCommand: OppdaterVerktoyCommand
) {
    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto =
        leggTilVerktoyCommand.execute(
            LeggTilVerktoyCommand.Command(
                navn = navn,
                beskrivelse = beskrivelse,
                taalerRegn = taalerRegn
            )
        )

    fun listVerktoy(): List<VerktoyDto> = listVerktoyCommand.execute()
    fun slettVerktoy(verktoyId: String) = slettVerktoyCommand.execute(verktoyId)
    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto =
        oppdaterVerktoyCommand.execute(
            OppdaterVerktoyCommand.Command(
                verktoyId = verktoyId,
                navn = navn,
                beskrivelse = beskrivelse,
                taalerRegn = taalerRegn
            )
        )
}
