package application.bruker

data class BrukerDto(
    val id: String,
    val navn: String,
    val laanteVerktoyIder: List<String>
)
