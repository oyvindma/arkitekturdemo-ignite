package infrastructure.bruker
import core.bruker.Bruker
import core.bruker.BrukerRepository
class InMemoryBrukerRepository : BrukerRepository {
    private val store: MutableMap<String, Bruker> = mutableMapOf()
    override fun lagre(bruker: Bruker) { store[bruker.id] = bruker }
    override fun finnMedId(id: String): Bruker? = store[id]
    override fun finnAlle(): List<Bruker> = store.values.toList()
    override fun finnMedNavn(navn: String): List<Bruker> = store.values.filter { it.navn.contains(navn, ignoreCase = true) }
    override fun slett(id: String) { store.remove(id) }
    override fun finnes(id: String): Boolean = store.containsKey(id)
}
