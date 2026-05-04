package brukere.application

import brukere.core.Bruker
import brukere.core.BrukerRepositoryPort
import java.util.UUID

class RegistrerBrukerCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {

    data class Command(val navn: String)

    fun execute(command: Command): BrukerDto {
        val bruker = Bruker(
            id = UUID.randomUUID().toString(),
            navn = command.navn
        )
        brukerRepositoryPort.lagre(bruker)
        return tilDto(bruker)
    }
}

fun tilDto(bruker: Bruker): BrukerDto = BrukerDto(
    id = bruker.id,
    navn = bruker.navn,
    laanteVerktoyIder = bruker.laanteVerktoyIder
)

