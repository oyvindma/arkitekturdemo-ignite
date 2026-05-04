package brukere.application

import brukere.core.BrukerRepositoryPort

class OppdaterBrukerCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {

    data class Command(val brukerId: String, val navn: String)

    fun execute(command: Command): BrukerDto {
        val eksisterende = brukerRepositoryPort.finnMedId(command.brukerId)
            ?: error("Bruker med id ${command.brukerId} ikke funnet")
        val oppdatert = eksisterende.copy(navn = command.navn)
        brukerRepositoryPort.lagre(oppdatert)
        return tilDto(oppdatert)
    }
}

