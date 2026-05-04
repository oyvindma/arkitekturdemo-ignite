package utlaan.infrastructure.acl

import brukere.application.BrukerDto
import brukere.application.HentBrukerUseCase
import utlaan.core.BrukerQueryPort
import utlaan.core.BrukerVisning

/**
 * ACL-adapter: oversetter brukere-kontekstens modell til utlaan-kontekstens BrukerVisning.
 * Forhindrer at brukere-kontekstens interne modell lekker inn i utlaan-konteksten.
 */
class BrukerQueryAdapter(
    private val hentBrukerUseCase: HentBrukerUseCase
) : BrukerQueryPort {

    override fun finnBrukerMedId(id: String): BrukerVisning? {
        return try {
            val dto = hentBrukerUseCase.execute(id)
            tilVisning(dto)
        } catch (e: IllegalStateException) {
            null
        }
    }

    override fun brukerFinnes(id: String): Boolean {
        return finnBrukerMedId(id) != null
    }

    private fun tilVisning(dto: BrukerDto): BrukerVisning = BrukerVisning(
        id = dto.id,
        navn = dto.navn
    )
}

