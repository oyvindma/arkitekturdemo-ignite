package verktoy.core

data class Tool(
    val id: String,
    val name: String,
    val description: String,
    val toleratesRain: Boolean,
    val loanedToUserId: String? = null
) {
    fun loanTo(userId: String): Tool = copy(loanedToUserId = userId)
    fun returnTool(): Tool = copy(loanedToUserId = null)
    fun isAvailable(): Boolean = loanedToUserId == null
}
