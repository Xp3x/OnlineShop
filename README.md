# OnlineShop

Vue-Frontend und Spring-Boot-Backend.

## Lokal starten (PowerShell)

Voraussetzungen: Node.js, JDK 17 und PostgreSQL mit Datenbank `shopdb` und Benutzer `shop`. Das Datenbankschema und die Produktdaten muessen bereits vorhanden sein; `db/setup.sql` richtet nur Berechtigungen ein.

Backend aus `shop_backend` starten:

```powershell
cd shop_backend
$env:SPRING_DATASOURCE_PASSWORD = '<lokales Datenbankpasswort>'
# Fuer PayPal-Zahlungen zusaetzlich PAYPAL_CLIENT_ID und PAYPAL_CLIENT_SECRET setzen.
.\mvnw.cmd spring-boot:run
```

Frontend in einem zweiten Terminal:

```powershell
cd frontend
npm.cmd ci
npm.cmd run dev
```

Frontend: http://localhost:5173; Backend: http://localhost:8080/api/products.

Die Backend-Startklasse liegt unter `shop_backend/src/main/java/com/example/shop_backend/ShopBackendApplication.java`.

## Projektstruktur

- `frontend/`: Vue-Anwendung mit eigenem npm-Build.
- `shop_backend/`: eigenstaendige Spring-Boot-Anwendung mit Maven Wrapper und JDK 17.
- `db/`: SQL fuer Datenbankberechtigungen.

Es gibt kein Maven-Projekt im Hauptverzeichnis. In IntelliJ `shop_backend/pom.xml` als Maven-Projekt importieren. Frontend und Backend werden separat gestartet.