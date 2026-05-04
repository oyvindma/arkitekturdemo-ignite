package verktoy
class ListVerktoyUseCase(private val verktoyRepository: VerktoyRepository) {
    fun execute(): List<VerktoyDto> = verktoyRepository.finnAlle().map { tilVerktoyDto(it) }
}
