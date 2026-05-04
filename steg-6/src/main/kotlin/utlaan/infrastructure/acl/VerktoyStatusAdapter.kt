package utlaan.infrastructure.acl

import utlaan.core.VerktoyStatusPort
import verktoy.core.VerktoyRepository

/**
 * ACL-adapter: oppdaterer verktoy-tilstand naar utlaan/retur skjer.
 * Bruker VerktoyRepository direkte (via injeksjon fra ApplicationFactory).
 */
class VerktoyStatusAdapter(
    private val verktoyRepository: VerktoyRepository
) : VerktoyStatusPort {

    override fun markerUtlaant(verktoyId: String, brukerId: String) {
        val verktoy = verktoyRepository.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        verktoyRepository.lagre(verktoy.laanUt(brukerId))
    }

    override fun markerReturnet(verktoyId: String) {
        val verktoy = verktoyRepository.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        verktoyRepository.lagre(verktoy.returner())
    }
}

