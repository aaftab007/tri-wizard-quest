import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Dimension;

public class OptionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JButton setHerm;	
	private JButton setRon;
	private JButton setHarry;
	private JTextField nameField;	
	
	private JRadioButton easyDifficulty;	
	private JRadioButton mediumDifficulty;
	private JRadioButton hardDifficulty;

	public OptionPanel(Game g){
		final Game game = g;
		this.setLayout(new GridBagLayout());		
		GridBagConstraints gbc = new GridBagConstraints();
		
		Picture HermPanel = new Picture(MazeDesign.HermPicture, 48, 48);
		Picture RonPanel = new Picture(MazeDesign.RonPicture, 48, 48);
		Picture HarryPanel = new Picture(MazeDesign.HarryPicture, 48, 48);
		
		setHerm = new JButton("Hermione Granger", HermPanel.getPicture() );
		setRon = new JButton("Ron Weasley", RonPanel.getPicture());
		setHarry = new JButton("Harry Potter", HarryPanel.getPicture());
		
		setHerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeDesign.HermPicture);
				new Thread(() -> {
		            try {
		                URL soundUrl = getClass().getResource("/sounds/button.wav");
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
		});
		setRon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeDesign.RonPicture);
				new Thread(() -> {
		            try {
		                URL soundUrl = getClass().getResource("/sounds/button.wav");
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
		});
		setHarry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeDesign.HarryPicture);
				new Thread(() -> {
		            try {
		                URL soundUrl = getClass().getResource("/sounds/button.wav");
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
		});;
		this.add(setHerm);	
		this.add(setRon);
		this.add(setHarry);
		
		nameField = new JTextField("Enter Name");
		nameField.setPreferredSize(new Dimension(200, 20));

		// Add focus listener to manage placeholder text
		nameField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (nameField.getText().equals("Enter Name")) {
		            nameField.setText("");
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (nameField.getText().isEmpty()) {
		            nameField.setText("Enter Name");
		        }
		    }
		});

		
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(20,20,0,0);
		
		JLabel nameText = new JLabel("Champion Name: ");
		this.add(nameText, gbc);
		
		gbc.gridx = 1;
		this.add(nameField, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		JLabel difficultyText = new JLabel("Difficulty Level:");
		this.add(difficultyText, gbc);
		
		ButtonGroup difficultyButtons = new ButtonGroup();
		easyDifficulty = new JRadioButton("Easy");
		mediumDifficulty = new JRadioButton("Medium", true);
		hardDifficulty = new JRadioButton("Hard");
		difficultyButtons.add(easyDifficulty);
		difficultyButtons.add(mediumDifficulty);
		difficultyButtons.add(hardDifficulty);
		
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(10,10,0,0);
		this.add(easyDifficulty,gbc);
		
		gbc.gridx = 1;
		this.add(mediumDifficulty,gbc);
		
		gbc.gridx = 2;
		this.add(hardDifficulty,gbc);
		
		easyDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.EASY);
				new Thread(() -> {
		            try {
		                URL soundUrl = getClass().getResource("/sounds/button.wav");
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
		});
		mediumDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.MEDIUM);
				new Thread(() -> {
		            try {
		                URL soundUrl = getClass().getResource("/sounds/button.wav");
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
		});
		hardDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.HARD);
				new Thread(() -> {
		            try {
		                URL soundUrl = getClass().getResource("/sounds/button.wav");
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
		});
	}
	
	public String getName(){
		return nameField.getText();
	}
}
