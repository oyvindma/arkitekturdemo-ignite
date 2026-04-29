package nettbutikk.infrastructure

import nettbutikk.core.ToolQueryPort
import nettbutikk.core.ToolView
import verktoy.application.ListToolsUseCase
import verktoy.application.ToolDto

/**
 * ACL adapter: translates verktøy domain objects into nettbutikk's ToolView.
 * Prevents verktøy's internal model from leaking into the nettbutikk context.
 */
class VerktoyToolQueryAdapter(
    private val listToolsUseCase: ListToolsUseCase
) : ToolQueryPort {

    override fun findAvailableTools(): List<ToolView> {
        return listToolsUseCase.execute()
            .filter { it.available }
            .map { toToolView(it) }
    }

    override fun findToolsByName(name: String): List<ToolView> {
        return listToolsUseCase.execute()
            .filter { it.name.contains(name, ignoreCase = true) }
            .map { toToolView(it) }
    }

    override fun findToolById(id: String): ToolView? {
        return listToolsUseCase.execute()
            .firstOrNull { it.id == id }
            ?.let { toToolView(it) }
    }

    private fun toToolView(dto: ToolDto): ToolView = ToolView(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        toleratesRain = dto.toleratesRain,
        available = dto.available
    )
}
