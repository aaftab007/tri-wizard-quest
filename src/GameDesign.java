import java.awt.*;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GameDesign {
    private InstructionDesign instructions;
    private JPanel optionPanel;
    private JFrame frame;
    private JButton playButton, howButton, exitButton;
    private static final String BUTTON_SOUND = "sounds/button.wav";
    private static final String INTRO_SOUND = "sounds/intro.wav";

    public GameDesign(Game game, int width, int height) {
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        initializeComponents(game);
        frame.pack();
        frame.setVisible(true);
    }

    private void initializeComponents(Game game) {
        instructions = new InstructionDesign();
        optionPanel = new OptionPanel(game);
        
        playSound(INTRO_SOUND);
        ImageIcon linkIcon = new ImageIcon(getClass().getResource("/pictures/linkImage.gif"));
        ImageIcon titleIcon = new ImageIcon(getClass().getResource("/pictures/tri.png"));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 10;
        c.gridheight = 10;
        frame.add(new JLabel(titleIcon), c);

        c.gridx = 3;
        c.gridy = 15;
        c.gridwidth = 4;
        c.gridheight = 10;
        c.insets = new Insets(0, 250, 0, 0);
        frame.add(new JLabel(linkIcon), c);

        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 26;
        c.insets = new Insets(0, 235, 0, 0);
        playButton = new JButton("START");
        playButton.setBackground(Color.WHITE);
        playButton.addActionListener(e -> handleButtonAction(game));
        frame.add(playButton, c);

        c.gridx = 4;
        c.gridy = 26;
        c.insets = new Insets(0, 0, 0, 0);
        howButton = new JButton("INSTRUCTIONS");
        howButton.setBackground(Color.WHITE);
        howButton.addActionListener(e -> instructions.setVisible(true));
        frame.add(howButton, c);

        c.gridx = 5;
        c.gridy = 26;
        exitButton = new JButton("EXIT");
        exitButton.setBackground(Color.WHITE);
        exitButton.addActionListener(e -> {
            playSound(BUTTON_SOUND);
            System.exit(0);
        });
        frame.add(exitButton, c);
    }

    private void handleButtonAction(Game game) {
        playSound(BUTTON_SOUND);
        if (JOptionPane.showConfirmDialog(null, optionPanel, "Choose Name & Character ", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            playSound(BUTTON_SOUND);
            game.getPlayer().setName(optionPanel.getName());
            game.createMaze();
            game.setIsInGame(true);
            game.setIsGameOver(false);
            frame.setVisible(false);
        }
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

    public JFrame getFrame() {
        return frame;
    }
}
