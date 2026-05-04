package commands.verktoy
import domain.Verktoy
import repositories.VerktoyRepository
import java.util.UUID
class LeggTilVerktoyUseCase(private val verktoyRepository: VerktoyRepository) {
    data class Command(val navn: String, val beskrivelse: String, val taalerRegn: Boolean)
    fun execute(command: Command): VerktoyDto {
        val verktoy = Verktoy(
            id = UUID.randomUUID().toString(),
            navn = command.navn, beskrivelse = command.beskrivelse, taalerRegn = command.taalerRegn
        )
        verktoyRepository.lagre(verktoy)
        return tilVerktoyDto(verktoy)
    }
}
fun tilVerktoyDto(verktoy: Verktoy): VerktoyDto = VerktoyDto(
    id = verktoy.id, navn = verktoy.navn, beskrivelse = verktoy.beskrivelse,
    taalerRegn = verktoy.taalerRegn, tilgjengelig = verktoy.erTilgjengelig(),
    utlaantTilBrukerId = verktoy.utlaantTilBrukerId
)
