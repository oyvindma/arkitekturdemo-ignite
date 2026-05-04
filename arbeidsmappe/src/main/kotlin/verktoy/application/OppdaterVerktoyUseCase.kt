package verktoy.application

import verktoy.core.VerktoyRepository

class OppdaterVerktoyUseCase(private val verktoyRepository: VerktoyRepository) {

    data class Command(
        val verktoyId: String,
        val navn: String,
        val beskrivelse: String,
        val taalerRegn: Boolean
    )

    fun execute(command: Command): VerktoyDto {
        val eksisterende = verktoyRepository.finnMedId(command.verktoyId)
            ?: error("Verktoy med id ${command.verktoyId} ikke funnet")

        val oppdatert = eksisterende.copy(
            navn = command.navn,
            beskrivelse = command.beskrivelse,
            taalerRegn = command.taalerRegn
        )
        verktoyRepository.lagre(oppdatert)
        return tilDto(oppdatert)
    }
}

