# Sunrise Dental Clinic — Appointment & Billing System

**CIS6003 Advanced Programming — WRIT1 Coursework (Cardiff Met / ICBT Campus)**

A distributed Java web application (Spring Boot REST API + HTML/JS frontend, backed by a
relational database) that replaces Sunrise Dental Clinic's manual paper-based appointment
process.

> ⚠️ **This project gives you a complete, working scaffold that satisfies every functional and
> non-functional requirement in the assessment brief.** You still must: (1) run and test it
> yourself, (2) write Task A's UML diagrams in your own words with your own assumptions
> (starter Mermaid versions are in `docs/UML/`), (3) push it to **your own** GitHub account with
> **your own** multi-day commit history (see `docs/GitWorkflowGuide.md`), and (4) write up the
> report around it. Submitting this unchanged as if it were entirely your own unaided work,
> without engaging with it, understanding it, or citing AI assistance where your institution's
> policy requires it, would itself be an academic integrity risk — check your module's policy
> on AI tool use before you submit.

---

## 1. What's included

```
sunrise-dental-system/
├── pom.xml                          # Maven build file (Spring Boot 3.2, Java 17)
├── src/main/java/com/sunrise/dental/
│   ├── DentalApplication.java        # Entry point
│   ├── config/SecurityConfig.java    # Task 1: authentication/authorization
│   ├── model/                        # JPA entities (User, Dentist, TreatmentType, Appointment, Bill)
│   ├── repository/                   # Spring Data JPA repositories
│   ├── dao/                          # Explicit DAO pattern layer
│   ├── pattern/singleton/            # AppointmentNumberGenerator (Singleton pattern)
│   ├── pattern/factory/              # BillFactory (Factory pattern)
│   ├── pattern/strategy/             # BillCalculationStrategy (Strategy pattern)
│   ├── service/                      # Business logic (Tasks 1–4 + reports)
│   ├── controller/                   # REST web-service endpoints
│   ├── dto/                          # Request/response objects + validation
│   └── exception/                    # Centralised error handling
├── src/main/resources/
│   ├── application.properties        # DB + server config
│   ├── schema.sql / data.sql          # (data.sql seeds dentists, treatments, staff logins)
│   └── static/                       # Plain HTML/CSS/JS frontend (menu-driven UI)
├── src/test/java/...                 # JUnit 5 + Mockito test suite (Task C)
└── docs/                             # UML diagrams, test plan, assumptions, Git guide
```

## 2. Requirements to run it

- **JDK 17+**
- **Maven 3.8+** (or use an IDE like IntelliJ IDEA / Eclipse which bundles Maven)
- Internet access the *first* time you build, so Maven can download dependencies from
  Maven Central (this sandbox could not reach Maven Central, so the project has **not** been
  compiled here — build it on your own machine)

- **XAMPP** with the **MySQL** module running. The app connects to a real MySQL database
  (`sunrise_dental`, created automatically on first run) — see `DB_SETUP.md` for step-by-step
  setup. The automated test suite still uses an in-memory H2 database internally, so tests
  never touch your real data.

## 3. How to run

```bash
# 1. Open the XAMPP Control Panel and click "Start" next to MySQL
#    (see DB_SETUP.md if this is your first time)

cd sunrise-dental-system

# 2. Run the app (downloads dependencies on first run)
mvn spring-boot:run

# In another terminal, run the test suite (uses H2, does not need XAMPP)
mvn test
```

Then open **http://localhost:8080** in your browser.

**Demo logins** (seeded in `data.sql`):
| Username    | Password      | Role  |
|-------------|---------------|-------|
| `admin`     | `password123` | ADMIN |
| `reception` | `password123` | STAFF |

You can inspect the live database through **phpMyAdmin** at **http://localhost/phpmyadmin**
(select the `sunrise_dental` schema) — phpMyAdmin ships with XAMPP, so no extra setup is needed.

## 4. Mapping requirements → implementation

| Brief requirement | Where it lives |
|---|---|
| 1. User Authentication (Login) | `SecurityConfig`, `AuthService`, `AuthController`, `login.html` |
| 2. Register New Appointment | `AppointmentService.registerAppointment`, `register.html` |
| 3. Display Appointment Details (search) | `AppointmentService.findByAppointmentNumber`, `search.html` |
| 4. Calculate and Print Bill | `BillingService`, `BillFactory`, strategy classes, `billing.html` |
| 5. Help Section | `help.html` |
| 6. Exit System | Logout button (`dashboard.html`) |
| Distributed app / web services | All `controller/*` classes — REST/JSON over HTTP, called from the JS frontend |
| Design patterns | **Singleton** (`AppointmentNumberGenerator`), **Factory** (`BillFactory`), **Strategy** (`BillCalculationStrategy` impls), **DAO** (`AppointmentDao`), overall **layered/MVC-ish 3-tier architecture** (controller → service → repository/DAO) |
| Proper database | Spring Data JPA + MySQL (via XAMPP), `data.sql`, normalized tables (users, dentists, treatment_types, appointments, bills) |
| Value-added reports | `ReportService` / `ReportController` — daily schedule + daily revenue summary (`reports.html`) |
| Input validation | Bean Validation annotations on `AppointmentRequest`/`LoginRequest`, `GlobalExceptionHandler` returns friendly messages |
| Testing / TDD | `src/test/java/...` — see `docs/TestPlan.md` |
| Git/GitHub | See `docs/GitWorkflowGuide.md` |

## 5. Design decisions & assumptions

See `docs/Assumptions.md` for the full list (e.g. one bill per appointment, emergency surcharge
rate, staff-only login with no public patient self-service portal). Document these — with your
own reasoning — in your report, as the brief explicitly asks for and rewards this.

## 6. Suggested next steps for you

1. Build it locally, click through every screen, and take screenshots for your report.
2. Read every file — don't submit code you can't explain in a viva/interview.
3. Redraw the UML diagrams in a dedicated tool (draw.io, Lucidchart, StarUML) starting from the
   Mermaid sketches in `docs/UML/`, refining them to match exactly what you built (and any
   changes you make).
4. Write and run the JUnit tests, capture the green test results as screenshots (rubric asks
   for this explicitly).
5. Create your own public GitHub repo and commit incrementally (see `docs/GitWorkflowGuide.md`)
   — do **not** upload this as one single commit.
6. Write the report following the exact formatting rules on page 3 of the brief (A4, Times New
   Roman, 1.5 spacing, Harvard referencing, etc.).
