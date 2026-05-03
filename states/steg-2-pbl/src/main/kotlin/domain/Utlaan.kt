package domain
import java.time.Instant
data class Utlaan(
    val id: String,
    val verktoyId: String,
    val brukerId: String,
    val laantVed: Instant,
    val returnertVed: Instant? = null
) {
    fun erAktiv(): Boolean = returnertVed == null
    fun returnerNaa(): Utlaan = copy(returnertVed = Instant.now())
}

