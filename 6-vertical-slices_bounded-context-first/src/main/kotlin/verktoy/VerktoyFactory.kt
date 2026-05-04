package verktoy

import verktoy.application.LeggTilVerktoyCommand
import verktoy.application.ListVerktoyCommand
import verktoy.application.OppdaterVerktoyCommand
import verktoy.application.SlettVerktoyCommand
import verktoy.core.VerktoyRepositoryPort
import verktoy.infrastructure.database.VerktoyRepositoryAdapter
import verktoy.presentation.api.VerktoyController

object VerktoyFactory {

    fun opprettVerktoyController(verktoyRepositoryPort: VerktoyRepositoryPort = VerktoyRepositoryAdapter()): VerktoyController {

        val leggTilVerktoyCommand = LeggTilVerktoyCommand(verktoyRepositoryPort)
        val listVerktoyCommand = ListVerktoyCommand(verktoyRepositoryPort)
        val slettVerktoyCommand = SlettVerktoyCommand(verktoyRepositoryPort)
        val oppdaterVerktoyCommand = OppdaterVerktoyCommand(verktoyRepositoryPort)

        return VerktoyController(
            leggTilVerktoyCommand = leggTilVerktoyCommand,
            listVerktoyCommand = listVerktoyCommand,
            slettVerktoyCommand = slettVerktoyCommand,
            oppdaterVerktoyCommand = oppdaterVerktoyCommand
        )
    }

    fun opprettListVerktoyUseCase(verktoyRepositoryPort: VerktoyRepositoryPort): ListVerktoyCommand {
        return ListVerktoyCommand(verktoyRepositoryPort)
    }
}

