package utlaan

import brukere.application.HentBrukerCommand
import utlaan.application.LaanVerktoyCommand
import utlaan.application.ReturnerVerktoyCommand
import utlaan.application.SoekVerktoyCommand
import utlaan.infrastructure.acl.BrukerQueryAdapter
import utlaan.infrastructure.acl.VerktoyQueryAdapter
import utlaan.infrastructure.acl.VerktoyStatusAdapter
import utlaan.infrastructure.database.UtlaanRepositoryAdapter
import utlaan.infrastructure.external.StubVaermeldingAdapter
import utlaan.presentation.api.UtlaanController
import verktoy.application.ListVerktoyCommand
import verktoy.core.VerktoyRepositoryPort

object UtlaanFactory {

    fun opprettUtlaanController(
        listVerktoyCommand: ListVerktoyCommand,
        hentBrukerCommand: HentBrukerCommand,
        verktoyRepositoryPort: VerktoyRepositoryPort
    ): UtlaanController {
        val utlaanRepository = UtlaanRepositoryAdapter()
        val vaerPort = StubVaermeldingAdapter()

        val verktoyQueryPort = VerktoyQueryAdapter(listVerktoyCommand)
        val brukerQueryPort = BrukerQueryAdapter(hentBrukerCommand)
        val verktoyStatusPort = VerktoyStatusAdapter(verktoyRepositoryPort)

        val soekVerktoyCommand = SoekVerktoyCommand(verktoyQueryPort)
        val laanVerktoyCommand =
            LaanVerktoyCommand(utlaanRepository, verktoyQueryPort, brukerQueryPort, vaerPort, verktoyStatusPort)
        val returnerVerktoyCommand = ReturnerVerktoyCommand(utlaanRepository, verktoyStatusPort)

        return UtlaanController(
            soekVerktoyCommand = soekVerktoyCommand,
            laanVerktoyCommand = laanVerktoyCommand,
            returnerVerktoyCommand = returnerVerktoyCommand
        )
    }
}

