# Goodwines 🍷

> *"Goodreads for wine"* — A personal and social memory system for wine lovers.

Goodwines is a mobile-first app that helps you remember wines you loved, understand your personal taste, discover new bottles, and share experiences with people who drink like you.

---

## What it is

Not another wine scanner. Not a marketplace. A **wine identity platform** — think Letterboxd or Goodreads, but for wine.

- 📓 **Personal wine journal** — log tastings with notes, ratings, mood, pairing and location
- 👅 **Dynamic taste profile** — the app learns what you like over time
- 🔍 **Personalised discovery** — recommendations based on your palate, not generic popularity
- 👥 **Social layer** — follow friends, see what they're drinking, share reviews
- 📊 **Yearly recaps** — beautiful insights into your wine year

---

## Tech stack

| Layer | Technology |
|---|---|
| Shared logic & UI | Kotlin Multiplatform + Compose Multiplatform |
| Architecture | Clean Architecture |
| Dependency injection | Koin |
| Local persistence | Room |
| Navigation | Navigation 3 |
| Targets | Android · iOS |
| Backend | Supabase (planned) |

---

## Project structure

```
goodwines/
├── androidApp/          # Android application entry point
├── iosApp/              # Xcode host app (consumes shared framework)
└── shared/              # Kotlin Multiplatform shared code
    └── src/commonMain/kotlin/com/lasecun/goodwines/
        ├── core/
        │   ├── data/source/{local,remote}   # Shared data source contracts
        │   ├── di/                           # Core Koin module
        │   └── presentation/                # App.kt — shared Compose entry point
        ├── features/
        │   ├── wine/      # Wine catalog — domain, data, di
        │   ├── journal/   # Tasting entries — domain, data, di
        │   ├── user/      # User profile & taste profile — domain, data, di
        │   └── social/    # Reviews & lists — domain, di
        └── utils/         # Shared utility helpers
```

### Dependency direction

```
presentation → domain ← data
```

`domain` has zero framework dependencies. `data` and `presentation` depend inward only.

---

## Getting started

### Prerequisites

- Android Studio Meerkat or later
- Xcode 16+
- JDK 17+
- Android SDK (set via `local.properties` or `ANDROID_HOME`)

### Build

```bash
# Android debug APK
./gradlew :androidApp:assembleDebug

# iOS shared framework (arm64 simulator)
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Both
./gradlew :androidApp:assembleDebug :shared:linkDebugFrameworkIosSimulatorArm64
```

### Lint & tests

```bash
# Android lint
./gradlew :androidApp:lint

# Android unit tests
./gradlew :androidApp:testDebugUnitTest

# Shared KMP tests
./gradlew :shared:allTests
```

### iOS host build

```bash
xcodebuild \
  -project iosApp/iosApp.xcodeproj \
  -scheme iosApp \
  -configuration Debug \
  -destination 'generic/platform=iOS Simulator' \
  ARCHS=arm64 ONLY_ACTIVE_ARCH=YES CODE_SIGNING_ALLOWED=NO \
  build
```

---

## Roadmap

| # | Task | Status |
|---|---|---|
| 1 | Bootstrap KMP project | ✅ Done |
| 2 | Clean Architecture module boundaries | ✅ Done |
| 3 | Koin dependency injection | 🔜 Next |
| 4 | Room local persistence | ⬜ |
| 5 | Navigation 3 | ⬜ |
| 6 | Design system & theming | ⬜ |
| 7 | Backend & sync contracts | ⬜ |
| 8 | Offline-first sync strategy | ⬜ |
| 9 | Authentication MVP | ⬜ |
| 10 | Wine journal core | ⬜ |
| 11 | Wine scanner entry flow | ⬜ |
| 12 | User profile MVP | ⬜ |
| 13 | Activity feed MVP | ⬜ |

Full backlog: [GitHub Projects board](https://github.com/users/lasecun/projects/1/views/1)

---

## Contributing

Work happens on feature branches targeting `main`. See open issues for what's next.
