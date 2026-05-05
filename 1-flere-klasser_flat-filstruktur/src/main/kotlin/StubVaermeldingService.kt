class StubVaermeldingService {
    fun hentVarsel(): Vaermelding {
        return Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")
    }
}

