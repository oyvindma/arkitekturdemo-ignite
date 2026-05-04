package verktoy.application

import verktoy.core.VerktoyRepositoryPort

class ListVerktoyCommand(private val verktoyRepositoryPort: VerktoyRepositoryPort) {

    fun execute(): List<VerktoyDto> {
        return verktoyRepositoryPort.finnAlle().map { tilDto(it) }
    }
}

