package brukere.application

import brukere.core.Bruker
import brukere.core.BrukerRepository
import java.util.UUID

class RegistrerBrukerUseCase(private val brukerRepository: BrukerRepository) {

    data class Command(val navn: String)

    fun execute(command: Command): BrukerDto {
        val bruker = Bruker(
            id = UUID.randomUUID().toString(),
            navn = command.navn
        )
        brukerRepository.lagre(bruker)
        return tilDto(bruker)
    }
}

fun tilDto(bruker: Bruker): BrukerDto = BrukerDto(
    id = bruker.id,
    navn = bruker.navn,
    laanteVerktoyIder = bruker.laanteVerktoyIder
)

