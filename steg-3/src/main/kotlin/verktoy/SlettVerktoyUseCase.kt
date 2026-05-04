package verktoy
class SlettVerktoyUseCase(private val verktoyRepository: VerktoyRepository) {
    fun execute(verktoyId: String) {
        val verktoy = verktoyRepository.finnMedId(verktoyId)
            ?: error("Verktoy med id $verktoyId ikke funnet")
        require(verktoy.erTilgjengelig()) { "Kan ikke slette verktoy '${verktoy.navn}' fordi det er utlaant" }
        verktoyRepository.slett(verktoyId)
    }
}
