# Copilot Instructions

## Project Overview

This is a **demo codebase** for an architecture course, implementing a **tool fleet management system** (verktøyflåte) with three bounded contexts. The primary goal is to **illustrate and demonstrate** architectural principles in practice — **Kotlin, no frameworks, no external libraries**.

### Architectural principles this codebase demonstrates

- **Separation of Concerns** — each layer and class has one distinct responsibility; domain logic, orchestration, persistence, and delivery are never mixed
- **Single Responsibility Principle** — each class does one thing; use cases are separate classes, not methods on a service
- **Inversion of Control** — high-level modules (core, application) define interfaces; low-level modules (infrastructure) implement them and are injected in
- **Ports and Adapters** — repository and external-service interfaces in `core`/`application` are the *ports*; `infrastructure` classes are the *adapters*
- **Anti-Corruption Layer** — when translating between bounded contexts or external systems, use explicit mapping/translation classes rather than leaking foreign models into the domain
- **Encapsulation** — domain state is only mutated through domain methods; entities do not expose raw mutable fields

## Architecture

Follow **Explicit Architecture** with a **feature-first (bounded context first), then layer** package structure:

```
src/main/kotlin/
  <feature-or-bounded-context>/
    core/            ← Domain layer (entities, value objects, domain services, repository interfaces)
    application/     ← Use cases, DTOs, commands/queries (CQRS)
    infrastructure/  ← Repository implementations (Map-based), external integrations
    presentation/    ← REST controllers, request/response mapping
```

**Dependency direction (strict):**
```
presentation → application → core
infrastructure → (implements interfaces from) → core / application
```

- `core` has **zero** dependencies on other layers
- `application` depends only on `core`
- `presentation` depends only on `application`
- `infrastructure` depends on all layers but is wired via interfaces

## Bounded Contexts

### verktøy (Tool Administration)
- UC0: Add tool with name, description, and rain tolerance flag
- UC1: List tools and see loan status, including who has borrowed each
- UC2: Delete tool
- UC3: Update tool information

### brukere (User Administration)
- UC0: Register user with name
- UC1: List users
- UC2: View user info and which tools they have borrowed
- UC3: Update user information
- UC4: Delete user

### nettbutikk (Lending)
- UC0: Search available tools
- UC1: Borrow a tool (check against weather forecast)
- UC2: Return a tool

## Key Conventions

- **Kotlin only — no frameworks, no external libraries**
- **Repositories use `MutableMap<String, T>` as the backing store** but must support search and filter operations
- **Repository interfaces are defined in `core`**, implementations live in `infrastructure`
- REST controllers live in `presentation` — they are the only entry point (no front end)
- Package names must reflect the domain, not technology (e.g., `verktøy.core.Tool`, not `repositories.ToolRepository`)
- Prefer Kotlin idioms: `data class` for entities/DTOs, sealed classes for domain events
- Do **not** use extension functions
- **Dependency injection is handled manually via factory methods** — create a factory/wiring class per bounded context (or a top-level application factory) that instantiates infrastructure implementations and injects them into application and presentation classes
- Use **ubiquitous language** from the domain in all naming

## What NOT to do

- Do not create a flat, technology-based structure like `controllers/`, `services/`, `repositories/`
- Do not put business logic in `infrastructure` or `presentation`
- Do not let `core` import from `application`, `infrastructure`, or `presentation`
- Do not use any external libraries or frameworks
- Do not use extension functions
