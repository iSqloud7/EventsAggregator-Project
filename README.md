# EventsMK 🎭

A full-stack web application for discovering and managing events in Macedonia,
with an automated Python web scraper that collects and syncs event data from mktickets.mk.

## Tech Stack

**Frontend:** Vue 3, Vite, Pinia, Vue Router, Chart.js  
**Backend:** Spring Boot, Spring Data JPA, PostgreSQL  
**Scraper:** Python, BeautifulSoup, Requests, SQLAlchemy, Schedule

## Features

- Browse and search events by keyword, city, and month
- User authentication (register / login)
- Role-based access control: CLIENT, DEVELOPER, ADMIN
- Wishlist system for saving favourite events
- Admin panel for managing events and users
- Statistics dashboard with charts (events by month, users by role)
- Pagination on the events listing page
- Transliteration support for Macedonian search queries
- Automated scraper that runs every 24 hours and syncs events to the database (insert, update, delete)

## Project Structure
EventsMK/
├── backend/                          # Spring Boot application
│   └── src/main/java/mk/ukim/finki/
│       ├── controller/
│       ├── model/
│       │   ├── entities/
│       │   ├── dtos/
│       │   ├── enumerations/
│       │   └── exceptions/
│       ├── repository/
│       └── service/
│           ├── application/
│           └── domain/
├── frontend/                         # Vue 3 application
│   └── src/
│       ├── api/
│       ├── components/
│       ├── router/
│       ├── stores/
│       └── views/
└── scraper_postgres_connector/       # Python scraper + DB connector
├── mktickets_scraper/
│   ├── scrapers/
│   │   └── scraper_prod.py
│   └── events-output/
│       └── events.json
├── db/
│   ├── database.py
│   └── models.py
├── services/
│   └── event_service.py
├── scheduler.py
├── populate_db.py
├── requirements.txt
└── .env

---

## Prerequisites

Before starting, make sure you have the following installed:

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Node.js 18+](https://nodejs.org/)
- [Python 3.10+](https://www.python.org/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [pgAdmin](https://www.pgadmin.org/) *(optional, for DB management)*

---

## Step-by-Step Setup

### Step 1 — Clone the Repository
```bash
git clone https://github.com/iSqloud7/EventsAggregator-Project.git
cd EventsAggregator-Project
```

---

### Step 2 — Create the Database

1. Open **pgAdmin** (or any PostgreSQL client)
2. Create a new database named:
events-aggregator-db

---

### Step 3 — Configure the Backend

Open `backend/src/main/resources/application.properties` and set your credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/events-aggregator-db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.open-in-view=false
```

---

### Step 4 — Set Up Python Environment

Navigate to the project root:
```bash
cd EventsAggregator-Project
```

**1. Install Python** (if not already installed)  
Download from https://www.python.org/downloads/ and add Python to your system PATH (User Variables).

**2. Create a virtual environment:**
```bash
python -m venv venv
```

**3. Activate the virtual environment:**

- Windows:
```bash
.\venv\Scripts\activate
```
- macOS/Linux:
```bash
source venv/bin/activate
```

**4. Install dependencies:**
```bash
pip install -r .\requirements.txt
```

---

### Step 5 — Configure the Scraper

Create a `.env` file inside `scraper_postgres_connector/`:
DB_URL=postgresql+psycopg2://postgres:your_password@localhost:5432/events-aggregator-db

---

### Step 6 — Create Tables and Populate the Database

This will create all tables and insert the scraped events:
```bash
python .\scraper_postgres_connector\main.py
```

---

### Step 7 — Start the Scheduler (Auto-sync every 24h)

This will scrape mktickets.mk and sync the database automatically:
```bash
python .\mktickets_scraper\scheduler\scheduler.py
```

---

### Step 8 — Re-populate the Database (if needed)

If you want to manually re-sync the database at any time:
```bash
python .\scraper_postgres_connector\main.py
```

---

### Step 9 — Run the Backend
```bash
cd backend
./mvnw spring-boot:run
```

The backend will start at: `http://localhost:8080`

---

### Step 10 — Configure and Run the Frontend

**1.** Navigate to the frontend directory:
```bash
cd frontend
```

**2.** Create a `.env` file:
VITE_API_URL=http://localhost:8080/api

**3.** Install dependencies:
```bash
npm install
```

**4.** Start the development server:
```bash
npm run dev
```

The frontend will start at: `http://localhost:5173`

---

## API Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | /api/events | Get all events | Public |
| GET | /api/events/{id} | Get event by ID | Public |
| GET | /api/events/filter | Filter events | Public |
| POST | /api/events/add-event | Create event | Admin |
| PUT | /api/events/edit-event/{id} | Update event | Admin |
| DELETE | /api/events/delete-event/{id} | Delete event | Admin |
| POST | /api/users/user-register | Register | Public |
| POST | /api/users/user-login | Login | Public |
| GET | /api/users | Get all users | Admin |
| GET | /api/wishlist/{userId} | Get wishlist | Auth |
| POST | /api/wishlist/{userId}/{eventId} | Add to wishlist | Auth |
| DELETE | /api/wishlist/{userId}/{eventId} | Remove from wishlist | Auth |
| GET | /api/stats | Get statistics | Admin/Developer |
