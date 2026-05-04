package commands.utlaan

data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)
class StubVaermeldingService {
    fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}
