# API Testing Guide

This guide describes how to test the Employee Management REST API using either **Postman** or **cURL**.

The server is currently running on `http://localhost:8080`.

---

## 🚀 Recommended Testing Sequence
Because employees are linked to departments, you should follow this sequence to test:
1. **Create a Department** and copy the generated `"id"` from the response.
2. **Create an Employee** using the copied `"id"` in the `"departmentId"` field.
3. Test retrieval, update, search, and delete endpoints.

---

## 📬 Testing with Postman (Easiest)

1. Open **Postman**.
2. Click **Import** in the top-left corner.
3. Drag and drop or browse to select the file [EmployeeManagement.postman_collection.json](file:///d:/Project%20java/employeemanagement/EmployeeManagement.postman_collection.json) located in the project root directory.
4. Once imported, you will see a folder named **Employee Management API** containing **Departments** and **Employees** subfolders.
5. Create a department, copy the ID from the response, and use it to replace `REPLACE_WITH_DEPT_ID` in the Employee requests.

---

## 💻 Testing with cURL

Open your terminal (Command Prompt, PowerShell, or Git Bash) and run the commands in the order below.

> [!NOTE]
> For PowerShell users, make sure to use `curl.exe` instead of `curl` (which is an alias to `Invoke-WebRequest`).

### 1. Department Endpoints

#### A. Create a Department
Run this first to generate a department.

```bash
curl.exe -X POST http://localhost:8080/departments \
  -H "Content-Type: application/json" \
  -d '{"name": "Engineering", "description": "Software development and engineering", "location": "Building A", "active": true}'
```
*Note the returned `"id"` (e.g. `64a2b1c...`). Use it to replace `<DEPT_ID>` in subsequent commands.*

#### B. Get All Departments
```bash
curl.exe -i -X GET http://localhost:8080/departments
```

#### C. Get Active Departments
```bash
curl.exe -i -X GET "http://localhost:8080/departments?active=true"
```

#### D. Get Department by ID
```bash
curl.exe -i -X GET http://localhost:8080/departments/<DEPT_ID>
```

#### E. Search Departments
```bash
curl.exe -i -X GET "http://localhost:8080/departments/search?keyword=Eng"
```

#### F. Update Department
```bash
curl.exe -X PUT http://localhost:8080/departments/<DEPT_ID> \
  -H "Content-Type: application/json" \
  -d '{"name": "Engineering & Technology", "description": "Core tech development", "location": "Building B", "active": true}'
```

---

### 2. Employee Endpoints

#### A. Create an Employee
Use the `<DEPT_ID>` from Step 1.

```bash
curl.exe -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{"firstName": "John", "lastName": "Doe", "email": "john.doe@example.com", "phone": "1234567890", "designation": "Software Engineer", "salary": 85000.00, "joiningDate": "2026-06-10", "status": "ACTIVE", "departmentId": "<DEPT_ID>"}'
```
*Note the returned `"id"` (e.g. `64a2b2f...`). Use it to replace `<EMP_ID>` in subsequent commands.*

#### B. Get All Employees (Paginated & Sorted)
```bash
curl.exe -i -X GET "http://localhost:8080/employees?page=0&size=5&sort=firstName"
```

#### C. Get Employee by ID
```bash
curl.exe -i -X GET http://localhost:8080/employees/<EMP_ID>
```

#### D. Get Employees by Department
```bash
curl.exe -i -X GET http://localhost:8080/employees/department/<DEPT_ID>
```

#### E. Get Employees by Status
Supported statuses: `ACTIVE`, `INACTIVE`, `ON_LEAVE`, `TERMINATED`.
```bash
curl.exe -i -X GET http://localhost:8080/employees/status/ACTIVE
```

#### F. Search Employees by Name
```bash
curl.exe -i -X GET "http://localhost:8080/employees/search?keyword=John"
```

#### G. Update Employee
```bash
curl.exe -X PUT http://localhost:8080/employees/<EMP_ID> \
  -H "Content-Type: application/json" \
  -d '{"firstName": "John", "lastName": "Doe Updated", "email": "john.doe.updated@example.com", "phone": "9876543210", "designation": "Senior Software Engineer", "salary": 95000.00, "joiningDate": "2026-06-10", "status": "ACTIVE", "departmentId": "<DEPT_ID>"}'
```

#### H. Update Employee Status (PATCH)
```bash
curl.exe -i -X PATCH "http://localhost:8080/employees/<EMP_ID>/status?status=INACTIVE"
```

---

### 3. Cleanup Endpoints

#### A. Delete Employee
```bash
curl.exe -i -X DELETE http://localhost:8080/employees/<EMP_ID>
```

#### B. Delete Department
```bash
curl.exe -i -X DELETE http://localhost:8080/departments/<DEPT_ID>
```
