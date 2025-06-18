# Microservices API Documentation

This repository is a mini project implementing microservices architecture using Java Spring Boot. It demonstrates how to build, organize, and connect multiple services (Quiz, Question, API Gateway, and Service Registry) in a scalable and modular way.

## API Gateway
- **Base URL:** `/`
- **Forwarding Rules:**
  - `/quiz/**` → Forwards to **quiz-service**
  - `/question/**` → Forwards to **question-service**

---

## Quiz Service (`/quiz`)

| Method | Path              | Description                       |
|--------|-------------------|-----------------------------------|
| POST   | `/quiz/create`    | Create a new quiz                 |
| GET    | `/quiz/get/{id}`  | Get questions for a quiz by ID    |
| POST   | `/quiz/submit/{id}` | Submit answers and get score    |

---

## Question Service (`/question`)

| Method | Path                        | Description                                 |
|--------|-----------------------------|---------------------------------------------|
| GET    | `/question/allQuestions`    | Get all questions                           |
| GET    | `/question/category/{category}` | Get questions by category              |
| POST   | `/question/add`             | Add a new question                          |
| GET    | `/question/generate`        | Generate questions for a quiz (by category and number) |
| POST   | `/question/getQuestions`    | Get questions by a list of IDs              |
| POST   | `/question/getScore`        | Calculate score from submitted answers      |

---

## Feign Client (Quiz Service → Question Service)

| Method | Path                        | Description                                 |
|--------|-----------------------------|---------------------------------------------|
| GET    | `/question/generate`        | Generate questions for a quiz (by category and number) |
| POST   | `/question/getQuestions`    | Get questions by a list of IDs              |
| POST   | `/question/getScore`        | Calculate score from submitted answers      |

---

## Service Registry (Eureka)
- **Base URL:** `/` (default port: 8761)
- **Purpose:** Service discovery for all microservices.

---

## Notes
- All services are registered with Eureka and can be discovered dynamically.
- The API Gateway forwards requests to the appropriate service based on the path.
- For more details, see each service's source code. 