package brukere

import brukere.application.HentBrukerCommand
import brukere.application.ListBrukereCommand
import brukere.application.OppdaterBrukerCommand
import brukere.application.RegistrerBrukerCommand
import brukere.application.SlettBrukerCommand
import brukere.core.BrukerRepository
import brukere.infrastructure.database.BrukerRepositoryAdapter
import brukere.presentation.api.BrukerController

object BrukereFactory {

    fun opprettBrukerController(brukerRepository: BrukerRepository = BrukerRepositoryAdapter()): BrukerController {

        val registrerBrukerCommand = RegistrerBrukerCommand(brukerRepository)
        val listBrukereCommand = ListBrukereCommand(brukerRepository)
        val hentBrukerCommand = HentBrukerCommand(brukerRepository)
        val oppdaterBrukerCommand = OppdaterBrukerCommand(brukerRepository)
        val slettBrukerCommand = SlettBrukerCommand(brukerRepository)

        return BrukerController(
            registrerBrukerCommand = registrerBrukerCommand,
            listBrukereCommand = listBrukereCommand,
            hentBrukerCommand = hentBrukerCommand,
            oppdaterBrukerCommand = oppdaterBrukerCommand,
            slettBrukerCommand = slettBrukerCommand
        )
    }

    fun opprettHentBrukerUseCase(brukerRepository: BrukerRepository): HentBrukerCommand {
        return HentBrukerCommand(brukerRepository)
    }
}

