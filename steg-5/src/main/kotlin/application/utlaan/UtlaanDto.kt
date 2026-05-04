package application.utlaan
import java.time.Instant
data class UtlaanDto(
    val id: String,
    val verktoyId: String,
    val brukerId: String,
    val laantVed: Instant,
    val returnertVed: Instant?,
    val aktiv: Boolean
)
