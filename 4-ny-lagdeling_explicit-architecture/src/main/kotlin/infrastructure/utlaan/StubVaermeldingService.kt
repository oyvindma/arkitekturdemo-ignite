package infrastructure

import core.Vaermelding
import core.VaerService

class StubVaermeldingService : VaerService {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Sol og klar himmel (stub)")
    }
}