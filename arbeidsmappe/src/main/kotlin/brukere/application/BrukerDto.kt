package brukere.application

data class BrukerDto(
    val id: String,
    val navn: String,
    val laanteVerktoyIder: List<String>
)

