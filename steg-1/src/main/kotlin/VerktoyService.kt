import java.util.UUID

class VerktoyService(private val verktoyRepository: VerktoyRepository) {

    data class VerktoyDto(
        val id: String,
        val navn: String,
        val beskrivelse: String,
        val taalerRegn: Boolean,
        val tilgjengelig: Boolean,
        val utlaantTilBrukerId: String?
    )

    fun leggTil(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        val verktoy = Verktoy(
            id = UUID.randomUUID().toString(),
            navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn
        )
        verktoyRepository.lagre(verktoy)
        return tilDto(verktoy)
    }

    fun list(): List<VerktoyDto> = verktoyRepository.finnAlle().map { tilDto(it) }

    fun slett(verktoyId: String) {
        val verktoy = verktoyRepository.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        require(verktoy.erTilgjengelig()) { "Kan ikke slette verktoy '${verktoy.navn}' fordi det er utlaant" }
        verktoyRepository.slett(verktoyId)
    }

    fun oppdater(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        val eksisterende = verktoyRepository.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        val oppdatert = eksisterende.copy(navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn)
        verktoyRepository.lagre(oppdatert)
        return tilDto(oppdatert)
    }

    private fun tilDto(verktoy: Verktoy): VerktoyDto = VerktoyDto(
        id = verktoy.id, navn = verktoy.navn, beskrivelse = verktoy.beskrivelse,
        taalerRegn = verktoy.taalerRegn, tilgjengelig = verktoy.erTilgjengelig(),
        utlaantTilBrukerId = verktoy.utlaantTilBrukerId
    )
}

