package utlaan

import brukere.application.HentBrukerUseCase
import utlaan.application.LaanVerktoyUseCase
import utlaan.application.ReturnerVerktoyUseCase
import utlaan.application.SoekVerktoyUseCase
import utlaan.infrastructure.acl.BrukerQueryAdapter
import utlaan.infrastructure.acl.VerktoyQueryAdapter
import utlaan.infrastructure.acl.VerktoyStatusAdapter
import utlaan.infrastructure.database.UtlaanRepositoryAdapter
import utlaan.infrastructure.external.StubVermeldingAdapter
import utlaan.presentation.api.UtlaanController
import verktoy.application.ListVerktoyUseCase
import verktoy.core.VerktoyRepository

object UtlaanFactory {

    fun opprettUtlaanController(
        listVerktoyUseCase: ListVerktoyUseCase,
        hentBrukerUseCase: HentBrukerUseCase,
        verktoyRepository: VerktoyRepository
    ): UtlaanController {
        val utlaanRepository = UtlaanRepositoryAdapter()
        val vaerPort = StubVermeldingAdapter()

        val verktoyQueryPort = VerktoyQueryAdapter(listVerktoyUseCase)
        val brukerQueryPort = BrukerQueryAdapter(hentBrukerUseCase)
        val verktoyStatusPort = VerktoyStatusAdapter(verktoyRepository)

        val soekVerktoyUseCase = SoekVerktoyUseCase(verktoyQueryPort)
        val laanVerktoyUseCase = LaanVerktoyUseCase(utlaanRepository, verktoyQueryPort, brukerQueryPort, vaerPort, verktoyStatusPort)
        val returnerVerktoyUseCase = ReturnerVerktoyUseCase(utlaanRepository, verktoyStatusPort)

        return UtlaanController(
            soekVerktoyUseCase = soekVerktoyUseCase,
            laanVerktoyUseCase = laanVerktoyUseCase,
            returnerVerktoyUseCase = returnerVerktoyUseCase
        )
    }
}

