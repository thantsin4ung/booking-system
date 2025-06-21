
# Booking System API

A Spring Boot-based hotel room booking API with JWT authentication, Redis caching, pagination, and Quartz job scheduling.

---

## Features

- User registration & JWT-based login
- Room CRUD and availability management
- Booking creation and history per user
- Redis caching
- Auto-cancel of unconfirmed bookings (Quartz scheduler)
- Swagger API documentation

---

## Tech Stack

| Layer              | Technology                        |
|-------------------|-----------------------------------|
| Backend Framework | Spring Boot 3.x                   |
| ORM               | Spring Data JPA, Hibernate        |
| Database          | MySQL               |
| Security          | Spring Security + JWT             |
| Caching           | Redis (via Spring Cache)          |
| Job Scheduler     | Quartz                            |
| Docs              | Swagger OpenAPI 3                 |

---

## Project Structure

```
com.tsa.dev
├── config             # Spring config (Security, Redis, Swagger, JWT)
├── controller         # REST endpoints (Auth, Room, Booking)
├── model              # JPA entities (User, Room, Booking)
├── quartzJob          # AutoCancelBooking job
├── repo               # Spring Data Repositories
├── requestDTO         # Request DTOs
├── responseDTO        # Response DTOs
└── service            # Business logic services and interfaces
```

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/thantsin4ung/booking-system.git
cd booking-system
```

### 2. Create the database

Create a MySQL database:

```sql
CREATE DATABASE test_db;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:3306/test_db
spring.datasource.username=your_user
spring.datasource.password=your_password
```

---

### 3. Start Redis Server

```bash
redis-server
```

Or with Docker:

```bash
docker run -p 6379:6379 redis
```

---

### 4. Build & Run the app

```bash
./mvnw spring-boot:run
```

---

### 5. Admin account is auto-created

At startup, the app creates a default admin if it doesn't exist:

```properties
Username: admin
Password: admin123
```

---

## Auth Endpoints

- `POST /api/auth/signup`
- `POST /api/auth/login` → returns JWT
- Use JWT in `Authorization: Bearer <token>` header for secured routes

---

## API Docs

Access Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Scheduled Jobs

- `AutoCancelBookingJob` cancels `PENDING` bookings created >3 mins ago
- Runs via Quartz every 2 minute

---
## 📡 API Endpoints

### AuthController

| Method | Endpoint           | Description       |
| ------ | ------------------ | ----------------- |
| POST   | `/api/auth/signup` | Register new user |
| POST   | `/api/auth/login`  | Authenticate user |

### RoomController

| Method | Endpoint                  | Description                 |
| ------ | ------------------------- | --------------------------- |
| POST   | `/api/user/rooms`         | Get paginated list of rooms |
| POST   | `/api/admin/create-rooms` | Create room (Admin only)    |

### BookingController

| Method | Endpoint                                    | Description                    |
| ------ | ------------------------------------------- | ------------------------------ |
| POST   | `/api/user/bookings`                        | Create a new booking           |
| GET    | `/api/user/bookings/{id}`                   | Get booking details by ID      |
| DELETE | `/api/user/bookings/{id}`                   | Delete a booking               |
| GET    | `/api/user/bookings/user/{userId}`          | Get bookings by user ID        |
| GET    | `/api/user/bookings/get-by-status/{status}` | Get bookings by booking status |
| GET    | `/api/user/bookings/get-all`                | Get all bookings (paginated)   |

---
