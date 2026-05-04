# Plan: Implement All Architecture Steps (BBM → Steg 6)

Eight versions of the same 4 use cases, each demonstrating a different architectural style. Steg 6 (current `states/steg-6/`) is the reference implementation. Each step adds one clear concept.

## Reference: Steg 6 (current code)

**3 bounded contexts:** `brukere/`, `verktoy/`, `utlaan/`

**Entities (same fields in ALL steps):**
- `Bruker(id: String, navn: String, laanteVerktoyIder: List<String>)` — domain methods: `laanVerktoy()`, `returnerVerktoy()`
- `Verktoy(id: String, navn: String, beskrivelse: String, taalerRegn: Boolean, utlaantTilBrukerId: String?)` — domain methods: `laanUt()`, `returner()`, `erTilgjengelig()`
- `Utlaan(id: String, verktoyId: String, brukerId: String, laantVed: Instant, returnertVed: Instant?)` — domain methods: `erAktiv()`, `returnerNaa()`

**4 use cases (same behaviour in ALL steps):**
1. **Registrer bruker** — creates Bruker with UUID, saves to repository
2. **Lån verktøy** — checks: verktøy exists + tilgjengelig, bruker exists, weather if !taalerRegn → creates Utlaan, marks verktøy utlaant
3. **List verktøy** — returns all verktøy with DTO mapping
4. **Slett verktøy** — guard: can't delete if `utlaantTilBrukerId != null`

**Additional operations present in all steps (for completeness):**
- Brukere: hent, list, oppdater, slett
- Verktøy: legg til, oppdater
- Utlån: søk tilgjengelige, returner

---

## Step progression

| Folder                | Name | Key concept | What changes from previous |
|-----------------------|------|-------------|---------------------------|
| `utgangspunkt/`    | Big Ball of Mud | — | 1–3 files, everything mixed |
| `steg-1/`             | Lagdeling | SRP, SoC | Flat files (no packages): separate controller, service, repository per concern |
| `steg-2-pbl/`         | Package-by-layer | Package structure | `controllers/`, `services/`, `repositories/`, `domain/` |
| `steg-2-pbf/`         | Package-by-feature | Feature grouping | `brukere/`, `verktoy/`, `utlaan/` (each has controller+service+repo+entity) |
| `steg-3/`             | Use case split | SRP on service level | God-services → one class per use case. Still PBF. |
| `steg-4/`             | Eksplisitt arkitektur | Onion layers, IoC | Layer-first: `core/`, `application/`, `presentation/`, `infrastructure/` as top packages. Repo interfaces in `core/`. Factory for DI. |
| `steg-5/`             | Ports, adapters & ACL | Context isolation | Port interfaces + ACL adapters. `LaanVerktoyUseCase` depends only on ports, never foreign-context types. |
| `steg-6/` = `endelig/` | Vertikale slices | Bounded context isolation | Slice-first: `brukere/core/`, `verktoy/core/`, `utlaan/core/` — each slice owns its layers |

---

## Detailed structure per step

### `utgangspunkt/` — Big Ball of Mud

```
src/main/kotlin/
  App.kt              ← everything: entities, repo logic (MutableMap inline),
                        controller methods, weather check, all use case logic
```

Possibly 2–3 files but no logical separation. MutableMaps declared as top-level/global. Functions do controller + business logic + persistence in one flow.

### `steg-1/` — Flat layer split

```
src/main/kotlin/
  Bruker.kt
  Verktoy.kt
  Utlaan.kt
  Vaermelding.kt
  BrukerController.kt
  VerktoyController.kt
  UtlaanController.kt
  BrukerService.kt         ← god-service: registrer, hent, list, oppdater, slett
  VerktoyService.kt        ← god-service: leggTil, list, slett, oppdater
  UtlaanService.kt         ← god-service: laanVerktoy, returner, soek + weather check
  BrukerRepository.kt      ← concrete class with MutableMap
  VerktoyRepository.kt     ← concrete class with MutableMap
  UtlaanRepository.kt      ← concrete class with MutableMap
```

No packages. Controllers call services. Services call repositories directly (concrete classes). `UtlaanService` imports `BrukerRepository` and `VerktoyRepository` directly.

### `steg-2-pbl/` — Package-by-layer

```
src/main/kotlin/
  domain/
    Bruker.kt, Verktoy.kt, Utlaan.kt, Vaermelding.kt
  controllers/
    BrukerController.kt, VerktoyController.kt, UtlaanController.kt
  services/
    BrukerService.kt, VerktoyService.kt, UtlaanService.kt
  repositories/
    BrukerRepository.kt, VerktoyRepository.kt, UtlaanRepository.kt
```

Same god-services, same concrete repos, same direct cross-context imports. Just organized into layer packages.

### `steg-2-pbf/` — Package-by-feature

```
src/main/kotlin/
  brukere/
    Bruker.kt, BrukerController.kt, BrukerService.kt, BrukerRepository.kt
  verktoy/
    Verktoy.kt, VerktoyController.kt, VerktoyService.kt, VerktoyRepository.kt
  utlaan/
    Utlaan.kt, Vaermelding.kt, UtlaanController.kt, UtlaanService.kt, UtlaanRepository.kt
```

Same god-services. `UtlaanService` imports `brukere.BrukerRepository` and `verktoy.VerktoyRepository` directly.

### `steg-3/` — Use case split

```
src/main/kotlin/
  brukere/
    Bruker.kt, BrukerRepository.kt, BrukerController.kt
    RegistrerBrukerUseCase.kt, HentBrukerUseCase.kt, ListBrukereUseCase.kt
    OppdaterBrukerUseCase.kt, SlettBrukerUseCase.kt, BrukerDto.kt
  verktoy/
    Verktoy.kt, VerktoyRepository.kt, VerktoyController.kt
    LeggTilVerktoyUseCase.kt, ListVerktoyUseCase.kt, SlettVerktoyUseCase.kt
    OppdaterVerktoyUseCase.kt, VerktoyDto.kt
  utlaan/
    Utlaan.kt, Vaermelding.kt, UtlaanRepository.kt, UtlaanController.kt
    LaanVerktoyUseCase.kt, ReturnerVerktoyUseCase.kt, SoekVerktoyUseCase.kt, UtlaanDto.kt
```

Each use case is its own class. Repos are still concrete classes. `LaanVerktoyUseCase` directly imports `brukere.BrukerRepository` and `verktoy.VerktoyRepository`. No interfaces.

### `steg-4/` — Eksplisitt arkitektur (layer-first)

```
src/main/kotlin/
  core/
    Bruker.kt, BrukerRepository.kt (interface)
    Verktoy.kt, VerktoyRepository.kt (interface)
    Utlaan.kt, UtlaanRepository.kt (interface), Vaermelding.kt
  application/
    RegistrerBrukerUseCase.kt, HentBrukerUseCase.kt, ListBrukereUseCase.kt, ...
    LeggTilVerktoyUseCase.kt, ListVerktoyUseCase.kt, SlettVerktoyUseCase.kt, ...
    LaanVerktoyUseCase.kt, ReturnerVerktoyUseCase.kt, SoekVerktoyUseCase.kt
    BrukerDto.kt, VerktoyDto.kt, UtlaanDto.kt
  presentation/
    BrukerController.kt, VerktoyController.kt, UtlaanController.kt
  infrastructure/
    InMemoryBrukerRepository.kt, InMemoryVerktoyRepository.kt, InMemoryUtlaanRepository.kt
    StubVaerService.kt
  ApplicationFactory.kt
```

Repos are now interfaces in `core/`, with InMemory impls in `infrastructure/`. Factory wires everything. But `LaanVerktoyUseCase` still imports `core.BrukerRepository` and `core.VerktoyRepository` directly — no ACL, no ports.

### `steg-5/` — Ports, adapters & ACL

Same as steg-4 but adds ports and ACL:

```
src/main/kotlin/
  core/
    Bruker.kt, BrukerRepository.kt (interface)
    Verktoy.kt, VerktoyRepository.kt (interface)
    Utlaan.kt, UtlaanRepository.kt (interface)
    BrukerQueryPort.kt (interface + BrukerVisning)      ← NEW
    VerktoyQueryPort.kt (interface + VerktoyVisning)    ← NEW
    VerktoyStatusPort.kt (interface)                     ← NEW
    VaerPort.kt (interface + Vaermelding)               ← NEW
  application/
    (same use cases, but LaanVerktoyUseCase now depends on ports, not BrukerRepository/VerktoyRepository)
  presentation/
    BrukerController.kt, VerktoyController.kt, UtlaanController.kt
  infrastructure/
    InMemoryBrukerRepository.kt, InMemoryVerktoyRepository.kt, InMemoryUtlaanRepository.kt
    BrukerQueryAdapter.kt      ← ACL: translates Bruker → BrukerVisning
    VerktoyQueryAdapter.kt     ← ACL: translates Verktoy → VerktoyVisning
    VerktoyStatusAdapter.kt    ← ACL: write operations on verktoy
    StubVaermeldingAdapter.kt  ← external system stub
  ApplicationFactory.kt
```

`LaanVerktoyUseCase` only depends on `BrukerQueryPort`, `VerktoyQueryPort`, `VaerPort`, `VerktoyStatusPort` — never sees `Bruker` or `BrukerRepository`.

### `steg-6/` = `endelig/` — Vertikale slices

Current code. Each bounded context owns its full layer stack:

```
src/main/kotlin/
  brukere/
    core/         Bruker.kt, BrukerRepository.kt (interface)
    application/  RegistrerBrukerUseCase.kt, HentBrukerUseCase.kt, ...
    presentation/api/  BrukerController.kt
    infrastructure/database/  BrukerRepositoryAdapter.kt
    BrukereFactory.kt
  verktoy/
    core/         Verktoy.kt, VerktoyRepository.kt (interface)
    application/  LeggTilVerktoyUseCase.kt, ListVerktoyUseCase.kt, ...
    presentation/api/  VerktoyController.kt
    infrastructure/database/  VerktoyRepositoryAdapter.kt
    VerktoyFactory.kt
  utlaan/
    core/         Utlaan.kt, UtlaanRepository.kt, BrukerQueryPort.kt, VerktoyQueryPort.kt, VerktoyStatusPort.kt, VaerPort.kt
    application/  LaanVerktoyUseCase.kt, ReturnerVerktoyUseCase.kt, SoekVerktoyUseCase.kt
    presentation/api/  UtlaanController.kt
    infrastructure/
      database/   UtlaanRepositoryAdapter.kt
      acl/        BrukerQueryAdapter.kt, VerktoyQueryAdapter.kt, VerktoyStatusAdapter.kt
      external/   StubVermeldingAdapter.kt
    UtlaanFactory.kt
  ApplicationFactory.kt
```

---

## Implementation order (one session each)

Work backwards from steg-6 (already implemented in `states/steg-6`). Each session removes one architectural concept to produce the simpler prior step.

| Session | Task                                                                   | Input |
|---------|------------------------------------------------------------------------|-------|
| 1 | Restructure `states/` folders if needed, update README.md              | This plan |
| 2 | Implement `steg-5/`                                                    | This plan + steg-6 code (flatten slices into layer-first, keep ports & ACL) |
| 3 | Implement `steg-4/`                                                    | This plan + steg-5 code (remove ports & ACL, LaanVerktoyUseCase uses repos directly) |
| 4 | Implement `steg-3/`                                                    | This plan + steg-4 code (remove interfaces & factory, flatten layers into PBF, keep use case split) |
| 5 | Implement `steg-2-pbf/`                                                | This plan + steg-3 code (collapse use cases back into god-services) |
| 6 | Implement `steg-2-pbl/`                                                | This plan + steg-2-pbf code (reorganize from feature packages to layer packages) |
| 7 | Implement `steg-1/`                                                    | This plan + steg-2-pbl code (remove all packages, flat files) |
| 8 | Implement `utgangspunkt/`                                              | This plan + steg-1 code (merge everything into 1–3 files) |
| 9 | Final review — verify all steps compile, update README, update mindmap | All steps |

## Rules for every session

- **Paste this plan** into the chat at session start
- **Same 4 use cases, same behaviour, same entity fields** across all steps
- **Same validation:** can't delete utlaant verktøy, weather check when `!taalerRegn`, bruker must exist for loan
- **Norwegian domain naming**, English technical terms (`adapter`, `repository`, `controller`, `port`)
- **No extension functions**
- **Pure Kotlin, no frameworks, `MutableMap`-based repos**
- **Each step in `states/<step>/src/main/kotlin/`** — self-contained
- **Do not modify `src/`** (steg-6 reference) — only work in `states/`

