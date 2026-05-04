package application

import core.BrukerRepository

class HentBrukerCommand(private val brukerRepository: BrukerRepository) {
    fun execute(brukerId: String): BrukerDto {
        val bruker = brukerRepository.finnMedId(brukerId)
            ?: error("Bruker med id $brukerId ikke funnet")
        return tilBrukerDto(bruker)
    }
}
