# Exam Portal - Backend Server

[![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-red)](https://maven.apache.org/)

**Exam Portal Server** is a robust assessment management system built using **Spring Boot**. It provides a comprehensive set of APIs to manage categories, quizzes, and questions, while also handling real-time exam logic and user evaluation.

---

## 🚀 Key Features

* **Category-Based Quiz Management:** Organize assessments into distinct academic or professional categories.
* **Dual-Interface Support:** Tailored REST endpoints for **Admins** (to create/edit exams) and **Students** (to attempt quizzes).
* **Dynamic Question Logic:** Supports complex data modeling for various question types with automated evaluation.
* **Real-time Timer Support:** Backend logic designed to sync with frontend timers for exam integrity.
* **Secure Authentication:** User management system with dedicated dashboards for different roles.
* **Automated Results:** Efficient calculation engine to process student performance instantly upon submission.

---

## 🛠️ Technical Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **ORM:** Hibernate / Spring Data JPA
* **Database:** MySQL
* **Build Tool:** Maven
* **API Pattern:** RESTful Services

---

## 📂 Project Architecture

The server is built with a focus on high cohesion and low coupling:

```text
src/main/java/com/exam/
├── config/             # App & Security configurations
├── controller/         # REST API Endpoints (Category, Quiz, Question)
├── entity/             # Database Models (User, Category, Quiz, Question)
├── helper/             # Custom utility classes
├── repository/         # JPA Data Access Layer
├── service/            # Business Logic Implementation
└── ExamServerApp       # Application Entry Point

src/test/java/com/exam/ # Unit and Integration Tests
```
---

### ⚙️ Setup & Installation
1. Prerequisites
JDK 17 or higher

MySQL database

Maven
