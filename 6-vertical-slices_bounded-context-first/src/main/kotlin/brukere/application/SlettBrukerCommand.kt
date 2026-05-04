package brukere.application

import brukere.core.BrukerRepository

class SlettBrukerCommand(private val brukerRepository: BrukerRepository) {

    fun execute(brukerId: String) {
        require(brukerRepository.finnes(brukerId)) { "Bruker med id $brukerId ikke funnet" }
        brukerRepository.slett(brukerId)
    }
}

