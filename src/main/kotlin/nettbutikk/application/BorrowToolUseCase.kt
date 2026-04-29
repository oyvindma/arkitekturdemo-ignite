package nettbutikk.application

import nettbutikk.core.Loan
import nettbutikk.core.LoanRepository
import nettbutikk.core.ToolQueryPort
import nettbutikk.core.UserQueryPort
import nettbutikk.core.WeatherService
import java.time.Instant
import java.util.UUID

class BorrowToolUseCase(
    private val loanRepository: LoanRepository,
    private val toolQueryPort: ToolQueryPort,
    private val userQueryPort: UserQueryPort,
    private val weatherService: WeatherService
) {

    data class Command(val toolId: String, val userId: String)

    fun execute(command: Command): BorrowResult {
        val tool = toolQueryPort.findToolById(command.toolId)
            ?: return BorrowResult.Failure("Tool with id ${command.toolId} not found")

        if (!tool.available) {
            return BorrowResult.Failure("Tool '${tool.name}' is not available for borrowing")
        }

        if (!userQueryPort.userExists(command.userId)) {
            return BorrowResult.Failure("User with id ${command.userId} not found")
        }

        if (!tool.toleratesRain) {
            val forecast = weatherService.getForecast()
            if (forecast.willRain) {
                return BorrowResult.Failure(
                    "Tool '${tool.name}' cannot be borrowed: rain is forecast (${forecast.description})"
                )
            }
        }

        val loan = Loan(
            id = UUID.randomUUID().toString(),
            toolId = command.toolId,
            userId = command.userId,
            borrowedAt = Instant.now()
        )
        loanRepository.save(loan)
        return BorrowResult.Success(toDto(loan))
    }

    sealed class BorrowResult {
        data class Success(val loan: LoanDto) : BorrowResult()
        data class Failure(val reason: String) : BorrowResult()
    }
}

fun toDto(loan: Loan): LoanDto = LoanDto(
    id = loan.id,
    toolId = loan.toolId,
    userId = loan.userId,
    borrowedAt = loan.borrowedAt,
    returnedAt = loan.returnedAt,
    active = loan.isActive()
)
