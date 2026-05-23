# Copilot Instructions

This repository now contains an initial Kotlin Multiplatform bootstrap plus `specs.md`. Treat `specs.md` as the source of truth for product intent, and treat the current KMP project structure as the source of truth for build/layout decisions unless later code changes replace it.

If a user gives an explicit stack decision, follow that decision even when it differs from the older technical suggestions inside `specs.md`.

## Product direction

Build toward a mobile-first wine app positioned as "Goodreads for wine" or "Letterboxd for wine," centered on personal memory, taste understanding, discovery, and social interaction.

Do not steer the product toward a Vivino-style scanner-first marketplace. The label scanner is an onboarding and capture mechanism, but the core value is a personal and social memory system for wine lovers.

Prioritize these pillars when proposing features or architecture:

- personal wine journal
- dynamic taste profile
- recommendation engine
- social identity and activity
- insights and yearly recap experiences

## Expected architecture

The chosen application direction is Kotlin Multiplatform. Future implementation work should treat that as the default unless the repository is later restructured around a different stack.

- app stack: Kotlin Multiplatform for shared business logic and data handling
- UI direction: prefer Compose Multiplatform for shared UI where it fits the product, while keeping room for native integrations on Android and iOS
- architectural style: Clean Architecture
- dependency injection: Koin
- local persistence: Room
- navigation: Navigation 3
- mobile scope: design for Android and iOS from the start, since the product is explicitly mobile-first
- backend: keep service contracts platform-neutral; `specs.md` still points toward PostgreSQL plus either a custom backend or Supabase-managed services
- search and AI: keep search, taste profiling, and recommendation capabilities as separate service concerns rather than coupling them to mobile UI code

Prefer the new Kotlin Multiplatform default structure introduced by JetBrains in 2026 instead of the older `composeApp`-centric layout:

- `shared`: shared multiplatform code with a single responsibility
- `androidApp`: Android application module and Android entry point
- `iosApp`: iOS application host consuming the shared code

If the project later mixes shared UI and native UI, prefer splitting the shared layer into `sharedLogic` and `sharedUI` rather than overloading one module with both responsibilities.

Keep Android application setup outside the shared multiplatform module so the project stays compatible with the newer AGP requirements.

When creating initial code, keep boundaries explicit between:

- client app and backend services
- wine catalog data and user-generated tasting data
- recommendation/taste-profile logic and core CRUD flows
- social features and private journal data

Within a Kotlin Multiplatform codebase, also keep boundaries explicit between:

- shared `commonMain` business logic and platform-specific integrations
- domain models/use cases and transport or persistence DTOs
- platform-specific device capabilities such as camera, image recognition, notifications, secure storage, and deep links

## Clean Architecture baseline

Use Clean Architecture as the default project structure from the beginning.

- `domain`: pure business rules, entities, value objects, repository contracts, and use cases
- `data`: repository implementations, local/remote data sources, DTOs, mappers, and persistence/network details
- `presentation`: screen models, UI state, user intents/events, and navigation wiring

Dependency direction should always point inward:

- `presentation` depends on `domain`
- `data` depends on `domain`
- `domain` depends on nothing framework-specific

Do not let Room entities, API payloads, or navigation-specific models leak into `domain`.

## Dependency injection with Koin

- Use Koin modules to declare feature-level wiring and shared platform wiring.
- Prefer constructor injection for repositories, use cases, and presentation models.
- Keep module definitions close to the feature or layer they configure instead of creating an oversized global module.
- Shared KMP modules should expose platform-agnostic contracts; platform modules should provide concrete integrations such as database builders, scanners, or secure storage.

## Local persistence with Room

- Use Room for local-first persistence of user journal data, cached catalog data, and offline-friendly flows.
- Keep Room usage isolated to the `data` layer.
- Map Room entities to domain models through explicit mappers; do not pass Room entities into use cases or UI state.
- Design local storage to support fast wine capture, draft entries, and later sync rather than assuming constant connectivity.

## Navigation with Navigation 3

- Use Navigation 3 as the standard navigation approach between screens.
- Keep navigation decisions in the `presentation` layer.
- Pass stable identifiers and lightweight route arguments rather than full mutable models between destinations.
- Do not mix navigation concerns into domain use cases or repository interfaces.

## Core domain model

The current spec centers on these core entities:

- `User`: profile, bio, avatar, followers/following, taste profile
- `Wine`: winery, name, vintage, region, grapes, tasting profile, image
- `TastingEntry`: user-to-wine journal entry with rating, notes, date, location, pairing, and mood/context
- `Review`: social text, likes, comments
- `List`: favorites, wishlist, region-based lists, custom collections

Keep a clear distinction between canonical wine metadata and a user's personal tasting history.

## Product conventions to preserve

- Optimize for fast capture. The scanning flow should support opening the app, scanning a label, and saving a wine in under 10 seconds.
- Keep journal entry UX frictionless. Advanced fields should stay optional, with a quick-save path available.
- Favor personalization over generic popularity. Recommendations should be framed around palate similarity, prior likes, context, and similar users.
- Preserve the product's social identity. Features should feel closer to Goodreads, Letterboxd, and Untappd than to a transactional wine catalog.
- Keep the tone premium but approachable. The spec emphasizes modern, emotional, elegant UX with strong visual presentation and dark mode support.
- Build for casual-premium wine lovers, not expert-only workflows. Advanced cellar management belongs to later phases unless requirements change.
- Treat emotional memory and taste understanding as higher priority than wine-expert complexity.

## Kotlin Multiplatform conventions

- Put reusable domain rules, validation, recommendation inputs, journal logic, and repository interfaces in shared code first.
- Keep platform-specific code thin and focused on capabilities that genuinely require native APIs, especially camera/scanner flows and OS integrations.
- Do not let Android-only assumptions leak into shared models, navigation concepts, or persistence abstractions.
- If Compose Multiplatform is used, model screen state and presentation logic so it can be shared, but isolate any camera, photo library, or native permission handling behind platform adapters.
- Preserve offline-friendly thinking for tasting capture flows; logging a wine should not depend on a heavy social or recommendation pipeline finishing first.
- If a library choice is not equally viable on every target, hide it behind interfaces so the domain and most presentation logic remain portable.

## Roadmap assumptions from the current spec

If implementation priorities are unclear, default to the MVP sequence defined in `specs.md`:

1. authentication
2. wine scanner
3. journal entries and ratings
4. activity feed
5. user profiles

Recommendations, AI taste profiling, advanced statistics, lists, communities, premium features, and cellar management are later-phase work unless explicitly reprioritized.

## Working in this repository

Current module layout:

- `shared`: shared Kotlin Multiplatform module and shared Compose UI entry surface
- `androidApp`: Android application module and Android entry point
- `iosApp`: Xcode host app consuming the shared framework

Current build, lint, and test commands:

- Android debug build: `./gradlew :androidApp:assembleDebug`
- Shared framework build for iOS simulator: `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`
- Bootstrap verification build: `./gradlew :androidApp:assembleDebug :shared:linkDebugFrameworkIosSimulatorArm64`
- Android lint: `./gradlew :androidApp:lint`
- Android unit tests: `./gradlew :androidApp:testDebugUnitTest`
- Shared multiplatform tests: `./gradlew :shared:allTests`
- Single Android unit test: `./gradlew :androidApp:testDebugUnitTest --tests "com.lasecun.goodwines.YourTestClass"`
- Single shared native test: `./gradlew :shared:iosSimulatorArm64Test --tests "com.lasecun.goodwines.YourTestClass"`
- iOS host build: `xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp -configuration Debug -destination 'generic/platform=iOS Simulator' ARCHS=arm64 ONLY_ACTIVE_ARCH=YES CODE_SIGNING_ALLOWED=NO build`

Repository-specific workflow notes:

- Local Android builds need a valid Android SDK via `local.properties` or `ANDROID_HOME`.
- The iOS host should be built against an `arm64` simulator destination; the shared module currently defines `iosArm64` and `iosSimulatorArm64` targets, not `iosX64`.
- The current app code is intentionally a thin bootstrap surface; Clean Architecture module boundaries, Koin wiring, Room persistence, and Navigation 3 are planned next rather than already implemented.

## Git workflow

- Canonical GitHub repository: `https://github.com/lasecun/goodwines`
- Do all implementation work on a feature branch, not directly on `main`
- Keep the feature branch updated with `rebase` rather than merge commits
- Open pull requests targeting `main`
- When preparing Git guidance, status checks, or release notes, assume `main` is the integration branch
