package nettbutikk.core

/** ACL port: nettbutikk's view of a tool from the verktøy context. */
data class ToolView(
    val id: String,
    val name: String,
    val description: String,
    val toleratesRain: Boolean,
    val available: Boolean
)

interface ToolQueryPort {
    fun findAvailableTools(): List<ToolView>
    fun findToolsByName(name: String): List<ToolView>
    fun findToolById(id: String): ToolView?
}
