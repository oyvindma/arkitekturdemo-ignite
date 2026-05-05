package infrastructure

import core.VaermeldingService
import core.Vaermelding

class StubVaermeldingServiceImpl : VaermeldingService {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Sol og klar himmel (stub)")
    }
}