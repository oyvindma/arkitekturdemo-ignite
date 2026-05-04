package utlaan

class UtlaanController(private val utlaanService: UtlaanService) {
    fun soekTilgjengeligeVerktoy(navn: String? = null): List<UtlaanService.VerktoyResultatDto> =
        utlaanService.soekTilgjengelige(navn)
    fun laanVerktoy(verktoyId: String, brukerId: String): UtlaanService.LaanResultat =
        utlaanService.laanVerktoy(verktoyId, brukerId)
    fun returnerVerktoy(utlaanId: String): UtlaanService.UtlaanDto =
        utlaanService.returnerVerktoy(utlaanId)
}

