package commands.bruker

import domain.Bruker
import repositories.BrukerRepository
import java.util.*

class RegistrerBrukerCommand(private val brukerRepository: BrukerRepository) {
    data class Command(val navn: String)

    fun execute(command: Command): BrukerDto {
        val bruker = Bruker(id = UUID.randomUUID().toString(), navn = command.navn)
        brukerRepository.lagre(bruker)
        return tilBrukerDto(bruker)
    }
}

fun tilBrukerDto(bruker: Bruker): BrukerDto = BrukerDto(
    id = bruker.id, navn = bruker.navn, laanteVerktoyIder = bruker.laanteVerktoyIder
)
