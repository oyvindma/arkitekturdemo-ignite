package verktoy.application

import verktoy.core.ToolRepository

class DeleteToolUseCase(private val toolRepository: ToolRepository) {

    fun execute(toolId: String) {
        require(toolRepository.exists(toolId)) { "Tool with id $toolId not found" }
        toolRepository.delete(toolId)
    }
}
