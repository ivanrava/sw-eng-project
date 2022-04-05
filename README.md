# sw-eng-project
Group project realized for the Software Engineering full-year course @ UniBs
# Baratti
Un progetto scritto in **Java** per il **baratto** tra utenti
## Come funziona?
L'applicazione ha una serie di **categorie** (es. Libro, Videogioco, Vestito, ecc...), ogni utente può aggiungere i propri articoli che vuole barattare in una delle tante categorie. Potrà cercare articoli di altri utenti e proporre loro un baratto con un proprio articolo. Sarà poi l'altro utente a dover decidere se accettare o meno lo scambio, in tal caso i due utenti, sempre tramite l'applicazione potranno concordarsi sul luogo e la data in cui incontrarsi per effettuare il baratto.
## Tipi di Utenti H2
- *Configuratore*
- *Consumatore*
### Cosa può fare il Configuratore
- Gestire la gerarchia delle categorie
  - Aggiungere una categoria
  - Importare delle categorie da un file
- Gestire i luoghi e le date disponibili per gli incontri
  - Aggiungere luoghi
  - Aggiungere date
  - Importare luoghi e date da un file
- Visualizzare tutti gli articoli e i baratti per una specifica categoria
### Cosa può fare il Consumatore
- Registrarsi al sistema
- Effettuare l'accesso al proprio profilo
- Gestire i propri articoli
  - Aggiungere articoli al proprio profilo
  - Ritirare articoli
- Visualizzare gli articoli degli altri consumatori
- Proporre scambi
- Proporre orari e luoghi di ritrovo per uno scambio accettato
# Aggiungere una categoria radice (Solo Configuratore)
Dopo aver selezionato l'apposita opzione dal menù, inserire il nome della nuova categoria radice *(non si possono avere categorie radice uguali!)*, inserire una descrizione della categoria *(Opzionale)* ed eventualmente una serie di campi (titolo, 
