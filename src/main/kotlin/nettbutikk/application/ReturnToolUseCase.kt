package nettbutikk.application

import nettbutikk.core.LoanRepository

class ReturnToolUseCase(private val loanRepository: LoanRepository) {

    fun execute(loanId: String): LoanDto {
        val loan = loanRepository.findById(loanId)
            ?: error("Loan with id $loanId not found")

        require(loan.isActive()) { "Loan $loanId is already closed" }

        val returned = loan.returnNow()
        loanRepository.save(returned)
        return toDto(returned)
    }
}
