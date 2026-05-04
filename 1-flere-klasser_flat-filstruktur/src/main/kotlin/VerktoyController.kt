class VerktoyController(private val verktoyService: VerktoyService) {
    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyService.VerktoyDto =
        verktoyService.leggTil(navn, beskrivelse, taalerRegn)
    fun listVerktoy(): List<VerktoyService.VerktoyDto> = verktoyService.list()
    fun slettVerktoy(verktoyId: String) = verktoyService.slett(verktoyId)
    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyService.VerktoyDto =
        verktoyService.oppdater(verktoyId, navn, beskrivelse, taalerRegn)
}

