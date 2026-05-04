package application.verktoy
import core.verktoy.VerktoyRepositoryPort
class SlettVerktoyCommand(private val verktoyRepositoryPort: VerktoyRepositoryPort) {
    fun execute(verktoyId: String) {
        val verktoy = verktoyRepositoryPort.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        require(verktoy.erTilgjengelig()) {
            "Kan ikke slette verktoy '${verktoy.navn}' fordi det er utlaant"
        }
        verktoyRepositoryPort.slett(verktoyId)
    }
}
