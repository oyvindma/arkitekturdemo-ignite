package application.bruker
import core.bruker.BrukerRepository
class ListBrukereCommand(private val brukerRepository: BrukerRepository) {
    fun execute(): List<BrukerDto> {
        return brukerRepository.finnAlle().map { tilBrukerDto(it) }
    }
}
