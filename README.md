# Descrizione e librerie
Progetto realizzato nell'ambito del corso di Ingegneria del Software dell'Università degli Studi di Brescia, utilizzando

Applicazione che permette a degli utenti di creare un proprio profilo, aggiungere articoli da barattare in categorie predisposte dall'amministratore dell'applicazione e proporre baratti con altri utenti. Ogni utente può aggiungere articoli al proprio profilo, e può proporre e gestire proposte di scambio con altri utenti. Dopo aver confermato uno scambio, gli utenti potranno accordarsi, direttamente tramite l'applicazione, sul luogo e sulla data dell'appuntamento. In qualunque momento un utente può ritirare un proprio articolo (nel caso non lo voglia più scambiare), e questo non sarà più visibile agli altri utenti.

# Tecnologie utilizzate
Il progetto è stato realizzato con le seguenti tecnologie e librerie:
- Java 18
- gson-2.9.0 (libreria di Google per la serializzazione / deserializzazione di classi Java su / da file .json)
- UniBSFPLib (libreria di comodo per le interfacce CLI usata nel corso di Fondamenti di Programmazione)

## Aggiunta articolo
_Prerequisiti:_
 - Essere registrati

Dopo aver effettuato l'accesso all'applicazione selezionare _"Gestisci gli articoli"_ e successivamente _"Aggiungi un articolo/offerta"_. Inserire tutti i dati richiesti (la _categoria_ di appartenenza e poi tutti i campi da compilare relativi alla categoria scelta, come ad esempio lo stato di conservazione).
Nel caso i campi siano _facoltativi_, é possibile non scrivere nulla.

L'aggiunta della categoria va fatta identificando prima la "categoria radice" e poi selezionando la "categoria foglia" desiderata all'interno delle categorie individuate dalla categoria radice scelta.

Terminata l'aggiunta dell'articolo sarà possibile visualizzarlo, insieme a tutti gli altri articoli aggiunti in precedenza, nella sezione _"Visualizza articoli/offerte dell'utente"_.

Gli articoli aggiunti saranno automaticamente resi disponibili per gli scambi (l'applicazione li indica come "aperti"). Se l'utente desidera ritirare gli articoli aggiunti può farlo successivamente con l'ausilio dell'applicazione.

Ogni utente che dispone di articoli "aperti" allo scambio può quindi proporre dei baratti agli altri utenti.

## Proporre uno scambio
_Prerequisiti:_
 - Essere registrati
 - Avere almeno un articolo _"aperto"_ allo scambio sul profilo

Dopo aver effettuato l'accesso all'applicazione selezionare _"Gestisci i baratti"_ e successivamente _"Proponi un baratto"_. Selezionare l'articolo da proporre, poi selezionare l'articolo desiderato tra tutti gli articoli della stessa categoria caricati dagli altri utenti (non è possibile effettuare scambi con articoli di categorie differenti).

Ora sarà l'altro utente a dover accettare o meno lo scambio.

## Accettare / rifiutare uno scambio
_Prerequisiti:_
 - Essere registrati

Per visualizzare/gestire le offerte di scambio che ci sono state proposte selezionare dal menu principale _"Gestisci i baratti"_ e successivamente _"Gestisci i baratti che ti sono stati proposti"_. Da qui sarà possibile visualizzare tutte le offerte di scambio, e per ognuna sarà possibile accettare o rifiutare l'offerta:
- Nel caso si **rifiuti** lo scambio il nostro articolo tornerà ad essere _"aperto"_ ad altre offerte, senza rimanere "bloccato" dall'offerta indesiderata.
- Nel caso si **accetti** lo scambio si dovrà proporre all'altro utente un luogo e una data per l'appuntamento. Il luogo può essere scelto da una lista di località predeterminata dai configuratori dell'applicazione e tutte localizzate in una stessa "piazza" (visibile chiedendo di mostrare le _"generalità dell'applicazione"_ dal menu principale del fruitore). Allo stesso modo, anche la data è vincolata a delle finestre temporali imposte dal configuratore: il giorno è selezionabile solo tra una lista predeterminata di giorni della settimana, mentre l'ora è vincolata all'interno di intervalli temporali con granularità di mezz'ora (anche questi intervalli sono visibili dalla schermata delle _"generalità"_).

Dopo l'accettazione e la proposta di luogo / data / orario, l'onere della risposta viene rimpallato all'altro utente, che potrà accettare e chiudere lo scambio oppure rifiutare e redigere un'altra proposta spazio-temporale. Il flusso continua sino a che un utente non accetta la proposta della controparte oppure sino a quando la proposta non scade senza ricevere risposta (la scadenza è anch'essa fissata in giorni dal configuratore, ed è visualizzabile nelle _"generalità"_).

# Prerequisiti
Prerequisiti:
- Java Runtime Environment
- Eseguibile sw-eng-project.jar

# Installazione
1. Creare una cartella e inserire al suo interno il file eseguibile (l'app durante l'utilizzo creerà dei file di configurazion
ed é necessario che siano nella stessa cartella dell'eseguibile)
2. Aprire un emulatore di terminale (bash, cmd, Powershell, ecc..) e spostarsi nella cartella.
3. Dare il comando apposito per eseguire il .jar da linea di comando, che può essere ad esempio:

### Windows
```
java -jar .\sw-eng-project.jar
```

### Linux / MacOS
```
java -jar ./sw-eng-project.jar
```

# Configurazione / setup iniziale (per configuratore)
Al primo avvio dell'applicazione il configuratore dovrà decidere una password di default che sarà poi utilizzata per creare degli account con permessi di configurazione del sistema.
Queste credenziali non possono essere modificate in un secondo momento e conferiscono la possibilità di creare nuovi utenti / credenziali con privilegi amministrativi. Per questi motivi è imperativo che **non vengano mai condivise con nessuno e che non siano mai dimenticate**. Idealmente dovrebbero essere scelte delle credenziali di non facile deduzione.

Fare l'accesso con le credenziali di default permetterà di creare un account "configuratore", definendone username e password. Gli username sono univoci per tutti gli utenti (che siano "configuratori" o "fruitori").

Prima dell'uso da parte degli utenti finali gli utenti configuratori dovranno creare le categorie, che poi andranno ad identificare gli articoli degli utenti (come ad es. Libro, Videogioco, Abbigliamento, ecc... - ma ne parleremo meglio successivamente), ma anche la "configurazione" dell'applicazione, ovvero i luoghi dove avvengono gli scambi, la città in cui avvengono gli scambi (detta "piazza" e determinabile una sola volta), la scadenza delle offerte e delle proposte, nonché gli orari / giorni di ritrovo in cui effettuare fisicamente gli scambi (usati in fase di definizione delle proposte).

## Configurazione categorie
L'applicazione gestisce categorie tramite più gerarchie ad albero, identificate dalla radice degli alberi. Ci sono dei vincoli sulla definizione dei nomi delle gerarchie:
- Il nome di una categoria radice è univoco tra tutte le altre categorie radice.
- Il nome di una categoria all'interno di una gerarchia è univoco per quella gerarchia.
Una categoria è pertanto univocamente determinata dal nome della sua categoria radice e della categoria stessa (in quella gerarchia).

Ogni categoria è identificata, oltre che dal suo nome e da una descrizione testuale, anche da campi testuali, a compilazione obbligatoria o facoltativa, determinati dal configuratore o dall'applicazione. In particolare, l'applicazione associa di _default_ ad ogni categoria radice 2 campi nativi:
- Stato di conservazione (obbligatorio)
- Descrizione libera (facoltativo)
Ogni categoria eredita i campi dei genitori e gli articoli possono essere aggiunti solo a categorie foglia, compilando tutti i campi di quella categoria foglia (sia propri che ereditati dagli ascendenti).

Per aggiungere le categorie bisogna aver effettuato l'accesso con un account configuratore e poi andare nel menù di gestione delle categorie.
Da lì sarà possibile creare categorie direttamente dall'applicazione inserendo i dati richiesti, oppure è anche possibile importare le categorie tramite un file .json in modalità _batch_. Dall'applicazione andrà inserito il percorso assoluto del file .json su disco, di cui forniamo un esempio a titolo esemplicativo del suo semplice formato:
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

## Setup configurazione
Anche la configurazione può essere importata direttamente da disco, dal menu di gestione della configurazione. Da lì sarà possibile inserire tutti i dati di configurazione (credenziali di default escluse) direttamente dall'applicazione, interattivamente, oppure é possibile importarli tramite un file .json. Dall'applicazione andrà inserito il percorso assoluto del file su disco. Ecco un esempio di file .json (la scadenza `deadline` è in giorni, i giorni della settimana `days` sono inseriti come interi da 1 a 7 e gli intervalli temporali `timeIntervals` sono granulari rispetto alla mezz'ora):
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
