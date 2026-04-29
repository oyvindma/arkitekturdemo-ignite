package nettbutikk.core

interface LoanRepository {
    fun save(loan: Loan)
    fun findById(id: String): Loan?
    fun findActiveByUserId(userId: String): List<Loan>
    fun findActiveByToolId(toolId: String): Loan?
    fun findAll(): List<Loan>
}
