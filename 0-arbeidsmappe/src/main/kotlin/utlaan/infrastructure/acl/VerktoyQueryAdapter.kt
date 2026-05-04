package utlaan.infrastructure.acl

import utlaan.core.VerktoyQueryPort
import utlaan.core.VerktoyVisning
import verktoy.application.ListVerktoyUseCase
import verktoy.application.VerktoyDto

/**
 * ACL-adapter: oversetter verktoy-kontekstens modell til utlaan-kontekstens VerktoyVisning.
 * Forhindrer at verktoy-kontekstens interne modell lekker inn i utlaan-konteksten.
 */
class VerktoyQueryAdapter(
    private val listVerktoyUseCase: ListVerktoyUseCase
) : VerktoyQueryPort {

    override fun finnTilgjengeligeVerktoy(): List<VerktoyVisning> {
        return listVerktoyUseCase.execute()
            .filter { it.tilgjengelig }
            .map { tilVisning(it) }
    }

    override fun finnVerktoyMedNavn(navn: String): List<VerktoyVisning> {
        return listVerktoyUseCase.execute()
            .filter { it.navn.contains(navn, ignoreCase = true) }
            .map { tilVisning(it) }
    }

    override fun finnVerktoyMedId(id: String): VerktoyVisning? {
        return listVerktoyUseCase.execute()
            .firstOrNull { it.id == id }
            ?.let { tilVisning(it) }
    }

    private fun tilVisning(dto: VerktoyDto): VerktoyVisning = VerktoyVisning(
        id = dto.id,
        navn = dto.navn,
        beskrivelse = dto.beskrivelse,
        taalerRegn = dto.taalerRegn,
        tilgjengelig = dto.tilgjengelig
    )
}

