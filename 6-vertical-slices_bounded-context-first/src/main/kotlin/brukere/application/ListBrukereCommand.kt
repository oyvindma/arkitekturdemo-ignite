package brukere.application

import brukere.core.BrukerRepositoryPort

class ListBrukereCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {

    fun execute(): List<BrukerDto> {
        return brukerRepositoryPort.finnAlle().map { tilDto(it) }
    }
}

