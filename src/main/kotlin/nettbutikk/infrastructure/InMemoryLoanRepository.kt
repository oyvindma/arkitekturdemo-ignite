package nettbutikk.infrastructure

import nettbutikk.core.Loan
import nettbutikk.core.LoanRepository

class InMemoryLoanRepository : LoanRepository {

    private val store: MutableMap<String, Loan> = mutableMapOf()

    override fun save(loan: Loan) {
        store[loan.id] = loan
    }

    override fun findById(id: String): Loan? = store[id]

    override fun findActiveByUserId(userId: String): List<Loan> {
        return store.values.filter { it.userId == userId && it.isActive() }
    }

    override fun findActiveByToolId(toolId: String): Loan? {
        return store.values.firstOrNull { it.toolId == toolId && it.isActive() }
    }

    override fun findAll(): List<Loan> = store.values.toList()
}
