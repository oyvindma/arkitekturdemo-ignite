package core.bruker
data class Bruker(
    val id: String,
    val navn: String,
    val laanteVerktoyIder: List<String> = emptyList()
) {
    fun laanVerktoy(verktoyId: String): Bruker = copy(laanteVerktoyIder = laanteVerktoyIder + verktoyId)
    fun returnerVerktoy(verktoyId: String): Bruker = copy(laanteVerktoyIder = laanteVerktoyIder - verktoyId)
}
