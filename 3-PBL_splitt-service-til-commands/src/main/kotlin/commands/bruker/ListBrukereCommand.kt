package commands.bruker

import repositories.BrukerRepository

class ListBrukereCommand(private val brukerRepository: BrukerRepository) {
    fun execute(): List<BrukerDto> = brukerRepository.finnAlle().map { tilBrukerDto(it) }
}