package nettbutikk.presentation

import nettbutikk.application.BorrowToolUseCase
import nettbutikk.application.LoanDto
import nettbutikk.application.ReturnToolUseCase
import nettbutikk.application.SearchToolsUseCase
import nettbutikk.core.ToolView

class NettbutikkController(
    private val searchToolsUseCase: SearchToolsUseCase,
    private val borrowToolUseCase: BorrowToolUseCase,
    private val returnToolUseCase: ReturnToolUseCase
) {

    fun searchAvailableTools(name: String? = null): List<ToolView> {
        return searchToolsUseCase.execute(SearchToolsUseCase.Query(name = name, availableOnly = true))
    }

    fun borrowTool(toolId: String, userId: String): BorrowToolUseCase.BorrowResult {
        return borrowToolUseCase.execute(BorrowToolUseCase.Command(toolId = toolId, userId = userId))
    }

    fun returnTool(loanId: String): LoanDto {
        return returnToolUseCase.execute(loanId)
    }
}
