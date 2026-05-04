package controllers

import commands.bruker.BrukerDto
import commands.bruker.HentBrukerCommand
import commands.bruker.ListBrukereCommand
import commands.bruker.OppdaterBrukerCommand
import commands.bruker.RegistrerBrukerCommand
import commands.bruker.SlettBrukerCommand

class BrukerController(
    private val registrerBrukerCommand: RegistrerBrukerCommand,
    private val listBrukereCommand: ListBrukereCommand,
    private val hentBrukerCommand: HentBrukerCommand,
    private val oppdaterBrukerCommand: OppdaterBrukerCommand,
    private val slettBrukerCommand: SlettBrukerCommand
) {
    fun registrerBruker(navn: String): BrukerDto = registrerBrukerCommand.execute(RegistrerBrukerCommand.Command(navn = navn))
    fun listBrukere(): List<BrukerDto> = listBrukereCommand.execute()
    fun hentBruker(brukerId: String): BrukerDto = hentBrukerCommand.execute(brukerId)
    fun oppdaterBruker(brukerId: String, navn: String): BrukerDto = oppdaterBrukerCommand.execute(OppdaterBrukerCommand.Command(brukerId = brukerId, navn = navn))
    fun slettBruker(brukerId: String) = slettBrukerCommand.execute(brukerId)
}