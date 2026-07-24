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
repository/   Spring Data JPA repositories
entity/       JPA entities
dto/          Request/response payloads
security/     JWT filter, token service, user details
exception/    Global exception handling
```

## Prerequisites

- Java 17
- Maven (or the bundled `./mvnw`)
- MySQL running locally

## Setup

1. Create the database and a dedicated MySQL user (do not use `root`):
   ```sql
   CREATE DATABASE chatop_db;
   CREATE USER 'chatop_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON chatop_db.* TO 'chatop_user'@'localhost';
   ```
2. Run the schema script from the front-end repo: `ressources/sql/script.sql`.
3. Set the following environment variables (never commit real values):
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `JWT_SECRET`
4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

The API listens on port `3001` (matching the front-end's expected Mockoon port).

## API documentation

Swagger UI: `http://localhost:3001/swagger-ui.html`
