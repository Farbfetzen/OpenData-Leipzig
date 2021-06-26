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
  - Password = password
