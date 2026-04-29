package verktoy.core

interface ToolRepository {
    fun save(tool: Tool)
    fun findById(id: String): Tool?
    fun findAll(): List<Tool>
    fun findByName(name: String): List<Tool>
    fun findAvailable(): List<Tool>
    fun delete(id: String)
    fun exists(id: String): Boolean
}
