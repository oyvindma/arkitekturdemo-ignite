package infrastructure.utlaan

import core.utlaan.Vaermelding
import core.utlaan.VaermeldingPort

class StubVaermeldingAdapter : VaermeldingPort {
    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}