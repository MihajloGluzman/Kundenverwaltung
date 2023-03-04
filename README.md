# Spring-Boot-CRUD

Hierbei handelt es sich um eine Webanwendung zur Kundenverwaltung.
Diese verwaltet personenbezogene Daten eines Kunden in einer SQL-Datenbank. 

#### Die Webansicht stellt fünf Funktionen zur Verfügung:

**1. Alle Kunden anzeigen:**
Auf der Startseite werden alle Kunden tabellarisch dargegestellt.

**2. Neuen Kunden anlegen:**
Der Button: "Neuen Kunden anlegen" öffnet eine Maske zum Eintragen der Kundendaten.
	
**3. Persönliche Daten eines Kunden anzeigen:**
Der Button: "Info" zeigt die persönlichen Daten eines Kunden an (E-Mail Adresse, Geschlecht)

**4. Kundendaten bearbeiten:** 
In der persönlichen Anzeige des Kunden, können die Daten bearbeitet werden. Der Button: "Bearbeiten" öffnet eine Maske, in der die Daten geändert werden können.

**5. Kunden löschen**
In der persönlichen Anzeige des Kunden kann der Kunde durch klicken des Buttons: "Löschen" gelöscht werden.

Bei ungültigen Benutzereingaben wird /error mit den jeweiligen Fehlermeldungen aufgerufen.

###### Folgende Technologien habe ich in diesem Projekt verwendet:

- Spring Boot
- Thymeleaf
- HTML, CSS
- Squirrel zum Erstellen der Datenbank
- Postgresql als Datenbank
- Postman zum Testen der POST-Controller
