package verktoy.infrastructure

import verktoy.core.Tool
import verktoy.core.ToolRepository

class InMemoryToolRepository : ToolRepository {

    private val store: MutableMap<String, Tool> = mutableMapOf()

    override fun save(tool: Tool) {
        store[tool.id] = tool
    }

    override fun findById(id: String): Tool? = store[id]

    override fun findAll(): List<Tool> = store.values.toList()

    override fun findByName(name: String): List<Tool> {
        return store.values.filter { it.name.contains(name, ignoreCase = true) }
    }

    override fun findAvailable(): List<Tool> {
        return store.values.filter { it.isAvailable() }
    }

    override fun delete(id: String) {
        store.remove(id)
    }

    override fun exists(id: String): Boolean = store.containsKey(id)
}
