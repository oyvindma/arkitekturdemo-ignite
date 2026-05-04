class VerktoyRepository {
    private val store: MutableMap<String, Verktoy> = mutableMapOf()
    fun lagre(verktoy: Verktoy) { store[verktoy.id] = verktoy }
    fun finnMedId(id: String): Verktoy? = store[id]
    fun finnAlle(): List<Verktoy> = store.values.toList()
    fun finnMedNavn(navn: String): List<Verktoy> = store.values.filter { it.navn.contains(navn, ignoreCase = true) }
    fun finnTilgjengelige(): List<Verktoy> = store.values.filter { it.erTilgjengelig() }
    fun slett(id: String) { store.remove(id) }
    fun finnes(id: String): Boolean = store.containsKey(id)
}

