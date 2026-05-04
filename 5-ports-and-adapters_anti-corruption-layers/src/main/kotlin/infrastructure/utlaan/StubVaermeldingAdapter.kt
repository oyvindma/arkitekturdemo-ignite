package infrastructure.utlaan

import core.utlaan.VaermeldingPort
import core.utlaan.Vaermelding

class StubVaermeldingAdapter : VaermeldingPort {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}