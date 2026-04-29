package nettbutikk.application

import java.time.Instant

data class LoanDto(
    val id: String,
    val toolId: String,
    val userId: String,
    val borrowedAt: Instant,
    val returnedAt: Instant?,
    val active: Boolean
)
