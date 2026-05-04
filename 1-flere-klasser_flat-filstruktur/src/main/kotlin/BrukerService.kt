import java.util.UUID

class BrukerService(private val brukerRepository: BrukerRepository) {

    data class BrukerDto(
        val id: String,
        val navn: String,
        val laanteVerktoyIder: List<String>
    )

    fun registrer(navn: String): BrukerDto {
        val bruker = Bruker(id = UUID.randomUUID().toString(), navn = navn)
        brukerRepository.lagre(bruker)
        return tilDto(bruker)
    }

    fun hent(brukerId: String): BrukerDto {
        val bruker = brukerRepository.finnMedId(brukerId)
            ?: error("Bruker med id $brukerId ikke funnet")
        return tilDto(bruker)
    }

    fun list(): List<BrukerDto> = brukerRepository.finnAlle().map { tilDto(it) }

    fun oppdater(brukerId: String, navn: String): BrukerDto {
        val eksisterende = brukerRepository.finnMedId(brukerId)
            ?: error("Bruker med id $brukerId ikke funnet")
        val oppdatert = eksisterende.copy(navn = navn)
        brukerRepository.lagre(oppdatert)
        return tilDto(oppdatert)
    }

    fun slett(brukerId: String) {
        require(brukerRepository.finnes(brukerId)) { "Bruker med id $brukerId ikke funnet" }
        brukerRepository.slett(brukerId)
    }

    private fun tilDto(bruker: Bruker): BrukerDto = BrukerDto(
        id = bruker.id, navn = bruker.navn, laanteVerktoyIder = bruker.laanteVerktoyIder
    )
}

