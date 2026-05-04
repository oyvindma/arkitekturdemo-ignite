package utlaan.application

import utlaan.core.VerktoyQueryPort
import utlaan.core.VerktoyVisning

class SoekVerktoyCommand(private val verktoyQueryPort: VerktoyQueryPort) {

    data class Command(val navn: String? = null, val kunTilgjengelige: Boolean = true)

    fun execute(command: Command): List<VerktoyResultatDto> {
        val verktoy = if (command.navn != null) {
            verktoyQueryPort.finnVerktoyMedNavn(command.navn)
        } else {
            verktoyQueryPort.finnTilgjengeligeVerktoy()
        }

        val filtrert = if (command.kunTilgjengelige) {
            verktoy.filter { it.tilgjengelig }
        } else {
            verktoy
        }

        return filtrert.map { tilResultatDto(it) }
    }

    private fun tilResultatDto(visning: VerktoyVisning): VerktoyResultatDto = VerktoyResultatDto(
        id = visning.id,
        navn = visning.navn,
        beskrivelse = visning.beskrivelse,
        taalerRegn = visning.taalerRegn,
        tilgjengelig = visning.tilgjengelig
    )
}

