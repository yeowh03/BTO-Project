# HDB Management System

## Team Members
| Name           | Matriculation Number |
|----------------|----------------------|
| Yeo Wen Hong   | U2421146B            |
| Peng Zixiao    | U2421551L            |
| Wong Xiao Yao  | U2421644E            |
| Zou Ning       | U2421398A            |

## 📋 Overview

The **HDB Management System** is a role-based simulation of Singapore’s Build-To-Order (BTO) process. It allows **Applicants**, **Officers**, and **Managers** to interact with BTO projects through a centralized command-line system built in Java. Each user role is assigned specific capabilities, mimicking the real-world BTO application and management experience.

---

## 📌 Features

### 🔑 Authentication & User Management
- NRIC-based login with default password (`password`)
- Change password functionality
- Role-based access control

### 👤 Applicant Capabilities
- View eligible and visible BTO projects
- Submit, withdraw, and check status of applications
- Submit, edit, delete project enquiries
- View booked flats

### 🧑‍💼 Officer Capabilities
- All applicant features
- Register to handle a project
- Manage flat availability and bookings
- Reply to enquiries
- Generate receipts

### 👨‍💼 Manager Capabilities
- Create, edit, delete BTO projects
- Toggle project visibility
- Approve/reject officer registrations and applications
- Generate booking reports
- Manage enquiries for own projects

---

## 🧠 System Architecture

### 🧱 Layered Structure (MVC + Repository)
- **Entity Layer**: Applicant, Officer, Manager, Project, etc.
- **Controller Layer**: Logic for each role (e.g. `OfficerController`, `ProjectController`)
- **Menu Layer (Boundary)**: CLI for interaction (e.g. `ApplicantMenu`, `ManagerMenu`)
- **Repository Layer**: In-memory data storage per entity type

### 📌 Design Highlights
- Clear role separation using inheritance (Officer inherits from Applicant)
- Responsibilities distributed across Controller, Menu, and Model
- Used Enum classes for status management (`ApplicationStatus`, `RegistrationStatus`)

---

## 📐 Object-Oriented Design

### SOLID Principles in Action
- **SRP**: Separate controllers, menus, and entities
- **OCP**: Extendable controllers without modifying base
- **LSP**: Officers can behave as Applicants
- **ISP**: Role-specific interfaces
- **DIP**: Menus depend on interfaces, not implementations

### UML & Sequence Diagrams
- Attached diagrams demonstrate registration and application workflows, enforcing date, role, and logic constraints.

---

## 💻 Implementation

- **Language**: Java 17
- **IDE**: Visual Studio Code / Eclipse
- **Version Control**: GitHub
- **Execution**: Run `Main.java`

---

## 🧪 Testing

### Strategy
- Manual functional testing for each major role
- Simulated realistic workflows and corner cases
- Verified both valid and invalid inputs

### Sample Test Cases
- Login and authentication
- Application submission and withdrawal
- Officer registration logic
- Manager approval and report generation

*(See Appendix for test tables and screenshots)*

---

## 📄 Documentation

- **Javadoc** included in project zip
- **Developer Guide**:
  1. Clone the repository
  2. Open project in VSCode or Eclipse
  3. Run `Main.java`

---

## 💭 Reflection

### ✅ What Went Well
- Achieved core and optional features
- Strong separation of concerns and modularity
- Applied SOLID principles effectively

### ⚠️ Areas for Improvement
- GUI was not implemented due to time constraints
- Initial controller designs tightly coupled with UI logic

### 👥 Individual Contributions
- **Wen Hong**: Logic flow, coding  
- **Zixiao**: Report, debugging  
- **Xiao Yao & Zou Ning**: UML diagrams, sequence logic

---

## 📚 Lessons Learned

- Translating real-world problems into OOP models
- Importance of controller-entity separation
- SOLID principles improve long-term maintainability
- Planning flow and data structures early reduces rework

## 📁 Project Structure (Overview)

```
/src
│
├── model/             # Domain classes (Applicant, Project, etc.)
├── controller/        # Business logic per role
├── menu/              # CLI Menus per role
├── Database/          # In-memory data handling
└── interfaces/        # Role-specific interfaces
└── auth/              # User Authentication
└── main               # Main entry point of program
└── docs               # javadoc

```
