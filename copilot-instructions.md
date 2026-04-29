Reflecting Architecture and Domain in Code (med mappestruktur)
🎯 Hovedregel

Kode skal organiseres slik at domene → arkitektur → teknologi reflekteres direkte i mappestrukturen.


🧱 Anbefalt overordnet struktur
MermaidNo diagram type detected matching given configuration for text: /src
  /<bounded-context eller feature>
    /application
    /core
    /infrastructure
    /presentation/src  /<bounded-context eller feature>    /application    /core    /infrastructure    /presentationShow more lines
Alternativt (mer eksplisitt per lag):
Plain Text/src  /application  /core  /infrastructure  /presentationShow more lines
👉 Men: Foretrekk feature/bounded context først, deretter lag.

📦 Lag og ansvar
🟦 /core (Domain Layer)

Kjernen av systemet – helt uavhengig av teknologi

Inneholder:

Entities
Value Objects
Domain Services
Aggregates
Domain Events
Repository interfaces (ikke implementasjoner)

Regler:

❌ Ingen avhengigheter til andre lag
✅ Ren forretningslogikk
✅ Domenespråk (ubiquitous language)


🟩 /application

Orkestrerer use-cases

Inneholder:

Use cases / Application services
DTOs
Kommandoer / spørringer (CQRS)
Interfaces til infrastruktur

Regler:

✅ Kan bruke core
❌ Ingen direkte bruk av infrastruktur (kun via interface)
✅ Ingen forretningslogikk som hører hjemme i domain


🟨 /infrastructure

Tekniske implementasjoner

Inneholder:

Database (repositories, ORM)
API-klienter
Filsystem
Messaging
Eksterne integrasjoner

Regler:

✅ Implementerer interfaces fra core/application
❌ Ingen forretningslogikk
✅ Avhenger av alle andre lag


🟥 /presentation

Input/output (UI eller API)

Inneholder:

Controllers / endpoints
Views / UI-komponenter
Request/response mapping

Regler:

✅ Kaller application
❌ Ingen business logic
✅ Tynt lag


🧭 Anbefalt struktur per feature (best praksis)
Plain Text/src  /orders    /core      Order.ts      OrderService.ts    /application      CreateOrderUseCase.ts    /infrastructure      OrderRepositorySql.ts    /presentation      OrderController.ts  /payments    ...Show more lines
👉 Dette gir:

Høy kohesjon
Lav kobling
Klar domenerepresentasjon


⚙️ Arkitekturregler (for KI-agent)
Avhengighetsretning
Plain Textpresentation → application → coreinfrastructure → (implements) → core/applicationShow more lines

core kjenner ingen andre
application kjenner kun core
presentation kjenner kun application
infrastructure kjenner alle, men brukes via interfaces


❌ Hva agenten IKKE skal gjøre
Ikke lag dette:
Plain Text/src  /controllers  /services  /repositoriesShow more lines
Unngå:

Teknologibasert struktur
Spredt domene
Skjult arkitektur


✅ Hva agenten SKAL gjøre

Gruppér kode etter domene/feature først
Reflekter lag (core, application, etc.) inni hver feature
Bruk tydelige navn fra domenet
Håndhev avhengigheter gjennom struktur


🧠 Kort instruks til agent

Lag en kodebase der hver feature inneholder core, application, infrastructure og presentation. Sørg for at domain (core) er uavhengig, application orkestrerer use-cases, infrastructure implementerer detaljer, og presentation kun håndterer input/output. Organiser primært etter domene – ikke teknologi.
