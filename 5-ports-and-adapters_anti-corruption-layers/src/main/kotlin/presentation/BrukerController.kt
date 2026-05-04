package presentation

import application.bruker.BrukerDto
import application.bruker.HentBrukerCommand
import application.bruker.ListBrukereCommand
import application.bruker.OppdaterBrukerCommand
import application.bruker.RegistrerBrukerCommand
import application.bruker.SlettBrukerCommand

class BrukerController(
    private val registrerBrukerCommand: RegistrerBrukerCommand,
    private val listBrukereCommand: ListBrukereCommand,
    private val hentBrukerCommand: HentBrukerCommand,
    private val oppdaterBrukerCommand: OppdaterBrukerCommand,
    private val slettBrukerCommand: SlettBrukerCommand
) {
    fun registrerBruker(navn: String): BrukerDto {
        return registrerBrukerCommand.execute(RegistrerBrukerCommand.Command(navn = navn))
    }

    fun listBrukere(): List<BrukerDto> {
        return listBrukereCommand.execute()
    }

    fun hentBruker(brukerId: String): BrukerDto {
        return hentBrukerCommand.execute(brukerId)
    }

    fun oppdaterBruker(brukerId: String, navn: String): BrukerDto {
        return oppdaterBrukerCommand.execute(OppdaterBrukerCommand.Command(brukerId = brukerId, navn = navn))
    }

    fun slettBruker(brukerId: String) {
        slettBrukerCommand.execute(brukerId)
    }
}
