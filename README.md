# 🏥 Medical Inventory Management System (Spring Boot)

A backend system for managing medical items, categories, inventory, and suppliers. Built using **Spring Boot**, this RESTful API helps efficiently organize medical stock, handle item categories, and manage suppliers.

## 🚀 Features

- ✅ CRUD Operations for Medical Items
- 📦 Category Management (One Category → Many Items)
- 🧾 Supplier Management
- 🔗 Entity Relationships using JPA/Hibernate
- 🔒 Input Validation & Exception Handling
- 🧪 Tested via Postman
- 🌱 DTO Usage for clean request/response objects
- 📚 Pagination & Sorting
- 🔐 JWT Authentication (Upcoming)
- 🔁 Forgot & Reset Password (Upcoming)

---

## 🛠️ Tech Stack

- Java 21+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Postman (for API testing)

---

## 📌 Sample API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/medical-items` | Add a medical item |
| `GET` | `/api/medical-items` | Get all items |
| `GET` | `/api/medical-items/{id}` | Get item by ID |
| `PUT` | `/api/medical-items/{id}` | Update item |
| `DELETE` | `/api/medical-items/{id}` | Delete item |

---

## 🔍 Example Request (Add Medical Item)

```json
{
  "name": "Paracetamol",
  "description": "Pain reliever",
  "expiryDate": "2025-12-31",
  "batchNumber": "ABC123",
  "unitPrice": 12.50,
  "category": {
    "name": "Painkillers"
  },
  "supplier": {
    "name": "MedSupplier Inc.",
    "contact": "09123456789",
    "address": "Manila, PH"
  },
  "inventory": {
    "location": "Main Storage A",
    "quantity": 100
  }
}
