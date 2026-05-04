package domain

data class Verktoy(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val taalerRegn: Boolean,
    val utlaantTilBrukerId: String? = null
) {
    fun laanUt(brukerId: String): Verktoy = copy(utlaantTilBrukerId = brukerId)
    fun returner(): Verktoy = copy(utlaantTilBrukerId = null)
    fun erTilgjengelig(): Boolean = utlaantTilBrukerId == null
}