package application.verktoy
import core.verktoy.Verktoy
import core.verktoy.VerktoyRepositoryPort
import java.util.UUID
class LeggTilVerktoyCommand(private val verktoyRepositoryPort: VerktoyRepositoryPort) {
    data class Command(val navn: String, val beskrivelse: String, val taalerRegn: Boolean)
    fun execute(command: Command): VerktoyDto {
        val verktoy = Verktoy(
            id = UUID.randomUUID().toString(),
            navn = command.navn,
            beskrivelse = command.beskrivelse,
            taalerRegn = command.taalerRegn
        )
        verktoyRepositoryPort.lagre(verktoy)
        return tilVerktoyDto(verktoy)
    }
}
fun tilVerktoyDto(verktoy: Verktoy): VerktoyDto = VerktoyDto(
    id = verktoy.id,
    navn = verktoy.navn,
    beskrivelse = verktoy.beskrivelse,
    taalerRegn = verktoy.taalerRegn,
    tilgjengelig = verktoy.erTilgjengelig(),
    utlaantTilBrukerId = verktoy.utlaantTilBrukerId
)
