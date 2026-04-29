package nettbutikk.application

import nettbutikk.core.ToolQueryPort
import nettbutikk.core.ToolView

class SearchToolsUseCase(private val toolQueryPort: ToolQueryPort) {

    data class Query(val name: String? = null, val availableOnly: Boolean = true)

    fun execute(query: Query): List<ToolView> {
        val tools = if (query.name != null) {
            toolQueryPort.findToolsByName(query.name)
        } else {
            toolQueryPort.findAvailableTools()
        }

        return if (query.availableOnly) {
            tools.filter { it.available }
        } else {
            tools
        }
    }
}
