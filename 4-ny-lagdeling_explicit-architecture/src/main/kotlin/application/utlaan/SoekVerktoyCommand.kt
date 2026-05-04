package application

import application.utlaan.VerktoyOppslagDto
import core.VerktoyRepository
import core.Verktoy

class SoekVerktoyCommand(private val verktoyRepository: VerktoyRepository) {
    data class Command(val navn: String? = null, val kunTilgjengelige: Boolean = true)

    fun execute(command: Command): List<VerktoyOppslagDto> {
        val verktoy = if (command.navn != null) {
            verktoyRepository.finnMedNavn(command.navn)
        } else {
            verktoyRepository.finnAlle()
        }
        val filtrert = if (command.kunTilgjengelige) {
            verktoy.filter { it.erTilgjengelig() }
        } else {
            verktoy
        }
        return filtrert.map { tilResultatDto(it) }
    }

    private fun tilResultatDto(verktoy: Verktoy): VerktoyOppslagDto = VerktoyOppslagDto(
        id = verktoy.id, navn = verktoy.navn, beskrivelse = verktoy.beskrivelse,
        taalerRegn = verktoy.taalerRegn, tilgjengelig = verktoy.erTilgjengelig()
    )
}
