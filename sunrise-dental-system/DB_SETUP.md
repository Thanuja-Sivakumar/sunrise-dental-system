# Database Setup — XAMPP MySQL

The app now runs on a real MySQL database instead of the embedded H2 database. Follow these
steps once, on the machine you're going to run the app from.

## 1. Install XAMPP (skip if already installed)

Download from https://www.apachefriends.org/ and install it for your OS.

## 2. Start the MySQL module

1. Open the **XAMPP Control Panel**.
2. Click **Start** on the **MySQL** row (you do *not* need to start Apache unless you also
   want to use phpMyAdmin's web UI — MySQL alone is enough to run the app).
3. Confirm the row turns green and shows a port number (default `3306`).

## 3. That's it — no manual database creation needed

The connection string in `src/main/resources/application.properties` includes
`createDatabaseIfNotExist=true`, so when you run the app for the first time, Spring Boot
will:

1. Connect to MySQL as `root` (XAMPP's default admin user, blank password by default).
2. Create the `sunrise_dental` schema automatically.
3. Create all the tables (`users`, `dentists`, `treatment_types`, `appointments`, `bills`)
   because `spring.jpa.hibernate.ddl-auto=update` is set.
4. Insert the seed data from `data.sql` (the demo logins and reference lists).

## 4. Run the app

```bash
cd sunrise-dental-system
mvn spring-boot:run
```

Open **http://localhost:8080** and log in with:

| Username    | Password      | Role  |
|-------------|---------------|-------|
| `admin`     | `password123` | ADMIN |
| `reception` | `password123` | STAFF |

## 5. If your XAMPP MySQL has a root password set

Most XAMPP installs leave the `root` password blank. If you changed it (or you're on a
shared/lab machine where it's already set), update these two lines in
`src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE
```

## 6. If port 3306 is already in use / you changed the MySQL port in XAMPP

Update the port in the JDBC URL in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/sunrise_dental?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

(replace `3307` with whatever port your MySQL is actually listening on — check the XAMPP
Control Panel's "Admin"/"Config" for `my.ini` if unsure).

## 7. Inspecting the data

With Apache also running in XAMPP, open **http://localhost/phpmyadmin**, select the
`sunrise_dental` database in the left sidebar, and browse the tables directly.

## 8. Starting fresh (wiping all data)

To reset the database back to just the seed data:

1. Open phpMyAdmin (or a MySQL client of your choice).
2. Drop the `sunrise_dental` database (`DROP DATABASE sunrise_dental;`).
3. Restart the app — it will recreate the schema and reseed the demo data automatically.

## Troubleshooting

| Symptom | Likely cause | Fix |
|---|---|---|
| `Communications link failure` / `Connection refused` | MySQL isn't running | Start MySQL in the XAMPP Control Panel |
| `Access denied for user 'root'@'localhost'` | Wrong password in `application.properties` | Match the password to your actual XAMPP MySQL root password |
| `Unknown database 'sunrise_dental'` | `createDatabaseIfNotExist=true` missing from the URL, or user lacks CREATE privilege | Confirm the URL in `application.properties` matches this guide exactly |
| App starts but tables are empty | First run in progress, or `data.sql` failed silently | Check the console log for SQL errors; confirm `spring.sql.init.mode=always` and `spring.jpa.defer-datasource-initialization=true` are both set |
| Port 3306 already bound (XAMPP MySQL won't start) | Another MySQL instance (e.g. a Windows service) is already using the port | Stop the other MySQL service, or change XAMPP's MySQL port and update step 6 above |
