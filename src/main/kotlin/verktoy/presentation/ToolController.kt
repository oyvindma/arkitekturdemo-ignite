package verktoy.presentation

import verktoy.application.AddToolUseCase
import verktoy.application.DeleteToolUseCase
import verktoy.application.ListToolsUseCase
import verktoy.application.ToolDto
import verktoy.application.UpdateToolUseCase

class ToolController(
    private val addToolUseCase: AddToolUseCase,
    private val listToolsUseCase: ListToolsUseCase,
    private val deleteToolUseCase: DeleteToolUseCase,
    private val updateToolUseCase: UpdateToolUseCase
) {

    fun addTool(name: String, description: String, toleratesRain: Boolean): ToolDto {
        val command = AddToolUseCase.Command(
            name = name,
            description = description,
            toleratesRain = toleratesRain
        )
        return addToolUseCase.execute(command)
    }

    fun listTools(): List<ToolDto> {
        return listToolsUseCase.execute()
    }

    fun deleteTool(toolId: String) {
        deleteToolUseCase.execute(toolId)
    }

    fun updateTool(toolId: String, name: String, description: String, toleratesRain: Boolean): ToolDto {
        val command = UpdateToolUseCase.Command(
            toolId = toolId,
            name = name,
            description = description,
            toleratesRain = toleratesRain
        )
        return updateToolUseCase.execute(command)
    }
}
