package application
import core.VerktoyRepository
import core.Verktoy
class SoekVerktoyUseCase(private val verktoyRepository: VerktoyRepository) {
    data class Command(val navn: String? = null, val kunTilgjengelige: Boolean = true)
    fun execute(command: Command): List<VerktoyResultatDto> {
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
    private fun tilResultatDto(verktoy: Verktoy): VerktoyResultatDto = VerktoyResultatDto(
        id = verktoy.id, navn = verktoy.navn, beskrivelse = verktoy.beskrivelse,
        taalerRegn = verktoy.taalerRegn, tilgjengelig = verktoy.erTilgjengelig()
    )
}
