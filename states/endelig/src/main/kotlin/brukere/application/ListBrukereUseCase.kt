package brukere.application

import brukere.core.BrukerRepository

class ListBrukereUseCase(private val brukerRepository: BrukerRepository) {

    fun execute(): List<BrukerDto> {
        return brukerRepository.finnAlle().map { tilDto(it) }
    }
}

