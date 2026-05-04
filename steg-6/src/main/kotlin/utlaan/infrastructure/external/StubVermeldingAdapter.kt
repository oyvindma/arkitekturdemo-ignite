package utlaan.infrastructure.external

import utlaan.core.Vaermelding
import utlaan.core.VaerPort

class StubVermeldingAdapter : VaerPort {

    override fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}

