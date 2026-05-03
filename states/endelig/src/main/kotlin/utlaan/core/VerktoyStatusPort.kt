package utlaan.core

/** Kommando-port for aa oppdatere verktoy-tilstand ved utlaan/retur. */
interface VerktoyStatusPort {
    fun markerUtlaant(verktoyId: String, brukerId: String)
    fun markerReturnet(verktoyId: String)
}

