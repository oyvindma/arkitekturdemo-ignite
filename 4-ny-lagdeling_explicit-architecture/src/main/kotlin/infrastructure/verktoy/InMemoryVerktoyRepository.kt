package infrastructure.verktoy

import core.Verktoy
import core.VerktoyRepository

class InMemoryVerktoyRepository : VerktoyRepository {
    private val store: MutableMap<String, Verktoy> = mutableMapOf()
    override fun lagre(verktoy: Verktoy) {
        store[verktoy.id] = verktoy
    }

    override fun finnMedId(id: String): Verktoy? = store[id]
    override fun finnAlle(): List<Verktoy> = store.values.toList()
    override fun finnMedNavn(navn: String): List<Verktoy> =
        store.values.filter { it.navn.contains(navn, ignoreCase = true) }

    override fun finnTilgjengelige(): List<Verktoy> = store.values.filter { it.erTilgjengelig() }
    override fun slett(id: String) {
        store.remove(id)
    }

    override fun finnes(id: String): Boolean = store.containsKey(id)
}