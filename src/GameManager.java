import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameManager {
    private GameDesign gameDesign;
    private MazeDesign mazeDesign;
    private Game game;
    
    private static final String CYAN_SOUND = "/sounds/champ_dead.wav";
    private static final String VICTORY_SOUND = "/sounds/victory.wav";
    public GameManager() {
        this.game = new Game();
    }

    public void run() {
        gameDesign = new GameDesign(game, 800, 800);
        while (true) {
            if (!game.isInGame())
                continue;
            createNewMazeDesign();
            long lastUpdateTime = System.currentTimeMillis();
            while (!game.isGameOver()) {
                if (System.currentTimeMillis() - lastUpdateTime > 20) {
                    game.updateScore();
                    if (!game.getFinishedLevel()) {
                        updateMazeDesign();
                    } else {
                        createNewMazeDesign();
                        game.setFinishedLevel(false);
                    }
                    lastUpdateTime = System.currentTimeMillis();
                }
            }
            showGameDesign();
        }
    }
    
    

    private void updateMazeDesign() {
        this.mazeDesign.update(game.getMaze());
        this.mazeDesign.getFrame().requestFocus();
        this.mazeDesign.getFrame().repaint();
        if (game.getPlayer().isDead()) {
            playSound(CYAN_SOUND);
            showOptionDialog("Dragon killed you! You're toasted!!", "OH NO!", "/pictures/dragon.gif", "End campaign");
            endGame();
        } else if (game.getMaze().exitedMaze()) {
            playSound(VICTORY_SOUND);
            showOptionDialog("Congratulations.. You' ve cleared this level!!\n Get ready to face new challenges!", "Room " + (game.getLevel()+1) + " cleared!", "/pictures/door_open.gif", "Next level");
            game.checkNextLevel();
        }
    }

    private void showGameDesign() {
        if (game.getLevel() == Game.MAX_LEVEL) {
            playSound(VICTORY_SOUND);
            showOptionDialog("Congratulations participant!\nYou are now a Dragon Warrior!!\n", "Level cleared!", "/pictures/door_open.gif", "Exit");
        }
        game.getPlayer().clearStats();
        game.clearGame();
        mazeDesign.getFrame().dispose();
        gameDesign.getFrame().setVisible(true);
    }

    private void createNewMazeDesign() {
        if (mazeDesign != null) {
            mazeDesign.getFrame().dispose();
        }
        mazeDesign = new MazeDesign(game, game.getMaze().getWidth(), game.getMaze().getHeight());
        mazeDesign.init(game.getMaze());
        mazeDesign.getFrame().requestFocus();
        mazeDesign.getFrame().addKeyListener(game.getController());
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

    private void showOptionDialog(String message, String title, String iconPath, String buttonText) {
        JOptionPane.showOptionDialog(
            mazeDesign.getFrame(),
            message,
            title,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            new ImageIcon(getClass().getResource(iconPath)),
            new Object[]{buttonText},
            buttonText
        );
    }

    private void endGame() {
        game.setIsGameOver(true);
        game.setIsInGame(false);
        mazeDesign.getFrame().requestFocus();
    }
}
