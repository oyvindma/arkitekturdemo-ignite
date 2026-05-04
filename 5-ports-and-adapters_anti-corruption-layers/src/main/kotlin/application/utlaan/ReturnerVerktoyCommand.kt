package application.utlaan
import core.utlaan.UtlaanRepository
import core.verktoy.VerktoyStatusPort
class ReturnerVerktoyCommand(
    private val utlaanRepository: UtlaanRepository,
    private val verktoyStatusPort: VerktoyStatusPort
) {
    fun execute(utlaanId: String): UtlaanDto {
        val utlaan = utlaanRepository.finnMedId(utlaanId)
            ?: error("Utlaan med id $utlaanId ikke funnet")
        require(utlaan.erAktiv()) { "Utlaan $utlaanId er allerede avsluttet" }
        val returnert = utlaan.returnerNaa()
        utlaanRepository.lagre(returnert)
        verktoyStatusPort.markerReturnet(returnert.verktoyId)
        return tilUtlaanDto(returnert)
    }
}
