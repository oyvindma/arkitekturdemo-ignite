package infrastructure.utlaan

import application.verktoy.ListVerktoyCommand
import application.verktoy.VerktoyDto
import core.utlaan.VerktoyQueryPort
import core.utlaan.VerktoyVisning

/**
 * ACL-adapter: oversetter verktoy-modellen til utlaan-kontekstens VerktoyVisning.
 */
class VerktoyQueryAdapter(
    private val listVerktoyCommand: ListVerktoyCommand
) : VerktoyQueryPort {
    override fun finnTilgjengeligeVerktoy(): List<VerktoyVisning> {
        return listVerktoyCommand.execute().filter { it.tilgjengelig }.map { tilVisning(it) }
    }
    override fun finnVerktoyMedNavn(navn: String): List<VerktoyVisning> {
        return listVerktoyCommand.execute().filter { it.navn.contains(navn, ignoreCase = true) }.map { tilVisning(it) }
    }
    override fun finnVerktoyMedId(id: String): VerktoyVisning? {
        return listVerktoyCommand.execute().firstOrNull { it.id == id }?.let { tilVisning(it) }
    }
    private fun tilVisning(dto: VerktoyDto): VerktoyVisning = VerktoyVisning(
        id = dto.id,
        navn = dto.navn,
        beskrivelse = dto.beskrivelse,
        taalerRegn = dto.taalerRegn,
        tilgjengelig = dto.tilgjengelig
    )
}