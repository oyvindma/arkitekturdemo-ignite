# Copilot Instructions

## What This Codebase Is

A **teaching codebase** for an architecture course. It contains **multiple steps** (`steg-0` through `steg-6`, plus `arbeidsmappe`) that each implement the **same use cases** at different levels of architectural maturity. See `README.md` for the progression.

**Each step is intentionally different.** Do not "fix" an earlier step to match a later one — the architectural flaws are the point.

## Tech Stack

- **Kotlin only** — no frameworks, no external libraries
- Maven multi-module project (one module per step)
- Repositories use `MutableMap<String, T>` as backing store
- No database, no HTTP framework — controllers are plain classes

## Domain (ubiquitous language)

The domain is a **tool fleet management system** (verktøyflåte). Three bounded contexts:

| Context | Norwegian name | What it does |
|---------|------------|-------------|
| Tool admin | verktøy | Add, list, update, delete tools |
| User admin | brukere | Register, list, view, update, delete users |
| Lending | utlån | Search tools, borrow (with weather check), return |

Use Norwegian domain terms in code: `Verktoy`, `Bruker`, `Utlaan`, `Vaermelding`, etc.

## When editing a specific step

**Respect that step's architectural level:**

- `steg-0`: Everything in one file. Keep it messy.
- `steg-1`: Flat file structure, one class per file. No packages.
- `steg-2-pbl`: Package-by-layer (`controllers/`, `services/`, `repositories/`). God-services.
- `steg-2-pbf`: Package-by-feature (`brukere/`, `verktoy/`, `utlaan/`). God-services.
- `steg-3`: One use case per class. No interfaces, no IoC.
- `steg-4`: Layer sub-packages (`core/`, `application/`, `infrastructure/`, `presentation/`). Repository interfaces. Manual DI via factories.
- `steg-5`: Ports, adapters, ACL — but flat (not grouped by bounded context).
- `steg-6`: Full vertical slices. Bounded context first, then layers. Ports, adapters, ACL, factory per context.
- `arbeidsmappe`: Workshop scratch space for students. Follow `steg-6` conventions unless told otherwise.

## Conventions (for steg-4 through steg-6)

- `core` has **zero** dependencies on other layers
- `application` depends only on `core`
- `presentation` depends only on `application`
- `infrastructure` implements interfaces from `core`/`application`
- Use cases are separate classes, not methods on a service
- Manual dependency injection via factory classes
- `data class` for entities/DTOs, `sealed class` for result types
- Do **not** use extension functions
- Package names reflect domain, not technology

## What NOT to do

- Do not apply steg-6 patterns to earlier steps
- Do not add external libraries or frameworks
- Do not use extension functions
- Do not put business logic in infrastructure or presentation
