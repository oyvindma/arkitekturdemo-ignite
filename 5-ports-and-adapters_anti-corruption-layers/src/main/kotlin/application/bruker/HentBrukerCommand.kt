package application.bruker

import core.bruker.BrukerRepositoryPort

class HentBrukerCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {
    fun execute(brukerId: String): BrukerDto {
        val bruker = brukerRepositoryPort.finnMedId(brukerId)
            ?: error("Bruker med id $brukerId ikke funnet")
        return tilBrukerDto(bruker)
    }
}
