package brukere.presentation.api

import brukere.application.BrukerDto
import brukere.application.HentBrukerUseCase
import brukere.application.ListBrukereUseCase
import brukere.application.OppdaterBrukerUseCase
import brukere.application.RegistrerBrukerUseCase
import brukere.application.SlettBrukerUseCase

class BrukerController(
    private val registrerBrukerUseCase: RegistrerBrukerUseCase,
    private val listBrukereUseCase: ListBrukereUseCase,
    private val hentBrukerUseCase: HentBrukerUseCase,
    private val oppdaterBrukerUseCase: OppdaterBrukerUseCase,
    private val slettBrukerUseCase: SlettBrukerUseCase
) {

    fun registrerBruker(navn: String): BrukerDto {
        return registrerBrukerUseCase.execute(RegistrerBrukerUseCase.Command(navn = navn))
    }

    fun listBrukere(): List<BrukerDto> {
        return listBrukereUseCase.execute()
    }

    fun hentBruker(brukerId: String): BrukerDto {
        return hentBrukerUseCase.execute(brukerId)
    }

    fun oppdaterBruker(brukerId: String, navn: String): BrukerDto {
        return oppdaterBrukerUseCase.execute(OppdaterBrukerUseCase.Command(brukerId = brukerId, navn = navn))
    }

    fun slettBruker(brukerId: String) {
        slettBrukerUseCase.execute(brukerId)
    }
}

