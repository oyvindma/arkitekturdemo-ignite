package infrastructure

import core.VaerService
import core.Vaermelding

class StubVaermeldingService : VaerService {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Sol og klar himmel (stub)")
    }
}