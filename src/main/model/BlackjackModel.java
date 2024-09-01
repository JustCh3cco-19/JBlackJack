package main.model;

import java.util.*;
import java.util.stream.*;

public class BlackjackModel extends Observable {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private UserProfile userProfile;
    private Player dealer;

    public BlackjackModel(String nickname, String avatarPath) {
        players = new ArrayList<>();
        players.add(new Player(nickname, new HumanPlayerStrategy(), true));
        players.add(new Player("MaxVerstappen", new BotStrategy(), false));
        players.add(new Player("CharlesLeclerc", new BotStrategy(), false));
        dealer = new Player("Banco", new DealerStrategy(), false); // Aggiungi il dealer
        deck = Deck.getInstance();
        currentPlayerIndex = 0;
        userProfile = new UserProfile(nickname, avatarPath);
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
    }

    public void hit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        if (currentPlayer.getHandValue() > 21) {
            stand();
        }
        setChanged();
        notifyObservers();
    }

    public void stand() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            playDealerTurn();
        } else if (!players.get(currentPlayerIndex).isHuman()) {
            playAITurn();
        }
        setChanged();
        notifyObservers();
    }

    private void playAITurn() {
        Player aiPlayer = players.get(currentPlayerIndex);
        while (aiPlayer.wantsToHit(dealer.getVisibleCard())) {
            aiPlayer.addCard(deck.drawCard());
            if (aiPlayer.getHandValue() > 21) {
                break;
            }
        }
        stand();
    }

    private void playDealerTurn() {
        while (dealer.wantsToHit(null)) { // Il dealer gioca secondo la sua strategia
            dealer.addCard(deck.drawCard());
            if (dealer.getHandValue() > 21) {
                break;
            }
        }
        endRound();
    }

    private void endRound() {
        determineWinner();
        resetRound();
        setChanged();
        notifyObservers();
    }

    private void determineWinner() {
        boolean humanWins = players.stream().anyMatch(player -> player.getHandValue() <= 21 &&
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
}
