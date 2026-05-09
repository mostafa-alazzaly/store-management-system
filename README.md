# Store Management System

A scalable backend system built using Spring Boot for managing products, suppliers, invoices, payments, users, and inventory transactions.

The project focuses on building secure and maintainable RESTful APIs following clean layered architecture principles and role-based access control using Spring Security.

---

## Features

- Authentication & Role-Based Authorization using Spring Security
- Product Management
- Category Management
- Supplier Management
- Customer Management
- Employee Management
- Invoice & Payment Handling
- Inventory Transactions
- Product-Supplier Management
- RESTful APIs
- Pagination & Filtering
- DTO Validation & Exception Handling
- Layered Architecture

---

## Tech Stack

### Backend & Frameworks
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- MySQL

### Tools
- Maven
- Postman
- Git & GitHub
- IntelliJ IDEA

---

## Architecture

The project follows a layered architecture:

- Controller Layer
- Service Layer
- Repository Layer

This structure improves:
- Maintainability
- Scalability
- Separation of Concerns
- Code Reusability

---

## Security

The application uses Spring Security with:

- HTTP Basic Authentication
- Role-Based Authorization
- BCrypt Password Encoding

Supported Roles:
- ADMIN
- MANAGER
- EMPLOYEE
- CUSTOMER

---

## Main Modules

### Products
- Create Products
- Update Products
- Activate / Deactivate Products
- Barcode Management

### Categories
- Create Categories
- Update Categories
- Activate / Deactivate Categories

### Suppliers
- Supplier CRUD Operations
- Product-Supplier Relationships

### Customers & Employees
- Manage Customers
- Manage Employees
- User & Role Management

### Invoices & Payments
- Create Invoices
- Post / Cancel Invoices
- Payment Processing

### Inventory Transactions
- Inventory In / Out Operations
- Reverse Inventory Transactions

---

## API Examples

### Products
```http
GET /api/products
POST /api/products
PATCH /api/products/{id}/activate
PATCH /api/products/{id}/deactivate
DELETE /api/products/{id}
```

### Categories
```http
GET /api/categories
POST /api/categories
PATCH /api/categories/{id}/activate
PATCH /api/categories/{id}/deactivate
DELETE /api/categories/{id}
```

### Suppliers
```http
GET /api/suppliers
POST /api/suppliers
DELETE /api/suppliers/{id}
```

### Invoices
```http
GET /api/invoices
POST /api/invoices
POST /api/invoices/{id}/post
POST /api/invoices/{id}/cancel
```

### Payments
```http
GET /api/payments
POST /api/payments
```

### Inventory Transactions
```http
GET /api/inventory-transactions
POST /api/inventory-transactions
POST /api/inventory-transactions/{id}/reverse
```

---

## Requirements

Before running the project, make sure you have installed:

- Java 17+
- Maven
- MySQL

---

## Run Locally

### Clone Repository

```bash
git clone https://github.com/mostafa-alazzaly/store-management-system.git
cd store-management-system
```

### Configure Database

Update database configuration inside:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/store_management
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Future Improvements

- JWT Authentication
- Docker Support
- Microservices Architecture
- CI/CD Pipeline
- Unit & Integration Testing
- Cloud Deployment

---

## Author

Mostafa Alazzaly

GitHub:
https://github.com/mostafa-alazzaly
