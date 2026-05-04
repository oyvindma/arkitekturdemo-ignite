# Arkitektursteg

Hver mappe representerer et steg i arkitekturprogresjonen — fra kaotisk til strukturert.
Alle steg implementerer de **samme 4 use casene** slik at man kan sammenligne konsekvensene av ulik struktur.

## De 4 brukstilfellene

| # | Use Case         | Kontekst | Hva det demonstrerer                                         |
|---|------------------|----------|--------------------------------------------------------------|
| 1 | Registrer bruker | brukere  | SRP-kontrast (enkel CRUD i god-service vs fokusert use case) |
| 2 | Lån verktøy      | utlån    | Ports, ACL, SoC, kryssing av bounded contexts, værsjekk      |
| 3 | List verktøy     | verktøy  | Enkel query, DTO-mapping, tilgjengelighet                    |
| 4 | Slett verktøy    | verktøy  | Encapsulation, domenelogikk i entitet (låneguard)            |

## Steg (fra enklest til mest strukturert)

### `0-big-ball-of-mud/` — Big Ball of Mud

- Alt i noen få store filer
- Ingen lagdeling, alt mikset sammen
- **Problem:** Umulig å teste isolert, merge conflicts, uforståelig kode

### `1-flere-klasser_flat-filstruktur/` — Lagdeling (flat filstruktur)

- Separert i controller, service og repository — én fil per klasse
- Flat struktur, ingen underpakker
- **Løser:** Lettere å se om noe er teknologi vs forretningslogikk
- **Problem:** Ingen organisering, alt i samme mappe, god-services

### `2-package-by-feature/` — Package-by-feature

- Pakker etter feature/bounded context (`brukere/`, `verktoy/`, `utlaan/`)
- God-services (én service per feature med alle metoder)
- **Løser:** Funksjonell encapsulation, locality of behaviour
- **Problem:** Servicene vokser, vanskelig å vite konsekvens av endring. Teknologiske bekymringer (f.eks. hvordan
  repositories fungerer) er spredt over alle pakker — endringer i infrastruktur krever endring i hver feature-pakke.
  Vanskelig å sikre konsistens på tvers.

### `2-package-by-layer/` — Package-by-layer

- Pakker etter teknologi (`controllers/`, `services/`, `repositories/`, `domain/`)
- God-services (én service per domene med alle metoder)
- **Løser:** Lik teknologi samlet, lik teststrategi
- **Problem:** Servicene vokser, vanskelig å vite konsekvens av endring. Mange filer i samme pakke. Ingen funksjonell
  gruppering — en endring i utlån krever navigering på tvers av alle lag.

### `3-PBL-splitt-service-til-commands/` — Commmand (use case) split

- En command per klasse (Single Responsibility)
- Pakker etter bounded context
- Direkte kryssreferanser mellom kontekster (ingen ACL)
- Repositories er fortsatt konkrete klasser
- **Løser:** Single Responsibility, fokuserte klasser, lav kognitiv last
- **Problem:** Kontekster lekker inn i hverandre, ingen IoC

### `4-ny-lagdeling_explicit-architecture/` — Eksplisitt arkitektur

- Layer-first pakkestruktur (`core/`, `application/`, `presentation/`, `infrastructure/`)
- Repository-interfaces i `core/`, implementasjoner i `infrastructure/`
- Factory for manuell dependency injection
- **Løser:** Inversion of Control, byttebare implementasjoner
- **Problem:** Ingen kontekst-isolasjon, `LaanVerktoyUseCase` bruker repos direkte

### `5-ports-and-adapters_anti-corruption-layers/` — Ports, adapters & ACL

- Port-interfaces og Anti-Corruption Layer
- `LaanVerktoyUseCase` avhenger kun av ports, aldri foreign-context typer
- Layer-first pakkestruktur med port/adapter-mønster
- **Løser:** Kontekst-isolasjon, byttebare integrasjoner
- **Problem:** Ikke organisert etter bounded context (alt i felles lagpakker)

### `6-vertical-slices_bounded-context-first/` — Vertikale slices (bounded context first)

- Slice-first: `brukere/core/`, `verktoy/core/`, `utlaan/core/`
- Hver bounded context eier hele sin lagstabel
- Full ports & adapters med ACL
- Factory per bounded context + top-level ApplicationFactory
- **Løser:** Full kontekst-isolasjon, team-autonomi, skalerbarhet
