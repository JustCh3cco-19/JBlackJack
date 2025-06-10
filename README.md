# JBlackJack

[cite_start]JBlackJack è un gioco di carte Blackjack basato su Java. Questo progetto è stato sviluppato per l'esame di Metodi di Ingegneria del Software presso l'Università degli Studi di Roma "La Sapienza". [cite_start]Presenta un'interfaccia utente grafica costruita con **Java Swing**  [cite_start]e implementa vari pattern di progettazione per una struttura modulare ed estensibile.

---

## 📋 Caratteristiche

* [cite_start]**Profili Utente**: I giocatori possono creare un profilo scegliendo un nickname e un avatar. [cite_start]Le statistiche del giocatore, come partite giocate, vinte, perse, livello ed esperienza, vengono tracciate e possono essere visualizzate.
* [cite_start]**Logica di Gioco**: Il gioco segue le regole standard del Blackjack, in cui il giocatore compete contro il banco. [cite_start]Include un giocatore umano, due giocatori bot e il banco.
* [cite_start]**Sistema di Progressione**: I giocatori partono dal livello zero e guadagnano 100 punti esperienza per ogni vittoria. [cite_start]Per salire di livello è necessario un numero di punti pari al livello attuale moltiplicato per 1000.
* [cite_start]**Feedback Audio**: Il gioco include musica di sottofondo ed effetti sonori per azioni come pescare una carta o stare.

---

## 🛠️ Per Iniziare

Segui queste istruzioni per compilare ed eseguire il progetto.

### Prerequisiti

* Java Development Kit (JDK)

### Compilazione ed Esecuzione

1.  **Naviga nella directory di origine**:
    Apri il terminale e cambia la directory in quella in cui si trovano i tuoi file `.java`. Supponendo che la struttura del tuo pacchetto sia `main/blackjack`, dovresti navigare nella directory che contiene la cartella `main`.

2.  **Compila il progetto**:
    Esegui il seguente comando per compilare i file sorgente Java.

    ```bash
    javac -d . main/blackjack/JBlackJack.java
    ```

3.  **Esegui l'applicazione**:
    Dopo una compilazione riuscita, esegui la classe principale con questo comando.

    ```bash
    java main.blackjack.JBlackJack
    ```

---

## 💡 Snippet del Progetto

[cite_start]JBlackJack è un semplice gioco di Blackjack sviluppato in Java Swing[cite: 1, 3]. [cite_start]Consente agli utenti di creare un profilo [cite: 4][cite_start], giocare contro avversari controllati dall'IA  [cite_start]e tenere traccia delle proprie statistiche. [cite_start]Il progetto mette in mostra l'uso di diversi pattern di progettazione, tra cui MVC, Singleton e Factory Method, per creare un'applicazione ben strutturata e manutenibile.
