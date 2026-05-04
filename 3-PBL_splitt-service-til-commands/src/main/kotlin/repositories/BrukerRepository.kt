package repositories

import domain.Bruker

class BrukerRepository {
    private val store: MutableMap<String, Bruker> = mutableMapOf()
    fun lagre(bruker: Bruker) {
        store[bruker.id] = bruker
    }

    fun finnMedId(id: String): Bruker? = store[id]
    fun finnAlle(): List<Bruker> = store.values.toList()
    fun finnMedNavn(navn: String): List<Bruker> = store.values.filter { it.navn.contains(navn, ignoreCase = true) }
    fun slett(id: String) {
        store.remove(id)
    }

    fun finnes(id: String): Boolean = store.containsKey(id)
}