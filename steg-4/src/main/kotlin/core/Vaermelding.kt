package core
data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)
interface VaerService {
    fun hentVarsel(): Vaermelding
}
