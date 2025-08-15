import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MazeDesign {

    private int height; 
    private int width;

    private Game g;

    private JFrame frame;
    private JPanel mazeGrid;
    private JLayeredPane[][] mazeGridComp;
    private Dimension blockSize;

    private JPanel sidePanel;
    private JLabel score;
    private JLabel level;
    private JButton exitButton;
    private JButton hintButton;
    private ArrayList<JLabel> inventory;

    private Tile lastPlayerPos;
    private Tile[] lastEnemyPos;
    private boolean updatedOnce;
    
    private static final String BUTTON_SOUND = "/sounds/button.wav";;
    private static final String HINT_SOUND = "/sounds/hint.wav";;

    private HashMap<String, Picture> pictures;

    public static final String wallPicture = "wall";
    public static final String pathPicture = "path";
    private String playerPicture;
    public static final String HermPicture = "hermoine";
    public static final String RonPicture = "ron";
    public static final String HarryPicture = "harry";
    public static final String doorPicture = "locked_door";
    public static final String keyPicture = "cup";
    public static final String coinPicture = "coin";
    public static final String enemyPicture = "dragon";
    public static final String killableEnemyPicture = "fdragon";
    public static final String swordPicture = "master_sword";
    public static final String spellPicture = "spell";
    public static final String hintPicture = "hint";
    
    

    public MazeDesign(Game game, int width, int height) {
        this.height = height;
        this.width = width;
        this.g = game;

        setupFrame();
        initializeComponents();
        setupListeners();
        setupPictures();
        configureLayout();

        frame.pack();
        frame.setVisible(true);
    }

    private void setupFrame() {
        frame = new JFrame();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
    }

    private void initializeComponents() {
        mazeGrid = new JPanel();
        mazeGridComp = new JLayeredPane[width][height];
        sidePanel = new JPanel();
        exitButton = new JButton("Exit");
        hintButton = new JButton("Hint");
        inventory = new ArrayList<>();

        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = (int) tk.getScreenSize().getWidth();
        int ySize = (int) tk.getScreenSize().getHeight();
        Dimension fullscreen = new Dimension(xSize, ySize);
        frame.setPreferredSize(fullscreen);

        blockSize = new Dimension((int) (ySize * 0.95 / height), (int) (ySize * 0.95 / height));
    }

    private void setupListeners() {
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                int dialogResult = showExitDialog();
                handleExit(dialogResult);
            }
        });

        hintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(HINT_SOUND);
                showHints();
            }
        });
    }

    private int showExitDialog() {
        Object[] options = {"Yes", "Cancel"};
        Icon icon = UIManager.getIcon("OptionPane.informationIcon");
        return JOptionPane.showOptionDialog(frame, "Game Progress will be lost! Are you Sure?\n", "Warning for Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, icon, options, options);
    }

    private void handleExit(int dialogResult) {
        if (dialogResult == JOptionPane.YES_OPTION) {
            g.setIsGameOver(true);
            g.setIsInGame(false);
        } else {
            frame.requestFocus();
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

    private void showHints() {
        Maze m = g.getMaze();
        List<Tile> hintTiles = m.giveHint(m.getPlayerTile());
        displayHints(hintTiles);
    }

    private void displayHints(List<Tile> hintTiles) {
        for (Tile tile : hintTiles) {
            if (mazeGridComp[tile.getX()][tile.getY()].getComponentCount() < 3) {
                JLabel hintImage = new JLabel(pictures.get(hintPicture).getPicture());
                mazeGridComp[tile.getX()][tile.getY()].add(hintImage, new Integer(0));
                mazeGridComp[tile.getX()][tile.getY()].repaint();
            }
        }
    }

    private void setupPictures() {
        pictures = new HashMap<>();
        int x = (int) blockSize.getWidth();
        int y = (int) blockSize.getHeight();
        playerPicture = g.getPlayer().getCharacter();

        // Initialize Pictures with dimensions
        String[] pictureNames = {wallPicture, pathPicture, playerPicture, doorPicture, keyPicture, enemyPicture, coinPicture, swordPicture, spellPicture, killableEnemyPicture, hintPicture};
        for (String pictureName : pictureNames) {
            pictures.put(pictureName, new Picture(pictureName, x, y));
        }
    }

    private void configureLayout() {
        sidePanel.setPreferredSize(new Dimension((int) (width * blockSize.getWidth() * 0.4), (int) (height * blockSize.getHeight())));
        sidePanel.setBackground(new Color(240, 240, 240));
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBorder(new LineBorder(Color.BLACK, 2));
    }


	
    public void update(Maze m) {
        Tile curPlayerPos = m.getPlayerTile();

        if (!m.playerDied() || lastPlayerPos != null) {
            if (!lastPlayerPos.equals(curPlayerPos)) {
                updatePlayerMovement(m, curPlayerPos);
            }
            lastPlayerPos = curPlayerPos;
        }

        updateEnemies(m);
    }

    private void updatePlayerMovement(Maze m, Tile curPlayerPos) {
        updateBlock(m, lastPlayerPos);
        if (!m.playerDied()) {
            updateBlock(m, curPlayerPos);
        }
        updateInventory(m);
        score.setText("Score: " + g.getScore());

        if (m.checkReachedEnd()) {
            updateBlock(m, m.getDestDoor());
        }
    }

    private void updateEnemies(Maze m) {
        for (int i = 0; i < m.getNumEnemies(); i++) {
            Tile curEnemyPos = m.getEnemyTile(i);
            if (!m.enemyDied(i)) {
                if (!lastEnemyPos[i].equals(curEnemyPos)) {
                    updateBlock(m, lastEnemyPos[i]);
                    updateBlock(m, curEnemyPos);
                    updatedOnce = false;
                } else if (m.itemCollected(Player.ICE_POWER) && !updatedOnce) {
                    updateBlock(m, lastEnemyPos[i]);
                    if (i == m.getNumEnemies() - 1) {
                        updatedOnce = true;
                    }
                }
                lastEnemyPos[i] = curEnemyPos;
            } else if (lastEnemyPos[i] != null) {
                updateBlock(m, lastEnemyPos[i]);
                lastEnemyPos[i] = null;
            }
        }
    }

    private void updateInventory(Maze m) {
        updateInventoryItem(m, Player.SWORD);
        updateInventoryItem(m, Player.KEY);
        updateInventoryItem(m, Player.ICE_POWER, true); 
    }

    private void updateInventoryItem(Maze m, int itemType, boolean toggleVisibility) {
        boolean collected = m.itemCollected(itemType);
        JLabel itemLabel = inventory.get(itemType);
        itemLabel.setVisible(collected);
        if (toggleVisibility && !collected) {
            itemLabel.setVisible(false);
        }
    }

    private void updateInventoryItem(Maze m, int itemType) {
        updateInventoryItem(m, itemType, false);
    }

    private void updateBlock(Maze m, Tile tile) {
        clearBlock(tile);
        String overlayPicture = determineOverlayPicture(m, tile);
        if (!overlayPicture.isEmpty()) {
            addOverlayImage(tile, overlayPicture);
        }
    }

    private void clearBlock(Tile tile) {
        int numComponents = mazeGridComp[tile.getX()][tile.getY()].getComponentCount();
        for (int i = 0; i < numComponents - 1; i++) {
            mazeGridComp[tile.getX()][tile.getY()].remove(0);
        }
    }

    private String determineOverlayPicture(Maze m, Tile tile) {
        if (tile.equals(m.getPlayerTile())) {
            return g.getPlayer().getCharacter();
        } else if (m.isEnemyTile(tile)) {
            return m.itemCollected(Player.ICE_POWER) ? killableEnemyPicture : enemyPicture;
        } else if (tile.equals(m.getDestDoor()) && tile.getType() == Tile.PATH) {
            return pathPicture;
        } else {
            return getTilePicture(tile);
        }
    }

    private String getTilePicture(Tile tile) {
        switch (tile.getType()) {
            case Tile.DOOR: return doorPicture;
            case Tile.KEY: return keyPicture;
            case Tile.TREASURE: return coinPicture;
            case Tile.SWORD: return swordPicture;
            case Tile.ICE_POWER: return spellPicture;
            default: return "";
        }
    }

    @SuppressWarnings("deprecation")
	private void addOverlayImage(Tile tile, String pictureName) {
        JLabel overlayImage = new JLabel(pictures.get(pictureName).getPicture());
        int index = (pictureName.equals(pathPicture)) ? -2 : 0;  // special case for pathPicture
        mazeGridComp[tile.getX()][tile.getY()].add(overlayImage, new Integer(index));
        frame.pack();
    }


    @SuppressWarnings("deprecation")
	public void init(Maze m) 
	{
		mazeGrid.removeAll();
		sidePanel.removeAll();
		
		mazeGrid.setLayout(new GridLayout(width, height));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		lastEnemyPos = new Tile[m.getNumEnemies()];
		for (int i = 0; i < m.getNumEnemies(); i++) {
			lastEnemyPos[i] = new Tile();
		}
		
		for (int y = 0; y < height; y++)
		{
			gbc.gridy = y;	
			for (int x = 0; x < width; x++)
			{
				gbc.gridx = x; 
				
				Tile t = m.getTile(x, y);
				
				JLayeredPane block = new JLayeredPane();
				this.mazeGridComp[x][y] = block;	
				block.setPreferredSize(blockSize);
				block.setLayout(new OverlayLayout(block));
				
				String blockPicture = pathPicture;	
				String overLayPicture = "";	
				if (m.getPlayerTile() != null && m.getPlayerTile().equals(t)) {
					overLayPicture = playerPicture;
					this.lastPlayerPos = t; 
				}
				else if (m.isEnemyTile(t)) {
					for (int i = 0; i < m.getNumEnemies(); i++) {	
						if (m.getEnemyTile(i) != null && m.getEnemyTile(i).equals(t)) {
							overLayPicture = enemyPicture;
							this.lastEnemyPos[i] = t;
							break;
						}
					}
				}
				else if (t.getType() == Tile.DOOR) {
					blockPicture = wallPicture;
					overLayPicture = doorPicture;
				}
				else if (t.getType() == Tile.KEY) {
					overLayPicture = keyPicture;
				}
				else if (t.getType() == Tile.TREASURE) {
					overLayPicture = coinPicture;
				}
				else if (t.getType() == Tile.SWORD){
					overLayPicture = swordPicture;
				}
				else if (t.getType() == Tile.ICE_POWER) {
					overLayPicture = spellPicture;
				}
				else if (t.getType() == Tile.WALL){
					blockPicture = wallPicture;
				}
				JLabel pictureImage = new JLabel(pictures.get(blockPicture).getPicture());
				block.add(pictureImage, new Integer(-2));
				
				if (overLayPicture != "") {
					JLabel overlayImage = new JLabel(pictures.get(overLayPicture).getPicture());
					block.add(overlayImage, new Integer(0));
				}
				
				mazeGrid.add(block, gbc);
			}
		}
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		frame.add(mazeGrid, gbc);
		
		JPanel playerPanel = new JPanel(new GridLayout(3,1));
		playerPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		ImageIcon title = new ImageIcon(this.getClass().getResource("/pictures/tri.png"));
		Image image = title.getImage().getScaledInstance(288, 144, Image.SCALE_FAST);
		title.setImage(image);	
		JLabel titleImage = new JLabel(title);
		playerPanel.add(titleImage);
		
		Picture player = new Picture(g.getPlayer().getCharacter(),96,96);	
		JLabel playerImage = new JLabel(player.getPicture());
		playerImage.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		playerPanel.add(playerImage);
		
		JPanel playerStats = new JPanel(new GridLayout(4,1));
		JLabel name = new JLabel("Name: " + g.getPlayer().getName());
		JLabel character = new JLabel("Character: " + g.getPlayer().getCharacter().substring(0, 1).toUpperCase() + g.getPlayer().getCharacter().substring(1));
		score = new JLabel("Score: " + g.getScore());
		level = new JLabel("Level: " + (g.getLevel()+1)); 
		
		name.setFont(new Font("Arial", Font.PLAIN, 16));
		character.setFont(new Font("Arial", Font.PLAIN, 16));
		score.setFont(new Font("Arial", Font.BOLD, 18));
		level.setFont(new Font("Arial", Font.BOLD, 18));
		
		playerStats.add(name);
		playerStats.add(character);
		playerStats.add(score);
		playerStats.add(level);	
		playerPanel.add(playerStats);	
		
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		sidePanel.add(playerPanel);	
		
		gbc.gridx = 0;
		gbc.gridy = -3;
		
		hintButton.setMargin(new Insets(5, 10, 5, 10));
		exitButton.setToolTipText("Click here to exit to main menu.");
		sidePanel.add(hintButton, gbc);
		exitButton.setMargin(new Insets(5, 10, 5, 10));
		sidePanel.add(exitButton, gbc);
		
		inventory.add(Player.KEY, new JLabel(new Picture(keyPicture,48,48).getPicture()));
		inventory.add(Player.SWORD, new JLabel(new Picture(swordPicture,48,48).getPicture()));
		inventory.add(Player.ICE_POWER, new JLabel(new Picture(spellPicture,48,48).getPicture()));
		
		 JPanel invPanel = new JPanel();
         invPanel.setPreferredSize(new Dimension(200,60));
        
         gbc.gridwidth = 1;
         gbc.gridy = 0;
         gbc.gridx = 0;
         invPanel.add(inventory.get(Player.SWORD),gbc);
        
         gbc.gridy = 0;
         gbc.gridx = 1;
         invPanel.add(inventory.get(Player.KEY),gbc);
        
         gbc.gridy = 0;
         gbc.gridx = 2;
         invPanel.add(inventory.get(Player.ICE_POWER),gbc);
        
         for (int i = 0; i < Player.NUM_INVENTORY_ITEMS; i++) {
        	 inventory.get(i).setVisible(false);
         }
         
         gbc.gridy = 2;
         gbc.gridx = 0;
         sidePanel.add(invPanel,gbc);
         
         gbc.gridx = 1;
         gbc.gridy = 0;
         frame.add(sidePanel, gbc);
        
         frame.pack();
	}
	
	public JFrame getFrame() {
		return frame;
	}	
}