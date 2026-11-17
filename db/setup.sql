-- shop darf sich verbinden (zur Sicherheit nochmal):
GRANT CONNECT ON DATABASE shopdb TO shop;

-- WICHTIG: Erlaube CREATE im public-Schema
GRANT USAGE, CREATE ON SCHEMA public TO shop;

-- Optional aber sauber: Schema-Eigentümer ändern
-- (dann gehören dem Benutzer auch zukünftige Objekte im Schema)
ALTER SCHEMA public OWNER TO shop;

-- Default-Rechte für künftig erstellte Tabellen/Sequenzen
ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO shop;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO shop;
