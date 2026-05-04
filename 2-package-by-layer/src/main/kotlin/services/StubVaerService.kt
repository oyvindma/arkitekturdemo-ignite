package services

import domain.Vaermelding

class StubVaerService {
    fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}

