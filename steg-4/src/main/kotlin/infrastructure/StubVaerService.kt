package infrastructure
import core.Vaermelding
import core.VaerService
class StubVaerService : VaerService {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}
