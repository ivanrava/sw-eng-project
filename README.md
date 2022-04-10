# sw-eng-project
Group project realized for the Software Engineering full-year course @ UniBS

# Baratti
Tecnologie utilizzate:
- Java 18
- gson-2.9.0 (libreria per serializzazione/deserializzazione Json)

# Spiegazione generale
Applicazione che permette a degli utenti di creare un proprio profilo, aggiungere articoli da barattare in categorie predisposte dall'amministratore dell'applicazione e proporre baratti con altri utenti. Ogni utente può aggiungere articoli al proprio profilo, e può proporre e gestire proposte di scambio con altri utenti. Dopo aver confermato uno scambio, gli utenti potranno accordarsi, direttamente tramite l'applicazione, sul luogo e sulla data dell'appuntamento. In qualunque momento un utente può ritirare un proprio articolo (nel caso non lo voglia più scambiare), e questo non sarà più visibile agli altri utenti.

## Aggiunta articolo
_Prerequisiti:_
 - Essere registrati

Dopo aver effettuato l'accesso all'applicazione selezionare _"Gestisci gli articoli"_ e successivamente _"Aggiungi un articolo/offerta"_. Inserire tutti i dati richiesti (categoria di appartenenza, stato di conservazione, e tutti gli altri campi), nel caso siano _facoltativi_, é possibile non scrivere nulla. Terminata l'aggiunta dell'articolo sarà possibile visualizzarlo, insieme a tutti gli altri articoli aggiunti in precedenza, nella sezione _"Visualizza articoli/offerte dell'utente"_.

## Proporre uno scambio
_Prerequisiti:_
 - Essere registrati
 - Avere almeno un articolo _"aperto"_ allo scambio sul profilo

Dopo aver effettuato l'accesso all'applicazione selezionare _"Gestisci i baratti"_ e successivamente _"Proponi un baratto"_. Selezionare l'articolo da proporre, poi selezionare l'articolo desiderato tra tutti gli articoli altrui della stessa categoria (Non è possibile effettuare scambi con articoli di categorie differenti). Ora sarà l'altro utente a dover accettare o meno lo scambio.

## Accettare/Rifiutare uno scambio
_Prerequisiti:_
 - Essere registrati

Per visualizzare/gestire le offerte di scambio che ci sono state proposte selezionare dal menù principale _"Gestisci i baratti"_ e successivamente _"Gestisci i baratti che ti sono stati proposti"_. Da qui sarà possibile visualizzare tutte le offerte di scambio, e per ognuna sarà possibile accettare o rifiutare l'offerta. Nel caso si rifiuti lo scambio il nostro articolo tornerà ad essere _aperto_ ad altre offerte. Nel caso si accetti lo scambio si dovrà proporre all'altro utente un luogo e una data per l'appuntamento.

# Come eseguire l'applicazione?
Prerequisiti:
- Java Runtime Environment
- Eseguibile sw-eng-project.jar

Creare una cartella e inserire al suo interno il file eseguibile (l'app durante l'utilizzo creerà dei file di configurazion
ed é necessario che siano nella stessa cartella dell'eseguibile), ora aprire una finestra di terminale (Bash, cmd, Powershell, ecc..)
nella cartella. dare il comando...
### Windows
```
java -jar .\sw-eng-project.jar
```

### Linux / MacOS
```
java -jar ./sw-eng-project.jar
```

# Setup Iniziale (per configuratore)
Al primo avvio dell'applicazione il configuratore dovrà decidere una password di default
che sarà poi utilizzata per creare degli account con permessi di configurazione del sistema.
Dopo aver fatto l'accesso con le credenziali di default e creato l'account di configuratore sarà necessario un setup iniziale.

Andranno configurate prima dell'utilizzo da parte degli utenti finali le categorie, che poi andranno a ospitare gli articoli (Libro, Videogioco, Abbigliamento, ecc...),
e i luoghi e gli orari di ritrovo per effettuare fisicamente gli scambi.

## Setup Categorie
Dopo aver effettuato l'accesso con un account configuratore andare nel menù di gestione delle categorie.
Da li sarà possibile creare categorie direttamente dall'applicazione inserendo i dati richiesti, o altrimenti, 
é possibile importare le categorie tramite un file Json. Dall'applicazione andrà inserito il percorso assoluto del file su disco. Ecco un esempio di file Json per l'importazione delle categorie...
```json
[
  {
    "name": "Libro",
    "description": "Opera cartacea",
    "fields": [
      {
        "name": "Titolo",
        "required": true
      },
      {
        "name": "Anno di uscita",
        "required": true
      },
      {
        "name": "Edizione",
        "required": false
      },
      {
        "name": "Autore",
        "required": true
      }
    ],
    "children": [
      {
        "name": "Romanzo",
        "description": "Narrazione scritta in prosa",
        "fields": [],
        "children": [
          {
            "name": "Romanzo straniero",
            "description": "Romanzo in lingua non italiana",
            "fields": [
              {
                "name": "Lingua originale",
                "required": true
              }
            ],
            "children": []
          }
        ]
      }
    ]
  }
]
```

## Setup Luoghi/Orari di scambio
Dopo aver effettuato l'accesso con un account configuratore andare nel menù di gestione della configurazione. Da li sarà possibile inserire Luoghi/Orari di scambio direttamente dall'applicazione inserendo i dati richiesti, o altrimenti, é possibile importarli tramite un file Json. Dall'applicazione andrà inserito il percorso assoluto del file su disco. Ecco un esempio di file Json per l'importazione dei Luoghi/Orari di scambio...
```json
{
  "square": "Brescia",
  "places": [
    "Portici",
    "UniBS - via Branze 38",
    "Fermata metro Vittoria"
  ],
  "deadline": 10,
  "days": [
    6,
    7
  ],
  "timeIntervals": [
    {
      "start": "08:00",
      "stop": "11:30"
    },
    {
      "start": "14:00",
      "stop": "18:30"
    },
    {
      "start": "20:00",
      "stop": "22:00"
    }
  ]
}
```

## Screenshots
### Accesso
![Cattura](https://user-images.githubusercontent.com/44038580/162405056-dc4d4f59-f6a0-46e9-95df-f6f6dccbcdc2.PNG)
### Interfaccia di amministrazione (configuratore)
![Cattura2](https://user-images.githubusercontent.com/44038580/162405145-d60c2e09-f132-4f82-95f0-665a70ee2303.PNG)
### Gestione categorie (configuratore)
![Cattura3](https://user-images.githubusercontent.com/44038580/162405148-ae76225f-4e29-469c-a6fc-75b942b667b7.PNG)
### Gestione Configurazione (configuratore)
![Cattura4](https://user-images.githubusercontent.com/44038580/162405150-837352f2-50f6-452a-9672-dbb43610548c.PNG)
### Interfaccia per l'utente finale
![Cattura5](https://user-images.githubusercontent.com/44038580/162405151-608b3fed-23fe-4d5f-ae3c-57387899a090.PNG)
### Gestione articoli (utente finale)
![Cattura6](https://user-images.githubusercontent.com/44038580/162405154-acf26e18-b184-4af1-8686-828d64a9bfe6.PNG)
### Categorie
![Cattura7](https://user-images.githubusercontent.com/44038580/162405156-c6f7ca22-d998-4f7a-aa21-57b649569364.PNG)
### Articoli
![Cattura8](https://user-images.githubusercontent.com/44038580/162405158-22989376-d428-4640-bc02-e56617905290.PNG)
