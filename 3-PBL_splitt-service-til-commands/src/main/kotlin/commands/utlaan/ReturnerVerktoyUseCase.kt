package commands.utlaan

import repositories.UtlaanRepository
import repositories.VerktoyRepository

class ReturnerVerktoyUseCase(
    private val utlaanRepository: UtlaanRepository,
    private val verktoyRepository: VerktoyRepository
) {
    fun execute(utlaanId: String): UtlaanDto {
        val utlaan = utlaanRepository.finnMedId(utlaanId)
            ?: error("Utlaan med id $utlaanId ikke funnet")
        require(utlaan.erAktiv()) { "Utlaan $utlaanId er allerede avsluttet" }
        val returnert = utlaan.returnerNaa()
        utlaanRepository.lagre(returnert)
        val verktoy = verktoyRepository.finnMedId(returnert.verktoyId)
            ?: error("Verktoy med id ${returnert.verktoyId} ikke funnet")
        verktoyRepository.lagre(verktoy.returner())
        return tilUtlaanDto(returnert)
    }
}