# 🎟️ EventsMK — Events Aggregator Platform

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
```

---

### Step 3 — Create and Activate Virtual Environment (venv)

Open PowerShell inside the project root and run:

```powershell
python -m venv venv
.\venv\Scripts\activate
```

> Your terminal prompt should now show `(venv)` indicating the virtual environment is active.

---

### Step 4 — Install Python Dependencies

```powershell
(venv) PS D:\Workspace\EventsAggregator-Project> pip install -r .\requirements.txt
```

---

### Step 5 — Configure Environment Variables

**Backend configuration:**

```
EventsAggregator-Project\.env
```

**Frontend configuration:**

```
EventsAggregator-Project\frontend\.env
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
```

Enter your desired database name (must match the one set in `application.properties`).

**Using IntelliJ IDEA:**

```
Database Icon → + → Data Source → PostgreSQL
→ Test Connection → Apply → OK
```

---

### Step 8 — Creating Database tables for Events, Movies, Theater-Shows, Users and Wishlists.

Run the initial connector to the Database to create the tables (`events`, `movies`, `theater_shows`, `users`, `wishlists`) in it:

```powershell
(venv) PS D:\Workspace\EventsAggregator-Project> python .\db_postgres_manager\main.py
```

---

### Step 9 — Run the scheduler to update events, theater-shows, and movies.

To keep events, theater-shows, and movies up to date, run the scheduler manually:
Optional: (automatic update every 24 hours) -> uncomment the code in the scheduler files.

```powershell
(venv) PS D:\Workspace\EventsAggregator-Project> python .\mktickets_scraper\scheduler\events_scheduler.py
(venv) PS D:\Workspace\EventsAggregator-Project> python .\theaters_scraper\scheduler\theater_scheduler.py
(venv) PS D:\Workspace\EventsAggregator-Project> python .\mktickets_scraper\scheduler\movies_scheduler.py
```

> Once the scripts from step 8 and step 9 are complete, the database should be populated and ready. ✅

---

## ▶️ Running the Application

### Start the Backend

In IntelliJ IDEA, locate and run the main Spring Boot class:

```
BackendApplication
```

The backend will start on the configured port (default: `http://localhost:8080`).

---

### Start the Frontend

Open a new terminal window, navigate to the frontend folder, and run:

```powershell
PS D:\Workspace\EventsAggregator-Project\frontend> npm run dev
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
