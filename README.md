# Einfache Kundenverwaltung

Hierbei handelt es sich um eine Webanwendung zur Kundenverwaltung.
Diese verwaltet personenbezogene Daten eines Kunden in einer SQL-Datenbank. 

## Die Webansicht stellt fünf Funktionen zur Verfügung:

**1. Alle Kunden anzeigen**
- Auf der Startseite werden alle Kunden tabellarisch dargegestellt.

**2. Neuen Kunden anlegen**
- Der Button: "Neuen Kunden anlegen" öffnet eine Maske zum Eintragen der Kundendaten.
	
**3. Persönliche Daten eines Kunden anzeigen:**
- Der Button: "Info" zeigt die persönlichen Daten eines Kunden an (E-Mail Adresse, Geschlecht)

**4. Kundendaten bearbeiten** 
- In der persönlichen Anzeige des Kunden, können die Daten bearbeitet werden. Der Button: "Bearbeiten" öffnet eine Maske, in der die Daten geändert werden können.

**5. Kunden löschen**
- In der persönlichen Anzeige des Kunden kann der Kunde durch klicken des Buttons: "Löschen" gelöscht werden.

Bei ungültigen Benutzereingaben wird /error mit den jeweiligen Fehlermeldungen aufgerufen.

## Folgende Technologien habe ich in diesem Projekt verwendet:

- Spring Boot
- Thymeleaf
- HTML, CSS
- Squirrel als SQL Client
- Postgresql als Datenbank
- Postman zum Testen der APIs

Releasenotes

Version 1.1 

- Adressen zum Kunden hinzufügen, löschen und bearbeiten


Version 1.2

Funktionen:
- Hauptadressen verwalten


Bug fixes/ Anpassungen:

- Kundendatendaten bearbeiten: Selectmenü zum auswählen des Geschlechts hinzugefügt, vorher textfeld
- Kundenübersicht: Leerer Tabellenkopf wird nicht mehr angezeigt, falls Kunde keine Adresse hat
- Controller: - Anzahl der Datenbankzugriffe verringert
		  - Beim Löschen von einem Kunden werden jetzt zuerst die Adressen gelöscht und dann der Kunde.
		    Vorher wurde zuerst der Kunde gelöscht und dann die Adressen.

