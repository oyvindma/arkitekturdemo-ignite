package application
import core.BrukerRepository
class ListBrukereCommand(private val brukerRepository: BrukerRepository) {
    fun execute(): List<BrukerDto> = brukerRepository.finnAlle().map { tilBrukerDto(it) }
}
