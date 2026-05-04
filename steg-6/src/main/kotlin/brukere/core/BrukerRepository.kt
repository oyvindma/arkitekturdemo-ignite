package brukere.core

interface BrukerRepository {
    fun lagre(bruker: Bruker)
    fun finnMedId(id: String): Bruker?
    fun finnAlle(): List<Bruker>
    fun finnMedNavn(navn: String): List<Bruker>
    fun slett(id: String)
    fun finnes(id: String): Boolean
}

