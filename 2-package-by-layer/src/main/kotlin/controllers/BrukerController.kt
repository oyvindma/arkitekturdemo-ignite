package controllers

import services.BrukerService

class BrukerController(private val brukerService: BrukerService) {
    fun registrerBruker(navn: String): BrukerService.BrukerDto = brukerService.registrer(navn)
    fun listBrukere(): List<BrukerService.BrukerDto> = brukerService.list()
    fun hentBruker(brukerId: String): BrukerService.BrukerDto = brukerService.hent(brukerId)
    fun oppdaterBruker(brukerId: String, navn: String): BrukerService.BrukerDto = brukerService.oppdater(brukerId, navn)
    fun slettBruker(brukerId: String) = brukerService.slett(brukerId)
}

