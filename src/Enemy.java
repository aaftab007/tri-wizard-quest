import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Enemy {
    private boolean isDead;   
    private Tile location;    
    private static final String DOOR_OPEN_SOUND = "/sounds/door_open.wav";
    private static final String DEAD_SOUND = "/sounds/monster_dead.wav";

    public Enemy(Tile location) {
        this.location = location;
    }

    public Enemy() {
        playSound(DOOR_OPEN_SOUND);
    }

    public void setDead(boolean dead) {
        isDead = dead;
        if (dead) {
            playSound(DEAD_SOUND);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public Tile getLocation() {
        return location;
    }

    public void setLocation(Tile location) {
        this.location = location;
    }
    
    private void playSound(String soundFile) {
        new Thread(() -> {
            try {
                URL soundUrl = getClass().getResource(soundFile);
                if (soundUrl == null) {
                    System.out.println("Sound file not found!");
                    return;
                }
                try (AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl);
                     Clip clip = AudioSystem.getClip()) {
                    clip.open(ais);
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000); // wait for sound to finish
                }
            } catch (Exception ex) {
                System.out.println("Error playing sound: " + ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
    }
   
}
