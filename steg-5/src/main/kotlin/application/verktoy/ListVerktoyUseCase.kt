package application.verktoy
import core.verktoy.VerktoyRepository
class ListVerktoyUseCase(private val verktoyRepository: VerktoyRepository) {
    fun execute(): List<VerktoyDto> {
        return verktoyRepository.finnAlle().map { tilVerktoyDto(it) }
    }
}
