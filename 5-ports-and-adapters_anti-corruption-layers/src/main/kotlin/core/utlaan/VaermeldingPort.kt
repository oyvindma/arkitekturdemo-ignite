package core.utlaan
data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)
interface VaermeldingPort {
    fun hentVarsel(): Vaermelding
}
