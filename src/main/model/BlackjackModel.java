package main.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.IntStream;

/**
 * La classe BlackjackModel implementa la logica del gioco.
 * 
 * <p>
 * Pattern utlizzati:
 * - MVC (Model-View-Controller): come parte del Model per gestire i giocatori
 * in partita, il mazzo di carte e lo stato della partita stessa.
 * - Observer: consente alla View di essere notificata ogni volta che avviene un
 * cambiamento di stato;
 * </p>
 * 
 * <p>
 * La logica include il mescolamento e la distribuzione delle carte, le
 * azioni "Pesca Carta" e "Stai", la gestione dei turni dei giocatori e il
 * determinare a fine partita il vincitore.
 * </p>
 */
public class BlackjackModel extends Observable {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private UserProfile userProfile;
    private Player dealer;

    /**
     * Costruttore della classe BlackjackModel.
     * 
     * <p>
     * Inizializza il giocatore umano, i bot, il Banco, il mazzo di carte e carica
     * il profilo utente del giocatore umano per gestirne le statistiche.
     * </p>
     * 
     * @param userProfile Il profilo utente del giocatore umano, compreso
     *                    di tutte le sue informazioni.
     */
    public BlackjackModel(UserProfile userProfile) {
        players = new ArrayList<>();
        players.add(new Player(userProfile.getNickname(), new HumanPlayerStrategy(), true));
        players.add(new Player("MaxVerstappen", new BotStrategy(), false));
        players.add(new Player("KimiRaikkonen", new BotStrategy(), false));
        dealer = new Player("Banco", new DealerStrategy(), false);
        deck = Deck.getInstance();
        currentPlayerIndex = 0;
        this.userProfile = userProfile;
        userProfile.loadProfile();
    }

    /**
     * Metodo che avvia il gioco, mescola il mazzo di carte e distribuisce le
     * due carte iniziali a tutti i giocatori.
     */
    public void startGame() {
        deck.shuffle();
        dealInitialCards();
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che distribuisce le carte iniziali ai giocatori e al Banco.
     * 
     * <p>
     * Ogni giocatore riceve due carte all'inizio del gioco.
     * </p>
     */
    private void dealInitialCards() {
        IntStream.range(0, 2).forEach(i -> {
            players.forEach(player -> player.addCard(deck.drawCard()));
            dealer.addCard(deck.drawCard());
        });
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che esegue l'azione "Pesca Carta" per il giocatore corrente,
     * aggiungendo una nuova carta alla sua mano.
     * 
     * <p>
     * Se il giocatore sballa (ovvero supera il valore di 21), il metodo
     * restituisce true, altrimenti false.
     * </p>
     * 
     * @return true se il giocatore ha sballato, altrimenti false.
     */
    public boolean hit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        setChanged();
        notifyObservers();
        return currentPlayer.getHandValue() > 21;
    }

    /**
     * Metodo che esegue l'azione "Stai" per il giocatore corrente,
     * terminando il suo turno e passando al giocatore successivo.
     */
    public void stand() {
        currentPlayerIndex++;
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che verifica se è il turno del giocatore umano.
     * 
     * @return true se è il turno del giocatore umano, altrimenti false.
     */
    public boolean isHumanTurn() {
        return currentPlayerIndex == 0;
    }

    /**
     * Metodo che determina se il bot corrente voglia eseguire l'azione
     * "Pesca Carta".
     * 
     * @return true se il bot vuole pescare una carta, altrimenti false.
     */
    public boolean botWantsToHit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.wantsToHit();
    }

    /**
     * Metodo che termina il round, esegue il turno del Banco
     * e determina il vincitore.
     * Salva il profilo utente del giocatore umano e notifica gli
     * Observer che la partita è terminata tramite il segnale "GAME_OVER".
     */
    public void endRound() {
        playDealerTurn();
        determineWinner();
        userProfile.saveProfile();
        setChanged();
        notifyObservers("GAME_OVER");
    }

    /**
     * Metodo che esegue il turno del Banco.
     * Il Banco continua a pescare carte finché non decide
     * di fermarsi o sballa (ossia che supera 21).
     */
    private void playDealerTurn() {
        while (dealer.wantsToHit()) {
            dealer.addCard(deck.drawCard());
            if (dealer.getHandValue() > 21) {
                break;
            }
        }
    }

    /**
     * Metodo che determina il vincitore del round confrontando il valore
     * della mano del giocatore umano e del Banco.
     * Aggiorna le statistiche del profilo giocatore
     * in base al risultato della partita.
     */
    private void determineWinner() {
        Player humanPlayer = players.get(0);
        int humanHandValue = humanPlayer.getHandValue();
        int dealerHandValue = dealer.getHandValue();

        if (humanHandValue > 21) {
            userProfile.incrementGamesLost();
        } else if (dealerHandValue > 21) {
            userProfile.incrementGamesWon();
        } else if (humanHandValue > dealerHandValue) {
            userProfile.incrementGamesWon();
        } else if (humanHandValue < dealerHandValue) {
            userProfile.incrementGamesLost();
        }
        userProfile.incrementGamesPlayed();
    }

    /**
     * Metodo che ripristina il round corrente,
     * ripristinando le mani dei giocatori e del Banco.
     * Mescola il mazzo e distribuisce nuove carte.
     */
    public void resetRound() {
        players.forEach(Player::clearHand);
        dealer.clearHand();
        currentPlayerIndex = 0;
        deck = Deck.getInstance();
        deck.shuffle();
        dealInitialCards();
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che verifica se il gioco è terminato.
     * 
     * @return true se tutti i giocatori hanno completato il loro turno,
     *         altrimenti false.
     */
    public boolean isGameOver() {
        return currentPlayerIndex >= players.size();
    }

    /**
     * Metodo che verifica se è il turno di un bot o del Banco.
     * 
     * @return true se è il turno di un bot o del Banco, altrimenti false.
     */
    public boolean isBotOrDealerTurn() {
        if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
            return false;
        }
        Player currentPlayer = players.get(currentPlayerIndex);
        return !currentPlayer.isHuman() || currentPlayer == dealer;
    }

    /**
     * Getter che restituisce la lista dei giocatori in gioco.
     * 
     * @return una lista di oggetti {@link Player}.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Getter che restituisce il Banco.
     * 
     * @return l'oggetto {@link Player} che rappresenta il Banco.
     */
    public Player getDealer() {
        return dealer;
    }

    /**
     * Getter che restituisce il profilo utente del giocatore umano.
     * 
     * @return il profilo utente del giocatore umano {@link UserProfile}.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Getter che restituisce l'indice del giocatore corrente.
     * 
     * @return l'indice del giocatore corrente.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Metodo che restituisce un messaggio che indica il vincitore della mano.
     * 
     * @return una stringa che rappresenta il messaggio del vincitore.
     */
    public String getWinnerMessage() {
        if (dealer.getHandValue() > 21) {
            return "Il Banco ha sballato. Tutti vincono, a meno di aver superato 21.";
        } else {
            // Filtra solo i giocatori che non hanno sballato e
            // confrontali anche con il Banco
            Player winner = players.stream()
                    .filter(p -> p.getHandValue() <= 21)
                    .max(Comparator.comparingInt(Player::getHandValue))
                    .orElse(dealer);

            // Confronta il valore della mano del Banco con quello del vincitore
            if (dealer.getHandValue() >= winner.getHandValue()) {
                return "Il Banco ha vinto la partita!";
            } else {
                return winner.getName() + " ha vinto la partita!";
            }
        }
    }
}
