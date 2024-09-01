// model/BlackjackModel.java
package main.model;

import java.util.*;
import java.util.stream.*;

public class BlackjackModel extends Observable {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private UserProfile userProfile;

    public BlackjackModel(String nickname, String avatarPath) {
        players = new ArrayList<>();
        players.add(new Player(nickname, new HumanPlayerStrategy()));
        players.add(new Player("AI Player 1", new BasicAIStrategy()));
        players.add(new Player("AI Player 2", new BasicAIStrategy()));
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
        IntStream.range(0, 2).forEach(i -> players.forEach(player -> player.addCard(deck.drawCard())));
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
            endRound();
        } else if (players.get(currentPlayerIndex).getStrategy() instanceof BasicAIStrategy) {
            playAITurn();
        }
        setChanged();
        notifyObservers();
    }

    private void playAITurn() {
        Player aiPlayer = players.get(currentPlayerIndex);
        while (aiPlayer.wantsToHit(players.get(0).getVisibleCard())) {
            aiPlayer.addCard(deck.drawCard());
            if (aiPlayer.getHandValue() > 21) {
                break;
            }
        }
        stand();
    }

    private void endRound() {
        determineWinner();
        resetRound();
        setChanged();
        notifyObservers();
    }

    private void determineWinner() {
        Player humanPlayer = players.get(0);
        boolean humanWins = players.stream()
                .skip(1)
                .allMatch(player -> player.getHandValue() > 21 ||
                        (humanPlayer.getHandValue() <= 21 &&
                                humanPlayer.getHandValue() > player.getHandValue()));

        if (humanWins) {
            userProfile.incrementGamesWon();
        } else {
            userProfile.incrementGamesLost();
        }
        userProfile.incrementGamesPlayed();
    }

    private void resetRound() {
        players.forEach(Player::clearHand);
        currentPlayerIndex = 0;
        deck = Deck.getInstance();
        deck.shuffle();
        dealInitialCards();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}
