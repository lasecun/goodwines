# Goodwines Backend Specification

> **For agent use:** This document is the authoritative implementation spec for the Goodwines backend service. Follow it top to bottom. Each section has concrete code examples, file paths, and acceptance criteria. Do not deviate from the tech stack or architecture decisions without updating this spec first.

---

## 1. Tech Stack Decision

| Concern | Choice | Rationale |
|---|---|---|
| Language | **Kotlin** | Full-Kotlin stack matches the KMP mobile client |
| Framework | **Ktor 3.x** | Idiomatic Kotlin, coroutines-native, lightweight; Spring Boot is Java-first and too heavy for this service |
| API layer | **GraphQL (graphql-kotlin)** | Already agreed client-side; Expedia's `graphql-kotlin` is the de-facto Kotlin GraphQL library |
| Database driver | **MongoDB Kotlin driver** (official, coroutines) | Official driver, async-first, KotlinX Serialization support |
| Database | **MongoDB Atlas** | Provided by the team; managed service, no infra to run |
| Auth | **JWT (HMAC-SHA256)** via `ktor-server-auth-jwt` | Stateless, mobile-friendly, easy to verify on the client |
| DI | **Koin** | Already used in the KMP client; consistent across the stack |
| Containerisation | **Docker + docker-compose** | Single command local dev; deployable to any container host |
| Build tool | **Gradle (Kotlin DSL)** | Consistent with the mobile project |
| Testing | **kotlin.test + MockK + Testcontainers (MongoDB)** | Idiomatic Kotlin testing |

---

## 2. Repository Layout

The backend lives in a `backend/` subdirectory of this monorepo.

```
backend/
├── build.gradle.kts
├── settings.gradle.kts
├── Dockerfile
├── docker-compose.yml
├── .env.example
└── src/
    ├── main/
    │   └── kotlin/
    │       └── com/lasecun/goodwines/
    │           ├── Application.kt          ← entry point
    │           ├── di/
    │           │   └── AppModules.kt       ← Koin module wiring
    │           ├── config/
    │           │   ├── AppConfig.kt        ← typed env config
    │           │   └── DatabaseConfig.kt
    │           ├── domain/
    │           │   ├── model/              ← pure domain entities
    │           │   │   ├── User.kt
    │           │   │   ├── Wine.kt
    │           │   │   ├── TastingEntry.kt
    │           │   │   └── AuthSession.kt
    │           │   └── repository/         ← repository interfaces
    │           │       ├── UserRepository.kt
    │           │       ├── WineRepository.kt
    │           │       └── TastingEntryRepository.kt
    │           ├── data/
    │           │   ├── document/           ← MongoDB documents (DTOs)
    │           │   │   ├── UserDocument.kt
    │           │   │   ├── WineDocument.kt
    │           │   │   └── TastingEntryDocument.kt
    │           │   ├── mapper/             ← document ↔ domain mappers
    │           │   └── repository/         ← repository implementations
    │           │       ├── UserRepositoryImpl.kt
    │           │       ├── WineRepositoryImpl.kt
    │           │       └── TastingEntryRepositoryImpl.kt
    │           ├── auth/
    │           │   ├── JwtService.kt       ← sign + verify tokens
    │           │   ├── PasswordService.kt  ← bcrypt hashing
    │           │   └── AuthUseCase.kt      ← sign-in / register logic
    │           └── api/
    │               ├── graphql/
    │               │   ├── schema/         ← graphql-kotlin schema types
    │               │   │   ├── UserSchema.kt
    │               │   │   ├── WineSchema.kt
    │               │   │   └── TastingEntrySchema.kt
    │               │   ├── query/
    │               │   │   ├── UserQuery.kt
    │               │   │   ├── WineQuery.kt
    │               │   │   └── TastingEntryQuery.kt
    │               │   ├── mutation/
    │               │   │   ├── AuthMutation.kt
    │               │   │   ├── WineMutation.kt
    │               │   │   └── TastingEntryMutation.kt
    │               │   └── context/
    │               │       └── AuthContext.kt  ← inject current user into resolvers
    │               └── plugin/
    │                   ├── GraphQL.kt      ← graphql-kotlin Ktor plugin setup
    │                   └── Authentication.kt
    └── test/
        └── kotlin/
            └── com/lasecun/goodwines/
                ├── auth/
                │   └── AuthUseCaseTest.kt
                ├── api/
                │   └── TastingEntryMutationTest.kt
                └── data/
                    └── TastingEntryRepositoryTest.kt
```

---

## 3. Dependencies (`build.gradle.kts`)

```kotlin
val ktorVersion = "3.1.3"
val graphqlKotlinVersion = "8.3.0"
val mongoDriverVersion = "5.3.0"
val koinVersion = "4.0.3"
val jwtVersion = "4.4.0"
val bcryptVersion = "0.10.2"
val logbackVersion = "1.4.14"
val mockkVersion = "1.13.12"
val testcontainersVersion = "1.20.4"

dependencies {
    // Ktor server
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")

    // GraphQL
    implementation("com.expediagroup:graphql-kotlin-ktor-server:$graphqlKotlinVersion")

    // MongoDB
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:$mongoDriverVersion")
    implementation("org.mongodb:bson-kotlinx:$mongoDriverVersion")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    // JWT + password hashing
    implementation("com.auth0:java-jwt:$jwtVersion")
    implementation("at.favre.lib:bcrypt:$bcryptVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Test
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.testcontainers:mongodb:$testcontainersVersion")
    testImplementation(kotlin("test"))
}
```

---

## 4. MongoDB Collections & Document Schemas

### 4.1 `users`

```kotlin
@Serializable
data class UserDocument(
    @BsonId val id: ObjectId = ObjectId(),
    val email: String,
    val passwordHash: String,
    val username: String,
    val displayName: String,
    val bio: String? = null,
    val avatarUrl: String? = null,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val tasteProfile: TasteProfileDocument? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
data class TasteProfileDocument(
    val preferredStyles: List<String> = emptyList(),   // e.g. "bold_red", "crisp_white"
    val preferredRegions: List<String> = emptyList(),
    val preferredGrapes: List<String> = emptyList(),
    val avgRating: Double? = null,
    val totalEntries: Int = 0
)
```

**Indexes:**
- `email` → unique
- `username` → unique

### 4.2 `wines`

```kotlin
@Serializable
data class WineDocument(
    @BsonId val id: ObjectId = ObjectId(),
    val name: String,
    val winery: String,
    val vintage: Int?,
    val region: String,
    val country: String,
    val grapes: List<String> = emptyList(),
    val style: String,           // "red", "white", "rosé", "sparkling", "dessert"
    val abv: Double?,
    val description: String? = null,
    val labelImageUrl: String? = null,
    val avgRating: Double? = null,
    val ratingCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
```

**Indexes:**
- `name + winery + vintage` → compound (for dedup)
- `name` → text index (for search)
- `region`, `country`, `grapes` → for filter queries

### 4.3 `tasting_entries`

```kotlin
@Serializable
data class TastingEntryDocument(
    @BsonId val id: ObjectId = ObjectId(),
    val userId: String,
    val wineId: String,
    val rating: Double,          // 0.5–5.0 in 0.5 increments
    val notes: String? = null,
    val location: String? = null,
    val pairing: String? = null,
    val mood: String? = null,
    val isPublic: Boolean = true,
    val isDraft: Boolean = false,
    val tastedAt: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
```

**Indexes:**
- `userId` → for fetching user's journal
- `wineId` → for fetching ratings per wine
- `userId + wineId` → compound (to prevent duplicates if needed)

### 4.4 `follows`

```kotlin
@Serializable
data class FollowDocument(
    @BsonId val id: ObjectId = ObjectId(),
    val followerId: String,
    val followingId: String,
    val createdAt: Long = System.currentTimeMillis()
)
```

**Indexes:**
- `followerId + followingId` → unique compound

### 4.5 `lists`

```kotlin
@Serializable
data class ListDocument(
    @BsonId val id: ObjectId = ObjectId(),
    val userId: String,
    val name: String,           // e.g. "Wishlist", "Favorites"
    val description: String? = null,
    val wineIds: List<String> = emptyList(),
    val isPublic: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
```

---

## 5. GraphQL Schema

Using `graphql-kotlin` **code-first** approach (types defined in Kotlin, schema generated automatically).

### 5.1 Types

```kotlin
// UserSchema.kt
data class UserType(
    val id: String,
    val username: String,
    val displayName: String,
    val bio: String?,
    val avatarUrl: String?,
    val followerCount: Int,
    val followingCount: Int,
    val tasteProfile: TasteProfileType?
)

data class TasteProfileType(
    val preferredStyles: List<String>,
    val preferredRegions: List<String>,
    val preferredGrapes: List<String>,
    val avgRating: Double?,
    val totalEntries: Int
)

// WineSchema.kt
data class WineType(
    val id: String,
    val name: String,
    val winery: String,
    val vintage: Int?,
    val region: String,
    val country: String,
    val grapes: List<String>,
    val style: String,
    val abv: Double?,
    val description: String?,
    val labelImageUrl: String?,
    val avgRating: Double?,
    val ratingCount: Int
)

// TastingEntrySchema.kt
data class TastingEntryType(
    val id: String,
    val userId: String,
    val wine: WineType,          // resolved via DataLoader
    val rating: Double,
    val notes: String?,
    val location: String?,
    val pairing: String?,
    val mood: String?,
    val isPublic: Boolean,
    val isDraft: Boolean,
    val tastedAt: Long,
    val createdAt: Long,
    val updatedAt: Long
)

// Auth
data class AuthPayload(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
    val user: UserType
)
```

### 5.2 Queries

```graphql
type Query {
  # Auth
  me: User

  # Wine catalog
  wine(id: ID!): Wine
  wines(query: String, region: String, style: String, page: Int, pageSize: Int): WineConnection

  # Journal
  myJournal(page: Int, pageSize: Int): TastingEntryConnection
  tastingEntry(id: ID!): TastingEntry

  # Social
  user(username: String!): User
  feed(page: Int, pageSize: Int): TastingEntryConnection   # entries from followed users
}
```

### 5.3 Mutations

```graphql
type Mutation {
  # Auth
  signIn(email: String!, password: String!): AuthPayload!
  register(email: String!, password: String!, username: String!, displayName: String!): AuthPayload!
  signOut: Boolean!
  refreshToken(refreshToken: String!): AuthPayload!

  # Wine catalog
  createWine(input: CreateWineInput!): Wine!

  # Journal
  createTastingEntry(input: CreateTastingEntryInput!): TastingEntry!
  updateTastingEntry(id: ID!, input: UpdateTastingEntryInput!): TastingEntry!
  deleteTastingEntry(id: ID!): Boolean!
  publishDraft(id: ID!): TastingEntry!

  # Social
  followUser(userId: ID!): Boolean!
  unfollowUser(userId: ID!): Boolean!

  # Lists
  createList(name: String!, description: String, isPublic: Boolean): WineList!
  addToList(listId: ID!, wineId: ID!): WineList!
  removeFromList(listId: ID!, wineId: ID!): WineList!
}
```

### 5.4 Input types

```kotlin
data class CreateTastingEntryInput(
    val wineId: String,
    val rating: Double,
    val notes: String? = null,
    val location: String? = null,
    val pairing: String? = null,
    val mood: String? = null,
    val isPublic: Boolean = true,
    val isDraft: Boolean = false,
    val tastedAt: Long
)

data class UpdateTastingEntryInput(
    val rating: Double? = null,
    val notes: String? = null,
    val location: String? = null,
    val pairing: String? = null,
    val mood: String? = null,
    val isPublic: Boolean? = null,
    val tastedAt: Long? = null
)

data class CreateWineInput(
    val name: String,
    val winery: String,
    val vintage: Int? = null,
    val region: String,
    val country: String,
    val grapes: List<String> = emptyList(),
    val style: String,
    val abv: Double? = null,
    val description: String? = null,
    val labelImageUrl: String? = null
)
```

---

## 6. Authentication

### 6.1 Flow

1. Client sends `signIn(email, password)` mutation
2. Server verifies password with bcrypt against stored hash
3. Server issues:
   - **access token** — JWT, signed HMAC-SHA256, expires in **15 minutes**
   - **refresh token** — opaque UUID stored in a `refresh_tokens` collection, expires in **30 days**
4. Client stores both tokens; sends `Authorization: Bearer <access_token>` on every request
5. When access token expires, client calls `refreshToken(refreshToken)` to get a new pair

### 6.2 JWT claims

```json
{
  "sub": "<userId>",
  "email": "<email>",
  "iat": 1234567890,
  "exp": 1234568790
}
```

### 6.3 Ktor JWT plugin setup

```kotlin
// Authentication.kt
fun Application.configureAuthentication(config: AppConfig) {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "goodwines"
            verifier(
                JWT.require(Algorithm.HMAC256(config.jwtSecret))
                    .withIssuer(config.jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.subject != null) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
```

### 6.4 Auth context in GraphQL resolvers

```kotlin
// AuthContext.kt
data class AuthContext(val userId: String?) : GraphQLContext

// Inject current user into every resolver via a custom context factory:
class AuthContextFactory(private val jwtService: JwtService) : GraphQLContextFactory<AuthContext> {
    override suspend fun generateContext(
        request: ApplicationRequest,
        response: ApplicationResponse
    ): AuthContext {
        val token = request.parseAuthorizationHeader()
            ?.let { (it as? HttpAuthHeader.Single)?.blob }
        val userId = token?.let { jwtService.verifyAndGetUserId(it) }
        return AuthContext(userId)
    }
}
```

---

## 7. Environment Configuration

### 7.1 `.env.example`

```
MONGODB_URI=mongodb+srv://<user>:<password>@cluster.mongodb.net/goodwines?retryWrites=true&w=majority
MONGODB_DATABASE=goodwines

JWT_SECRET=change-me-to-a-256-bit-random-secret
JWT_ISSUER=goodwines-api
JWT_EXPIRY_MINUTES=15
REFRESH_TOKEN_EXPIRY_DAYS=30

SERVER_PORT=8080
ENVIRONMENT=development
```

### 7.2 Typed config class

```kotlin
// AppConfig.kt
data class AppConfig(
    val port: Int,
    val environment: String,
    val mongoUri: String,
    val mongoDatabase: String,
    val jwtSecret: String,
    val jwtIssuer: String,
    val jwtExpiryMinutes: Long,
    val refreshTokenExpiryDays: Long
) {
    companion object {
        fun fromEnvironment(): AppConfig = AppConfig(
            port = System.getenv("SERVER_PORT")?.toInt() ?: 8080,
            environment = System.getenv("ENVIRONMENT") ?: "development",
            mongoUri = requireEnv("MONGODB_URI"),
            mongoDatabase = requireEnv("MONGODB_DATABASE"),
            jwtSecret = requireEnv("JWT_SECRET"),
            jwtIssuer = System.getenv("JWT_ISSUER") ?: "goodwines-api",
            jwtExpiryMinutes = System.getenv("JWT_EXPIRY_MINUTES")?.toLong() ?: 15L,
            refreshTokenExpiryDays = System.getenv("REFRESH_TOKEN_EXPIRY_DAYS")?.toLong() ?: 30L
        )
        private fun requireEnv(name: String) =
            System.getenv(name) ?: error("Required environment variable $name is not set")
    }
}
```

---

## 8. Docker Setup

### 8.1 `Dockerfile`

```dockerfile
FROM gradle:8.11-jdk21 AS builder
WORKDIR /app
COPY backend/ .
RUN gradle shadowJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 8.2 `docker-compose.yml`

```yaml
version: "3.9"
services:
  api:
    build:
      context: .
      dockerfile: backend/Dockerfile
    ports:
      - "8080:8080"
    env_file:
      - backend/.env
    restart: unless-stopped
```

> **Local dev without Docker:** run `./gradlew run` inside `backend/` with a local `.env` file.

---

## 9. Application Entry Point

```kotlin
// Application.kt
fun main() {
    val config = AppConfig.fromEnvironment()
    embeddedServer(Netty, port = config.port) {
        configureKoin(config)
        configureSerialization()
        configureAuthentication(config)
        configureCors()
        configureStatusPages()
        configureGraphQL()
        configureCallLogging()
    }.start(wait = true)
}

fun Application.configureKoin(config: AppConfig) {
    install(KoinIsolated) {
        modules(appModules(config))
    }
}

// di/AppModules.kt
fun appModules(config: AppConfig) = module {
    single { config }
    single { MongoClient.create(config.mongoUri).getDatabase(config.mongoDatabase) }

    // Data sources
    single { UserRepositoryImpl(get()) }
    single { WineRepositoryImpl(get()) }
    single { TastingEntryRepositoryImpl(get()) }

    // Services
    single { JwtService(config) }
    single { PasswordService() }
    single { AuthUseCase(get(), get(), get()) }

    // GraphQL resolvers
    single { UserQuery(get()) }
    single { WineQuery(get()) }
    single { TastingEntryQuery(get()) }
    single { AuthMutation(get()) }
    single { WineMutation(get()) }
    single { TastingEntryMutation(get()) }
}
```

---

## 10. GraphQL Ktor Plugin Setup

```kotlin
// plugin/GraphQL.kt
fun Application.configureGraphQL() {
    install(GraphQL) {
        schema {
            packages = listOf("com.lasecun.goodwines")
            queries = listOf(
                get<UserQuery>(),
                get<WineQuery>(),
                get<TastingEntryQuery>()
            )
            mutations = listOf(
                get<AuthMutation>(),
                get<WineMutation>(),
                get<TastingEntryMutation>()
            )
            hooks = SchemaGeneratorHooks()   // custom scalar or directive hooks if needed
        }
        engine {
            // DataLoaders for N+1 prevention on nested types (e.g. wine inside tasting entry)
        }
        contextFactory = AuthContextFactory(get())
    }
}
```

GraphQL endpoint is available at `POST /graphql`.
GraphiQL playground available at `GET /graphiql` in development mode.

---

## 11. Key Repository Patterns

```kotlin
// TastingEntryRepositoryImpl.kt (example)
class TastingEntryRepositoryImpl(db: MongoDatabase) : TastingEntryRepository {

    private val collection = db.getCollection<TastingEntryDocument>("tasting_entries")

    override suspend fun findById(id: String): TastingEntry? =
        collection.findOne(TastingEntryDocument::id eq ObjectId(id))?.toDomain()

    override suspend fun findByUserId(userId: String, page: Int, pageSize: Int): List<TastingEntry> =
        collection.find(TastingEntryDocument::userId eq userId)
            .sort(descending(TastingEntryDocument::tastedAt))
            .skip((page - 1) * pageSize)
            .limit(pageSize)
            .toList()
            .map { it.toDomain() }

    override suspend fun save(entry: TastingEntry): TastingEntry {
        val doc = entry.toDocument()
        collection.insertOne(doc)
        return doc.toDomain()
    }

    override suspend fun update(entry: TastingEntry): TastingEntry {
        val doc = entry.toDocument()
        collection.replaceOne(TastingEntryDocument::id eq ObjectId(entry.id), doc)
        return doc.toDomain()
    }

    override suspend fun deleteById(id: String): Boolean =
        collection.deleteOne(TastingEntryDocument::id eq ObjectId(id)).deletedCount > 0
}
```

---

## 12. Error Handling

Use `graphql-kotlin`'s `GraphQLError` for domain errors:

```kotlin
class AuthenticationException(message: String) :
    GraphQLKotlinException(message, extensions = mapOf("code" to "UNAUTHENTICATED"))

class NotFoundException(resource: String, id: String) :
    GraphQLKotlinException("$resource $id not found", extensions = mapOf("code" to "NOT_FOUND"))

class ValidationException(message: String) :
    GraphQLKotlinException(message, extensions = mapOf("code" to "BAD_USER_INPUT"))
```

---

## 13. CORS Configuration

```kotlin
fun Application.configureCors() {
    install(CORS) {
        anyHost()  // tighten in production to specific mobile app origins / API gateway
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Post)
    }
}
```

---

## 14. Client Contract (what the KMP app expects)

The mobile client (`RemoteAuthDataSourceImpl`, `RemoteJournalDataSource`, etc.) sends requests to:

- **Endpoint:** `POST https://<host>/graphql`
- **Auth header:** `Authorization: Bearer <access_token>`
- **Content-Type:** `application/json`

Payload format:
```json
{
  "query": "mutation SignIn($email: String!, $password: String!) { signIn(email: $email, password: $password) { accessToken refreshToken expiresAt user { id username displayName } } }",
  "variables": { "email": "...", "password": "..." }
}
```

The client expects the `AuthSessionDto` shape:
```json
{
  "data": {
    "signIn": {
      "accessToken": "...",
      "refreshToken": "...",
      "expiresAt": 1234567890000,
      "user": { "id": "...", "username": "...", "displayName": "..." }
    }
  }
}
```

---

## 15. Implementation Order (MVP)

Implement in this order to unblock the mobile client as fast as possible:

1. **Project scaffold** — Gradle project, Ktor server starts, health check endpoint `GET /health`
2. **MongoDB connection** — connect to Atlas, verify ping in startup log
3. **Auth mutations** — `register`, `signIn`, `refreshToken`, `signOut`; bcrypt + JWT
4. **`me` query** — returns current user from JWT context
5. **Wine mutations** — `createWine`; Wine queries — `wine(id)`, `wines(query)`
6. **TastingEntry mutations** — `createTastingEntry`, `updateTastingEntry`, `deleteTastingEntry`
7. **TastingEntry queries** — `myJournal`, `tastingEntry(id)`
8. **Social** — `followUser`, `unfollowUser`, `feed` query
9. **Docker** — Dockerfile + docker-compose; verify `docker compose up` starts server
10. **Tests** — auth use case unit tests, repository integration tests with Testcontainers

---

## 16. Future Phases (not MVP)

- **Image upload** — S3/Cloudflare R2 for wine label images; pre-signed URLs via a dedicated mutation
- **Wine label OCR** — connect to Google Vision or AWS Rekognition; separate microservice or Lambda
- **Taste profile engine** — compute `TasteProfile` from tasting history; background job or on-write trigger
- **Recommendations** — query similar users by taste profile; MongoDB vector search or a lightweight ML service
- **Push notifications** — social events (new follower, comment); APNs + FCM via a notification service
- **Rate limiting** — per-user token bucket on the GraphQL endpoint
- **Subscriptions** — real-time feed updates via WebSocket GraphQL subscriptions
