package nettbutikk

import brukere.application.GetUserUseCase
import brukere.core.UserRepository
import nettbutikk.application.BorrowToolUseCase
import nettbutikk.application.ReturnToolUseCase
import nettbutikk.application.SearchToolsUseCase
import nettbutikk.infrastructure.BrukereUserQueryAdapter
import nettbutikk.infrastructure.InMemoryLoanRepository
import nettbutikk.infrastructure.StubWeatherAdapter
import nettbutikk.infrastructure.VerktoyToolQueryAdapter
import nettbutikk.presentation.NettbutikkController
import verktoy.application.ListToolsUseCase
import verktoy.core.ToolRepository

object NettbutikkFactory {

    fun createNettbutikkController(
        toolRepository: ToolRepository,
        userRepository: UserRepository
    ): NettbutikkController {
        val loanRepository = InMemoryLoanRepository()
        val weatherService = StubWeatherAdapter()

        val listToolsUseCase = ListToolsUseCase(toolRepository)
        val toolQueryPort = VerktoyToolQueryAdapter(listToolsUseCase)

        val getUserUseCase = GetUserUseCase(userRepository)
        val userQueryPort = BrukereUserQueryAdapter(getUserUseCase)

        val searchToolsUseCase = SearchToolsUseCase(toolQueryPort)
        val borrowToolUseCase = BorrowToolUseCase(loanRepository, toolQueryPort, userQueryPort, weatherService)
        val returnToolUseCase = ReturnToolUseCase(loanRepository)

        return NettbutikkController(
            searchToolsUseCase = searchToolsUseCase,
            borrowToolUseCase = borrowToolUseCase,
            returnToolUseCase = returnToolUseCase
        )
    }
}
