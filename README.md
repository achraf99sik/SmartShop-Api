# SmartShop API

SmartShop API is a Spring Boot backend for managing commercial operations including clients, products, orders, payments, and user authentication.

## Features

✔️ User login via **session-based authentication**  
✔️ CRUD for **Clients, Products, Orders, and Payments**  
✔️ Automatic **payment number generation**  
✔️ Multi-part orders with items  
✔️ Fidelity service: calculates customer tiers & discounts  
✔️ Global exception handling  
✔️ Liquibase support for database changelog

---

## Project Structure

```
src/main/java/com/smartshop/api
├── config            # Auth filter, security setup
├── controller        # REST controllers
├── dto               # Request/Response DTOs
├── enums             # Status and type enums
├── exception         # Custom runtime exceptions
├── mapper            # MapStruct mappers
├── model             # JPA entities
├── repository        # Spring Data repositories
├── service           # Business logic
└── util              # Helpers (Password hashing etc)
```

Database changelog:

```
src/main/resources/db/changelog
├── changelog-master.xml
└── changeSets/*.xml
```

---

## Requirements

- Java 17+
- Maven
- PostgreSQL
- Spring Boot 3+
- Liquibase
- MapStruct

---

## Setup & Run

### 1️⃣ Update Database Configuration

In:

```
src/main/resources/application.properties
```

Set credentials:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/smartshop
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 2️⃣ Build & Run

```
mvn clean install
mvn spring-boot:run
```

---

## Authentication (Session-Based)

- User logs in: `/login`
- Server creates **HTTP session**
- Every request uses `AuthFilter` to check session attribute `"user"`

No JWT is used.

---

## Example Endpoints

### Login
```
POST /login
{
  "email": "admin@shop.com",
  "password": "123456"
}
```

### Create Order
```
POST /orders
```

### Update Payment Status
```
PUT /payments/{id}/status
```

---

## Technologies Used

| Technology | Purpose |
|---|---|
| Spring Boot | Backend Framework |
| PostgreSQL | Database |
| Liquibase | DB migrations |
| MapStruct | DTO mapping |
| Session Auth | Authentication |
| Maven | Build tool |

---
## File structure

```shell
SmartShop
├───.idea
├───.mvn
│   └───wrapper
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───smartshop
│   │   │           └───api
│   │   │               ├───config
│   │   │               ├───controller
│   │   │               ├───dto
│   │   │               ├───enums
│   │   │               ├───exception
│   │   │               ├───mapper
│   │   │               ├───model
│   │   │               ├───repository
│   │   │               ├───service
│   │   │               └───util
│   │   └───resources
│   │       ├───db
│   │       │   └───changelog
│   │       │       └───changeSets
│   │       ├───static
│   │       └───templates
│   └───test
│       └───java
│           └───com
│               └───smartshop
│                   └───api
└───target
    ├───classes
    │   ├───com
    │   │   └───smartshop
    │   │       └───api
    │   │           ├───config
    │   │           ├───controller
    │   │           ├───dto
    │   │           ├───enums
    │   │           ├───exception
    │   │           ├───mapper
    │   │           ├───model
    │   │           ├───repository
    │   │           ├───service
    │   │           └───util
    │   └───db
    │       └───changelog
    │           └───changeSets
    ├───generated-sources
    │   └───annotations
    │       └───com
    │           └───smartshop
    │               └───api
    │                   └───mapper
    ├───generated-test-sources
    │   └───test-annotations
    ├───maven-archiver
    ├───maven-status
    │   └───maven-compiler-plugin
    │       ├───compile
    │       │   └───default-compile
    │       └───testCompile
    │           └───default-testCompile
    ├───surefire-reports
    └───test-classes
        └───com
            └───smartshop
                └───api

```