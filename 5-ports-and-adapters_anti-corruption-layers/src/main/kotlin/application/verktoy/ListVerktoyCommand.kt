package application.verktoy

import core.verktoy.VerktoyRepositoryPort

class ListVerktoyCommand(private val verktoyRepositoryPort: VerktoyRepositoryPort) {
    fun execute(): List<VerktoyDto> {
        return verktoyRepositoryPort.finnAlle().map { tilVerktoyDto(it) }
    }
}
