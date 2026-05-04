package application.bruker
import core.bruker.BrukerRepositoryPort
class ListBrukereCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {
    fun execute(): List<BrukerDto> {
        return brukerRepositoryPort.finnAlle().map { tilBrukerDto(it) }
    }
}
