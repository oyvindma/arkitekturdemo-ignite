import java.time.Instant
import java.util.*

// === Entities ===

data class Bruker(
    val id: String,
    val navn: String,
    val laanteVerktoyIder: List<String> = emptyList()
) {
    fun laanVerktoy(verktoyId: String): Bruker = copy(laanteVerktoyIder = laanteVerktoyIder + verktoyId)
    fun returnerVerktoy(verktoyId: String): Bruker = copy(laanteVerktoyIder = laanteVerktoyIder - verktoyId)
}

data class Verktoy(
    val id: String,
    val navn: String,
    val beskrivelse: String,
    val taalerRegn: Boolean,
    val utlaantTilBrukerId: String? = null
) {
    fun laanUt(brukerId: String): Verktoy = copy(utlaantTilBrukerId = brukerId)
    fun returner(): Verktoy = copy(utlaantTilBrukerId = null)
    fun erTilgjengelig(): Boolean = utlaantTilBrukerId == null
}

data class Utlaan(
    val id: String,
    val verktoyId: String,
    val brukerId: String,
    val laantVed: Instant,
    val returnertVed: Instant? = null
) {
    fun erAktiv(): Boolean = returnertVed == null
    fun returnerNaa(): Utlaan = copy(returnertVed = Instant.now())
}

data class Vaermelding(val blirRegn: Boolean, val beskrivelse: String)

// === Global state (inline repositories) ===

val brukere: MutableMap<String, Bruker> = mutableMapOf()
val verktoy: MutableMap<String, Verktoy> = mutableMapOf()
val utlaan: MutableMap<String, Utlaan> = mutableMapOf()

// === Weather stub ===

fun hentVaervarsel(): Vaermelding = Vaermelding(blirRegn = false, beskrivelse = "Klart vaer (stub)")

// === Bruker operations ===

fun registrerBruker(navn: String): Bruker {
    val bruker = Bruker(id = UUID.randomUUID().toString(), navn = navn)
    brukere[bruker.id] = bruker
    return bruker
}

fun hentBruker(brukerId: String): Bruker {
    return brukere[brukerId] ?: error("Bruker med id $brukerId ikke funnet")
}

fun listBrukere(): List<Bruker> = brukere.values.toList()

fun oppdaterBruker(brukerId: String, navn: String): Bruker {
    val eksisterende = brukere[brukerId] ?: error("Bruker med id $brukerId ikke funnet")
    val oppdatert = eksisterende.copy(navn = navn)
    brukere[oppdatert.id] = oppdatert
    return oppdatert
}

fun slettBruker(brukerId: String) {
    require(brukere.containsKey(brukerId)) { "Bruker med id $brukerId ikke funnet" }
    brukere.remove(brukerId)
}

// === Verktøy operations ===

fun leggTilVerktoy(navn: String, beskrivelse: String, taalerRegn: Boolean): Verktoy {
    val v = Verktoy(id = UUID.randomUUID().toString(), navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn)
    verktoy[v.id] = v
    return v
}

fun listVerktoy(): List<Verktoy> = verktoy.values.toList()

fun slettVerktoy(verktoyId: String) {
    val v = verktoy[verktoyId] ?: error("Verktoy med id $verktoyId ikke funnet")
    require(v.erTilgjengelig()) { "Kan ikke slette verktoy '${v.navn}' fordi det er utlaant" }
    verktoy.remove(verktoyId)
}

fun oppdaterVerktoy(verktoyId: String, navn: String, beskrivelse: String, taalerRegn: Boolean): Verktoy {
    val eksisterende = verktoy[verktoyId] ?: error("Verktoy med id $verktoyId ikke funnet")
    val oppdatert = eksisterende.copy(navn = navn, beskrivelse = beskrivelse, taalerRegn = taalerRegn)
    verktoy[oppdatert.id] = oppdatert
    return oppdatert
}

// === Utlån operations ===

fun soekTilgjengeligeVerktoy(navn: String? = null): List<Verktoy> {
    val alle = if (navn != null) {
        verktoy.values.filter { it.navn.contains(navn, ignoreCase = true) }
    } else {
        verktoy.values.toList()
    }
    return alle.filter { it.erTilgjengelig() }
}

fun laanVerktoy(verktoyId: String, brukerId: String): String {
    val v = verktoy[verktoyId] ?: return "FEIL: Verktoy med id $verktoyId ikke funnet"
    if (!v.erTilgjengelig()) return "FEIL: Verktoy '${v.navn}' er ikke tilgjengelig for utlaan"
    if (!brukere.containsKey(brukerId)) return "FEIL: Bruker med id $brukerId ikke funnet"
    if (!v.taalerRegn) {
        val varsel = hentVaervarsel()
        if (varsel.blirRegn) {
            return "FEIL: Verktoy '${v.navn}' kan ikke laanes ut: regn er meldt (${varsel.beskrivelse})"
        }
    }
    val nyttUtlaan = Utlaan(
        id = UUID.randomUUID().toString(),
        verktoyId = verktoyId,
        brukerId = brukerId,
        laantVed = Instant.now()
    )
    utlaan[nyttUtlaan.id] = nyttUtlaan
    verktoy[verktoyId] = v.laanUt(brukerId)
    brukere[brukerId] = brukere[brukerId]!!.laanVerktoy(verktoyId)
    return "OK: Utlaan opprettet med id ${nyttUtlaan.id}"
}

fun returnerVerktoy(utlaanId: String): String {
    val u = utlaan[utlaanId] ?: error("Utlaan med id $utlaanId ikke funnet")
    require(u.erAktiv()) { "Utlaan $utlaanId er allerede avsluttet" }
    val returnert = u.returnerNaa()
    utlaan[utlaanId] = returnert
    val v = verktoy[returnert.verktoyId] ?: error("Verktoy med id ${returnert.verktoyId} ikke funnet")
    verktoy[returnert.verktoyId] = v.returner()
    brukere[returnert.brukerId] = brukere[returnert.brukerId]!!.returnerVerktoy(returnert.verktoyId)
    return "OK: Verktoy '${v.navn}' returnert"
}

// === Main ===

fun main() {
    println("=== Verktøyflåte (Big Ball of Mud) ===")

    val bruker = registrerBruker("Ola Nordmann")
    println("Registrert bruker: $bruker")

    val hammer = leggTilVerktoy("Hammer", "En god hammer", taalerRegn = true)
    val sag = leggTilVerktoy("Sag", "Håndsag", taalerRegn = false)
    println("Lagt til verktøy: $hammer")
    println("Lagt til verktøy: $sag")

    println("\nTilgjengelige verktøy: ${soekTilgjengeligeVerktoy()}")

    val resultat = laanVerktoy(hammer.id, bruker.id)
    println("\nLån hammer: $resultat")

    println("Verktøy etter lån: ${listVerktoy()}")
    println("Bruker etter lån: ${hentBruker(bruker.id)}")

    val returResultat = returnerVerktoy(utlaan.values.first().id)
    println("\nRetur: $returResultat")

    println("Verktøy etter retur: ${listVerktoy()}")
}

