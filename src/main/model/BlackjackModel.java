package main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * Represents the {@code BlackjackModel} class.
 */
public class BlackjackModel {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private UserProfile userProfile;
    private Player dealer;
    private final List<GameListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Creates a new {@code BlackjackModel} instance.
     * @param userProfile the user profile
     */
    public BlackjackModel(UserProfile userProfile) {
        players = new ArrayList<>();
        players.add(new Player(userProfile.getNickname(), new HumanPlayerStrategy(), true));
        players.add(new Player("MaxVerstappen", new BotStrategy(), false));
        players.add(new Player("KimiRaikkonen", new BotStrategy(), false));
        dealer = new Player("Banco", new DealerStrategy(), false);
        deck = new Deck();
        currentPlayerIndex = 0;
        this.userProfile = userProfile;
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        deck.reset();
        dealInitialCards();
        fireEvent(GameEvent.STATE_CHANGED);
    }

    /**
     * Deals two initial cards to every player and the dealer.
     */
    private void dealInitialCards() {
        IntStream.range(0, 2).forEach(i -> {
            players.forEach(player -> player.addCard(deck.drawCard()));
            dealer.addCard(deck.drawCard());
        });
    }

    /**
     * Handles the hit action.
     * @return the operation result
     */
    public boolean hit() {
        ensureActivePlayer();
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        fireEvent(GameEvent.STATE_CHANGED);
        return currentPlayer.getHandValue() > 21;
    }

    /**
     * Handles the stand action.
     */
    public void stand() {
        ensureActivePlayer();
        currentPlayerIndex++;
        fireEvent(GameEvent.STATE_CHANGED);
    }

    /**
     * Returns whether human turn.
     * @return whether human turn
     */
    public boolean isHumanTurn() {
        return currentPlayerIndex == 0;
    }

    /**
     * Returns whether the current bot should hit.
     * @return whether the player should hit
     */
    public boolean botWantsToHit() {
        Player currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.wantsToHit();
    }

    /**
     * Ends the round, evaluates its result, and saves the profile.
     */
    public void endRound() {
        playDealerTurn();
        determineWinner();
        userProfile.saveProfile();
        fireEvent(GameEvent.GAME_OVER);
    }

    /**
     * Plays the dealer turn.
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
     * Calculates the winner.
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
     * Resets the round.
     */
    public void resetRound() {
        players.forEach(Player::clearHand);
        dealer.clearHand();
        currentPlayerIndex = 0;
        deck.reset();
        dealInitialCards();
        fireEvent(GameEvent.STATE_CHANGED);
    }

    /**
     * Returns whether game over.
     * @return whether game over
     */
    public boolean isGameOver() {
        return currentPlayerIndex >= players.size();
    }

    /**
     * Returns whether bot or dealer turn.
     * @return whether bot or dealer turn
     */
    public boolean isBotOrDealerTurn() {
        if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
            return false;
        }
        Player currentPlayer = players.get(currentPlayerIndex);
        return !currentPlayer.isHuman() || currentPlayer == dealer;
    }

    /**
     * Returns the players.
     * @return the players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Returns the dealer.
     * @return the dealer
     */
    public Player getDealer() {
        return dealer;
    }

    /**
     * Returns the user profile.
     * @return the user profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Returns the current player index.
     * @return the current player index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Returns the winner message.
     * @return the winner message
     */
    public String getWinnerMessage() {
        int human = players.get(0).getHandValue();
        int bank = dealer.getHandValue();
        if (human > 21) return "Hai sballato: il Banco vince.";
        if (bank > 21) return "Il Banco ha sballato: hai vinto!";
        if (human > bank) return "Hai vinto " + human + " a " + bank + "!";
        if (human < bank) return "Il Banco vince " + bank + " a " + human + ".";
        return "Pareggio a " + human + ": la puntata è salva.";
    }

    /**
     * Registers a listener for model changes.
     *
     * @param listener the listener to register
     */
    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters a previously registered game listener.
     *
     * @param listener the listener to remove
     */
    public void removeGameListener(GameListener listener) {
        listeners.remove(listener);
    }

    private void fireEvent(GameEvent event) {
        listeners.forEach(listener -> listener.gameChanged(this, event));
    }

    private void ensureActivePlayer() {
        if (isGameOver()) throw new IllegalStateException("Il round è già terminato");
    }
}
