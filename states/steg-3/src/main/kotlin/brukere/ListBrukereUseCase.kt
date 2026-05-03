package brukere
class ListBrukereUseCase(private val brukerRepository: BrukerRepository) {
    fun execute(): List<BrukerDto> = brukerRepository.finnAlle().map { tilBrukerDto(it) }
}
