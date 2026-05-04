package brukere.application

import brukere.core.BrukerRepositoryPort

class HentBrukerCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {

    fun execute(brukerId: String): BrukerDto {
        val bruker = brukerRepositoryPort.finnMedId(brukerId)
            ?: error("Bruker med id $brukerId ikke funnet")
        return tilDto(bruker)
    }
}

