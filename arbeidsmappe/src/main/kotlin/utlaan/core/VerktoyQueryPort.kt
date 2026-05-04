package utlaan.core

/** ACL-port: utlaan-kontekstens syn paa et verktoy fra verktoy-konteksten. */
data class VerktoyVisning(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val taalerRegn: Boolean,
    val tilgjengelig: Boolean
)

interface VerktoyQueryPort {
    fun finnTilgjengeligeVerktoy(): List<VerktoyVisning>
    fun finnVerktoyMedNavn(navn: String): List<VerktoyVisning>
    fun finnVerktoyMedId(id: String): VerktoyVisning?
}

