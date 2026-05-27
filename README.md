# JavaFX Cloud Orchestrator

Un sistema di orchestrazione ibrido con architettura **Client-Server** basata su **socket TCP** per la gestione centralizzata di container **Docker** e macchine virtuali **VirtualBox**. L'applicazione è dotata di un'interfaccia grafica (GUI) realizzata in **JavaFX** (progettata con **Scene Builder**) e include un sistema di logging persistente su database.

## Dettagli Tecnici e Architettura

### Il Server Multi-Thread e Polimorfismo
Per far sì che il server possa gestire più richieste di orchestrazione contemporaneamente senza bloccare i processi in attesa, l'architettura implementa il multithreading. Ogni volta che il Client invia un comando, il Server accetta la connessione e avvia un nuovo thread isolato (`HandleClient`) per quell'operazione. L'astrazione delle risorse è gestita tramite **Polimorfismo**: il server interagisce con un'interfaccia comune (`ResourceCloud`), avviando o spegnendo le macchine senza doversi preoccupare dei dettagli implementativi sottostanti.

### Interazione con il Sistema Operativo
Il backend dell'applicazione interagisce a basso livello con il sistema operativo ospite. Tramite l'uso di `ProcessBuilder`, il programma Java genera processi figli che effettuano System Call per comunicare direttamente con i demoni di Docker e `VBoxManage`, scavalcando lo User Mode per l'allocazione delle risorse fisiche.

### Sincronizzazione dei Dati (Logging)
La gestione di molteplici thread in esecuzione parallela richiede attenzione alla memoria e alle risorse condivise, come il Database MySQL utilizzato per i log delle operazioni. Il modulo `DatabaseManager` fa uso di metodi **sincronizzati** per:
* Prevenire **race condition** quando più thread (es. spegnimento di una VM e avvio di un container nello stesso istante) cercano di eseguire query di `INSERT` nel database contemporaneamente.
* Garantire l'integrità dei dati salvati ed evitare la corruzione dello storico delle azioni.

## Come iniziare

### Prerequisiti
* Java Development Kit (JDK) 11 o versioni successive
* Apache Maven (per la gestione delle dipendenze, inclusi JavaFX e driver JDBC)
* Docker e VirtualBox installati e configurati sul sistema host
* Un'istanza di MySQL in esecuzione (locale o containerizzata)

### Istruzioni di Avvio
1. **Configurazione:** Modifica il file `src/main/resources/config.xml` inserendo la porta desiderata per il Server e le credenziali (URL, Utente, Password) per l'accesso al database MySQL.
2. **Compilazione:** Esegui una pulizia e compilazione del progetto tramite Maven (`mvn clean compile`).
3. **Server:** Avvia per primo il file `Server.java` (o lancia il target Maven corrispondente) per metterlo in attesa di connessioni sulla porta TCP designata.
4. **Client:** Avvia l'applicazione Client lanciando `MainGUI.java`. Si aprirà la dashboard grafica da cui potrai selezionare e orchestrare le tue macchine virtuali e i tuoi container con un semplice click.
