package application.utlaan
import core.utlaan.UtlaanRepositoryPort
import core.verktoy.VerktoyStatusPort
class ReturnerVerktoyCommand(
    private val utlaanRepositoryPort: UtlaanRepositoryPort,
    private val verktoyStatusPort: VerktoyStatusPort
) {
    fun execute(utlaanId: String): UtlaanDto {
        val utlaan = utlaanRepositoryPort.finnMedId(utlaanId)
            ?: error("Utlaan med id $utlaanId ikke funnet")
        require(utlaan.erAktiv()) { "Utlaan $utlaanId er allerede avsluttet" }
        val returnert = utlaan.returnerNaa()
        utlaanRepositoryPort.lagre(returnert)
        verktoyStatusPort.markerReturnet(returnert.verktoyId)
        return tilUtlaanDto(returnert)
    }
}
