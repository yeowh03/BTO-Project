# HDB Management System

A role-based Build-To-Order (BTO) Management System that simulates Singapore’s HDB BTO application process in a command-line interface. This system was developed for an Object-Oriented Design & Programming (OODP) assignment, incorporating layered architecture and SOLID principles.

---

## 👥 Team Members

- **Yeo Wen Hong** - Logic Flow & Coding  
- **Peng Zixiao** - Report Writing & Debugging  
- **Wong Xiao Yao** - UML and Sequence Diagrams  
- **Zou Ning** - UML and Sequence Diagrams  

---

## 📚 Table of Contents

- [Chapter 1: Requirement Analysis & Feature Selection](#chapter-1-requirement-analysis--feature-selection)
- [Chapter 2: System Architecture & Structural Planning](#chapter-2-system-architecture--structural-planning)
- [Chapter 3: Object-Oriented Design](#chapter-3-object-oriented-design)
- [Chapter 4: Implementation (Java)](#chapter-4-implementation-java)
- [Chapter 5: Testing](#chapter-5-testing)
- [Chapter 7: Reflection & Challenges](#chapter-7-reflection--challenges)

---

## Chapter 1: Requirement Analysis & Feature Selection

### 🎯 Key Requirements
- NRIC-based login with role-based access
- Role-specific functions:
  - **Applicants**: Apply/Withdraw BTO, View Projects, Enquiries
  - **Officers**: Handle Applications, Manage Projects, Booking, Enquiries
  - **Managers**: Manage Projects, Approvals, Generate Reports

### ✅ Core Features
- User Authentication and Role-based Menus
- Flat Booking, Project Visibility, Registration Workflow
- Repositories for in-memory data persistence
- Clean command-line interface (CLI)

### 🚫 Excluded
- GUI/Web-based UI
- Multi-language Support

---

## Chapter 2: System Architecture & Structural Planning

### 🧱 Layers
- **Entity Layer**: Domain classes (User, Project, Application, etc.)
- **Controller Layer**: Business logic (ApplicantController, etc.)
- **Menu Layer**: CLI navigation (ApplicantMenu, etc.)
- **Repository Layer**: In-memory data storage

### 🗺️ Design Choices
- Role-specific menus
- MVC pattern for separation of concerns
- Encapsulation of booking logic under OfficerController

---

## Chapter 3: Object-Oriented Design

### 🧩 Class Modeling
- **User Roles**: `Applicant`, `Officer`, `Manager` via inheritance
- **Domain Entities**: `Project`, `Application`, `Enquiry`, `Registration`
- **Utilities**: Enum types for `Role`, `MaritalStatus`, `UnitType`

### 🛠️ SOLID Principles
- **SRP**: Separate classes for Menu, Controller, Entity
- **OCP**: Extend ApplicantController for OfficerController
- **LSP**: Officers/Managers substitute their parent roles
- **ISP**: Interfaces per role for cleaner code
- **DIP**: Menus depend on interfaces, not implementations

---

## Chapter 4: Implementation (Java)

- **Language**: Java 17
- **IDE**: Eclipse / VSCode
- **Version Control**: GitHub

---

## Chapter 5: Testing

### 🧪 Test Strategy
- Manual functional and scenario-based testing
- Role-specific feature testing
- All edge cases (e.g., invalid login, project overlaps) covered

### 📋 Sample Test Cases
- NRIC/Password validation
- Application logic for all user roles
- Booking, Withdrawals, Visibility toggles, Enquiries, etc.

---

## Chapter 7: Reflection & Challenges

### ✅ What Went Well
- Clear role separation and clean architecture
- Full implementation of required features
- Effective use of OO principles and layered design

### ❗ What Could Be Improved
- Lack of automated/unit testing
- Time constraints affected UI-decoupling and deeper planning

### 🎓 Lessons Learned
- Importance of planning and mapping real-world scenarios
- Trade-offs between simplicity and scalability
- Benefits of controller-entity separation and interface use

---

## 📁 Project Structure (Overview)

```
/src
│
├── model/              # Domain classes (Applicant, Project, etc.)
├── controller/          # Business logic per role
├── menu/                # CLI Menus per role
├── Database/            # In-memory data handling
└── interfaces/          # Role-specific interfaces
└── auth/               # User Authentication
```
