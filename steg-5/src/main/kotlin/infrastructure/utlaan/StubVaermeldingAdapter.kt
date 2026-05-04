package infrastructure.utlaan

import core.utlaan.VaerPort
import core.utlaan.Vaermelding

class StubVaermeldingAdapter : VaerPort {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}