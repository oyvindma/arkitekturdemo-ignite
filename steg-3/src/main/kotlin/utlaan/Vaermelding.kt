package utlaan
data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)
class StubVaerService {
    fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}
