# Filmography — Training Project (Spring Boot, PostgreSQL, JWT, Thymeleaf)

**TL;DR:** A full-featured web app for a small film library. It ships both a Thymeleaf MVC UI and a REST API.
You can manage films and directors, search/filter items, register/login users, and create film rentals.
Authentication: form login for MVC and JWT for REST.

## Tech Stack
- **Java / JDK 17** (recommended; compatible with Spring Boot 2.7.6)
- **Spring Boot 2.7.6**: Web, Validation, Security
- **Spring Data JPA** (Hibernate)
- **PostgreSQL** (docker-compose provided)
- **Thymeleaf** (+ layout dialect, springsecurity5 extras) for MVC pages
- **OpenAPI 3 (springdoc-openapi-ui)** for REST docs
- **Lombok**, **ModelMapper**
- **JWT** for REST authentication

> Key dependencies in `pom.xml`: spring-boot-starter-web, data-jpa, security, thymeleaf, validation, springdoc-openapi-ui, postgresql, lombok, modelmapper, io.jsonwebtoken.

## Architecture & Layers
- **Model (entities)**: `Film`, `Director`, `User`, `Role`, `Order`, base `GenericModel` (audit/soft-delete).
- **DTO + Mapper**: DTOs & mappers (`FilmDto`, `DirectorDto`, `UserDto`, `OrderDto`, etc.) using ModelMapper.
- **Repository**: `JpaRepository` interfaces (e.g., `FilmRepository`, `DirectorRepository`, `UserRepository`).
- **Service**: business logic incl. pagination/search (`FilmService`, `DirectorService`, `OrderService`, `UserService`).
- **Controllers**:
  - **REST** (`/rest/**`): `FilmController`, `DirectorController`, `UserController`, `OrderController` + generic `GenericController` for CRUD.
  - **MVC** (`/`, `/films/**`, `/directors/**`, etc.): Thymeleaf pages and forms.

## Domain Model
- **Film**: `title`, `premierYear`, `country`, `genre`, `directors` (Many-to-Many via a join table).
- **Director**: `directorFIO`, `position`.
- **User**: registration/login, `Role`, `orders`.
- **Order**: `user`, `film`, `rentDate`, `rentPeriod`, `isPurchased`.
- **Genre**: enum.

## Features (selection)
- CRUD for **films** and **directors** (via MVC and REST).
- Search/filter films by `title`, `country`, `genre`: `GET /rest/film?title=...&country=...&genre=...`.
- Film ↔ Director Many-to-Many relation.
- User registration (`GET/POST /registration`), form login (`/login`) for MVC.
- REST auth via JWT: `POST /rest/user/auth` → token; then `Authorization: Bearer ...`.
- Orders (rentals): `POST /rest/order/add`, `GET /rest/order/user-orders/{userId}`.
- OpenAPI/Swagger UI (springdoc): default at `/swagger-ui/index.html`.

## Security
- **MVC**: form login SecurityFilterChain in `WebSecurityConfig`.
- **REST**: `JwtTokenFilter` and `JwtSecurityConfig` for JWT handling.
- Example auth response: `{ "token": "...", "authorities": [...] }`.

## How to Run Locally
1. Install **JDK 17** (or a compatible version for Spring Boot 2.7.x).
2. Start PostgreSQL (compose file provided):
   ```bash
   docker-compose up -d postgres
   ```
3. Create `src/main/resources/application.properties` (if empty), e.g.:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/db_filmography
   spring.datasource.username=postgres
   spring.datasource.password=12345
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   server.port=8080
   ```
4. Build & run:
   ```bash
   ./mvnw spring-boot:run
   # or
   ./mvnw clean package && java -jar target/filmography-0.0.1-SNAPSHOT.jar
   ```
5. Open:
   - Web UI (Thymeleaf): `http://localhost:8080/`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`

> Note: In `docker-compose.yml` the DB is `db_filmography` on port `5432`, user `postgres`, password `12345` — keep it in sync with `application.properties`.

## REST Examples
- Authenticate (get JWT):
  ```http
  POST /rest/user/auth
  Content-Type: application/json

  {"login":"user","password":"password"}
  ```
- Search films:
  ```http
  GET /rest/film?title=ring&country=USA&genre=DRAMA
  Authorization: Bearer <JWT>
  ```
- Add director:
  ```http
  POST /rest/director/add-director
  Content-Type: application/json
  Authorization: Bearer <JWT>

  {"directorFIO":"John Smith","position":"Producer"}
  ```

## Improvements / Next Steps
- Introduce **DB migrations** (Flyway/Liquibase) instead of `ddl-auto`.
- Clarify/merge **security configs** (separate MVC/REST matchers or use `@Order`), verify access rules for `/rest/**`.
- Extract **validators** & extend Bean Validation at DTO level.
- Add **unit & integration tests** (Spring Boot Test).
- Move secrets to `.env` / **Spring Config** (don’t commit real passwords).
- Add a **Dockerfile** and a compose service for the app itself.

---

**Elevator pitch:**
> “Filmography is a Spring Boot training project with PostgreSQL and Thymeleaf. It exposes both an MVC UI and a JWT-secured REST API. You can manage films/directors, search by attributes, register users and create rentals. The project demonstrates layered architecture (DTO/Mapper/Service/Repository), a Many-to-Many relation, Spring Security, and OpenAPI.”
