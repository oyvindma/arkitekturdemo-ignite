package verktoy.application

import verktoy.core.Tool
import verktoy.core.ToolRepository
import java.util.UUID

class AddToolUseCase(private val toolRepository: ToolRepository) {

    data class Command(val name: String, val description: String, val toleratesRain: Boolean)

    fun execute(command: Command): ToolDto {
        val tool = Tool(
            id = UUID.randomUUID().toString(),
            name = command.name,
            description = command.description,
            toleratesRain = command.toleratesRain
        )
        toolRepository.save(tool)
        return toDto(tool)
    }
}

fun toDto(tool: Tool): ToolDto = ToolDto(
    id = tool.id,
    name = tool.name,
    description = tool.description,
    toleratesRain = tool.toleratesRain,
    available = tool.isAvailable(),
    loanedToUserId = tool.loanedToUserId
)
