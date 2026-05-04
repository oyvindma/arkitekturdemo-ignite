package core
/** ACL-port: utlaan-kontekstens syn paa en bruker. */
data class BrukerVisning(val id: String, val navn: String)
interface BrukerQueryPort {
    fun finnBrukerMedId(id: String): BrukerVisning?
    fun brukerFinnes(id: String): Boolean
}
