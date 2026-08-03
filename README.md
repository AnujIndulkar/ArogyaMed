# 💊 ArogyaMed – Smart Medicine Supply & Verification Platform

<p align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-success?style=for-the-badge)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-brown?style=for-the-badge&logo=hibernate)
![MySQL](https://img.shields.io/badge/MySQL-8-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![REST API](https://img.shields.io/badge/API-REST-informational?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

</p>

---

# 📖 About ArogyaMed

**ArogyaMed** is a **full-stack enterprise Medicine Supply & Verification Platform** developed using **Java Spring Boot** and **React**.

The platform securely connects **Patients, Doctors, Pharmacists, Medicine Companies, Wholesalers, Delivery Partners, Ambulance Providers, and Administrators** into a single digital ecosystem.

Unlike traditional pharmacy systems, ArogyaMed manages the **complete medicine lifecycle**, from manufacturing to patient delivery, while ensuring medicine authenticity, prescription verification, inventory tracking, quality inspection, secure payments, emergency support, and role-based access control.

---

# 🎯 Project Objectives

- Digital medicine supply chain
- Prevent counterfeit medicines
- Secure prescription verification
- Smart inventory management
- Online medicine ordering
- Delivery tracking
- Quality inspection
- QR/Barcode verification
- Emergency SOS support
- Digital medical records
- Enterprise dashboard & analytics

---

# 👥 User Roles

| Role | Description |
|-------|-------------|
| 👤 Patient | Purchase medicines, upload prescriptions, view records |
| 👨‍⚕️ Doctor | Manage appointments & generate prescriptions |
| 💊 Pharmacist | Verify prescriptions & dispense medicines |
| 📦 Wholesaler | Supply medicines to pharmacies |
| 🏭 Medicine Company | Manufacture & distribute medicines |
| 🚚 Delivery Partner | Deliver medicine orders |
| 🚑 Ambulance Provider | Emergency transportation |
| 🛡️ Admin | Complete platform management |

---

# ✨ Core Features

- 🔐 JWT Authentication & Authorization
- 👥 Role-Based Access Control (RBAC)
- 📄 KYC Verification
- 💊 Medicine Management
- 📦 Inventory Management
- 🛒 Medicine Ordering
- 🚚 Live Delivery Tracking
- 📄 Prescription Verification
- 🏥 Medical Records
- 📅 Appointment Management
- 🚑 SOS Emergency Service
- 💳 Payment Integration
- 🔔 Real-Time Notifications
- ⭐ Reviews & Ratings
- 📊 Analytics Dashboard
- 📈 Reports
- 📱 QR & Barcode Verification
- 🧪 Quality Inspection
- 📜 Audit Logs
- 🔍 Advanced Search & Filtering

---

# 🏗 System Architecture

```
                   +----------------------+
                   |      Frontend        |
                   | React + TypeScript   |
                   +----------+-----------+
                              |
                              |
                     REST API (HTTPS)
                              |
+------------------------------------------------------------+
|                  Spring Boot Backend                        |
|                                                            |
| Authentication • Authorization • Business Logic            |
| Medicine • Orders • Inventory • Prescriptions              |
| Dashboard • Reports • Payments • Notifications             |
+--------------------------+---------------------------------+
                           |
                    Spring Data JPA
                           |
                    Hibernate ORM
                           |
                      MySQL Database
```

---

# 🛠 Technology Stack

## Backend

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate ORM
- Maven
- REST APIs
- Lombok

## Frontend

- React
- TypeScript
- Vite
- Tailwind CSS
- React Query
- Axios
- React Router
- Framer Motion

## Database

- MySQL 8

## Tools

- Git
- GitHub
- IntelliJ IDEA
- VS Code
- Postman
- Maven

---

# 📂 Project Structure

```
ArogyaMed
│
├── backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── dto
│   ├── security
│   ├── config
│   └── utils
│
├── frontend
│   ├── components
│   ├── pages
│   ├── layouts
│   ├── hooks
│   ├── contexts
│   ├── services
│   ├── routes
│   ├── theme
│   └── assets
│
└── README.md
```

---

# 📊 Project Statistics

| Category | Count |
|-----------|------:|
| User Roles | 8 |
| Backend Modules | 29 |
| REST APIs | 120+ |
| Database Tables | 25+ |
| Dashboards | 8 |
| JWT Authentication | ✅ |
| RBAC | ✅ |
| QR/Barcode | ✅ |
| KYC Verification | ✅ |
| Audit Logs | ✅ |

---

# 🚀 Getting Started

Follow these steps to set up the ArogyaMed project on your local machine.

---

# 📋 Prerequisites

Make sure the following software is installed before running the project.

| Software | Version |
|----------|---------|
| Java | 17+ |
| Maven | 3.9+ |
| Node.js | 20+ |
| npm | 10+ |
| MySQL | 8.0+ |
| Git | Latest |
| IntelliJ IDEA / VS Code | Latest |

---

# 📥 Clone Repository

```bash
git clone https://github.com/AnujIndulkar/ArogyaMed.git
```

```bash
cd ArogyaMed
```

---

# ⚙ Backend Setup

Navigate to the backend project.

```bash
cd healthcare
```

Install dependencies.

```bash
mvn clean install
```

Run the application.

```bash
mvn spring-boot:run
```

Backend will start at

```
http://localhost:8080
```

---

# 💻 Frontend Setup

Navigate to frontend.

```bash
cd arogyamed-frontend
```

Install packages.

```bash
npm install
```

Run development server.

```bash
npm run dev
```

Frontend will start at

```
http://localhost:5173
```

---

# 🗄 Database Configuration

Create a MySQL database.

```sql
CREATE DATABASE arogyamed;
```

Update your **application.properties**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/arogyamed
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true
```

---

# 🌍 Environment Variables

Frontend (`.env`)

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

Backend

```properties
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000
```

---

# ▶ Running the Application

### Backend

```bash
mvn spring-boot:run
```

### Frontend

```bash
npm run dev
```

---

# 🔐 Authentication

ArogyaMed uses **JWT (JSON Web Token)** authentication.

Workflow

```
Register
      │
      ▼
Login
      │
      ▼
Receive JWT Token
      │
      ▼
Store Token
      │
      ▼
Attach Token in Every Request
      │
      ▼
Authorized Access
```

Authorization Header

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 🔑 Role-Based Access Control (RBAC)

| Role | Access |
|------|--------|
| Patient | Order medicines, prescriptions, appointments |
| Doctor | Patients, prescriptions, appointments |
| Pharmacist | Inventory, prescription verification |
| Wholesaler | Bulk medicine supply |
| Company | Medicine manufacturing & supply |
| Delivery Partner | Deliver assigned orders |
| Ambulance Provider | Emergency services |
| Admin | Complete platform management |

---

# 📦 Backend Modules

| Module | Description |
|---------|-------------|
| Authentication | Login, Register, JWT |
| User Management | User CRUD & RBAC |
| Patient | Patient profile & dashboard |
| Doctor | Doctor management |
| Pharmacist | Pharmacy operations |
| Wholesaler | Wholesale management |
| Medicine Company | Manufacturing & supply |
| Delivery Partner | Delivery operations |
| Ambulance Provider | Emergency services |
| Admin | Platform administration |
| Medicine | Medicine catalog |
| Inventory | Stock management |
| Orders | Medicine ordering |
| Delivery Tracking | Real-time tracking |
| Prescription | Digital prescriptions |
| Medical Records | Patient history |
| Appointments | Doctor appointments |
| Payments | Online payments |
| Notifications | Alerts & reminders |
| Reviews | Ratings & feedback |
| Address | Address management |
| SOS | Emergency requests |
| KYC | Identity verification |
| QR / Barcode | Product verification |
| Quality Inspection | Medicine quality checks |
| Dashboard | Analytics |
| Reports | Business reports |
| Audit Logs | Activity tracking |
| Advanced Search | Global search & filters |

---

# 📁 Backend Package Structure

```
com.arogyamed.healthcare
│
├── config
├── controller
├── dto
├── model
├── repository
├── security
├── service
│     └── impl
├── util
└── HealthcareApplication.java
```

---

# 🎨 Frontend Structure

```
src
│
├── api
├── assets
├── components
├── constants
├── contexts
├── hooks
├── layouts
├── pages
├── routes
├── services
├── theme
├── types
├── utils
├── App.tsx
└── main.tsx
```

---

# 🔄 Request Flow

```
React Frontend
      │
Axios API
      │
Spring Security
      │
JWT Filter
      │
Controller
      │
Service Layer
      │
Repository
      │
Hibernate
      │
MySQL
```

---

# 📌 API Base URL

```
http://localhost:8080/api
```

Example

```
GET /api/medicines
POST /api/orders
POST /api/auth/login
```

---

# 📈 Current Project Status

| Component | Status |
|-----------|--------|
| Backend Development | ✅ |
| Frontend Development | ✅ |
| Authentication | ✅ |
| JWT Security | ✅ |
| Database Design | ✅ |
| REST APIs | ✅ |
| Inventory Module | ✅ |
| Order Module | ✅ |
| Dashboard | ✅ |
| QR / Barcode | ✅ |
| Quality Inspection | ✅ |
| Documentation | 🚧 In Progress |

---

# 📡 REST API Documentation

> **Base URL**

```
http://localhost:8080/api
```

> **Authentication**

Most endpoints require a valid JWT token.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 🔐 Authentication APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login & receive JWT |
| POST | `/api/auth/refresh-token` | Refresh JWT token |
| POST | `/api/auth/logout` | Logout current user |
| POST | `/api/auth/forgot-password` | Send password reset link |
| POST | `/api/auth/reset-password` | Reset password |
| POST | `/api/auth/change-password` | Change password |
| GET | `/api/auth/profile` | Get logged-in profile |

---

# 👤 User Management APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get user details |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |
| PATCH | `/api/users/{id}/status` | Update account status |
| GET | `/api/users/search` | Search users |
| GET | `/api/users/role/{role}` | Filter users by role |

---

# 👤 Patient APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/patients/dashboard` | Patient dashboard |
| GET | `/api/patients/profile` | View profile |
| PUT | `/api/patients/profile` | Update profile |
| GET | `/api/patients/orders` | Order history |
| GET | `/api/patients/prescriptions` | Prescription history |
| GET | `/api/patients/medical-records` | Medical records |
| GET | `/api/patients/appointments` | Appointment history |
| GET | `/api/patients/notifications` | User notifications |

---

# 👨‍⚕️ Doctor APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/doctors` | List doctors |
| GET | `/api/doctors/{id}` | Doctor details |
| PUT | `/api/doctors/{id}` | Update profile |
| GET | `/api/doctors/dashboard` | Doctor dashboard |
| GET | `/api/doctors/patients` | Assigned patients |
| GET | `/api/doctors/appointments` | View appointments |
| POST | `/api/doctors/prescriptions` | Create prescription |
| GET | `/api/doctors/earnings` | Earnings summary |

---

# 💊 Pharmacist APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/pharmacists/dashboard` | Pharmacist dashboard |
| GET | `/api/pharmacists/orders` | Medicine orders |
| GET | `/api/pharmacists/inventory` | Pharmacy inventory |
| POST | `/api/pharmacists/verify-prescription` | Verify prescription |
| POST | `/api/pharmacists/dispense` | Dispense medicines |
| PATCH | `/api/pharmacists/orders/{id}/approve` | Approve order |
| PATCH | `/api/pharmacists/orders/{id}/reject` | Reject order |

---

# 📦 Wholesaler APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/wholesalers/dashboard` | Dashboard overview |
| GET | `/api/wholesalers/orders` | Wholesale orders |
| GET | `/api/wholesalers/inventory` | Available inventory |
| POST | `/api/wholesalers/supply` | Supply medicines |
| POST | `/api/wholesalers/purchase-orders` | Create purchase order |
| PATCH | `/api/wholesalers/orders/{id}/dispatch` | Dispatch order |

---

# 🏭 Medicine Company APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/companies/dashboard` | Company dashboard |
| GET | `/api/companies/profile` | Company profile |
| PUT | `/api/companies/profile` | Update profile |
| POST | `/api/companies/medicines` | Register medicine |
| PUT | `/api/companies/medicines/{id}` | Update medicine |
| DELETE | `/api/companies/medicines/{id}` | Remove medicine |
| GET | `/api/companies/orders` | Supply orders |
| GET | `/api/companies/batches` | Medicine batches |

---

# 🚚 Delivery Partner APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/delivery-partners/dashboard` | Delivery dashboard |
| GET | `/api/delivery-partners/orders` | Assigned deliveries |
| PATCH | `/api/delivery-partners/orders/{id}/accept` | Accept delivery |
| PATCH | `/api/delivery-partners/orders/{id}/pickup` | Mark as picked up |
| PATCH | `/api/delivery-partners/orders/{id}/deliver` | Complete delivery |
| PATCH | `/api/delivery-partners/location` | Update live location |
| GET | `/api/delivery-partners/earnings` | Earnings history |

---

# 🚑 Ambulance Provider APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/ambulances/dashboard` | Ambulance dashboard |
| POST | `/api/ambulances/request` | Request ambulance |
| GET | `/api/ambulances/requests` | Active requests |
| PATCH | `/api/ambulances/{id}/accept` | Accept emergency |
| PATCH | `/api/ambulances/{id}/reject` | Reject emergency |
| PATCH | `/api/ambulances/{id}/complete` | Complete service |
| GET | `/api/ambulances/history` | Service history |

---

# 🛡️ Admin APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/admin/dashboard` | Admin dashboard |
| GET | `/api/admin/users` | View all users |
| GET | `/api/admin/orders` | View all orders |
| GET | `/api/admin/medicines` | View medicines |
| PATCH | `/api/admin/users/{id}/block` | Block user |
| PATCH | `/api/admin/users/{id}/unblock` | Unblock user |
| DELETE | `/api/admin/users/{id}` | Delete user |
| GET | `/api/admin/reports` | Generate reports |
| GET | `/api/admin/statistics` | Platform analytics |
| GET | `/api/admin/audit-logs` | System audit logs |

---

# 💊 Medicine APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/medicines` | List all medicines |
| GET | `/api/medicines/{id}` | Get medicine details |
| POST | `/api/medicines` | Add new medicine |
| PUT | `/api/medicines/{id}` | Update medicine |
| DELETE | `/api/medicines/{id}` | Delete medicine |
| GET | `/api/medicines/search` | Search medicines |
| GET | `/api/medicines/category/{category}` | Medicines by category |
| GET | `/api/medicines/barcode/{barcode}` | Find by barcode |

---

# 📦 Inventory APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/inventory` | View inventory |
| POST | `/api/inventory` | Add inventory |
| PUT | `/api/inventory/{id}` | Update inventory |
| DELETE | `/api/inventory/{id}` | Remove inventory |
| GET | `/api/inventory/low-stock` | Low stock items |
| GET | `/api/inventory/expired` | Expired medicines |

---

# 🛒 Order APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/orders` | Place order |
| GET | `/api/orders` | List orders |
| GET | `/api/orders/{id}` | Order details |
| PATCH | `/api/orders/{id}/confirm` | Confirm order |
| PATCH | `/api/orders/{id}/ship` | Ship order |
| PATCH | `/api/orders/{id}/deliver` | Complete delivery |
| PATCH | `/api/orders/{id}/cancel` | Cancel order |
| GET | `/api/orders/history` | Order history |

---

# 🚚 Delivery Tracking APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/tracking/{trackingNumber}` | Track shipment |
| PATCH | `/api/tracking/update-location` | Update location |
| PATCH | `/api/tracking/update-status` | Update status |
| GET | `/api/tracking/order/{orderId}` | Track by order |

---

# 📄 Prescription APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/prescriptions` | Upload prescription |
| GET | `/api/prescriptions` | List prescriptions |
| GET | `/api/prescriptions/{id}` | Prescription details |
| PATCH | `/api/prescriptions/{id}/verify` | Verify prescription |
| PATCH | `/api/prescriptions/{id}/reject` | Reject prescription |

---

# 🏥 Medical Record APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/medical-records` | Create record |
| GET | `/api/medical-records` | List records |
| GET | `/api/medical-records/{id}` | Record details |
| PUT | `/api/medical-records/{id}` | Update record |
| DELETE | `/api/medical-records/{id}` | Delete record |

---

# 📅 Appointment APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/appointments` | Book appointment |
| GET | `/api/appointments` | List appointments |
| GET | `/api/appointments/{id}` | Appointment details |
| PATCH | `/api/appointments/{id}/approve` | Approve appointment |
| PATCH | `/api/appointments/{id}/cancel` | Cancel appointment |
| PATCH | `/api/appointments/{id}/complete` | Complete appointment |

---

# 💳 Payment APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/payments/initiate` | Start payment |
| POST | `/api/payments/verify` | Verify payment |
| GET | `/api/payments/{id}` | Payment details |
| GET | `/api/payments/history` | Payment history |
| POST | `/api/payments/refund` | Refund payment |

---

# 🔔 Notification APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/notifications` | List notifications |
| PATCH | `/api/notifications/{id}/read` | Mark as read |
| PATCH | `/api/notifications/read-all` | Read all |
| DELETE | `/api/notifications/{id}` | Delete notification |

---

# ⭐ Review APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/reviews` | Add review |
| GET | `/api/reviews` | List reviews |
| PUT | `/api/reviews/{id}` | Update review |
| DELETE | `/api/reviews/{id}` | Delete review |

---

# 📍 Address APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/addresses` | Add address |
| GET | `/api/addresses` | List addresses |
| PUT | `/api/addresses/{id}` | Update address |
| DELETE | `/api/addresses/{id}` | Delete address |
| PATCH | `/api/addresses/{id}/default` | Set default |

---

# 🚨 SOS APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/sos` | Send emergency alert |
| GET | `/api/sos/active` | Active alerts |
| PATCH | `/api/sos/{id}/resolve` | Resolve alert |
| GET | `/api/sos/history` | SOS history |

---

# 🪪 KYC Verification APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/kyc/upload` | Upload documents |
| GET | `/api/kyc/status` | View status |
| GET | `/api/kyc/pending` | Pending requests |
| PATCH | `/api/kyc/{id}/approve` | Approve KYC |
| PATCH | `/api/kyc/{id}/reject` | Reject KYC |

---

# 📱 QR / Barcode APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/barcodes/generate` | Generate barcode |
| POST | `/api/barcodes/verify` | Verify barcode |
| GET | `/api/barcodes/{id}` | Barcode details |
| GET | `/api/barcodes/scan/{barcode}` | Scan barcode |

---

# 🧪 Quality Inspection APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/quality-checks` | Create inspection |
| GET | `/api/quality-checks` | Inspection list |
| GET | `/api/quality-checks/{id}` | Inspection details |
| PATCH | `/api/quality-checks/{id}/approve` | Approve batch |
| PATCH | `/api/quality-checks/{id}/reject` | Reject batch |

---

# 📊 Dashboard APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/dashboard/admin` | Admin dashboard |
| GET | `/api/dashboard/patient` | Patient dashboard |
| GET | `/api/dashboard/doctor` | Doctor dashboard |
| GET | `/api/dashboard/pharmacist` | Pharmacist dashboard |
| GET | `/api/dashboard/company` | Company dashboard |
| GET | `/api/dashboard/wholesaler` | Wholesaler dashboard |
| GET | `/api/dashboard/delivery-partner` | Delivery dashboard |
| GET | `/api/dashboard/ambulance` | Ambulance dashboard |

---

# 📈 Report APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/reports/sales` | Sales report |
| GET | `/api/reports/orders` | Order report |
| GET | `/api/reports/revenue` | Revenue report |
| GET | `/api/reports/inventory` | Inventory report |
| GET | `/api/reports/users` | User report |

---

# 📜 Audit Log APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/audit-logs` | Activity logs |
| GET | `/api/audit-logs/user/{id}` | User logs |
| GET | `/api/audit-logs/module/{module}` | Module logs |

---

# 🔍 Advanced Search APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/search/global` | Global search |
| GET | `/api/search/medicines` | Search medicines |
| GET | `/api/search/doctors` | Search doctors |
| GET | `/api/search/pharmacies` | Search pharmacies |
| GET | `/api/search/orders` | Search orders |
| GET | `/api/search/users` | Search users |

---

# 📌 API Summary

| Category | Count |
|----------|------:|
| Backend Modules | 29 |
| REST APIs | 120+ |
| User Roles | 8 |
| Authentication | JWT |
| Authorization | Role-Based (RBAC) |
| Database | MySQL |
| Architecture | RESTful |
| Response Format | JSON |
| API Version | v1 |

---

