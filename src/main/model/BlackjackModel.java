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
        boolean humanWins = players.stream().allMatch(player -> player.getHandValue() <= 21 &&
                (dealer.getHandValue() > 21 || player.getHandValue() > dealer.getHandValue()));

        if (humanWins) {
            userProfile.incrementGamesWon();
        } else {
            userProfile.incrementGamesLost();
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
            return "Tutti i giocatori hanno vinto! Il dealer ha sforato.";
        } else {
            Player winner = players.stream()
                    .filter(p -> p.getHandValue() <= 21)
                    .max(Comparator.comparingInt(Player::getHandValue))
                    .orElse(dealer);
            return winner.getName() + " ha vinto la partita!";
        }
    }
}
