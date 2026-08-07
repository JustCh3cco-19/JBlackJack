package main.model;

/** Receives typed notifications from a {@link BlackjackModel}. */
@FunctionalInterface
public interface GameListener {
    /**
     * Handles a model event.
     *
     * @param model the model that emitted the event
     * @param event the emitted event
     */
    void gameChanged(BlackjackModel model, GameEvent event);
}
