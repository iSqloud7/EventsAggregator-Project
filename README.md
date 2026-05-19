<img width="838" height="204" alt="image" src="https://github.com/user-attachments/assets/bb4142f1-e341-40c4-8b7f-231e172ed78e" /># 🎟️ EventsMK — Events Aggregator Platform

> **EventsMK** is a full-stack web application that automatically aggregates events from various Macedonian event websites every 24 hours. It serves as a centralized hub where users can discover, save, and explore upcoming events — all in one place.

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Key Features](#-key-features)
- [User Roles](#-user-roles)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Setup & Installation](#-setup--installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [Authors](#-authors)

---

## 📖 About the Project

**EventsMK** solves a common problem — event information is scattered across dozens of websites. Instead of checking multiple platforms, EventsMK automatically scrapes event listings every 24 hours using a Python-based scheduler and stores them in a central PostgreSQL database.

Users can browse events, search by keyword, save favorites to a wishlist, and view insightful analytics about event trends. Administrators and developers have elevated permissions for full content management.

---

## ✨ Key Features

| Feature | Description |
|---|---|
| 🤖 **Auto Scraping** | Python scraper runs every 24h to fetch and store fresh events from external sites |
| 🔍 **Event Search** | Search bar to quickly filter events by name or keyword |
| ❤️ **Wishlist** | Users can save events to a personal wishlist for easy access |
| 👤 **User Profiles** | Every registered user has a personal profile page |
| 🔐 **Auth System** | Registration and login with role-based access control |
| 📊 **Statistics** | Pie chart showing the distribution of users across roles |
| 📈 **Analytics** | Monthly breakdown of active events + total event count |
| ✏️ **CRUD Operations** | Role-based create, read, update, and delete functionality for events |

---

## 👥 User Roles

EventsMK implements a three-tier role system, each with distinct permissions:

### 🔴 Admin
- Full CRUD access over all events and users
- Access to platform-wide statistics and analytics dashboards
- Manage user roles and permissions

### 🟡 Developer
- Can manage and modify event listings
- Access to technical configuration and scraper controls
- Extended analytics visibility

### 🟢 Client
- Browse and search all available events
- Add events to personal wishlist
- View and edit personal profile

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Frontend** | React + Vite (`npm run dev`) |
| **Backend** | Java Spring Boot (Maven) |
| **Database** | PostgreSQL (via pgAdmin / IntelliJ) |
| **Scraper** | Python (scheduler + scraper connector) |
| **Environment Config** | `.env` files for both frontend and backend |

---

## ✅ Prerequisites

Before you begin, make sure you have the following installed:

- **Python** (3.10+) — added as a `User Variable` in system environment variables
- **Java JDK** (17+)
- **Node.js & npm**
- **IntelliJ IDEA** (recommended IDE for backend)
- **PyCharm** (recommended IDE for frontend)
- **pgAdmin** — for PostgreSQL database management
- **Git**

---

## 🚀 Setup & Installation

Follow these steps in order to get the application running locally.

### Step 1 — Install Python

Download and install Python from [python.org](https://www.python.org). During installation, make sure to add Python to your system's **User Variables** (environment variables).

---

### Step 2 — Clone the Repository

```bash
git clone <repository-url>
cd EventsAggregator-Project
<img width="975" height="271" alt="image" src="https://github.com/user-attachments/assets/b23d23e0-d86d-4593-9a95-ec5563a36a69" />
```

---

### Step 3 — Create and Activate Virtual Environment (venv)

Open PowerShell inside the project root and run:

```powershell
python -m venv venv
.\venv\Scripts\activate
<img width="975" height="150" alt="image" src="https://github.com/user-attachments/assets/394c6a90-da0b-43d9-8c97-5a04158eee21" />
```

> Your terminal prompt should now show `(venv)` indicating the virtual environment is active.

---

### Step 4 — Install Python Dependencies

```powershell
(venv) PS D:\Workspace\EventsAggregator-Project> pip install -r .\requirements.txt
<img width="975" height="41" alt="image" src="https://github.com/user-attachments/assets/1b685a6a-9f0b-4897-84ae-67351ddc0a95" />
```

---

### Step 5 — Configure Environment Variables

**Backend configuration:**

```
EventsAggregator-Project\.env
<img width="975" height="34" alt="image" src="https://github.com/user-attachments/assets/55fdc736-1dc1-44a7-b298-adfbe26d960b" />
EventsAggregator-Project\backend\src\main\resources\application.properties
<img width="711" height="498" alt="image" src="https://github.com/user-attachments/assets/200083bf-ba90-4167-bce1-59c17af1ad7a" />
```

**Frontend configuration:**

```
EventsAggregator-Project\frontend\.env
<img width="681" height="48" alt="image" src="https://github.com/user-attachments/assets/b4e42faf-ea2c-4412-ae6d-69ee491772c7" />
```

> ⚠️ Make sure to fill in all required values (database credentials, API keys, ports, etc.) before proceeding.

---

### Step 6 — Configure Maven (Backend)

1. In IntelliJ IDEA, locate the file: `EventsAggregator-Project\backend\pom.xml`
2. **Unlink** the Maven project first (right-click → Maven → Unlink)
3. Then **re-link** it (right-click → Maven → Link Maven Project)

This ensures IntelliJ properly re-indexes the Maven configuration.

---

### Step 7 — Create the PostgreSQL Database

**Using pgAdmin:**

```
Database → Create → Database
<img width="751" height="584" alt="image" src="https://github.com/user-attachments/assets/9c5d79ce-93d1-4573-a4e6-d0d80592d804" />
```

Enter your desired database name (must match the one set in `application.properties`).

**Using IntelliJ IDEA:**

```
Database Icon → + → Data Source → PostgreSQL
<img width="995" height="846" alt="image" src="https://github.com/user-attachments/assets/8a819fff-7b35-45a5-a280-d5d363969be5" />
→ Test Connection → Apply → OK
```

---

### Step 8 — Populate the Database with Events

Run the initial scraper to create the `events` table and populate it with data:

```powershell
(venv) PS D:\Workspace\EventsAggregator-Project> python .\scraper_postgres_connector\main.py
<img width="975" height="34" alt="image" src="https://github.com/user-attachments/assets/2442a92e-79d8-4d04-af97-65533c8aeabc" />
```

---

### Step 9 — Run the Event Update Scheduler

To keep events up to date (runs every 24 hours), execute the scheduler:

```powershell
(venv) PS D:\Workspace\EventsAggregator-Project> python .\mktickets_scraper\scheduler\scheduler.py
<img width="975" height="34" alt="image" src="https://github.com/user-attachments/assets/bbdd58e4-03e0-414f-811d-a44ec1904d9b" />
```

> After both scripts complete, the database should be populated and ready. ✅

---

## ▶️ Running the Application

### Start the Backend

In IntelliJ IDEA, locate and run the main Spring Boot class:

```
BackendApplication
<img width="975" height="297" alt="image" src="https://github.com/user-attachments/assets/f41ad246-b73a-42cb-9779-77fcaf7c360e" />
```

The backend will start on the configured port (default: `http://localhost:8080`).

---

### Start the Frontend

Open a new terminal window, navigate to the frontend folder, and run:

```powershell
<img width="838" height="204" alt="image" src="https://github.com/user-attachments/assets/80b0f474-4caa-41fe-babc-e08ca9fd3453" />
PS D:\Workspace\EventsAggregator-Project\frontend> npm run dev
<img width="573" height="227" alt="image" src="https://github.com/user-attachments/assets/bc0df9bc-ce87-47e4-9e3e-4cb6335b7108" />
```

The frontend will be available at: `http://localhost:5173` (or as configured in `.env`).

---

## 🎉 Application Successfully Running!

Once both the backend and frontend are up, open your browser and navigate to the frontend URL. You should see the EventsMK platform ready to use.

---

## 👨‍💻 Authors

This project was developed as part of an academic assignment.

| Name | Index |
|---|---|
| Иван Пупиноски | 223260 |
| Клаудија Стаменова | 223282 |
| Марија Цветаноска | 223109 |

---

<div align="center">
  <sub>Built with ❤️ in Macedonia!</sub>
</div>
