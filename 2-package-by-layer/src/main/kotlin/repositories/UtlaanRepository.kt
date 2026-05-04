package repositories

import domain.Utlaan

class UtlaanRepository {
    private val store: MutableMap<String, Utlaan> = mutableMapOf()
    fun lagre(utlaan: Utlaan) {
        store[utlaan.id] = utlaan
    }

    fun finnMedId(id: String): Utlaan? = store[id]
    fun finnAktiveMedBrukerId(brukerId: String): List<Utlaan> =
        store.values.filter { it.brukerId == brukerId && it.erAktiv() }

    fun finnAktivMedVerktoyId(verktoyId: String): Utlaan? =
        store.values.firstOrNull { it.verktoyId == verktoyId && it.erAktiv() }

    fun finnAlle(): List<Utlaan> = store.values.toList()
}

