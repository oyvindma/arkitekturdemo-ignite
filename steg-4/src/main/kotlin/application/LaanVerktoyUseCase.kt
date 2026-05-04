package application
import core.BrukerRepository
import core.Utlaan
import core.UtlaanRepository
import core.VaerService
import core.VerktoyRepository
import java.time.Instant
import java.util.UUID
class LaanVerktoyUseCase(
    private val utlaanRepository: UtlaanRepository,
    private val verktoyRepository: VerktoyRepository,
    private val brukerRepository: BrukerRepository,
    private val vaerService: VaerService
) {
    data class Command(val verktoyId: String, val brukerId: String)
    fun execute(command: Command): LaanResultat {
        val verktoy = verktoyRepository.finnMedId(command.verktoyId)
            ?: return LaanResultat.Feil("Verktoy med id ${command.verktoyId} ikke funnet")
        if (!verktoy.erTilgjengelig()) {
            return LaanResultat.Feil("Verktoy '${verktoy.navn}' er ikke tilgjengelig for utlaan")
        }
        if (!brukerRepository.finnes(command.brukerId)) {
            return LaanResultat.Feil("Bruker med id ${command.brukerId} ikke funnet")
        }
        if (!verktoy.taalerRegn) {
            val varsel = vaerService.hentVarsel()
            if (varsel.blirRegn) {
                return LaanResultat.Feil(
                    "Verktoy '${verktoy.navn}' kan ikke laanes ut: regn er meldt (${varsel.beskrivelse})"
                )
            }
        }
        val utlaan = Utlaan(
            id = UUID.randomUUID().toString(),
            verktoyId = command.verktoyId,
            brukerId = command.brukerId,
            laantVed = Instant.now()
        )
        utlaanRepository.lagre(utlaan)
        verktoyRepository.lagre(verktoy.laanUt(command.brukerId))
        return LaanResultat.Suksess(tilUtlaanDto(utlaan))
    }
    sealed class LaanResultat {
        data class Suksess(val utlaan: UtlaanDto) : LaanResultat()
        data class Feil(val grunn: String) : LaanResultat()
    }
}
fun tilUtlaanDto(utlaan: Utlaan): UtlaanDto = UtlaanDto(
    id = utlaan.id, verktoyId = utlaan.verktoyId, brukerId = utlaan.brukerId,
    laantVed = utlaan.laantVed, returnertVed = utlaan.returnertVed, aktiv = utlaan.erAktiv()
)
