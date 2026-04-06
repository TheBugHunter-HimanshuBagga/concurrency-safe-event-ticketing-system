# 🎟️ Concurrency-Safe Event Ticketing System (Backend)

## 🚀 Overview

This project is a **production-style backend system** designed to manage college events and ticket bookings with a strong focus on **data consistency and concurrency control**.

Unlike basic CRUD apps, this system tackles a real-world challenge:
👉 **Preventing overbooking under high concurrent traffic**

---

## ⚡ Why This Project?

Most event systems fail when multiple users book simultaneously.

This system is built to answer:

> *"How do real-world platforms ensure no two users book the last seat at the same time?"*

💡 Solution: Transactions + Locking + Clean Architecture

---

## 🧠 Core Features

### 🔐 Authentication & Authorization

* Secure user registration & login
* JWT-based authentication
* Role-based access control (ADMIN / USER)

---

### 🎟 Event Management

* Create, update, delete events (Admin only)
* Fetch all events and details
* Real-time seat tracking

---

### 🪑 Smart Ticket Booking System

* Book tickets with seat validation
* Cancel bookings safely
* Prevent overbooking using:

    * Transaction management
    * Concurrency control (locking)

---

### 📊 Booking History

* Users can view all past bookings
* Maintains consistent records

---

## 🏗 Architecture

```
Client (Postman)
        ↓
Controller Layer
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA)
        ↓
MySQL Database
```

---

## 🔥 Key Engineering Highlights

* ✅ Handles concurrent booking safely
* ✅ Prevents race conditions
* ✅ Clean layered architecture
* ✅ Secure REST APIs with JWT
* ✅ Scalable backend design

---

## 🛠 Tech Stack

* Java 21
* Spring Boot 3.5.x
* Spring Security
* JWT
* MySQL
* JPA (Hibernate)
* Maven

---

## ⚙️ Setup Instructions

```bash
git clone <repo-link>
cd event-ticketing-system
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_ticketing
spring.datasource.username=root
spring.datasource.password=your_password
```

Run the app:

```bash
mvn spring-boot:run
```

---

## 📡 API Endpoints

### 🔐 Authentication

* **POST** `/auth/register` → Register a new user
* **POST** `/auth/login` → Authenticate user and return JWT

---

### 🎟 Event Management

* **GET** `/events` → Fetch all events
* **POST** `/events` → Create a new event *(ADMIN only)*
* **PUT** `/events/{id}` → Update event details *(ADMIN only)*
* **DELETE** `/events/{id}` → Delete an event *(ADMIN only)*

---

### 🪑 Booking Management

* **POST** `/bookings` → Book tickets for an event
* **DELETE** `/bookings/{id}` → Cancel a booking
* **GET** `/users/{id}/bookings` → Get all bookings for a user

---

---

## 🧪 Testing Strategy

* API testing using Postman
* Edge case validation
* Concurrent booking simulation

---

## 🚀 Future Enhancements

* Payment gateway integration
* Email notifications
* Redis caching
* Microservices architecture

---

## 🎯 What This Project Demonstrates

* Real-world backend system design
* Handling concurrency in databases
* Secure authentication mechanisms
* Clean, maintainable code structure

---

## 👨‍💻 Author

Himanshu Bagga 
SRM-KTR / RA2411003010445

> *“This is not just a project — it’s a simulation of how real backend systems handle users, data, and concurrency.”*
