package utlaan.infrastructure.database

import utlaan.core.Utlaan
import utlaan.core.UtlaanRepository

class UtlaanRepositoryAdapter : UtlaanRepository {

    private val store: MutableMap<String, Utlaan> = mutableMapOf()

    override fun lagre(utlaan: Utlaan) {
        store[utlaan.id] = utlaan
    }

    override fun finnMedId(id: String): Utlaan? = store[id]

    override fun finnAktiveMedBrukerId(brukerId: String): List<Utlaan> {
        return store.values.filter { it.brukerId == brukerId && it.erAktiv() }
    }

    override fun finnAktivMedVerktoyId(verktoyId: String): Utlaan? {
        return store.values.firstOrNull { it.verktoyId == verktoyId && it.erAktiv() }
    }

    override fun finnAlle(): List<Utlaan> = store.values.toList()
}

