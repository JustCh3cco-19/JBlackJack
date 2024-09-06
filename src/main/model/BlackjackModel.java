package main.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.IntStream;

public class BlackjackModel extends Observable {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private UserProfile userProfile;
    private Player dealer;

    // Costruttore
    public BlackjackModel(UserProfile userProfile) {
        players = new ArrayList<>();
        players.add(new Player(userProfile.getNickname(), new HumanPlayerStrategy(), true));
        players.add(new Player("MaxVerstappen", new BotStrategy(), false));
        players.add(new Player("KimiRaikkonen", new BotStrategy(), false));
        dealer = new Player("Banco", new DealerStrategy(), false); // Aggiungi il banco
        deck = Deck.getInstance();
        currentPlayerIndex = 0;
        this.userProfile = userProfile;
        userProfile.loadProfile(); // Carica il profilo utente esistente, se presente
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

    public boolean hit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        setChanged();
        notifyObservers();
        return currentPlayer.getHandValue() > 21;
    }

    public void stand() {
        currentPlayerIndex++;
        setChanged();
        notifyObservers();
    }

    public boolean isHumanTurn() {
        return currentPlayerIndex == 0;
    }

    public boolean botWantsToHit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.wantsToHit(dealer.getVisibleCard());
    }

    public void endRound() {
        playDealerTurn();
        determineWinner();
        userProfile.saveProfile();
        setChanged();
        notifyObservers("GAME_OVER");
    }

    private void playDealerTurn() {
        while (dealer.wantsToHit(null)) {
            dealer.addCard(deck.drawCard());
            if (dealer.getHandValue() > 21) {
                break;
            }
        }
    }

    private void determineWinner() {
        Player humanPlayer = players.get(0);
        int humanHandValue = humanPlayer.getHandValue();
        int dealerHandValue = dealer.getHandValue();

        if (humanHandValue > 21) {
            // Il giocatore ha sballato
            userProfile.incrementGamesLost();
        } else if (dealerHandValue > 21) {
            // Il dealer ha sballato, il giocatore vince
            userProfile.incrementGamesWon();
        } else if (humanHandValue > dealerHandValue) {
            // Il giocatore ha un punteggio più alto del dealer
            userProfile.incrementGamesWon();
        } else if (humanHandValue < dealerHandValue) {
            // Il dealer ha un punteggio più alto del giocatore
            userProfile.incrementGamesLost();
        } else {
            // Pareggio
            // Puoi decidere come gestire i pareggi, ad esempio:
            // userProfile.incrementGamesTied();
        }
        userProfile.incrementGamesPlayed();
    }

    public void resetRound() {
        players.forEach(Player::clearHand);
        dealer.clearHand(); // Resetta anche il dealer
        currentPlayerIndex = 0;
        deck = Deck.getInstance();
        deck.shuffle();
        dealInitialCards();
        setChanged();
        notifyObservers(); // Notifica la vista dopo aver resettato il round
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

    public String getCurrentPlayerName() {
        return players.get(currentPlayerIndex).getName();
    }

    public int getCurrentPlayerHandValue() {
        return players.get(currentPlayerIndex).getHandValue();
    }

    public boolean isGameOver() {
        return currentPlayerIndex >= players.size();
    }

    public boolean isBotOrDealerTurn() {
        if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
            return false; // Assumi che non sia né il turno di un bot né del dealer
        }
        Player currentPlayer = players.get(currentPlayerIndex);
        return !currentPlayer.isHuman() || currentPlayer == dealer;
    }

    public String getWinnerMessage() {
        if (dealer.getHandValue() > 21) {
            return "Il dealer ha sballato. Tutti vincono, a meno di aver sballato.";
        } else {
            // Filtra solo i giocatori che non hanno sballato e confrontali anche con il
            // dealer
            Player winner = players.stream()
                    .filter(p -> p.getHandValue() <= 21)
                    .max(Comparator.comparingInt(Player::getHandValue))
                    .orElse(dealer);

            // Confronta il valore della mano del dealer con quello del vincitore
            if (dealer.getHandValue() >= winner.getHandValue()) {
                return "Il dealer ha vinto la partita!";
            } else {
                return winner.getName() + " ha vinto la partita!";
            }
        }
    }
}
