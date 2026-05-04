package application
data class VerktoyResultatDto(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val taalerRegn: Boolean,
    val tilgjengelig: Boolean
)
