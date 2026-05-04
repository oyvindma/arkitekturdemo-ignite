package utlaan.presentation.api

import utlaan.application.LaanVerktoyUseCase
import utlaan.application.ReturnerVerktoyUseCase
import utlaan.application.SoekVerktoyUseCase
import utlaan.application.UtlaanDto
import utlaan.application.VerktoyOppslagDto

class UtlaanController(
    private val soekVerktoyUseCase: SoekVerktoyUseCase,
    private val laanVerktoyUseCase: LaanVerktoyUseCase,
    private val returnerVerktoyUseCase: ReturnerVerktoyUseCase
) {

    fun soekTilgjengeligeVerktoy(navn: String? = null): List<VerktoyOppslagDto> {
        return soekVerktoyUseCase.execute(SoekVerktoyUseCase.Command(navn = navn, kunTilgjengelige = true))
    }

    fun laanVerktoy(verktoyId: String, brukerId: String): LaanVerktoyUseCase.LaanResultat {
        return laanVerktoyUseCase.execute(LaanVerktoyUseCase.Command(verktoyId = verktoyId, brukerId = brukerId))
    }

    fun returnerVerktoy(utlaanId: String): UtlaanDto {
        return returnerVerktoyUseCase.execute(utlaanId)
    }
}

