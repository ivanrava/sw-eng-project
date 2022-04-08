# sw-eng-project
Group project realized for the Software Engineering full-year course @ UniBS

# Baratti
Tecnologie utilizzate:
- Java 18
- gson-2.9.0 (libreria per serializzazione/deserializzazione Json)

# Spiegazione generale
Applicazione che permette a degli utenti di registrarsi, aggiungere i propri articoli da barattare in categorie predisposte dall'amministratore dell'applicazione
e proporre scambi con altri utenti, che potranno poi decidere se accettare o meno lo scambio

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

# Setup Categorie
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

# Setup Luoghi/Orari di scambio
Dopo aver effettuato l'accesso con un account configuratore andare nel menù di gestione della configurazione. Da li sarà possibile inserire Luoghi/Orari di scambio direttamente dall'applicazione inserendo i dati richiesti, o altrimenti, é possibile importarli tramite un file Json. Dall'applicazione andrà inserito il percorso assoluto del file su disco. Ecco un esempio di file Json per l'importazione dei Luoghi/Orari di scambio...
```json
{
  "square": "Brescia",
  "places": ["Portici", "UniBS - via Branze 38", "Fermata metro Vittoria"],
  "deadline": 10,
  "days": [6,7],
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

# Screenshots
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
