package verktoy

import verktoy.application.AddToolUseCase
import verktoy.application.DeleteToolUseCase
import verktoy.application.ListToolsUseCase
import verktoy.application.UpdateToolUseCase
import verktoy.core.ToolRepository
import verktoy.infrastructure.InMemoryToolRepository
import verktoy.presentation.ToolController

object VerktoyFactory {

    fun createToolController(toolRepository: ToolRepository = InMemoryToolRepository()): ToolController {

        val addToolUseCase = AddToolUseCase(toolRepository)
        val listToolsUseCase = ListToolsUseCase(toolRepository)
        val deleteToolUseCase = DeleteToolUseCase(toolRepository)
        val updateToolUseCase = UpdateToolUseCase(toolRepository)

        return ToolController(
            addToolUseCase = addToolUseCase,
            listToolsUseCase = listToolsUseCase,
            deleteToolUseCase = deleteToolUseCase,
            updateToolUseCase = updateToolUseCase
        )
    }
}
