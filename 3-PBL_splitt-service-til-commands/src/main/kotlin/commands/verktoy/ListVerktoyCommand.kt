package commands.verktoy

import repositories.VerktoyRepository

class ListVerktoyCommand(private val verktoyRepository: VerktoyRepository) {
    fun execute(): List<VerktoyDto> = verktoyRepository.finnAlle().map { tilVerktoyDto(it) }
}