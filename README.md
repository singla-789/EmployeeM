# Employee Management System

A clean, simplified, and production-ready REST API built using **Spring Boot**, **MongoDB**, and **Maven** for managing employees and departments.

---

## 📋 Features

### 🏢 Department Management
- Create new departments
- Retrieve all departments (with optional filter for active departments)
- Retrieve department details by ID
- Search departments by name
- Update department details
- Delete departments

### 👥 Employee Management
- Create new employees (linked to a department)
- Retrieve all employees (with optional **pagination** and **sorting**)
- Retrieve employee details by ID
- Retrieve all employees under a specific department
- Filter employees by status (`ACTIVE`, `INACTIVE`, `ON_LEAVE`, `TERMINATED`)
- Search employees by first/last name
- Update employee details
- Update only employment status (using `PATCH`)
- Delete employees

---

## 🛠️ Prerequisites

Ensure you have the following installed on your system:
- **Java JDK 17** or higher (Java 21+ recommended)
- **Apache Maven**
- **MongoDB** (running locally on default port `27017`)

---

## ⚙️ Database & App Configuration

The application configuration is defined in [src/main/resources/application.properties](file:///d:/Project%20java/employeemanagement/src/main/resources/application.properties):

```properties
server.port=8080
spring.data.mongodb.uri=mongodb://localhost:27017/EmployeeManagment
```

Ensure your MongoDB instance is running before starting the application.

---

## 🚀 How to Run the Application

You can compile and start the Spring Boot application using Maven:

```bash
# Clean and compile the project
mvn clean compile

# Run the Spring Boot application
mvn spring-boot:run
```

The application will start and listen on port **`8080`**.

---

## 📬 API Endpoints

### 1. Departments (`/departments`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/departments` | Create a new department |
| **GET** | `/departments` | Get all departments (supports optional query `?active=true`) |
| **GET** | `/departments/{id}` | Get department details by ID |
| **GET** | `/departments/search` | Search departments by name (e.g. `/departments/search?keyword=Eng`) |
| **PUT** | `/departments/{id}` | Update department details |
| **DELETE** | `/departments/{id}` | Delete department by ID |

#### Sample Create Department Payload:
```json
{
  "name": "Engineering",
  "description": "Software development and engineering",
  "location": "Building A",
  "active": true
}
```

---

### 2. Employees (`/employees`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/employees` | Create a new employee |
| **GET** | `/employees` | Get all employees (supports pagination: `?page=0&size=5&sort=firstName`) |
| **GET** | `/employees/{id}` | Get employee details by ID |
| **GET** | `/employees/department/{departmentId}` | Get all employees assigned to a department |
| **GET** | `/employees/status/{status}` | Filter employees by status (`ACTIVE`, `INACTIVE`, `ON_LEAVE`, `TERMINATED`) |
| **GET** | `/employees/search` | Search employees by first or last name (e.g. `/employees/search?keyword=John`) |
| **PUT** | `/employees/{id}` | Update employee details |
| **PATCH** | `/employees/{id}/status` | Update only the employment status (query: `?status=INACTIVE`) |
| **DELETE** | `/employees/{id}` | Delete employee by ID |

#### Sample Create Employee Payload:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "designation": "Software Engineer",
  "salary": 85000.0,
  "joiningDate": "2026-06-10",
  "status": "ACTIVE",
  "departmentId": "DEPARTMENT_MONGODB_ID_HERE"
}
```

---

## 🧪 Testing the APIs

For a complete guide on manual testing, refer to the [api-testing-guide.md](file:///d:/Project%20java/employeemanagement/api-testing-guide.md).

### 1. Using Postman (Recommended)
A pre-configured Postman Collection file is included in the project:
* **File name**: [EmployeeManagement.postman_collection.json](file:///d:/Project%20java/employeemanagement/EmployeeManagement.postman_collection.json)
* **How to use**: 
  1. Open Postman.
  2. Click **Import** and select the collection JSON file.
  3. Run the requests inside the folders. *(Remember to run the Department creation first, copy the generated ID, and paste it into the employee creation payload)*.

### 2. Using cURL
Example to create a department:
```bash
curl.exe -X POST http://localhost:8080/departments \
  -H "Content-Type: application/json" \
  -d '{"name": "Engineering", "description": "Software development", "location": "Building A", "active": true}'
```

---

## 📂 Project Structure

```text
employeemanagement/
├── src/
│   ├── main/
│   │   ├── java/com/hkcs/employeemanagement/
│   │   │   ├── controller/      # REST API Controllers (endpoints)
│   │   │   ├── Dto/             # Data Transfer Objects (Request/Response validation/contracts)
│   │   │   ├── Entity/          # MongoDB Document Entities (plain mapping, no clutter)
│   │   │   ├── repo/            # Spring Data MongoDB Repositories
│   │   │   ├── service/         # Business Logic layer
│   │   │   └── EmployeemanagementApplication.java # Spring Boot entry point
│   │   └── resources/
│   │       └── application.properties # Server port & MongoDB configuration
├── EmployeeManagement.postman_collection.json # Ready-to-import Postman Collection
├── api-testing-guide.md # Details on how to test each endpoint via command line
├── pom.xml # Maven dependency management
└── README.md # Project documentation for submission
```
