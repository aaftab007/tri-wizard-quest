import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Game {
    private Maze maze;
    private Player player;
    private Controller controller;

    private int difficulty;
    private int score;
    private int level;
    private boolean finishedLevel;

    private volatile boolean inGame;
    private volatile boolean isGameOver;

    private static final int START_LEVEL_WIDTH = 17;
    private static final int START_LEVEL_HEIGHT = 17;
    private static final int POINTS_ENEMY_KILLED = 10;
    public static final int MAX_LEVEL = 10;
    public static final int EASY = -1;
    public static final int MEDIUM = 0;
    public static final int HARD = 1;

    private static final String BUTTON_SOUND = "/sounds/button.wav";

    public Game() {
        isGameOver = false;
        inGame = false;
        player = new Player("Default", "Harry");
        score = 0;
        level = 0;
        difficulty = MEDIUM;
    }

    public void createMaze() {
        maze = new Maze(START_LEVEL_WIDTH + (2 * (level + difficulty)),
                        START_LEVEL_HEIGHT + (2 * (level + difficulty)), player);
        controller = new Controller(maze);
    }

    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setIsInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public Player getPlayer() {
        return player;
    }

    public Maze getMaze() {
        return maze;
    }

    public void updateScore() {
        score = player.getNumTreasureCollected() + POINTS_ENEMY_KILLED * player.getEnemyKilled();
    }

    public int getScore() {
        return score;
    }

    public void checkNextLevel() {
        playSound(BUTTON_SOUND);
        if (maze.exitedMaze()) {
            finishedLevel = true;
            level++;
            player.clearInventory();
            if (level == MAX_LEVEL) {
                setIsGameOver(true);
                setIsInGame(false);
            } else {
                createMaze();
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean getFinishedLevel() {
        return finishedLevel;
    }

    public void setFinishedLevel(boolean finished) {
        finishedLevel = finished;
    }

    public void clearGame() {
        score = 0;
        level = 0;
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
