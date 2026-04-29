package nettbutikk.core

import java.time.Instant

data class Loan(
    val id: String,
    val toolId: String,
    val userId: String,
    val borrowedAt: Instant,
    val returnedAt: Instant? = null
) {
    fun isActive(): Boolean = returnedAt == null
    fun returnNow(): Loan = copy(returnedAt = Instant.now())
}
