package application.bruker
import core.bruker.BrukerRepository
class HentBrukerUseCase(private val brukerRepository: BrukerRepository) {
    fun execute(brukerId: String): BrukerDto {
        val bruker = brukerRepository.finnMedId(brukerId)
            ?: error("Bruker med id $brukerId ikke funnet")
        return tilBrukerDto(bruker)
    }
}
