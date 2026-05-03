package core
data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)
interface VaerPort {
    fun hentVarsel(): Vaermelding
}
