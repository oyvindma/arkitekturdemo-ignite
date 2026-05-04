package commands.bruker

import repositories.BrukerRepository

class SlettBrukerUseCase(private val brukerRepository: BrukerRepository) {
    fun execute(brukerId: String) {
        require(brukerRepository.finnes(brukerId)) { "Bruker med id $brukerId ikke funnet" }
        brukerRepository.slett(brukerId)
    }
}