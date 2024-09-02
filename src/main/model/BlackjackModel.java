package main.model;

import java.util.*;
import java.util.stream.*;

public class BlackjackModel extends Observable {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private UserProfile userProfile;
    private Player dealer;
    private boolean isBotOrDealerTurn; // Flag per indicare se è il turno di un bot o del dealer

    // Costruttore
    public BlackjackModel(String nickname, String avatarPath) {
        players = new ArrayList<>();
        players.add(new Player(nickname, new HumanPlayerStrategy(), true));
        players.add(new Player("MaxVerstappen", new BotStrategy(), false));
        players.add(new Player("KimiRaikkonen", new BotStrategy(), false));
        dealer = new Player("Banco", new DealerStrategy(), false); // Aggiungi il banco
        deck = Deck.getInstance();
        currentPlayerIndex = 0;
        userProfile = new UserProfile(nickname, avatarPath);
        userProfile.loadProfile(); // Carica il profilo utente esistente, se presente
        isBotOrDealerTurn = false;
    }

    public void startGame() {
        deck.shuffle();
        dealInitialCards();
        setChanged();
        notifyObservers();
    }

    private void dealInitialCards() {
        IntStream.range(0, 2).forEach(i -> {
            players.forEach(player -> player.addCard(deck.drawCard()));
            dealer.addCard(deck.drawCard()); // Distribuisce carte anche al dealer
        });
        setChanged();
        notifyObservers();
    }

    public void hit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        if (currentPlayer.getHandValue() > 21) {
            stand(); // Se il giocatore sfora, passa il turno
        } else {
            setChanged();
            notifyObservers(); // Notifica la vista ogni volta che il giocatore pesca una carta
        }
    }

    public void stand() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            // Tutti i giocatori hanno completato il turno, ora tocca al dealer
            playDealerTurn();
        } else {
            Player nextPlayer = players.get(currentPlayerIndex);
            if (!nextPlayer.isHuman()) {
                playAITurn(); // Se è un bot, giocano
            } else {
                setChanged();
                notifyObservers(); // Notifica la vista per il cambio di turno
            }
        }
    }

    private void playAITurn() {
        isBotOrDealerTurn = true;
        Player aiPlayer = players.get(currentPlayerIndex);
        new Thread(() -> { // Utilizza un thread per simulare la pausa tra le azioni
            while (aiPlayer.wantsToHit(dealer.getVisibleCard())) {
                aiPlayer.addCard(deck.drawCard());
                setChanged();
                notifyObservers(); // Notifica la vista ad ogni carta pescata
                try {
                    Thread.sleep(2000); // Pausa di 2 secondi per simulare la riflessione del bot
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (aiPlayer.getHandValue() > 21) {
                    break;
                }
            }
            stand(); // Dopo il turno del bot, passa al turno successivo
        }).start();
    }

    private void playDealerTurn() {
        isBotOrDealerTurn = true;
        new Thread(() -> { // Utilizza un thread per simulare la pausa tra le azioni
            while (dealer.wantsToHit(null)) {
                dealer.addCard(deck.drawCard());
                setChanged();
                notifyObservers(); // Notifica la vista ad ogni carta pescata
                try {
                    Thread.sleep(2000); // Pausa di 2 secondi per simulare la riflessione del dealer
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (dealer.getHandValue() > 21) {
                    break;
                }
            }
            endRound(); // Dopo che il dealer ha finito, conclude il round
        }).start();
    }

    private void endRound() {
        isBotOrDealerTurn = false;
        determineWinner();
        resetRound();
        userProfile.saveProfile(); // Salva il profilo utente dopo ogni round
        setChanged();
        notifyObservers();
    }

    private void determineWinner() {
        boolean humanWins = players.stream().allMatch(player -> player.getHandValue() <= 21 &&
                (dealer.getHandValue() > 21 || player.getHandValue() > dealer.getHandValue()));

        if (humanWins) {
            userProfile.incrementGamesWon();
        } else {
            userProfile.incrementGamesLost();
        }
        userProfile.incrementGamesPlayed();
    }

    private void resetRound() {
        players.forEach(Player::clearHand);
        dealer.clearHand(); // Resetta anche il dealer
        currentPlayerIndex = 0;
        deck = Deck.getInstance();
        deck.shuffle();
        dealInitialCards();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getDealer() {
        return dealer;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public boolean isBotOrDealerTurn() {
        return isBotOrDealerTurn;
    }
}
