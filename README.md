
[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)

DOCUMENTAZIONE SOFTWARE
GEOPIC

Flaminio Villa & Vasile Andrei Calin \| Politecnico di Milano

![](./media/image1.png)

Geopic nasce come applicazione mobile per la gestione di convenzioni e
sconti per esercizi commerciali.

Grazie ad una grafica accattivante assiste l\'utente nella ricerca degli
esercizi commerciali convenzionati a lui più vicini, offrendo filtri
accurati, descrizioni e informazioni utili.

> INDICE

1\. OGGETTO E SCOPO

2\. GLOSSARIO

3\. ANALISI FUNZIONALE E CASI D'USO

5\. PROGETTAZIONE BACK-END

5.1 ACTIVITY DIAGRAM

5.2 DEFINIZIONE DELLE ENTITÀ DI SISTEMA

> 5.2.1 DESIGN DELLA BASE DI DATI

5.3 ANALISI SOFTWARE

5.4 ANALISI DELL'ALGORITMO DI RICERCA DELLE STRUTTURE

> 5.4.1 APPROFONDIMENTO ALGORITMO DI VINCENTY GEODESY

5.5 SECURITY

> 5.5.1 CORS CONFIGURATION

5.6 API

5.7 TESTING

5.8 Docker

6\. CONCLUSIONE

##1 OGGETTO E SCOPO

Il focus dell\'applicativo è la visualizzazione delle strutture
convenzionate in modo strutturato ed intuitivo con una suddivisione in
base al tipo di struttura ed in base al tipo di servizio offerto.

È disponibile una ricerca per categorie, per tipo di struttura e per
nome ed una gestione relativa alla posizione in tempo reale che mostra
le strutture convenzionate più vicine all\'utente.

L\' applicazione ha diverse funzionalità che permettono l\'accesso in
modo semplice e veloce alle informazioni riportate su un sito che
verranno importate tramite Web Scraping esempio news, eventi, che
verranno importate automaticamente, o gestite tramite i profili
amministratore.

È disponibile anche una mappa personalizzata appositamente per il
servizio che permette la visualizzazione della posizione di tutte le
strutture convenzionate, divise per regioni ed identificate univocamente
dal tipo di struttura.

##2 GLOSSARIO

**Controller**

> Questa è una specializzazione della classe *\@Component,* che ci
> consente di rilevare automaticamente le classi che contengono i metodi
> di gestione delle richieste.

**Service**

> All' interno dei Service vengono inseriti tutti i controlli relativi
> ai parametri che verranno passati dal controller verso gli Helper.

**Helper**

> Gli Helper implementano la business logic ed interagiscono con le
> repository.

**JWT**

> JSON Web Token (JWT) è uno standard aperto che definisce un modo
> compatto e autonomo per trasmettere in modo sicuro le informazioni tra
> le parti come oggetto JSON

**CORS**

> Il Cross-Origin Resource Sharing (CORS) è un meccanismo che usa header
> HTTP addizionali per indicare che un dominio dispone
> dell\'autorizzazione per accedere alle risorse selezionate.

**GeoPoint**

> Il **GeoPoint** è un oggetto che contiene latitudine e longitudine di
> una coordinata geografica.

**GeoHash**

> Dati due valori double effettua un encoding binario che rappresenta
> l'unione dei **GeoPoint**

**BoundingBox**

> Una B**oundingBox** è un contenitore quadrato che ha come vertici le
> coordinate di latitudine e longitudine, grazie all'algoritmo di
> Vincenty geodesy verranno trovate le strutture che hanno **GeoPoint**
> interni alla bounding box.

**GeoHashCircle**

> Dato un **GeoPoint** ed un raggio crea una approssimazione di un
> cerchio in quadrato, che corrisponderà poi ad una B**oundingBox.**

**DTO**

> L\'oggetto di trasferimento dati (**DTO**), è un modello di
> progettazione utilizzato per trasferire i dati tra i sottosistemi
> dell\'applicazione software.

##3 ANALISI FUNZIONALE E CASI D'USO

L'applicativo segue il modello client-server, permette all'utente finale
l'accesso diretto ai dati delle convenzioni localizzati su un database.

Di seguito è possibile vedere la schermata principale dell'applicativo.

Dispone di una ricerca per categorie, per tipo di struttura e per nome
ed una gestione relativa alla posizione in tempo reale che mostra le
strutture convenzionate più vicine all\'utente.

![](./media/image2.png)Il pulsante in alto a destra
![](./media/image3.png) permette di cambiare la località di
ricerca, è infatti possibile ricercare le strutture convenzionate di una
determinata località , oltre a quelle vicine rispetto la posizione
fisica del dispositivo.

![](./media/image4.png)[Queste sono alcune immagini prese
dall'applicazione che sta funzionando sui nostri dispositivi]{.ul}

**Strutture localizzate**

È disponibile anche una mappa personalizzata appositamente per il
servizio che permette la visualizzazione della posizione di tutte le
strutture convenzionate, divise per regioni ed identificate univocamente
dal tipo di struttura. E' infatti possibile variare il colore del punto
visualizzato sulla mappa, per rendere distinguibile la diversa categoria
di esercizio commerciale.

[Queste sono alcune immagini prese dall'applicazione che sta funzionando
sui nostri dispositivi.]{.ul} ![Immagine che contiene mappa Descrizione
generata
automaticamente](./media/image5.png)![Immagine che contiene mappa Descrizione
generata
automaticamente](./media/image6.png)

La mappa implementa anche una barra di ricerca delle varie località così
da rendere fruibile la sua navigazione e la visualizzazione di tutte le
strutture offerte.

Quando viene premuto il pulsante
![](./media/image7.png) la mappa sposterà automaticamente il suo
punto focale rispetto la posizione geografica del dispositivo.

I tasti di navigazione portano dalla pagina principale verso la mappa
oppure verso una pagina che contiene informazioni descrittive.

Scorrendo da sinistra verso destra oppure attraverso il pulsante
![](./media/image8.png) si può avere accesso alla barra laterale,
che contiene le sezioni di notizie.

Le notizie vengono ottenute mediante web scraping su un sito web.

![](./media/image9.jpg)![](./media/image10.jpg)

E' possibile effettuare ricerche fra le varie notizie e cliccando sopra
si vede il contenuto completo della news.

Per tornare al menu precedente basta usare il tasto indietro o scorrere
da destra verso sinistra.

Il flusso di gestione della piattaforma è la suddivisione a zone di
tutto il territorio Italiano con ogni zona gestita da del personale
autorizzato che inserisce le convenzioni della zona, ogni zona
ovviamente deve avere uno o più amministratori segreteria che
inizialmente dovranno creare e gestire il personale di quella zona. La
gestione delle zone è adibita a degli amministratori nazionali che
avranno il controllo di tutte le regioni, strutture e profili del nostro
servizio.

##5 **PROGETTAZIONE BACK-END**

Dal punto di vista back-end, si è scelto di utilizzare **Spring Boot**,
un framework per lo sviluppo di applicazioni web basate su codice Java
che offre un ulteriore livello di astrazione rispetto a Spring
Framework, di più complicato utilizzo e adatto a progetti aziendali più
elaborati.

Spring Boot ha numerosi vantaggi tra cui:

-   Incorporare direttamente applicazioni web server/container come
    Apache Tomcat, per cui non è necessario l\'uso di file WAR (Web
    Application Archive);

-   Configurazione di **Maven** semplificata grazie ai POM \"Starter\"
    (Project Object Models);

-   Caratteristiche non funzionali come metriche o configurazioni
    esterne automatiche.

La memorizzazione delle informazioni è affidata ad un Database **SQL**,
in particolare MySQL, che viene gestito a livello di persistenza
dall'applicazione tramite l'uso di **JPA** (Java Persistence API) e
della sua più famosa implementazione framework **Hibernate**.

I dati contenuti vengono monitorati tramite l'uso di Datagrip un visual
tool che permette di verificare che i contenuti del database siano
coerenti con le operazioni eseguite.

La security invece viene gestita mediante Spring Security.

**Spring Security** è un framework di autenticazione e controllo degli
accessi potente e altamente personalizzabile, si concentra sulla
fornitura di autenticazione e autorizzazione alle applicazioni Java.
Come tutti i progetti Spring, il vero potere di Spring Security si trova
nella facilità con cui può essere esteso per soddisfare i requisiti
personalizzati.

**SpringBoot** è stato affiancato a **Maven**, progetto open-source che
permette di organizzare al meglio il progetto Java, poiché ha la
caratteristica di essere modulare, cioè in fase di progettazione si
scelgono in base alle funzionalità che deve avere l'applicativo, le
librerie (chiamate **dependencies**) da aggiungere al progetto in un
file denominato pom.xml (Project Object Model).

Di fondamentale importanza sono le **Annotations** di SpringBoot,
indicate nelle classi Java con la **@** che precede il loro nome e che
specificano informazioni aggiuntive utili a definire valori,
comportamenti, proprietà delle classi o degli attributi.

In quanto framework ORM (Object / Relational Mapping), **Hibernate** si
occupa della persistenza dei dati in quanto si applica ai database
relazionali (tramite **JPA**); **Hibernate** è ben noto per la sua
eccellente stabilità e qualità, dimostrata dall\'accettazione e
dall\'utilizzo da parte di decine di migliaia di sviluppatori Java.

##5.1 **ACTIVITY DIAGRAM**

Gli Use Cases catturano il comportamento del sistema, illustrando quali
sono i suoi requisiti funzionali.

Lo use case realizzato in figura permette di visualizzare tutte le
funzionalità a cui può accedere l'utente scaricando l'applicazione
mobile.

![](./media/image29.png)

Come si può osservare, un cliente si autentica sul sistema, cioè
effettua il login e dopo di che può eseguire una serie di funzionalità.

![](./media/image30.png)
**5.2** **DEFINIZIONE DELLE ENTITÀ DI SISTEMA**

Il Class Diagram mostra le classi che compongono il software e come esse
si relazionano l'una con l'altra.

![](./media/image31.png)

Il diagramma in figura mostra le classi che costituiscono i *"model"*
del progetto. Per questioni di visibilità grafica non sono stati
riportati i costruttori e i metodi all'interno delle singole classi.

Le classi rappresentano le Entità di cui è composto il Database e ne
riprendono le relazioni, in particolare:

-   La classe **Authority** contiene il nome dei possibili ruoli
    assumibili dagli utenti

-   La classe **UserAuthority** associa ad ogni **User** i ruoli
    corrispondenti ruoli

-   La classe **User** contiene le informazioni principali necessarie
    per gli utenti della piattaforma, ogni User è associato ad una
    **Region.**

-   La classe **Region** contiene nome e id di tutte le regioni
    d'Italia, serve per dividere settorialmente utenti e strutture.

-   La classe **Structure** è l'entità principale, contiene infatti
    tutti dati di una struttura convenzionata da esporre sulla
    piattaforma, vengono divise per **Category**.

Ogni struttura viene gestita da una **ReferralPerson** associata ad una
**Secretary** e localizzata in una **Region,** viene memorizzato anche
il **GeoPoint .**

-   La classe **Category** specifica il nome ed il colore da usare nei
    ![](./media/image32.png){width="0.17490594925634295in"
    height="0.2755686789151356in"} personalizzati della mappa

-   La classe **ReferralPerson** definisce le generalità del personale
    gestore delle convenzioni

-   La classe **Secretary** contiene le informazioni delle sedi di
    controllo in cui lavorano le **ReferralPerson**

All'interno del progetto possono essere individuati altri tre gruppi di
entity:

![](./media/image33.png)

Per effettuare un **Reverse geocoding** il più veloce ed efficiente
possibile, è stato creato un albero binario, che effettua memorizzazione
e ricerca di coordinate geografiche.

Analizzando meglio le classi:

-   La classe **Geoname** contiene i parametri necessari
    all\'identificazione della città italiana corrispondente.

-   La classe **KDNode** costituisce la struttura della foglia
    dell'albero, che verrà creato tramite **KDTree**

-   La classe **ReverseGeoCode** effettua le operazioni di ricerca
    all'interno dell' albero.

-   La classe **KDNodeComparator** ridefinisce i metodi comparator.

Di seguito vengono riassunti i principali **DTO** utilizzati all'
interno del progetto

![](./media/image34.png)Le **exception** di ogni **Entity**
vengono gestite
singolarmente:![](./media/image35.png)

##5.2.1 **DESIGN DELLA BASE DI DATI**

Il Database, come spiegato in precedenza,è di tipo relazionale.

E' strutturato sulla base delle classi presenti all'interno del package
"model", contenente le Entity di sistema.

Il diagramma Entità-Relazioni del Database è il seguente:

![](./media/image36.png)

**Hibernate_sequence** e **user_seq** servono a memorizzare l'id
utilizzato, utile per avere una successione esatta per la generazione
degli ID.

##5.3 **ANALISI SOFTWARE**

Il progetto è suddiviso in **32** package mostrati in figura

![](./media/image37.png)

##### 

##### Model

Il package "model" contiene le classi Entity che rappresentano le
tabelle del Database.

Ogni classe è dichiarata con l'annotazione **\@Entity** facente parte di
Jpa Hibernate.

L'annotazione **\@Data** genera getter per tutti i campi, un utile
metodo toString e hashCode. Genererà anche setter per tutti i campi non
finali, oltre ai costruttori che verranno specificati tramite
**\@AllArgsConstructor** e **\@NoArgsConstructor.**

L'annotazione **\@Builder** è un meccanismo utile per utilizzare il
[pattern
Builder](https://www.baeldung.com/creational-design-patterns#builder)
senza scrivere codice ridondante.

##### Security

La security verrà analizzata nel dettaglio nel punto **4.5.**

##### Controller

Il package "controller " contiene al suo interno il package "dto"
contenente le classi che definiscono gli standard per i passaggi di dati
tra il back-end e il front-end, utilizzando come tipo il **JSON**. I
dati successivamente verranno trasferiti verso i sottosistemi
dell\'applicazione software.

Sono divisi in *name***Controller** e *name***CRUDController**, come si
può intuire dal nome avranno tipi di accesso differenti, i primi si
occuperanno solo di fornire dati, i secondi effettueranno operazioni di
interfaccia con il database.

le classi Controller, sono annotate con @**RestController**. I
controller fanno uso dei service (richiamati con @**Autowired**) per
prelevare i dati secondo le logiche di business, mappando le richieste
HTTP chiamate dalla view ai microservizi sviluppati e rispondendo
utilizzando il medesimo protocollo e lo standard **JSON**.

Le richieste GET vengono mappate usando @**GetMapping**() con tra
parentesi indicato il path dell'endpoint. Le richieste POST vengono
mappate usando @**PostMapping**(). Le richieste POST vengono mappate
usando @**PostMapping**().Le richieste DELETE vengono mappate usando
@**DeleteMapping**().

##### Service

Il package "service " contiene classi che svolgono tutti i controlli
relativi ai parametri ricevuti dai controlli, e tramite @**Autowired**
richiamano gli helper.

##### Helper

Il package "helper " contiene la **bussines logic** di progetto, si
interfaccia tramite @**Autowired** verso le repository , definendo le
operazioni sul database.

##### Repository

Il package "repository" contiene le interfacce che estendono
*JpaRepository\<T, ID>* e sono annotate con \@Service.

Esse si occupano di eseguire operazioni CRUD sulle Entity nel Database.

L'interfaccia *JpaRepository* offre alcuni metodi base come findById() e
findAll(), invece se si ha bisogno di eseguire una query SQL nativa si
può utilizzare l'annotazione \@Query.

##### Exceptions

Il package "exceptions" contiene le classi che estendono la classe
***RuntimeException*** (lanciate da Java)*.*

Queste classi rappresentano eccezioni personalizzate create per mandare
dettagli più precisi alla view (front-end), sia per quanto riguarda lo
status HTTP dell'errore generato, sia per quanto riguarda un messaggio
che descriva brevemente il problema.

##### Component

Il package "component" contiene I package "**geoCode**" e "**geoHash**",
oltre ad alcune classi di utils, contiene anche la classe Scraper
(svolge il ruolo di web scraper).

Una parte molto importante dell'applicativo è relativa a la
**geolocalizzazione** del dispositivo.

Per effettuare un Reverse geocoding il più veloce ed efficiente
possibile, è stato creato un albero binario, che effettua memorizzazione
e ricerca di coordinate geografiche, questo meccanismo è sviluppato
all'interno del package "**geoCode**".

Un'altro punto focale è la funzione che permette di mostrare all'utente
le strutture presenti vicine a la sua posizione.

Non è per niente banale la sua implementazione, infatti effettuare un
semplice controllo di distanza tra la posizione gps del dispositivo e
tutte le strutture del DB sarebbe inefficiente e lento.

E' stato quindi implementato una funzione che sfrutta l\'algoritmo di
[Vincenty\'s geodesy]{.ul} per effettuare tale misurazione.

##5.4 **ANALISI DELL'ALGORITMO DI RICERCA DELLE STRUTTURE**

Il blocco contenuto all\'interno del package "component" -\> "geoCode"
principale è la componente che ci permette di identificare le strutture
presenti in una determinata area geografica.

![](./media/image38.png)

Per dare una breve descrizione delle classi sopra riportate :

-   La classe **GeoPoint** è un oggetto che contiene latitudine e
    longitudine di una coordinata geografica.

-   La classe **GeoHash** effettua un encoding binario che rappresenta
    l'unione dei **GeoPoint.**

-   La classe **BoundingBox** definisce un contenitore quadrato che ha
    come vertici le coordinate di latitudine e longitudine

-   La classe **GeoHashCircle** viene creata da un **GeoPoint** ed un
    raggio crea una approssimazione di un cerchio in quadrato, che
    corrisponderà poi ad una B**oundingBox.**

-   La classe **GeoHashCircle** Incapsula l\'algoritmo di [Vincenty\'s
    geodesy.]{.ul}

##5.4.1 APPROFONDIMENTO ALGORITMO DI VINCENTY GEODESY**

La formula di Vincenty è un metodo iterativo per calcolare la distanza
tra due punti sulla superficie di uno sferoide, sviluppati da Thaddeus
Vincenty (1975a). Si basa sul presupposto che la figura della Terra sia
uno sferoide oblato , e quindi sono più accurati dei metodi che assumono
una Terra sferica , come la distanza del cerchio maggiore .

-   La latitudine deve essere compresa tra 0 ° e ± 90 ° e le latitudini
    sud sono negative (ad esempio, -35 ° 55 \'56,12 \"\');

-   La longitudine deve essere compresa tra 0 ° e ± 180 ° e le
    longitudini ovest sono negative (ad esempio, -148 ° 56 \'25,12
    \"\');

Le formule sono processi iterativi, il che significa che viene calcolata
una sequenza di equazioni in cui l\'output viene reinserito nella stessa
sequenza di equazioni. L\'obiettivo è ridurre al minimo il valore di
output dopo un determinato numero di iterazioni.

![](./media/image39.png)![](./media/image40.png)

Dove d è la distanza tra due punti con longitudine e latitudine ( *λ, Φ*
) er è il raggio della terra.

![](./media/image41.png)

Le formule di Vincenty sono indicate come accurate entro circa lo
**0,3%**, il che è ottimo.

**5.5 SECURITY**

Il package "security " è strutturato come un micro servizio di login
indipendente, infatti gestisce tutta la configurazione per l'accesso
verso la piattaforma, con l'annotazione @**EnableWebSecurity**.

Spring Security si trova sul classpath, **Spring Boot** protegge
automaticamente tutti gli endpoint HTTP con l\'autenticazione \"di
base\". Analizziamo il workflow del login
:![](./media/image42.png)

1)  La richiesta di login arriva a AuthenticationRestController con
    **/api/authenticate**.

2)  Il metodo authorize genera un token con username e password.

3)  Il token viene aggiunto a Authentication e salvato nel
    **SecurityContextHolder**.

4)  Bisogna generare il jwt token chiamo quindi
    tokenProvider.createToken.

5)  Da application.yml prendo i valori che indicano la durata dei token.

\*\*createToken

6)  prendo le authorities da Authentication e genero un jwts token con
    nome,authorities, chiave di crittografia e durata.

7)  Genero gli headers http e aggiungo il **jwt Token**

8)  Nella risposta aggiungo JWTToken(jwt), httpHeaders, HttpStatus.OK

Analizziamo il workflow delle richieste :

1)  La richiesta viene ricevurta da **JWTFilter** che prende la
    servletRequest, e risolve il token a partire dalla
    **httpServletRequest**

2)  il token viene validato da tokenProvider

Authentication-\> tokenProvider.getAuthentication(jwt);

3)  Authentication viene inserita nel **SecurityContextHolder**

4)  Adesso è garantito l'accesso verso gli endPoint gestiti dai
    controller

![](./media/image43.png)

##5.5.1 CORS CONFIGURATION**

Il Cross-Origin Resource Sharing (CORS), un meccanismo che usa header
HTTP addizionali per indicare che un dominio dispone
dell\'autorizzazione per accedere alle risorse selezionate.

![](./media/image44.png)

##5.6 API**

La documentazione ha l\'obiettivo di mostrare le chiamate API di cui si
compone l\'applicativo e le quattro classi Java annotate con
**\@Controller.**

Questo applicativo RESTful è progettato per restituire dati (**JSON**)
che verranno inviati dai controller direttamente tramite la risposta
HTTP.

L\' annotazione @**RestController** di Spring Boot è fondamentalmente
una scorciatoia rapida che ci evita di dover sempre definire
@**ResponseBody** .

La documentazione ha l'obiettivo di mostrare le chiamate API di cui si compone l'applicativo
e le quattro classi Java annotate con @Controller.
Questo applicativo RESTful è progettato per restituire dati (JSON) che verranno inviati dai
controller direttamente tramite la risposta HTTP.
L' annotazione @RestController di Spring Boot è fondamentalmente una scorciatoia rapida
che ci evita di dover sempre definire @ResponseBody.

**Login**

Per effettuare il login bisogna fare una richiesta di tipo POST, con i seguenti header json:

```
POST https://geopic.flaminiovilla.it/api/authenticate
```
```
@CrossOrigin(origins = "*")
@PostMapping("/authenticate")
public ResponseEntity<UserHelper.JWTToken> authorize(@Valid @RequestBody
LoginDTO loginDto)
)
```
```
Request Response
{
"email" : "viflaadmin@gmail.com",
"password" : "flaminio"
}
```
```
{
"id_token": “nygsvsgsodjpa64s...",
"user": {
"email": "viflaadmin@gmail.com",
"password": null,
"role": ADMIN,
"firstName": "flaminio",
"lastName": "villa",
"regionId": 99
}
}
```
Chiaramente la password dell’utente non viene inserita all’interno della risposta, ma verrà sostituita
con il valore null.

Ad ogni mail sono associati ruoli particolari , quindi in base al tipo di utente che andrà ad
effettuare il login , sono previsti diversi tipi di profili, con autorizzazioni differenti.Questa
parte non viene gestita, ma verranno utilizzati principalmente i profili di tipo ADMIN e
USER.


**Registrazione**

Per effettuare la registrazione bisogna fare una richiesta di tipo POST, i ruoli seguono la
seguente gerarchia, è necessario inserire il proprio token durante le richieste:
● Un admin può creare un altro admin
POST https://geopic.flaminiovilla.it/api/register/admin

```
@PostMapping("/register/admin")
public User registerAdmin(@Valid @RequestBody UserDTO userDTO)
```
```
● Un admin può creare un profilo di tipo secretary
POST https://geopic.flaminiovilla.it/api/register/adminSecretary
```
```
@PostMapping("/register/adminSecretary")
public User registerAdminSecretary(@Valid @RequestBody UserDTO userDTO)
```
```
● Una secretary può generare i profili adminSecretary
POST https://geopic.flaminiovilla.it/api/register/secretary
```
```
PostMapping("/register/secretary")
public User registerSecretary(@Valid @RequestBody UserDTO userDTO)
```
```
● Chiunque può registrarsi come user
POST https://geopic.flaminiovilla.it/api/register/user
```
```
@PostMapping("/register/user")
public User registerUser(@Valid @RequestBody UserDTO userDTO)
```
Per testare che l’utente creato ha i permessi necessari sono stati creati specifici endpoint di
test, che date le credenziali di login, restituiscono i ruoli posseduti.

```
POST https://geopic.flaminiovilla.it/api/testAdmin
POST https://geopic.flaminiovilla.it/api/testAdminSecretary
POST https://geopic.flaminiovilla.it/api/testSecretary
POST https://geopic.flaminiovilla.it/api/testUser
```

**Category**

Le API della sezione Category sono gestite da due diversi @RestController :

CategoryController :
Restituisce un json contenente tutte le category presenti :

```
POST https://geopic.flaminiovilla.it/category/findAll
RESPONSE[ { "id": 1 ,"name": "GEOPIC", "color": "#ffff00"},...]
```
```
@GetMapping("/findAll")
List<Category> findAll()
```
CategoryCRUDController :
Restituisce un json contenente tutte le category presenti ma in forma ridotta, mostrando solo
id e nome :

```
POST https://geopic.flaminiovilla.it/api/category/findAllShort
RESPONSE { "id": 1 ,"name": "GEOPIC"}
```
```
@GetMapping("/findAllShort")
String findAllShort()
```
Dati nome e colore creo un nuovo obj di tipo category, se l'inserimento riesce lo restituisco
in formato json con l’ id (che viene generato automaticamente) :

```
POST https://geopic.flaminiovilla.it/api/category/create
INPUT {"name": "GEOPIC", "color": "#ffff00"}
RESPONSE { "id": 1 ,"name": "GEOPIC", "color": "#ffff00"}
```
```
@PostMapping("/create")
@ResponseBody
public Category create(@RequestBody CategoryDTO categoryDTO)
```
Specificando id , name e color sovrascrive la category con lo stesso id :

```
POST https://geopic.flaminiovilla.it/api/category/update
INPUT { "id": 1 ,"name": "GEOPIC", "color": "#aaaa11"}
RESPONSE come INPUT
```

```
@PutMapping("/update")
@ResponseBody
public Category update(@RequestBody CategoryDTO categoryDTO)
```
Ricerca dato un id e restituisce la category corrispondente in formato json.

```
POST https://geopic.flaminiovilla.it/api/category/findById
INPUT {"id": 1 }
RESPONSE { "id": 1 ,"name": "GEOPIC", "color": "#ffff00"}
```
```
@PostMapping("/findById")
Optional<Category> findById(@RequestBody CategoryDTO categoryDTO)
```
Elimina dato un id :

```
POST https://geopic.flaminiovilla.it/api/category/delete
INPUT {"id": 1 }
RESPONSE 200
```
```
@DeleteMapping("/delete")
public Boolean delete(@RequestBody CategoryDTO categoryDTO)
```
**News**

Le API della sezione News sono gestite da due diversi @RestController :

NewsController :
Restituisce tutte le news che contengono la stringa di ricerca nel titolo, se non trovo niente
cerco {search} all'interno della description.

```
POST https://geopic.flaminiovilla.it/news/search/{search}
RESPONSE [{
"id": 16 ,
"description": "Lorem ipsum",
"title": "10 Ways To Immediately Start Selling POSIZIONE",
"image": "https://picsum.photos/200/300?random=7",
"date": "2021- 03 - 02 00:00:00",
"section": "News"} ,...]
```
```
@PostMapping("/search/{search}")
List<NewsDTO> search(@PathVariable("search") String search, @RequestBody
StructureDTO structureDTO)
```

Restituisce tutte le news di una section:

```
POST https://geopic.flaminiovilla.it/news/section
INPUT { "section":"News"}
RESPONSE ["id": 1 ,"description": "...","title": “...","image":"...",
"date":"2021- 03 - 02","section": "News"} , ...]
```
```
@PostMapping("/section")
List<NewsDTO> section(@RequestBody StructureDTO structureDTO)
```
Ricerca dato un id e restituisce la news corrispondente in formato json.:

```
POST https://geopic.flaminiovilla.it/news/findById
INPUT { "section":"News"}
RESPONSE ["id": 1 ,"description": "...","title": “...","image":"...",
"date":"2021- 03 - 02","section": "News"} , ...]
```
```
@PostMapping("/findById")
Optional<News> findById(@RequestBody NewsDTO newsDTO)
```
NewsCRUDController :
Crea una nuova news ,Date e' in formato stringa e verrà convertiro in in Sql.Date ,section puo'
essere: ["News","Comunicazioni","Eventi"], se l'inserimento riesce lo restituisco in formato
json con l’ id (che viene generato automaticamente).

```
POST https://geopic.flaminiovilla.it/api/news/create
INPUT {"id": 1 ,"description": "...","title": “...","image":"...",
"date":"2021- 03 - 02","section": "News"}
```
```
@PostMapping("/create")
@ResponseBody
public News create(@RequestBody NewsDTO newsDTO)0
```
Specificando l'id , e modificando eventuali campi vado a sovrascrivere la news :

```
POST https://geopic.flaminiovilla.it/api/news/update
INPUT {"id": 1 ,"description": "...","title": “...","image":"...",
"date":"2021- 03 - 02","section": "News"}
RESPONSE come INPUT
```
```
@PutMapping("/update")
@ResponseBody
public News update(@RequestBody NewsDTO newsDTO)
```

Elimina dato un id :

```
POST https://geopic.flaminiovilla.it/api/news/delete
INPUT {"id": 1 }
RESPONSE 200
```
```
@DeleteMapping("/delete")
public Boolean delete(@RequestBody NewsDTO newsDTO)
```
**Place**

Questo gruppo di api si occupa della ricerca delle strutture in una determinata località :

Aiuto al completamento del nome della località , mostra solo i primi 10 record
esempio : ...api/place/rom

```
POST https://geopic.flaminiovilla.it/place/{initStr}
RESPONSE [{
"id": 4792 ,
"istat": "58091",
"comune": "Roma",
"latitude": 41.89277044,
"longitude": 12.48366723
}, ...]
```
```
@GetMapping("/{initStr}")
List<Place> findPlaceByNameBeginning(@PathVariable("initStr") String
initialString)
```
Data latitudine e longitudine restituisce il nome del comune più vicino.

```
POST https://geopic.flaminiovilla.it/place/cityname
INPUT { "latitude": 41.89277044,"longitude": 12.48366723 }
RESPONSE { "comune": "Roma" }
```
```
@PostMapping("/cityname")
public String cityname(@RequestBody StructureDTO structureDTO)
```


**Referral Person**

ReferralPerson definisce le generalità del personale gestore delle convenzioni e sono gestite
da due diversi @RestController :

ReferralPersonController :

Specificando l'id , e modificando eventuali campi vado a sovrascrivere la referralPerson :

```
POST https://geopic.flaminiovilla.it/referralPerson/findAll
RESPONSE{
"id": 1 ,
"email": "vifla01@gmail.com",
"name": "Flaminio",
"surname": "Villa",
"phone": "3775093443",
"secretary": {
"id": 1 ,
"name": "geopic",
"address": "via sandro sandri 81",
"email": "geopic@flaminiovilla.it",
"phone": "3775093443E9",
"region": {
"id": 3 ,
"name": "Calabria"
}
},
"region": {
"id": 3 ,
"name": "Calabria"
}
}, ...]
```
```
},
```
```
@GetMapping("/findAll")
List<ReferralPerson> findAll()
```

ReferralPersonCRUDController :
Restituisce un json contenente tutte le referralPerson presenti ma in forma ridotta,
mostrando solo id e nome :

```
POST https://geopic.flaminiovilla.it/api/referralPerson/findAllShort
RESPONSE { "id": 1 ,"name": "Andrei"}
```
```
@GetMapping("/findAllShort")
String findAllShort()
```
Ricerca dato un id e restituisce la referralPerson corrispondente in formato json.

```
POST https://geopic.flaminiovilla.it/api/referralPerson/findById
INPUT {"id": 1 }
RESPONSE {"id": 1,"email": "","name": "","phone": "","address": "","regionId": }
```
```
@PostMapping("/findById")
Optional<ReferralPerson> findById(@RequestBody ReferralPersonDTO
referralPersonDTO)
```
Crea una nuova referralPerson e se l’inserimento va a buon fine la restituisce in formato
json

```
POST https://geopic.flaminiovilla.it/api/referralPerson/create
INPUT{ "email": "","name": "","phone": "","address": "","regionId": 1 }
RESPONSE come INPUT + ID
```
```
@PostMapping("/create")
@ResponseBody
public ReferralPerson create(@RequestBody ReferralPersonDTO
referralPersonDTO)
```
Specificando l'id , e modificando eventuali campi vado a sovrascrivere la referralPerson :

```
POST https://geopic.flaminiovilla.it/api/referralPerson/update
INPUT Come nel caso dell’inserimento ma specificando l’id
```
```
@PutMapping("/update")
@ResponseBody
public ReferralPerson update(@RequestBody ReferralPersonDTO
referralPersonDTO)
```

Elimina dato un id (Se presente) :

```
POST https://geopic.flaminiovilla.it/api/referralPerson/delete
INPUT {"id": 1 }
RESPONSE 200
```
```
@DeleteMapping("/delete")
public Boolean delete(@RequestBody SecretaryDTO secretaryDTO)
```
**Secretary**

La classe Secretary contiene le informazioni delle sedi di controllo in cui lavorano le
ReferralPerson sono gestite da due diversi @RestController :
SecretaryController :
Restituisce un json contenente tutte le secretary presenti :

```
POST https://geopic.flaminiovilla.it/secretary/findAll
RESPONSE [{
"id": 1 ,
"name": "geopic",
"address": "via sandro sandri 81",
"email": "geopic@flaminiovilla.it",
"phone": "3775093443E9",
"region": {
"id": 3 ,
"name": "Calabria"
}
}, ... ]
```
```
@GetMapping("/findAll")
List<Secretary> findAll()
```
SecretaryCRUDController :

Ricerca dato un id e restituisce la secretary corrispondente in formato json.

```
POST https://geopic.flaminiovilla.it/api/secretary/findById
INPUT {"id": 1 }
RESPONSE {"id": 1,"email": "","name": "","phone": "","address": "","regionId": }
```

```
@PostMapping("/findById")
Optional<Secretary> findById(@RequestBody SecretaryDTO secretaryDTO)
```
Restituisce un json contenente tutte le secretary presenti ma in forma ridotta, mostrando
solo id e nome :

```
POST https://geopic.flaminiovilla.it/api/secretary/findAllShort
RESPONSE [{ "id": 1 ,"name": "" , ... ]}
```
```
@GetMapping("/findAllShort")
String findAllShort()
```
Crea una nuova secretary e se l’inserimento va a buon fine la restituisce in formato json

```
POST https://geopic.flaminiovilla.it/api/secretary/create
INPUT{ "email": "","name": "","phone": "","address": "","regionId": 1 }
RESPONSE come INPUT + ID
```
```
@PostMapping("/create")
@ResponseBody
public Secretary create(@RequestBody SecretaryDTO secretaryDTO)
```
Specificando l'id , e modificando eventuali campi vado a sovrascrivere la secretary :

```
POST https://geopic.flaminiovilla.it/api/secretary/update
INPUT Come nel caso dell’inserimento ma specificando l’id
```
```
@PutMapping("/update")
@ResponseBody
public Secretary update(@RequestBody SecretaryDTO secretaryDTO)
```
Elimina dato un id (Se presente) :

```
POST https://geopic.flaminiovilla.it/api/secretary/delete
INPUT {"id": 1 }
RESPONSE 200
```
```
@DeleteMapping("/delete")
public Boolean delete(@RequestBody SecretaryDTO secretaryDTO)
```

**Structure**

La classe Structure è l’entità principale, contiene infatti tutti dati di una struttura
convenzionata da esporre sulla piattaforma, analizziamola per ultimo , dopo aver già
analizzato tutte le entity che contiene:
sono gestite da due diversi @RestController
StructureController :
Restituisce un json contenente tutte le structure presenti :

```
POST https://geopic.flaminiovilla.it/structure/findAll
RESPONSE [{
"id": 30 ,"name": "Geopic","macroCategory": "Sanitaria",
"category": {"id": 1 ,"name": "GEOPIC","color": "#ffff00"},
"description": "Geopic","logo": "”,"discount": 90 ,"distance": null ,
"expireDateConvention": "2050/12/12","email": "...","phone": "...",
"website": "https://www.geopic.com",
"address": "via sandro sandri 71, 00184 Roma RM, Italy",
"latitude": 41.887701387610505,
"longitude": 12.496582779052002,
"secretary": {"id": 2 ,"name": "Elis","address": "via sandro sandri 71",
"email": "elis@elis.it","phone": "...",
"region": {"id": 7 ,"name": "Lazio" }
},
"referralPerson": {
"id": 2 ,
"email": "calinvasileandrei@gmail.com",
"name": "Andrei",
"surname": "Calin",
"phone": "3775093443",
"secretary": {
"id": 2 ,
"name": "Elis",
"address": "via sandro sandri 71",
"email": "elis@elis.it",
"phone": "0.0",
"region": {"id": 7 , "name": "Lazio"
}},"region": {"id": 7 ,"name": "Lazio"}
}, "geoPoint": {
"longitude": 12.496582779052002,
"latitude": 41.887701387610505
}
},}, ... ]
```

```
@GetMapping("/findAll")
List<Secretary> findAll()
```
Ricerca dato un id e restituisce la structure corrispondente in formato json.

```
POST https://geopic.flaminiovilla.it/structure/findById
INPUT {"id": 1 }
RESPONSE {"id": 1,"email": "","name": "","phone": "","address": "","regionId": }
```
```
@PostMapping("/findById")
StructureDateDTO findById(@RequestBody StructureDTO structureDTO)
```
Restituisco tutte le structure che sono di una determinata macro categoria e contengono la
posizione (ESEMPIO roma ) all'interno del campo indirizzo dopo /.

```
POST https://geopic.flaminiovilla.it/structure/findSection/{search}
INPUT {"macroCategory":"Convenzioni Commerciali"}
```
```
@PostMapping("/findSection/{search}")
List<StructureDateDTO> findSection(@PathVariable("search") String search,
@RequestBody StructureDTO structureDTO)
```
Restituisco tutte le structure che sono di una determinata macro categoria e località.

```
POST https://geopic.flaminiovilla.it/structure/findSection
INPUT {"macroCategory":"Convenzioni Commerciali","location":"roma"}
```
```
@PostMapping("/findSection")
List<StructureDateDTO> findSection(@RequestBody StructureDTO structureDTO)
```
Grazie all'algoritmo di ricerca delle structure , analizzato precedentemente, dato un punto
geografico ed una distanza in km , effettua la ricerca delle strutture comprese in questa
distanza

```
POST https://geopic.flaminiovilla.it/structure/findStructuresNearGeoLocation
INPUT {
"latitude":38.9027885,
"longitude":16.5944429,
"distance": 6
}
```
```
@PostMapping("/findStructuresNearGeoLocation")
List<StructureDateDTO> findStructuresNearGeoLocation(@RequestBody
StructureDTO structureDTO)
```
Data la posizione per cui si vogliono avere le structure restituisce una lista di strutture
divise per macro categorie.

```
POST https://geopic.flaminiovilla.it/structure/findAllByCategory
INPUT {
"location":"catanzaro",
"category":"farmacia"
}
```
```
@PostMapping("/findAllByCategory")
List<StructureDateDTO> findAllByCategory(@RequestBody StructureDTO
structureDTO)
```
Restituisce tutte le structure che contengono _"location"_ all'interno dell'indirizzo

```
POST https://geopic.flaminiovilla.it/structure/findAllStructuresByLocation
INPUT {
"location":"catanzaro"
}
```
```
@PostMapping("/findAllStructuresByLocation")
String findAllStructuresByLocation(@RequestBody StructureDTO structureDTO)
```
Data la _"location"_ restituisce i customMarker di quella località:

```
POST https://geopic.flaminiovilla.it/structure/markers
RESPONSE {
"id": 39 ,
"name": "Geopic",
"latitude": 38.89357180922953,
"longitude": 16.587004309020646,
"category": {
"id": 1 ,
"color": "#ffff00",
"name": "GEOPIC" }}
```


```
@PostMapping("/markers")
@ResponseBody
public List<MarkerStructureDTO> markers(@RequestBody StructureDTO
structureDTO)
```
Ricerca dentro il db le structure che contengono le parole cercate nel nome, data una
localita'.

```
POST https://geopic.flaminiovilla.it/structure/search/{search}
INPUT {
"location":"catanzaro"
}
```
```
@PostMapping("/search/{search}")
List<StructureDateDTO> findStructureByContainsLetters(@PathVariable("search")
String search, @RequestBody StructureDTO structureDTO)
```
StructureCRUDController :

Elimina dato un id (Se presente) :

```
POST https://geopic.flaminiovilla.it/api/structure/delete
INPUT {"id": 1 }
RESPONSE 200
```
```
DeleteMapping("/delete")
public Boolean delete(@RequestBody StructureDTO structureDTO)
```
Crea una nuova structure e se l’inserimento va a buon fine la restituisce in formato json

```
POST https://geopic.flaminiovilla.it/api/structure/create
INPUT
{
"name" : "", "macroCategory" : "", "description" : "", "discount" : ,
"expireDateConvention" : "", "email" : "", "phone" : "", "website" : "",
"address" : "", "latitude" "", "longitude" "", "categoryId" : 1 ,
"referralPersonId" : 1 , "secretaryId" : 1 ,
}
RESPONSE come INPUT + ID
```
```
@PostMapping("/create")
@ResponseBody
public Structure create(@RequestBody StructureDTO structureDTO)
```


Specificando l'id , e modificando eventuali campi vado a sovrascrivere la structure :

```
POST https://geopic.flaminiovilla.it/api/structure/update
INPUT Come nel caso dell’inserimento ma specificando l’id
```
```
@PutMapping("/update")
@ResponseBody
public Structure update(@RequestBody StructureDTO structureDTO)
```
Tutte le **API** sono state testate con **Postman** (https://www.postman.com/) , di seguito riporto il link

per importare i test relativi a tutte le singole api sopra descritte:

E’ possibile scaricare le collection di postman dal seguente link:
https://polimi365-my.sharepoint.com/:u:/g/personal/10713791_polimi_it/EXycM3PA4qlMk5Qt-B4tnYYB_pxAeeznYR4Ogh1TgUE42g?e=hCdoWJ

```
Per accedere ad alcune api, è necessaria
l’autenticazione occorre quindi effettuare prima il
login e successivamente includere il token nella
richiesta, esempio:
Authorization + Bearer eyJhbGciOiJIUzUxMiJ9....
```

##### Tutte le API sono state testate con Postman ([[https://www.postman.com/]{.ul}](https://www.postman.com/)) , di seguito riporto il link per importare i test relativi a tutte le singole api sopra descritte:

![](./media/image45.png)![](./media/image46.png)
E' possibile scaricare le collection di postman dal seguente link:

  -------------------------------------------------------------------------------------------------------------------------------
https://polimi365-my.sharepoint.com/:u:/g/personal/10713791_polimi_it/EXycM3PA4qlMk5Qt-B4tnYYB_pxAeeznYR4Ogh1TgUE42g?e=hCdoWJ

  -------------------------------------------------------------------------------------------------------------------------------

![](./media/image47.png)
Per accedere ad alcune api, è necessaria l'autenticazione occorre quindi
effettuare prima il login e successivamente includere il token nella
richiesta, esempio:

Authorization + Bearer eyJhbGciOiJIUzUxMiJ9....

##5.7 TESTING

Il testing sfrutta le classi di SpringBootTest e la piattaforma JUnit 5,
I Test d'integrazione per l'applicazione sono contenuti all'interno del
package:

  -----------------------------------------------------------------------
src/test/java/com/flaminiovilla/geopic/

  -----------------------------------------------------------------------

I test sono stati realizzati con SpringBootTest, una libreria Maven che
permette di effettuare un setup automatico dell'ambiente di test, viene
richiamata tramite l'annotazione \@SpringBootTest all'inizio di ogni
classe.

Il metodo testato va annotato con \@Test e al suo interno si richiamano
i metodi della classe Assertions (facente parte della libreria JUnit 5
prevista da SpringBootTest) per verificare la coerenza dei dati.

I metodi della classe Assertions usati sono:

-   **assertTrue()** verifica che la condizione passata nelle parentesi
    tonde sia vera;

-   **assertFalse()** verifica che la condizione passata nelle parentesi
    tonde sia falsa;

-   **assertNull()** verifica che la condizione passata nelle parentesi
    tonde ritorni null;

-   **assertNotNull()** verifica che la condizione passata nelle
    parentesi tonde non ritorni null;

-   **assertThat()** verifica che il valore atteso sia uguale al valore
    effettivo;

-   **assertContains()** verifica che un array contiene un valore;

-   **assertNotContains()** verifica che un array non contiene un
    valore;

Per quanto riguarda il testing delle api è stato fatto uso dei MockMvc,
definiti come punto di ingresso principale per i test Spring MVC lato
server. I test si MockMvc si trovano a metà strada tra i test di unità e
quelli di integrazione.

I controlli implementati sono:

-   **.contentType()** verifica il tipo di parametri ricevuti ( esempio
    formato JSON);

-   **.content()** verifica se sono presenti determinati parametri;

-   **.addAxpect()** verifica se il tipo di risposta lato server
    coincide (esempio : unauthorized);

-   **.getResponse()** analizza il risultato della servet dispatcher;

La struttura dei test è la seguente:

![](./media/image48.png)

**REPORT DI
TESTING:**![](./media/image49.png)
Essendo molto vasta la struttura sono riportati i test dei punti critici
principali del sistema:

**JwtDemoApplicationTest**

Inizializza i test con \@SpringBootTest

Per i test che coprono l\'intera applicazione Spring Boot dalla
richiesta in entrata al database, o per i test che coprono alcune parti
dell\'applicazione che sono difficili da configurare manualmente,
us\@SpringBootTest.

\@SpringBootTest per impostazione predefinita inizia la ricerca nel
pacchetto corrente della classe di test e quindi cerca verso l\'alto
nella struttura del pacchetto, cercando una classe annotata con la
\@SpringBootConfigurationquale e legge la configurazione per creare un
contesto dell\'applicazione

Utilizziamo poi MockMvc per eseguire una POST richiesta , per verificare
che risponda come previsto.

Analizziamo di seguito l\'utilità delle varie classi di test.

**[GeoHashTest]**

Controlla che l'operazione di hashing di latitudine e longitudine,
specificando la precisione dei bit, avvenga correttamente.

**[GeoPointTest]**

Crea diversi **GeoPoint** e successivamente associa i valori
corrispondenti, creandone delle copie e verificando che il processo di
confronto vada a buon fine.

Inoltre viene effettuato il test dell'algoritmo di VincentyGeodesy.

**[BoundingBoxTest]**

Definite delle **BoundingBox** verifico se contengono dei **GeoPoint**,
definiti internamente secondo specifiche criticità, per esempio punti
molto esterni o molto vicini al centro della **BoundingBox.**

**[GeoHashCircleQueryTest]**

Dati dei **GeoPoint** ed un raggio crea una approssimazione di un
cerchio in quadrato, che corrisponderà poi ad una B**oundingBox**
analizzando se contiene dei punti critici**.**

In alcuni punti è precedentemente è stata calcolata la distanza relativa
grazie a google maps, e successivamente è stata effettuata la verifica
della distanza calcolata mediante l'algoritmo di VincentyGeodesy.

**[PersonRestControllerTest]**

Tramite assertSuccessfulPersonRequest verifico se tutti i parametri
vengono trasmessi correttamente, e se il token viene aggiunto
correttamente nell Header.

**[AuthenticationRestControllerTest]**

Esegue dei test relativi l'accesso:

1.  successfulAuthenticationWithUser

2.  successfulAuthenticationWithAdmin

3.  unsuccessfulAuthenticationWithDisabled

4.  unsuccessfulAuthenticationWithWrongPassword

5.  unsuccessfulAuthenticationWithNotExistingUser

**[UserRestControllerTest]**

Provo a risalire all'utente loggato, estraendo il token dall'Header
della request.

**[LogInUtils]**

Effettuo il test della generazione del token con getTokenForLogin e ne
controllo la validità.

## 5 .8 Docker

Docker è uno strumento progettato per semplificare la creazione, la distribuzione e
l'esecuzione di applicazioni utilizzando i container.
I container consentono di creare un pacchetto di un'applicazione con tutte le parti di cui ha
bisogno, e distribuirla come un unico pacchetto. In questo modo, grazie al container,
l'applicazione verrà eseguita su macchina Linux.
Per effettuare il deploy di Geopic grazie a docker compose si unisce mariadb con l’app
spring.
Compose è uno strumento per la definizione e l'esecuzione di applicazioni Docker multi-
container, si utilizza un file YAML per configurare i servizi per l’ applicazione. L'utilizzo di
**Compose** è fondamentalmente un processo in tre fasi: Definizione dell'ambiente della applicazione
con un in Dockerfile in modo che possa essere riprodotta ovunque.Definizione dei servizi che la
compongono in docker-compose.yml per essere eseguiti insieme in un ambiente isolato.

1. Esecuzione di **docker compose up --build** ( il comando di composizione Docker avvia ed
   esegue l'intera app).

# Contenuto del docker-compose.yml :

```
version: '3.1'
services:
database:
image: mariadb
restart: always
environment:
MYSQL_ROOT_PASSWORD: ---------@
volumes:

- /home/ubuntu/docker/geopic:/var/lib/mysql
  ports:
- 3309 : 3306
  backend:
  depends_on:- database
  build:.
  restart: always
  command:
  java -jar /app.jar
  --
  spring.datasource.url="jdbc:mariadb://database/geopic?createDatabaseIfNotExis
  t=true&autoReconnect=true&useSSL=false"
  --spring.datasource.password="---------@"
  ports:
- 5001 : 8080
```
**6 Conclusioni**

La documentazione di questa piattaforma formata dal server backend e
dell'applicazione frontend fornisce le informazioni necessarie per
capire il progetto al meglio, capire il suo funzionamento e come queste
due parti interagiscono tra di loro.

La documentazione definisce la logica e l'architettura utilizzata per la
realizzazione della piattaforma, non viene trattato il codice riga per
riga dato che è stato commentato adeguatamente, così che possa essere
compreso al meglio.

Per collaborare efficacemente durante la realizzazione, è stata usata
una task-board che permette la comunicazione di problematiche o
richieste nel team. Usando questo tipo di board è possibile creare una
"issue" che rappresenta un problema, una funzionalità o una feature da
implementare così da garantire una certa autonomia nello sviluppo del
lato frontend/backend.

Analizziamo una casistica:

Il frontend necessità di un endpoint che ancora non è stato sviluppato
dal backend.

La persona che lavora sullo sviluppo frontend crea una issue per esempio
(Implementazione Endpoint per la ricerca delle strutture), a questa
issue è possibile assegnare una priorità che rappresenta l'urgenza.

Lato backend lo sviluppatore dovrà cercare di risolvere le issue a lui
assegnate così da garantire il corretto proseguimento del lavoro.

Ovviamente con la stessa logica può avvenire la situazione opposta dei
Team di sviluppo o l'interazione di più Team nel caso di progetti di
grandi dimensioni formati da sviluppatori backend, frontend, designer
ecc.

![](./media/image50.png)

I sorgenti sia della parte front end che della parte backend sono
pubblici sui nostri github:

Back-end![](./media/image51.png)

<https://github.com/villaflaminio/geopic.git>

Front-end

<https://github.com/calinvasileandrei/geopic_polimi>

E' anche possibile scaricare in formato .zip i sorgenti e l'APK
(Disponibile per android)

![](./media/image52.png)
[Link
Drive](https://drive.google.com/drive/folders/1nLddSE-cNAg9Fj6zxkKhnKQ94jkr6Awz?usp=sharing)

![](./media/image53.png)

[Link Video](https://youtu.be/1a3FBhnKKfU)

**Speriamo che il nostro progetto abbia soddisfatto tutte le aspettative
create, stiamo provando a concretizzarlo, rendendolo disponibile presso
gli store.**

