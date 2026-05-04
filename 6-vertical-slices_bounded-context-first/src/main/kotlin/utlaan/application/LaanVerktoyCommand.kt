package utlaan.application

import utlaan.core.BrukerQueryPort
import utlaan.core.Utlaan
import utlaan.core.UtlaanRepositoryPort
import utlaan.core.VaerPort
import utlaan.core.VerktoyQueryPort
import utlaan.core.VerktoyStatusPort
import java.time.Instant
import java.util.*

class LaanVerktoyCommand(
    private val utlaanRepositoryPort: UtlaanRepositoryPort,
    private val verktoyQueryPort: VerktoyQueryPort,
    private val brukerQueryPort: BrukerQueryPort,
    private val vaerPort: VaerPort,
    private val verktoyStatusPort: VerktoyStatusPort
) {

    data class Command(val verktoyId: String, val brukerId: String)

    fun execute(command: Command): LaanResultat {
        val verktoy = verktoyQueryPort.finnVerktoyMedId(command.verktoyId)
            ?: return LaanResultat.Feil("Verktoy med id ${command.verktoyId} ikke funnet")

        if (!verktoy.tilgjengelig) {
            return LaanResultat.Feil("Verktoy '${verktoy.navn}' er ikke tilgjengelig for utlaan")
        }

        if (!brukerQueryPort.brukerFinnes(command.brukerId)) {
            return LaanResultat.Feil("Bruker med id ${command.brukerId} ikke funnet")
        }

        if (!verktoy.taalerRegn) {
            val varsel = vaerPort.hentVarsel()
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
        utlaanRepositoryPort.lagre(utlaan)
        verktoyStatusPort.markerUtlaant(command.verktoyId, command.brukerId)
        return LaanResultat.Suksess(tilUtlaanDto(utlaan))
    }

    sealed class LaanResultat {
        data class Suksess(val utlaan: UtlaanDto) : LaanResultat()
        data class Feil(val grunn: String) : LaanResultat()
    }
}

fun tilUtlaanDto(utlaan: Utlaan): UtlaanDto = UtlaanDto(
    id = utlaan.id,
    verktoyId = utlaan.verktoyId,
    brukerId = utlaan.brukerId,
    laantVed = utlaan.laantVed,
    returnertVed = utlaan.returnertVed,
    aktiv = utlaan.erAktiv()
)

