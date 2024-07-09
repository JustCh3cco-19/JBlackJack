public class Main {
    public static void main(String[] args) {
        // Percorso relativo al file audio
        String audioFilePath = "resources/audio/paradise.wav";
        
        // Ottieni l'istanza di AudioManager e riproduci l'audio
        AudioManager audioManager = AudioManager.getInstance();
        audioManager.play(audioFilePath);
    }
}
