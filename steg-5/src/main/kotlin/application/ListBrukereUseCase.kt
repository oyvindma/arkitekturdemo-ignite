package application
import core.BrukerRepository
class ListBrukereUseCase(private val brukerRepository: BrukerRepository) {
    fun execute(): List<BrukerDto> {
        return brukerRepository.finnAlle().map { tilBrukerDto(it) }
    }
}
