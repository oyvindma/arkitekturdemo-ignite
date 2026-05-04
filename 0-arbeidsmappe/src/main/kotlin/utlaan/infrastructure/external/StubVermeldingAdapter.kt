package utlaan.infrastructure.external

import utlaan.core.VaerPort
import utlaan.core.Vaermelding

class StubVermeldingAdapter : VaerPort {

    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}

