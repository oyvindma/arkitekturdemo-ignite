package utlaan.application

/** Dto returnert fra soek, slik at presentation kun avhenger av application. */
data class VerktoyOppslagDto(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val taalerRegn: Boolean,
    val tilgjengelig: Boolean
)

