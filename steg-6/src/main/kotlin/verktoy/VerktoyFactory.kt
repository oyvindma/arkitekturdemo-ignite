package verktoy

import verktoy.application.LeggTilVerktoyUseCase
import verktoy.application.ListVerktoyUseCase
import verktoy.application.OppdaterVerktoyUseCase
import verktoy.application.SlettVerktoyUseCase
import verktoy.core.VerktoyRepository
import verktoy.infrastructure.database.VerktoyRepositoryAdapter
import verktoy.presentation.api.VerktoyController

object VerktoyFactory {

    fun opprettVerktoyController(verktoyRepository: VerktoyRepository = VerktoyRepositoryAdapter()): VerktoyController {

        val leggTilVerktoyUseCase = LeggTilVerktoyUseCase(verktoyRepository)
        val listVerktoyUseCase = ListVerktoyUseCase(verktoyRepository)
        val slettVerktoyUseCase = SlettVerktoyUseCase(verktoyRepository)
        val oppdaterVerktoyUseCase = OppdaterVerktoyUseCase(verktoyRepository)

        return VerktoyController(
            leggTilVerktoyUseCase = leggTilVerktoyUseCase,
            listVerktoyUseCase = listVerktoyUseCase,
            slettVerktoyUseCase = slettVerktoyUseCase,
            oppdaterVerktoyUseCase = oppdaterVerktoyUseCase
        )
    }

    fun opprettListVerktoyUseCase(verktoyRepository: VerktoyRepository): ListVerktoyUseCase {
        return ListVerktoyUseCase(verktoyRepository)
    }
}

