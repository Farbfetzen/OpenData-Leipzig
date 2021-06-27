## Straßennamen

- Namen suchen und Ergebnis anzeigen mit Infos wie Länge, Erläuterung, Einwohner, etc.
- paginierte Anzeige, sortiert nach
    - Länge
    - Einwohner
- Datei lokal in Datenbank speichern. Das schont die Server der Stadt Leipzig. Laufende
  Aktualisierung ist nicht notwendig, da sich die Daten nur sehr selten ändern. Aber es sollte
  auch ein Button geben, mit dem die Datenbank aktualisiert werden kann. Für die Entwicklung 
  erstmal ein PostRequest einrichten, mit dem die Daten aus der Datei eingelesen werden. Dann
  später das Request so abändern, dass die Daten aus dem Internet heruntergeladen und
  eingelesen werden.
- Möglichkeit zu jeder Straße eigene Notizen hinzuzufügen. Achtung, selbst eingetragene Infos könnten bei 
  Aktualisierung der Datenbank verloren gehen und müssen irgendwie in die neue übertragen werden.
- Zeige nur die Straßen mit eigenen Notizen an.
- Mit **Unittests!**


## Web-Adressen:

- H2-Konsole
  - http://localhost:8080/h2-console
  - JDBC URL = jdbc:h2:file:./data
  - User Name = user
  - kein Passwort
- Straße hinzufügen:
  - http://localhost:8080/streets/add?name=Foo&key=01234
  - Name und key müssen unique sein. Key ist maximal 5 Zeichen lang.
- Alle Straßen abfragen:
  - http://localhost:8080/streets/getall
  - Gibt eine JSON-Liste zurück.
- Update:
  - POST http://localhost:8080/streets/update
  - Macht momentan noch nichts.
  - Wird dazu benutzt werden die Daten zu aktualisieren. Vielleicht mit Parameter Dateiname?
    Und später dann direkt aus dem Web.
  
## Links
- Opendata-Portal der Stadt Leipzig: https://opendata.leipzig.de
- Infos zum Straßenschlüssel: https://recht.nrw.de/lmi/owa/br_vbl_detail_text?anw_nr=6&vd_id=17207&ver=8&val=17207&sg=0&menu=0&vd_back=N