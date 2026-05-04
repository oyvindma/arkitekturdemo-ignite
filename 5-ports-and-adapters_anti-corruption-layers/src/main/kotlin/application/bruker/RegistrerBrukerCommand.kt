package application.bruker
import core.bruker.Bruker
import core.bruker.BrukerRepositoryPort
import java.util.UUID
class RegistrerBrukerCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {
    data class Command(val navn: String)
    fun execute(command: Command): BrukerDto {
        val bruker = Bruker(
            id = UUID.randomUUID().toString(),
            navn = command.navn
        )
        brukerRepositoryPort.lagre(bruker)
        return tilBrukerDto(bruker)
    }
}
fun tilBrukerDto(bruker: Bruker): BrukerDto = BrukerDto(
    id = bruker.id,
    navn = bruker.navn,
    laanteVerktoyIder = bruker.laanteVerktoyIder
)
