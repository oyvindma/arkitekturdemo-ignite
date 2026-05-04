package application.verktoy

data class VerktoyDto(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val taalerRegn: Boolean,
    val tilgjengelig: Boolean,
    val utlaantTilBrukerId: String?
)
