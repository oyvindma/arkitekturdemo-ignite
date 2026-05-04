package utlaan.infrastructure.acl

import utlaan.core.VerktoyStatusPort
import verktoy.core.VerktoyRepositoryPort

/**
 * ACL-adapter: oppdaterer verktoy-tilstand naar utlaan/retur skjer.
 * Bruker VerktoyRepository direkte (via injeksjon fra ApplicationFactory).
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

