package application.verktoy

import core.verktoy.VerktoyRepositoryPort

class OppdaterVerktoyCommand(private val verktoyRepositoryPort: VerktoyRepositoryPort) {
    data class Command(val verktoyId: String, val navn: String, val beskrivelse: String, val taalerRegn: Boolean)

    fun execute(command: Command): VerktoyDto {
        val eksisterende = verktoyRepositoryPort.finnMedId(command.verktoyId)
            ?: error("Verktoy med id ${command.verktoyId} ikke funnet")
        val oppdatert = eksisterende.copy(
            navn = command.navn,
            beskrivelse = command.beskrivelse,
            taalerRegn = command.taalerRegn
        )
        verktoyRepositoryPort.lagre(oppdatert)
        return tilVerktoyDto(oppdatert)
    }
}
