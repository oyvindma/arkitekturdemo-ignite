package core.verktoy
interface VerktoyRepositoryPort {
    fun lagre(verktoy: Verktoy)
    fun finnMedId(id: String): Verktoy?
    fun finnAlle(): List<Verktoy>
    fun finnMedNavn(navn: String): List<Verktoy>
    fun finnTilgjengelige(): List<Verktoy>
    fun slett(id: String)
    fun finnes(id: String): Boolean
}
