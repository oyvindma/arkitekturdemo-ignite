package verktoy.application

import verktoy.core.VerktoyRepositoryPort

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

