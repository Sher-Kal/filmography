# 🎬 Filmography — учебное веб‑приложение (Spring Boot, PostgreSQL, JWT, Thymeleaf)

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://adoptium.net)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.x-6DB33F)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13%2B-336791)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> **Коротко:** небольшая «фильмотека» с двумя способами доступа — MVC-интерфейс на Thymeleaf и REST API с JWT.  
> CRUD для фильмов и режиссёров, поиск/фильтрация, регистрация и авторизация пользователей, оформление «аренды» фильмов.

---

## 🔎 Содержание
- [Функциональность](#-функциональность)
- [Технологический стек](#-технологический-стек)
- [Архитектура](#-архитектура)
- [Быстрый старт](#-быстрый-старт)
- [Конфигурация окружения](#-конфигурация-окружения)
- [REST API — примеры](#-rest-api--примеры)
- [Скриншоты](#-скриншоты)
- [Планы и улучшения](#-планы-и-улучшения)
- [Лицензия](#-лицензия)

---

## ✅ Функциональность
- **Фильмы**: создание/редактирование/удаление, поиск по `title`, `country`, `genre`.
- **Режиссёры**: создание/редактирование/удаление, связь с фильмами (**Many‑to‑Many**).
- **Пользователи**: регистрация, вход через форму (для MVC).
- **Авторизация для REST**: `JWT` (получение токена, доступ к защищённым эндпоинтам).
- **Аренда**: оформление и просмотр «заказов» пользователя.
- **Документация API**: OpenAPI/Swagger UI.

---

## 🛠 Технологический стек
- **Java 17+**, **Spring Boot 2.7.x** (Web, Validation, Security)
- **Spring Data JPA (Hibernate)**
- **PostgreSQL** (для локального запуска — `docker-compose.yml` в корне)
- **Thymeleaf** + layout dialect + extras Spring Security
- **JWT (io.jsonwebtoken)** для REST-аутентификации
- **ModelMapper**, **Lombok**
- **springdoc-openapi-ui** — Swagger UI

> Основные зависимости см. в `pom.xml`.

---

## 🧩 Архитектура
Слоистая структура:
- **model** — сущности: `Film`, `Director`, `User`, `Role`, `Order`, базовый `GenericModel` (поля аудита/soft-delete).
- **dto** и **mapper** — перенос данных и маппинг (ModelMapper).
- **repository** — интерфейсы `JpaRepository`.
- **service** — бизнес-логика, пагинация и поиск.
- **rest/controller** — REST-эндпоинты (`/rest/**`), часть CRUD унаследована от `GenericController`.
- **MVC/controller** + `templates/` — страницы и формы на Thymeleaf.
- **security** — две конфигурации: MVC (form login) и REST (JWT).

**Связи домена:**
- `Film` ↔ `Director` — Many‑to‑Many (join-таблица).
- `User` ↔ `Role` — многие‑к‑одному.
- `Order` — связывает `User` и `Film` с датой и периодом.

---

## 🚀 Быстрый старт

### Вариант 1: локально (JDK + Maven)
1. Установите **JDK 17+**.
2. Поднимите PostgreSQL (вариант из репозитория):
   ```bash
   docker-compose up -d postgres
   ```
3. Создайте конфиг `src/main/resources/application.properties` (см. пример ниже).
4. Запуск:
   ```bash
   ./mvnw spring-boot:run
   # или
   ./mvnw clean package && java -jar target/filmography-0.0.1-SNAPSHOT.jar
   ```
5. Откройте:
   - Web UI: `http://localhost:8080/`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### Вариант 2: только посмотреть интерфейс
- Заполните `application.properties` (или используйте `application.properties.example` как основу).  
- Запустите через `spring-boot:run` и зайдите на `/` — будет простая главная и страницы фильмов/режиссёров.

---

## ⚙️ Конфигурация окружения

`src/main/resources/application.properties` (пример):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_filmography
spring.datasource.username=postgres
spring.datasource.password=12345

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

> **Важно:** файл `application.properties` добавлен в `.gitignore`. В репозитории лежит шаблон `application.properties.example` — скопируйте его и заполните локально.

---

## 🧪 REST API — примеры

**Получить JWT:**
```http
POST /rest/user/auth
Content-Type: application/json

{"login":"user","password":"password"}
```

**Поиск фильмов:**
```http
GET /rest/film?title=ring&country=USA&genre=DRAMA
Authorization: Bearer <JWT>
```

**Добавить режиссёра:**
```http
POST /rest/director/add-director
Content-Type: application/json
Authorization: Bearer <JWT>

{"directorFIO":"John Smith","position":"Producer"}
```

**Заказы пользователя:**
```http
GET /rest/order/user-orders/{userId}
Authorization: Bearer <JWT>
```

> Полная документация доступна в Swagger UI.

---

## 🖼 Скриншоты
Создайте папку `docs/` и положите изображения интерфейса. Пример включения в README:
```markdown
![Список фильмов](docs/films-list.png)
![Форма добавления](docs/film-add-form.png)
```

---

## 📌 Планы и улучшения
- Миграции БД (**Flyway/Liquibase**) вместо `ddl-auto`.
- Явно развести/унифицировать конфиги безопасности для MVC и REST.
- Расширить **Bean Validation** на уровне DTO.
- Добавить **unit** и **интеграционные тесты** (Spring Boot Test).
- Вынести чувствительные параметры в переменные окружения/конфигурацию.
- Добавить **Dockerfile** и сервис приложения в `docker-compose.yml`.

---

## 📄 Лицензия
Проект распространяется по лицензии **MIT** — см. файл [LICENSE](LICENSE).

---

> 🧠 *Проект учебный; структура и функционал собирались по шагам в рамках курса. Код адаптирован под собственные задачи и требования.*
