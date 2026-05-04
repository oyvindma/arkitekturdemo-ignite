package verktoy.presentation.api

import verktoy.application.LeggTilVerktoyUseCase
import verktoy.application.ListVerktoyUseCase
import verktoy.application.OppdaterVerktoyUseCase
import verktoy.application.SlettVerktoyUseCase
import verktoy.application.VerktoyDto

class VerktoyController(
    private val leggTilVerktoyUseCase: LeggTilVerktoyUseCase,
    private val listVerktoyUseCase: ListVerktoyUseCase,
    private val slettVerktoyUseCase: SlettVerktoyUseCase,
    private val oppdaterVerktoyUseCase: OppdaterVerktoyUseCase
) {

    fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        val command = LeggTilVerktoyUseCase.Command(
            navn = navn,
            beskrivelse = beskrivelse,
            taalerRegn = taalerRegn
        )
        return leggTilVerktoyUseCase.execute(command)
    }

    fun listVerktoy(): List<VerktoyDto> {
        return listVerktoyUseCase.execute()
    }

    fun slettVerktoy(verktoyId: String) {
        slettVerktoyUseCase.execute(verktoyId)
    }

    fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): VerktoyDto {
        val command = OppdaterVerktoyUseCase.Command(
            verktoyId = verktoyId,
            navn = navn,
            beskrivelse = beskrivelse,
            taalerRegn = taalerRegn
        )
        return oppdaterVerktoyUseCase.execute(command)
    }
}

