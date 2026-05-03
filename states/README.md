# Arkitektursteg

Hver mappe representerer et steg i arkitekturprogresjonen — fra kaotisk til strukturert.
Alle steg implementerer de **samme 4 use casene** slik at man kan sammenligne konsekvensene av ulik struktur.

## De 4 brukstilfellene

| # | Use Case | Kontekst | Hva det demonstrerer |
|---|----------|----------|---------------------|
| 1 | Registrer bruker | brukere | SRP-kontrast (enkel CRUD i god-service vs fokusert use case) |
| 2 | Lån verktøy | utlån | Ports, ACL, SoC, kryssing av bounded contexts, værsjekk |
| 3 | List verktøy | verktøy | Enkel query, DTO-mapping, tilgjengelighet |
| 4 | Slett verktøy | verktøy | Encapsulation, domenelogikk i entitet (låneguard) |

## Steg (implementeringsrekkefølge: endelig → utgangspunkt)

### `endelig/` — Hexagonal med ACL og Ports ✅
- Full ports & adapters
- Anti-corruption layer mellom bounded contexts
- Eksplisitt dependency direction
- **Løser:** Kontekst-isolasjon, byttebare integrasjoner, testbarhet

### `steg-3/` — Use case split (uten ports/ACL)
- En use case per klasse (SRP)
- Pakker etter bounded context
- Direkte kryssreferanser mellom kontekster (ingen ACL)
- **Løser:** SRP, fokuserte klasser, lav kognitiv last
- **Problem:** Kontekster lekker inn i hverandre

### `steg-2-pbf/` — Package-by-feature med god-services
- Pakker etter feature/bounded context (brukere/, verktoy/, utlaan/)
- God-services (én service per feature med alle metoder)
- **Løser:** Funksjonell encapsulation, locality of behaviour
- **Problem:** Servicene vokser, vanskelig å vite konsekvens av endring

### `steg-2-pbl/` — Package-by-layer med god-services
- Pakker etter teknologi (controllers/, services/, repositories/)
- God-services (én service per domene med alle metoder)
- **Løser:** Lik teknologi samlet, lik teststrategi
- **Problem:** Mange filer i samme pakke, ingen funksjonell gruppering

### `steg-1/` — 3-lags arkitektur (Controller / Service / Repository)
- Separert teknologi fra forretningslogikk
- Flat struktur, ingen underpakker
- **Løser:** Lettere å se om noe er teknologi vs forretning
- **Problem:** Ingen organisering utover lag

### `utgangspunkt/` — Big Ball of Mud
- Alt i noen få store filer
- Ingen lagdeling, alt mikset sammen
- **Problem:** Umulig å teste isolert, merge conflicts, uforståelig kode
