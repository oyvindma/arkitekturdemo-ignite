package brukere.core

data class User(
    val id: String,
    val name: String,
    val borrowedToolIds: List<String> = emptyList()
) {
    fun borrowTool(toolId: String): User = copy(borrowedToolIds = borrowedToolIds + toolId)
    fun returnTool(toolId: String): User = copy(borrowedToolIds = borrowedToolIds - toolId)
}
