package brukere.application

data class UserDto(
    val id: String,
    val name: String,
    val borrowedToolIds: List<String>
)
