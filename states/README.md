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

## Steg (fra enklest til mest strukturert)

### `utgangspunkt/` — Big Ball of Mud
- Alt i noen få store filer
- Ingen lagdeling, alt mikset sammen
- **Problem:** Umulig å teste isolert, merge conflicts, uforståelig kode

### `steg-1/` — Lagdeling (flat filstruktur)
- Separert i controller, service og repository — én fil per klasse
- Flat struktur, ingen underpakker
- **Løser:** Lettere å se om noe er teknologi vs forretning
- **Problem:** Ingen organisering utover lag, god-services

### `steg-2-pbl/` — Package-by-layer
- Pakker etter teknologi (`controllers/`, `services/`, `repositories/`, `domain/`)
- God-services (én service per domene med alle metoder)
- **Løser:** Lik teknologi samlet, lik teststrategi
- **Problem:** Mange filer i samme pakke, ingen funksjonell gruppering

### `steg-2-pbf/` — Package-by-feature
- Pakker etter feature/bounded context (`brukere/`, `verktoy/`, `utlaan/`)
- God-services (én service per feature med alle metoder)
- **Løser:** Funksjonell encapsulation, locality of behaviour
- **Problem:** Servicene vokser, vanskelig å vite konsekvens av endring

### `steg-3/` — Use case split
- En use case per klasse (SRP)
- Pakker etter bounded context
- Direkte kryssreferanser mellom kontekster (ingen ACL)
- Repositories er fortsatt konkrete klasser
- **Løser:** SRP, fokuserte klasser, lav kognitiv last
- **Problem:** Kontekster lekker inn i hverandre, ingen IoC

### `steg-4/` — Eksplisitt arkitektur
- Layer-first pakkestruktur (`core/`, `application/`, `presentation/`, `infrastructure/`)
- Repository-interfaces i `core/`, implementasjoner i `infrastructure/`
- Factory for manuell dependency injection
- **Løser:** Inversion of Control, byttebare implementasjoner
- **Problem:** Ingen kontekst-isolasjon, `LaanVerktoyUseCase` bruker repos direkte

### `steg-5/` — Ports, adapters & ACL
- Port-interfaces og Anti-Corruption Layer
- `LaanVerktoyUseCase` avhenger kun av ports, aldri foreign-context typer
- Layer-first pakkestruktur med port/adapter-mønster
- **Løser:** Kontekst-isolasjon, byttebare integrasjoner
- **Problem:** Ikke organisert etter bounded context (alt i felles lagpakker)

### `steg-6/` — Vertikale slices (bounded context first)
- Slice-first: `brukere/core/`, `verktoy/core/`, `utlaan/core/`
- Hver bounded context eier hele sin lagstabel
- Full ports & adapters med ACL
- Factory per bounded context + top-level ApplicationFactory
- **Løser:** Full kontekst-isolasjon, team-autonomi, skalerbarhet
