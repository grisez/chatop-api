# ChâTop API

Backend REST API for the ChâTop rental platform, built with Spring Boot to replace the Mockoon mock server used by the provided Angular front-end.

## Stack

- Java 17
- Spring Boot (Web, Data JPA, Security, Validation)
- MySQL
- JWT authentication (jjwt)
- springdoc-openapi (Swagger UI)

## Architecture

Layered architecture: `Controller -> Service -> Repository (JPA)`.

```
config/       Spring configuration (security, OpenAPI)
controller/   REST endpoints
service/      Business logic interfaces
service/impl/ Business logic implementations
mapper/       Entity <-> DTO mapping (MapStruct)
repository/   Spring Data JPA repositories
entity/       JPA entities
dto/          Request/response payloads
security/     JWT filter, token service, user details
storage/      Rental picture upload, resize and storage
exception/    Global exception handling
```

## Security

- Public routes: `POST /api/auth/register`, `POST /api/auth/login`, and the Swagger/OpenAPI docs.
- Every other route requires a `Bearer <token>` JWT, obtained from register/login. In Swagger UI, click **Authorize** and paste the token to test protected routes.
- Passwords are hashed with Argon2id; database credentials and the JWT secret are read from environment variables, never hardcoded.

## Error handling

Every error response has the shape `{"message": "..."}`, with the matching HTTP status (400/401/403/404/413/500), handled centrally in `GlobalExceptionHandler`. Unexpected exceptions return a generic 500 message; the real error is logged server-side, never exposed to the client.

## Prerequisites

- Java 17
- Maven (or the bundled `./mvnw`)
- MySQL running locally

## Setup

1. Clone this repo alongside the front-end repo (`Mod-lisez-et-impl-mentez-le-back-end-en-utilisant-du-code-Java-maintenable`) — the database schema script lives there.
2. Copy `.env.example` to `.env` and fill in `DB_USERNAME`, `DB_PASSWORD` and `JWT_SECRET` (the other variables already have sensible defaults). `.env` is gitignored — never commit real values.
3. Create the database and a dedicated MySQL user (do not use `root`):
   ```sql
   CREATE DATABASE chatop_db;
   CREATE USER 'chatop_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON chatop_db.* TO 'chatop_user'@'localhost';
   ```
4. Run the schema script from the front-end repo against that database: `ressources/sql/script.sql`.

## Running the application

```bash
./mvnw spring-boot:run
```

The API listens on port `3001` (matching the front-end's expected Mockoon port).

## API documentation

Swagger UI: `http://localhost:3001/swagger-ui.html`

## Tests

No automated test suite is included in this delivery — testing was out of scope for this iteration. The layered architecture (constructor-injected services, thin controllers) supports adding unit and integration tests later without rework.

## Author

Cindy Grisez — Master Lead Tech Java Angular (OpenClassrooms).
