# Backend - BravIA

This is the backend service of the academic project **BravIA**, built with **Kotlin** and **Spring Boot**. It follows Clean Architecture principles and provides secure access to resources through **JWT authentication**.

---

## Technologies Used

- **Kotlin**
- **Spring Boot (REST API)**
- **JWT** for authentication
- **Docker** (for local development)
- **PostgreSQL**
- **Testcontainers** for integration testing

---

## 🚀 Deployment

The backend is currently hosted on **Heroku** for demonstration purposes.

> Note: The Heroku link is private or internal for academic use. Please contact the authors if access is required.

---

## 🧪 Development Setup (optional)

If you want to run the backend locally:

### Requirements:
- Java 17+
- Docker & Docker Compose
- PostgreSQL (local or containerized)

### Run with Docker:
```bash
docker-compose up --build
```
### Run with  gradle (non-Docker)
```bash
./gradlew bootRun
```
###About the Project
BravIA is an academic internship management platform that enables users to:
- Register and log in
- Manage roles (Admin, Company, Student)
- Apply for internships
- View enriched internship descriptions (via Flutter module)
- The backend supports all core features through a clean and secure REST API.


### Authors
- Mauricio Chaves Chaves
- Joshua Amador Lara
- Gabriel Vega Fernandez
