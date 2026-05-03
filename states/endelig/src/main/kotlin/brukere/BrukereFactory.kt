package brukere

import brukere.application.HentBrukerUseCase
import brukere.application.ListBrukereUseCase
import brukere.application.OppdaterBrukerUseCase
import brukere.application.RegistrerBrukerUseCase
import brukere.application.SlettBrukerUseCase
import brukere.core.BrukerRepository
import brukere.infrastructure.database.BrukerRepositoryAdapter
import brukere.presentation.api.BrukerController

object BrukereFactory {

    fun opprettBrukerController(brukerRepository: BrukerRepository = BrukerRepositoryAdapter()): BrukerController {

        val registrerBrukerUseCase = RegistrerBrukerUseCase(brukerRepository)
        val listBrukereUseCase = ListBrukereUseCase(brukerRepository)
        val hentBrukerUseCase = HentBrukerUseCase(brukerRepository)
        val oppdaterBrukerUseCase = OppdaterBrukerUseCase(brukerRepository)
        val slettBrukerUseCase = SlettBrukerUseCase(brukerRepository)

        return BrukerController(
            registrerBrukerUseCase = registrerBrukerUseCase,
            listBrukereUseCase = listBrukereUseCase,
            hentBrukerUseCase = hentBrukerUseCase,
            oppdaterBrukerUseCase = oppdaterBrukerUseCase,
            slettBrukerUseCase = slettBrukerUseCase
        )
    }

    fun opprettHentBrukerUseCase(brukerRepository: BrukerRepository): HentBrukerUseCase {
        return HentBrukerUseCase(brukerRepository)
    }
}

