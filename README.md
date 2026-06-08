# CampusConnect

A full-stack campus resource sharing and interview preparation platform built using **Java, Spring Boot, Spring Security, JWT, JPA/Hibernate, MySQL, React, and Cloudinary**.

CampusConnect helps students discover academic resources, share interview experiences, and connect with their college community through a secure and scalable platform.

---

## Overview

CampusConnect was designed to solve a common problem faced by students: scattered academic resources and interview preparation materials.

The platform allows students to upload and discover notes, previous year question papers (PYQs), assignments, and interview experiences while providing administrators with moderation capabilities.

The project focuses heavily on backend engineering concepts including authentication, authorization, file management, filtering, pagination, validation, and secure REST API design.

---

## Features

### Authentication & Authorization

* User Registration
* User Login
* JWT-Based Authentication
* Password Encryption using BCrypt
* Role-Based Access Control (RBAC)
* Protected API Endpoints

#### Supported Roles

* STUDENT
* ADMIN

---

### Resource Sharing Module

Students can upload:

* Notes
* Previous Year Question Papers (PYQs)
* Assignments
* Study Resources

Each resource contains:

* Title
* Description
* Subject
* Semester
* Branch
* College
* Resource Type
* File URL
* Upload Timestamp

Files are stored using Cloudinary.

---

### Resource Discovery

Advanced search and filtering support:

* Search by Title
* Filter by Branch
* Filter by Semester
* Filter by Subject
* Filter by College
* Filter by Resource Type

Pagination support for efficient data retrieval.

---

### Interview Experience Module

Students can share placement experiences including:

* Company Name
* Role
* Online Assessment Questions
* Interview Rounds
* Difficulty Level
* Tips and Suggestions

Filter experiences by:

* Company
* Role
* Batch Year

---

### Admin Module

Administrators can:

* Delete inappropriate resources
* Delete abusive interview experiences
* Manage platform users
* Moderate content

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* Hibernate
* Bean Validation
* Lombok

### Authentication

* JWT (JSON Web Tokens)
* BCrypt Password Encoding

### Database

* MySQL

### File Storage

* Cloudinary

### Frontend

* React
* Tailwind CSS

### API Documentation

* Swagger / OpenAPI

### Build Tool

* Maven

### Deployment

* Backend: Render / Railway
* Frontend: Vercel
* Database: Railway MySQL

---

## Architecture

```text
Client (React)
       |
       v
REST Controllers
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
MySQL Database
```

The application follows a layered architecture to ensure maintainability, scalability, and separation of concerns.

---

## Database Design

### Users

| Field     | Description                |
| --------- | -------------------------- |
| id        | Primary Key                |
| name      | User Name                  |
| email     | Unique Email               |
| password  | Encrypted Password         |
| role      | STUDENT / ADMIN            |
| createdAt | Account Creation Timestamp |

### Resources

| Field        | Description              |
| ------------ | ------------------------ |
| id           | Primary Key              |
| title        | Resource Title           |
| description  | Resource Description     |
| resourceType | Notes / PYQ / Assignment |
| fileUrl      | Cloudinary URL           |
| semester     | Semester                 |
| branch       | Branch                   |
| subject      | Subject                  |
| college      | College                  |
| uploadedBy   | User Reference           |
| createdAt    | Upload Timestamp         |

### Interview Experiences

| Field       | Description       |
| ----------- | ----------------- |
| id          | Primary Key       |
| companyName | Company           |
| role        | Job Role          |
| experience  | Interview Details |
| difficulty  | Difficulty Level  |
| postedBy    | User Reference    |
| createdAt   | Posted Timestamp  |

---

## Security Implementation

### Password Security

Passwords are never stored in plain text.

BCryptPasswordEncoder is used to securely hash user passwords before persistence.

### JWT Authentication Flow

```text
User Login
    |
    v
Credential Validation
    |
    v
JWT Generation
    |
    v
Client Stores Token
    |
    v
Authorization Header
    |
    v
JWT Validation
    |
    v
Protected Resource Access
```

### Security Features

* Stateless Authentication
* JWT Token Validation
* Role-Based Authorization
* Protected Endpoints
* Secure Password Storage

---

## Backend Engineering Features

This project demonstrates:

* Layered Architecture
* RESTful API Design
* DTO Pattern
* JWT Authentication
* Role-Based Access Control
* Bean Validation
* Global Exception Handling
* File Upload Management
* Search & Filtering
* Pagination
* Database Relationships
* API Documentation
* Production Deployment

---
## API Endpoints

### Authentication
| Method | Endpoint |
| ------ | -------- |
| POST | `/auth/register` |
| POST | `/auth/login` |

### Resources
| Method | Endpoint |
| ------ | -------- |
| POST | `/resources` |
| GET | `/resources` |
| GET | `/resources/{id}` |
| PUT | `/resources/{id}` |
| DELETE | `/resources/{id}` |

### Interview Experiences
| Method | Endpoint |
| ------ | -------- |
| POST | `/interviews` |
| GET | `/interviews` |
| GET | `/interviews/{id}` |
| DELETE | `/interviews/{id}` |

---

## Search & Filtering Examples

```http
GET /resources?semester=5
GET /resources?subject=DBMS
GET /resources?branch=CSE
GET /resources?page=0&size=10
```

---

## Validation

Request validation is implemented using Bean Validation annotations:

* @NotBlank
* @Email
* @Valid

Invalid requests return meaningful error responses.

---

## Exception Handling

Global exception handling is implemented using:

```java
@ControllerAdvice
```

This ensures consistent and structured error responses across the application.

---

## API Documentation

Swagger/OpenAPI documentation is available after application startup.

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Local Setup

### Prerequisites

* Java 17+
* Maven
* MySQL
* Cloudinary Account

### Clone Repository

```bash
git clone https://github.com/anupamchaubey/Campus-Connect.git
```

### Configure Database

Update application.properties:

```properties
DB_URL=jdbc:mysql://localhost:3306/campusconnect
DB_USERNAME=root
DB_PASSWORD=your_mysql_password

JWT_SECRET=your_super_secret_jwt_key_make_it_long
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Future Enhancements

Planned features:

* Bookmarks
* Likes
* Comments
* Content Reporting
* Notifications
* Moderator Role

---

## Learning Outcomes

This project provided hands-on experience with:

* Spring Boot
* Spring Security
* JWT Authentication
* Hibernate & JPA
* Database Design
* API Development
* Secure Backend Systems
* Cloudinary Integration
* Production Deployment

---

## Author

**Anupam Chaubey**

Java Backend Developer focused on Spring Boot, SQL, Software Engineering, and Open Source Development.
