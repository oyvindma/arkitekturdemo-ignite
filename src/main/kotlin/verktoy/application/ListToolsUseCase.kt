package verktoy.application

import verktoy.core.ToolRepository

class ListToolsUseCase(private val toolRepository: ToolRepository) {

    fun execute(): List<ToolDto> {
        return toolRepository.findAll().map { toDto(it) }
    }
}
