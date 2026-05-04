package infrastructure.utlaan

import core.Utlaan
import core.UtlaanRepository

class InMemoryUtlaanRepository : UtlaanRepository {
    private val store: MutableMap<String, Utlaan> = mutableMapOf()
    override fun lagre(utlaan: Utlaan) {
        store[utlaan.id] = utlaan
    }

    override fun finnMedId(id: String): Utlaan? = store[id]
    override fun finnAktiveMedBrukerId(brukerId: String): List<Utlaan> =
        store.values.filter { it.brukerId == brukerId && it.erAktiv() }

    override fun finnAktivMedVerktoyId(verktoyId: String): Utlaan? =
        store.values.firstOrNull { it.verktoyId == verktoyId && it.erAktiv() }

    override fun finnAlle(): List<Utlaan> = store.values.toList()
}