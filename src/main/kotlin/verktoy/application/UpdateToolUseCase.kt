package verktoy.application

import verktoy.core.ToolRepository

class UpdateToolUseCase(private val toolRepository: ToolRepository) {

    data class Command(
        val toolId: String,
        val name: String,
        val description: String,
        val toleratesRain: Boolean
    )

    fun execute(command: Command): ToolDto {
        val existing = toolRepository.findById(command.toolId)
            ?: error("Tool with id ${command.toolId} not found")

        val updated = existing.copy(
            name = command.name,
            description = command.description,
            toleratesRain = command.toleratesRain
        )
        toolRepository.save(updated)
        return toDto(updated)
    }
}
