package brukere.application

import brukere.core.BrukerRepositoryPort

class SlettBrukerCommand(private val brukerRepositoryPort: BrukerRepositoryPort) {

    fun execute(brukerId: String) {
        require(brukerRepositoryPort.finnes(brukerId)) { "Bruker med id $brukerId ikke funnet" }
        brukerRepositoryPort.slett(brukerId)
    }
}

