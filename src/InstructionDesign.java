import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class InstructionDesign {

    private JButton backButton;
    private JPanel instructions;
    private JFrame frame;

    public InstructionDesign() {
        frame = new JFrame();
        frame.setTitle("Instructions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        backButton = new JButton("Close");
        instructions = new JPanel(new GridBagLayout());

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    URL url = new URL("");
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                    clip.open(ais);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                frame.setVisible(false);
            }
        });
        frame.getContentPane().setBackground(Color.WHITE);
        instructions.setBackground(Color.WHITE);
        frame.setResizable(true);
        frame.setLayout(new GridBagLayout());
        BufferedImage wasd = null;
        try {
            wasd = ImageIO.read(this.getClass().getResource("/pictures/arrow.png"));
        } catch (IOException e) {
            System.err.println("Failed to read image.");
        }

        BufferedImage htp = null;
        try {
            htp = ImageIO.read(this.getClass().getResource("/pictures/howtoplay2.png"));
        } catch (IOException e) {
            System.err.println("Error reading image.");
        }
        JLabel sword = new JLabel(new Picture(MazeDesign.swordPicture, 48, 48).getPicture());
        JLabel key = new JLabel(new Picture(MazeDesign.keyPicture, 48, 48).getPicture());
        JLabel coin = new JLabel(new Picture(MazeDesign.coinPicture, 48, 48).getPicture());
        JLabel enemy = new JLabel(new Picture(MazeDesign.enemyPicture, 48, 48).getPicture());
        JLabel freeze = new JLabel(new Picture(MazeDesign.spellPicture, 48, 48).getPicture());
        JLabel enemyF = new JLabel(new Picture(MazeDesign.killableEnemyPicture, 48, 48).getPicture());

        JLabel keyboard = new JLabel(new ImageIcon(wasd));
        JLabel howtoplay = new JLabel(new ImageIcon(htp));

        sword.setText("Pick your Sword to slay the dragons !!");
        key.setText("Collect the Cup to escape");
        coin.setText("Show me the money!!");
        enemy.setText("Get ready to burn alive");
        enemyF.setText("Kill the dragons when they're frozen after spell");
        keyboard.setText("Use the arrow keys to navigate");
        freeze.setText("Pick spell up to freeze dragon");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        c.gridy = 0;
        instructions.add(howtoplay, c);

        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 170, 0, 0);

        c.gridy = 2;
        instructions.add(enemy, c);

        c.gridy = 3;
        instructions.add(enemyF, c);

        c.gridx = 0;
        c.gridy = 4;
        instructions.add(sword, c);

        c.gridy = 5;
        instructions.add(freeze, c);

        c.gridy = 6;
        instructions.add(key, c);

        c.gridy = 7;
        instructions.add(coin, c);

        c.insets = new Insets(0, 120, 0, 0);
        c.gridy = 8;
        instructions.add(keyboard, c);

        c.gridy = 9;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 0);
        this.backButton.setEnabled(true);
        instructions.add(backButton, c);

        c.gridx = 0;
        frame.add(instructions);

        c.gridy = 1;
        frame.pack();
        frame.setVisible(false);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
