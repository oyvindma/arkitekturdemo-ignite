package core

data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)
interface VaermeldingService {
    fun hentVarsel(): Vaermelding
}
