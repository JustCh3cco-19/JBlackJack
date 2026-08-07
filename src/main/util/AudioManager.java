package main.util;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Represents the {@code AudioManager} class.
 */
public final class AudioManager {
    private static final AudioManager INSTANCE = new AudioManager();

    /**
     * Returns the shared audio manager instance.
     *
     * @return the shared audio manager
     */
    public static AudioManager getInstance() {
        return INSTANCE;
    }

    private AudioManager() { }

    /**
     * Plays an optional audio resource from the application classpath.
     * Missing or unsupported resources are ignored so that audio failures never
     * interrupt the game.
     *
     * @param resourcePath the absolute classpath location of the audio resource
     */
    public void play(String resourcePath) {
        URL resource = AudioManager.class.getResource(resourcePath);
        if (resource == null) return;
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(resource)) {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) clip.close();
            });
            clip.open(stream);
            clip.start();
        } catch (Exception ignored) {
            // Audio feedback is optional and must never prevent a game from starting.
        }
    }
}
