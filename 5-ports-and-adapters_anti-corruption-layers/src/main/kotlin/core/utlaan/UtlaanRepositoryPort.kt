package core.utlaan

interface UtlaanRepositoryPort {
    fun lagre(utlaan: Utlaan)
    fun finnMedId(id: String): Utlaan?
    fun finnAktiveMedBrukerId(brukerId: String): List<Utlaan>
    fun finnAktivMedVerktoyId(verktoyId: String): Utlaan?
    fun finnAlle(): List<Utlaan>
}