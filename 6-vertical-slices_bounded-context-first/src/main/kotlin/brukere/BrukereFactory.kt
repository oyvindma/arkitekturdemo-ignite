package brukere

import brukere.application.HentBrukerCommand
import brukere.application.ListBrukereCommand
import brukere.application.OppdaterBrukerCommand
import brukere.application.RegistrerBrukerCommand
import brukere.application.SlettBrukerCommand
import brukere.core.BrukerRepositoryPort
import brukere.infrastructure.database.BrukerRepositoryAdapter
import brukere.presentation.api.BrukerController

object BrukereFactory {

    fun opprettBrukerController(brukerRepositoryPort: BrukerRepositoryPort = BrukerRepositoryAdapter()): BrukerController {

        val registrerBrukerCommand = RegistrerBrukerCommand(brukerRepositoryPort)
        val listBrukereCommand = ListBrukereCommand(brukerRepositoryPort)
        val hentBrukerCommand = HentBrukerCommand(brukerRepositoryPort)
        val oppdaterBrukerCommand = OppdaterBrukerCommand(brukerRepositoryPort)
        val slettBrukerCommand = SlettBrukerCommand(brukerRepositoryPort)

        return BrukerController(
            registrerBrukerCommand = registrerBrukerCommand,
            listBrukereCommand = listBrukereCommand,
            hentBrukerCommand = hentBrukerCommand,
            oppdaterBrukerCommand = oppdaterBrukerCommand,
            slettBrukerCommand = slettBrukerCommand
        )
    }

    fun opprettHentBrukerUseCase(brukerRepositoryPort: BrukerRepositoryPort): HentBrukerCommand {
        return HentBrukerCommand(brukerRepositoryPort)
    }
}

