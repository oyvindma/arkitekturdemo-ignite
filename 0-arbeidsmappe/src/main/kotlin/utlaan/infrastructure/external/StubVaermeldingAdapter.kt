package utlaan.infrastructure.external

import utlaan.core.VaermeldingPort
import utlaan.core.Vaermelding

class StubVaermeldingAdapter : VaermeldingPort {

    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}

