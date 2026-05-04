package verktoy

import verktoy.application.LeggTilVerktoyCommand
import verktoy.application.ListVerktoyCommand
import verktoy.application.OppdaterVerktoyCommand
import verktoy.application.SlettVerktoyCommand
import verktoy.core.VerktoyRepository
import verktoy.infrastructure.database.VerktoyRepositoryAdapter
import verktoy.presentation.api.VerktoyController

object VerktoyFactory {

    fun opprettVerktoyController(verktoyRepository: VerktoyRepository = VerktoyRepositoryAdapter()): VerktoyController {

        val leggTilVerktoyCommand = LeggTilVerktoyCommand(verktoyRepository)
        val listVerktoyCommand = ListVerktoyCommand(verktoyRepository)
        val slettVerktoyCommand = SlettVerktoyCommand(verktoyRepository)
        val oppdaterVerktoyCommand = OppdaterVerktoyCommand(verktoyRepository)

        return VerktoyController(
            leggTilVerktoyCommand = leggTilVerktoyCommand,
            listVerktoyCommand = listVerktoyCommand,
            slettVerktoyCommand = slettVerktoyCommand,
            oppdaterVerktoyCommand = oppdaterVerktoyCommand
        )
    }

    fun opprettListVerktoyUseCase(verktoyRepository: VerktoyRepository): ListVerktoyCommand {
        return ListVerktoyCommand(verktoyRepository)
    }
}

