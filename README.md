## BiblioNet 📚
A secure, containerized, and production-ready RESTful API for a community book-sharing platform. Built with Spring Boot to demonstrate advanced backend engineering concepts, including JWT authentication, role-based authorization, and containerization with Docker.

## 🚀 Tech Stack
Backend Framework: Spring Boot 3.x

Security: Spring Security 6 + JWT (JJWT)

Database: PostgreSQL with Spring Data JPA & Hibernate

Containerization: Docker & Docker Compose

Tooling: Lombok, MapStruct (Optional), Maven

## ✨ Features
  - 🔐 JWT Authentication & Authorization: Secure user login and registration using JSON Web Tokens.

  - 👥 Role-Based Access Control (RBAC): Implements USER and ADMIN roles with different permissions.

  - 📖 Full Book CRUD Operations: Users can create, read, update, and delete their own books.

  - 🤝 Book Borrowing System: Users can browse and borrow books from other community members.

  - 🐳 Dockerized Deployment: The entire application (App + Database) can be run with a single command using Docker Compose.

  - 🗄️ Persistent Data Storage: Uses a production-grade PostgreSQL database.

## 🐳 Running the Project with Docker (Recommended)

**Prerequisites**
  -Docker installed on your machine
  -Docker Compose

  **Steps**
  1.Clone the repository:
  ```bash
    git clone https://github.com/Eo9ine/biblionet-repo.git
    cd biblionet-repo
  ```

2.Run with Docker Compose:
```bash
  docker-compose up --build
```

This command will:

- Build the Docker image for the Spring Boot application.

- Start a PostgreSQL database container.

- Start the application container and link it to the database.

3.Access the application:
  - API: http://localhost:8080 

## 🔐 Default Roles & Access
  - USER: Can create, view, update, and delete their own books. Can borrow books from others.
  - ADMIN: Has full permissions, including managing all books and users.

## 🚧 Future Enhancements
  - Email notifications for borrow requests.

  - Book review and rating system.

  - Advanced search and filtering.

  - Frontend application with Angular/React.

  - Deployment to a cloud platform (AWS/Azure).

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page.

**Note:** This project is a demonstration of advanced Spring Boot concepts for portfolio purposes.

