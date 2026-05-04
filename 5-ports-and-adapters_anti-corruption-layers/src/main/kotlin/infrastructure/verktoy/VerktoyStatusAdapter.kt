package infrastructure.verktoy

import core.verktoy.VerktoyRepository
import core.verktoy.VerktoyStatusPort

/**
 * ACL-adapter: oppdaterer verktoy-tilstand ved utlaan/retur.
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