package verktoy.application

data class ToolDto(
    val id: String,
    val name: String,
    val description: String,
    val toleratesRain: Boolean,
    val available: Boolean,
    val loanedToUserId: String?
)
