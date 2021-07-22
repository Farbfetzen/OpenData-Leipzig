## Straßennamen

- Namen suchen und Ergebnis anzeigen mit Infos wie Länge, Erläuterung, Einwohner, etc.
- paginierte Anzeige, sortiert nach
    - Länge
    - Einwohner
- Mit Unittests!
    - Straße suchen mit vorhandenem Namen.
    - Straße suchen mit falschem Namen.
    - Update erzeugt Tabelle mit richtiger Länge.
- Straße suchen mit Namen oder Länge > x oder Population < y.
- Bei der ersten Abfrage, falls das Repo leer ist (mit repo.count() == 0) die Daten aktualisieren.

## Web-Adressen:

- H2-Konsole
  - http://localhost:8080/h2-console
  - JDBC URL = jdbc:h2:file:./data
  - User Name = user
  - kein Passwort
- Alle Straßen abfragen:
  - http://localhost:8080/streets/all
  - Gibt eine Liste von Straßen zurück.
- Update:
  - POST http://localhost:8080/streets/update
  - Lädt Straßeninformationen aus einer XML-Datei in die Datenbank.
    Wird später die Daten direkt aus dem Web herunterladen statt aus einer Datei.
  
## Links
- Opendata-Portal der Stadt Leipzig: https://opendata.leipzig.de
- Infos zum Straßenschlüssel: https://recht.nrw.de/lmi/owa/br_vbl_detail_text?anw_nr=6&vd_id=17207&ver=8&val=17207&sg=0&menu=0&vd_back=N
