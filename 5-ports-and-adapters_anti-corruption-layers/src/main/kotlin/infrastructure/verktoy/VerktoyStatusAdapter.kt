package infrastructure.verktoy

import core.verktoy.VerktoyRepositoryPort
import core.verktoy.VerktoyStatusPort

/**
 * ACL-adapter: oppdaterer verktoy-tilstand ved utlaan/retur.
 */
class VerktoyStatusAdapter(
    private val verktoyRepositoryPort: VerktoyRepositoryPort
) : VerktoyStatusPort {
    override fun markerUtlaant(verktoyId: String, brukerId: String) {
        val verktoy = verktoyRepositoryPort.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        verktoyRepositoryPort.lagre(verktoy.laanUt(brukerId))
    }

    override fun markerReturnet(verktoyId: String) {
        val verktoy = verktoyRepositoryPort.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        verktoyRepositoryPort.lagre(verktoy.returner())
    }
}