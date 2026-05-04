package verktoy.application

import verktoy.core.VerktoyRepository

class ListVerktoyCommand(private val verktoyRepository: VerktoyRepository) {

    fun execute(): List<VerktoyDto> {
        return verktoyRepository.finnAlle().map { tilDto(it) }
    }
}

