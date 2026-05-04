package infrastructure
import core.Vaermelding
import core.VaerPort
class StubVaermeldingAdapter : VaerPort {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}
