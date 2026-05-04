package commands.bruker

import repositories.BrukerRepository

class OppdaterBrukerCommand(private val brukerRepository: BrukerRepository) {
    data class Command(val brukerId: String, val navn: String)
    fun execute(command: Command): BrukerDto {
        val eksisterende = brukerRepository.finnMedId(command.brukerId)
            ?: error("Bruker med id ${command.brukerId} ikke funnet")
        val oppdatert = eksisterende.copy(navn = command.navn)
        brukerRepository.lagre(oppdatert)
        return tilBrukerDto(oppdatert)
    }
}