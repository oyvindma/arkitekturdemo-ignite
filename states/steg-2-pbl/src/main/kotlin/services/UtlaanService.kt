package services
import domain.Utlaan
import repositories.BrukerRepository
import repositories.UtlaanRepository
import repositories.VerktoyRepository
import java.time.Instant
import java.util.UUID

class UtlaanService(
    private val utlaanRepository: UtlaanRepository,
    private val verktoyRepository: VerktoyRepository,
    private val brukerRepository: BrukerRepository,
    private val vaerService: StubVaerService
) {

    data class UtlaanDto(
        val id: String,
        val verktoyId: String,
        val brukerId: String,
        val laantVed: Instant,
        val returnertVed: Instant?,
        val aktiv: Boolean
    )

    data class VerktoyResultatDto(
        val id: String,
        val navn: String,
        val beskrivelse: String,
        val taalerRegn: Boolean,
        val tilgjengelig: Boolean
    )

    sealed class LaanResultat {
        data class Suksess(val utlaan: UtlaanDto) : LaanResultat()
        data class Feil(val grunn: String) : LaanResultat()
    }

    fun soekTilgjengelige(navn: String? = null): List<VerktoyResultatDto> {
        val verktoy = if (navn != null) {
            verktoyRepository.finnMedNavn(navn)
        } else {
            verktoyRepository.finnAlle()
        }
        return verktoy.filter { it.erTilgjengelig() }.map {
            VerktoyResultatDto(
                id = it.id, navn = it.navn, beskrivelse = it.beskrivelse,
                taalerRegn = it.taalerRegn, tilgjengelig = true
            )
        }
    }

    fun laanVerktoy(verktoyId: String, brukerId: String): LaanResultat {
        val verktoy = verktoyRepository.finnMedId(verktoyId)
            ?: return LaanResultat.Feil("Verktoy med id $verktoyId ikke funnet")
        if (!verktoy.erTilgjengelig()) {
            return LaanResultat.Feil("Verktoy '${verktoy.navn}' er ikke tilgjengelig for utlaan")
        }
        if (!brukerRepository.finnes(brukerId)) {
            return LaanResultat.Feil("Bruker med id $brukerId ikke funnet")
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
            verktoyId = verktoyId,
            brukerId = brukerId,
            laantVed = Instant.now()
        )
        utlaanRepository.lagre(utlaan)
        verktoyRepository.lagre(verktoy.laanUt(brukerId))
        return LaanResultat.Suksess(tilDto(utlaan))
    }

    fun returnerVerktoy(utlaanId: String): UtlaanDto {
        val utlaan = utlaanRepository.finnMedId(utlaanId)
            ?: error("Utlaan med id $utlaanId ikke funnet")
        require(utlaan.erAktiv()) { "Utlaan $utlaanId er allerede avsluttet" }
        val returnert = utlaan.returnerNaa()
        utlaanRepository.lagre(returnert)
        val verktoy = verktoyRepository.finnMedId(returnert.verktoyId)
            ?: error("Verktoy med id ${returnert.verktoyId} ikke funnet")
        verktoyRepository.lagre(verktoy.returner())
        return tilDto(returnert)
    }

    private fun tilDto(utlaan: Utlaan): UtlaanDto = UtlaanDto(
        id = utlaan.id, verktoyId = utlaan.verktoyId, brukerId = utlaan.brukerId,
        laantVed = utlaan.laantVed, returnertVed = utlaan.returnertVed, aktiv = utlaan.erAktiv()
    )
}

