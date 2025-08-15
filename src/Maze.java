import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Maze {
    private Tile[][] grid;
    private int width;
    private int height;
    private Player player;
    private Enemy[] enemy;
    
    private static final String KEY_SOUND = "/sounds/key.wav";
    private static final String COIN_SOUND = "/sounds/coin.wav";
    private static final String SPELL_SOUND = "/sounds/spell.wav";
    private static final String SWORD_SOUND = "/sounds/sword.wav";
    
    
    public Maze(int w, int h, Player p) {
        width = w + 2;
        height = h + 2;
        grid = new Tile[width][height];
        player = p;
        enemy = new Enemy[(width - 13) / 3 + 1];
        
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Enemy();
        }
        MazeGenerator mazeGenerator = new AlgoGenerator();
        createMaze(mazeGenerator);
        startEnemy();
    }

    private void createMaze(MazeGenerator mazeGenerator) {
        grid = mazeGenerator.genMaze(width, height);
        player.setLocation(grid[1][1]);

        placeEnemies();
        placeDoors();
        placeItems();
    }

    private void placeEnemies() {
        int numEnemy = 0;
        while (numEnemy < enemy.length) {
            int randomX = 1 + (int) (Math.random() * (width - 2));
            int randomY = (height - 2) / 3 * 2 + (int) (Math.random() * ((height - 2) / 3) + 1);
            if (isSuitableLocation(randomX, randomY, player)) {
                enemy[numEnemy++].setLocation(grid[randomX][randomY]);
            }
        }
    }

    private void placeDoors() {
        grid[1][0].setType(Tile.DOOR);
        grid[width - 2][height - 1].setType(Tile.DOOR);
    }

    private void placeItems() {
        placeKeyAndSword();
        placePowerItems();
        placeTreasures();
    }

    private void placeKeyAndSword() {
        grid[1][height - 2].setType(Tile.KEY);
        grid[width - 2][1].setType(Tile.SWORD);
    }

    private void placePowerItems() {
        int numIcePower = 0;
        while (numIcePower < enemy.length) {
            int randomX = 1 + (int) (Math.random() * (width - 2));
            int randomY = 1 + (int) (Math.random() * (height - 2));
            if (isSuitableLocation(randomX, randomY, player)) {
                grid[randomX][randomY].setType(Tile.ICE_POWER);
                numIcePower++;
            }
        }
    }

    private void placeTreasures() {
        int numTreasure = 0;
        while (numTreasure < (width - 13) / 3 + 3) {
            int randomX = 1 + (int) (Math.random() * (width - 2));
            int randomY = 1 + (int) (Math.random() * (height - 2));
            if (isSuitableLocation(randomX, randomY, player)) {
                grid[randomX][randomY].setType(Tile.TREASURE);
                numTreasure++;
            }
        }
    }

    private boolean isSuitableLocation(int x, int y, Player p) {
        return grid[x][y].getType() == Tile.PATH && !grid[x][y].equals(p.getLocation());
    }

    public void showMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(getMazeChar(i, j, player.getLocation()));
            }
            System.out.println();
        }
    }

    private char getMazeChar(int x, int y, Tile playerLoc) {
        if (grid[x][y].isWalkable()) {
            if (playerLoc != null && playerLoc.equals(grid[x][y])) {
                return 'P';
            } else if (x == width - 2 && y == height - 2) {
                return 'D';
            } else {
                return '0';
            }
        } else {
            return '-';
        }
    }

    public boolean findPath(int x, int y, boolean[][] visited, HashMap<Tile, Tile> parent) {
        if (x == width - 2 && y == height - 2) return true;
        if (!grid[x][y].isWalkable() || visited[x][y]) return false;
        visited[x][y] = true;
        return findPathFrom(x, y, visited, parent);
    }

    private boolean findPathFrom(int x, int y, boolean[][] visited, HashMap<Tile, Tile> parent) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (isValidLocation(newX, newY) && findPath(newX, newY, visited, parent)) {
                parent.put(grid[newX][newY], grid[x][y]);
                return true;
            }
        }
        return false;
    }

    public boolean findPath(int x, int y, int x1, int y1, boolean[][] visited, HashMap<Tile, Tile> parent) {
        if (x == x1 && y == y1) return true;
        if (!grid[x][y].isWalkable() || visited[x][y]) return false;
        visited[x][y] = true;
        return findPathToDestination(x, y, x1, y1, visited, parent);
    }

    private boolean findPathToDestination(int x, int y, int x1, int y1, boolean[][] visited, HashMap<Tile, Tile> parent) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (isValidLocation(newX, newY) && findPath(newX, newY, x1, y1, visited, parent)) {
                parent.put(grid[newX][newY], grid[x][y]);
                return true;
            }
        }
        return false;
    }

    private boolean isValidLocation(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public List<Tile> giveHint(Tile t) {
        boolean[][] visited = new boolean[width][height];
        HashMap<Tile, Tile> parent = new HashMap<>();
        parent.put(t, null);
        boolean pathFound = findPath(t.getX(), t.getY(), visited, parent);
        if (!pathFound) {
            return null;
        } else {
            return buildPath(parent, grid[width - 2][height - 2]);
        }
    }

    private List<Tile> buildPath(HashMap<Tile, Tile> parent, Tile destination) {
        LinkedList<Tile> path = new LinkedList<>();
        for (Tile curr = destination; curr != null; curr = parent.get(curr)) {
            path.addFirst(curr);
        }
        return path.subList(0, Math.min(10, path.size()));
    }

    public Tile getPlayerTile() {
        return player.isDead() ? null : player.getLocation();
    }

    public Tile getEnemyTile(int i) {
        return (i >= enemy.length || enemy[i].isDead()) ? null : enemy[i].getLocation();
    }

    public boolean isEnemyTile(Tile t) {
        for (Enemy e : enemy) {
            if (!e.isDead() && e.getLocation().equals(t)) {
                return true;
            }
        }
        return false;
    }

    public Tile getDestDoor() {
        return grid[width - 2][height - 1];
    }
	
    public void updatePlayerLoc(int x, int y) {
        if (!isValid(x, y) || player.isDead()) return;

        player.setLocation(grid[player.getLocation().getX() + x][player.getLocation().getY() + y]);
        Tile playerLoc = player.getLocation();
        checkEnemyInteraction(playerLoc);
        checkTileType(playerLoc);
        checkReachedEnd();
    }

    private void checkEnemyInteraction(Tile playerLoc) {
        for (Enemy enemy : enemy) {
            if (playerLoc.equals(enemy.getLocation()) && !enemy.isDead()) {
                resolvePlayerEnemyConflict(enemy);
            }
        }
    }

    private void resolvePlayerEnemyConflict(Enemy enemy) {
        if (!itemCollected(Player.SWORD) && !itemCollected(Player.ICE_POWER)) {
            player.setDead(true);
        } else {
            enemy.setDead(true);
            player.addEnemyKilled();
        }
    }

    private void checkTileType(Tile tile) {
        switch (tile.getType()) {
            case Tile.KEY:
                playSound(KEY_SOUND);
                player.setItemCollected(Player.KEY, true);
                break;
            case Tile.TREASURE:
                playSound(COIN_SOUND);
                player.addNumTreasureCollected();
                break;
            case Tile.SWORD:
                playSound(SWORD_SOUND);
                player.setItemCollected(Player.SWORD, true);
                break;
            case Tile.ICE_POWER:
                playSound(SPELL_SOUND);
                player.setItemCollected(Player.ICE_POWER, true);
                break;
        }
        tile.setType(Tile.PATH);
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

    private void startEnemy() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (player.isDead()) {
                    timer.cancel();
                } else {
                    updateEnemyPositions();
                }
            }
        }, 1500, 1500);
    }

    private void updateEnemyPositions() {
        for (Enemy e : enemy) {
            if (!e.isDead()) {
                updateSingleEnemyPosition(e);
            }
        }
    }

    private void updateSingleEnemyPosition(Enemy enemy) {
        if (player.isItemCollected(Player.ICE_POWER)) {
            delayEnemyMovement();
        }
        Tile enemyLoc = enemy.getLocation();
        HashMap<Tile, Tile> path = new HashMap<>();
        path.put(enemyLoc, null);
        boolean[][] visited = new boolean[width][height];
        findPath(enemyLoc.getX(), enemyLoc.getY(), player.getLocation().getX(), player.getLocation().getY(), visited, path);
        Tile next = getNextTileInPath(path, enemyLoc);
        enemy.setLocation(next);
        if (enemy.getLocation().equals(player.getLocation())) {
            resolvePlayerEnemyConflict(enemy);
        }
    }

    private void delayEnemyMovement() {
        try {
            Thread.sleep(5000);
            player.setItemCollected(Player.ICE_POWER, false);
        } catch (InterruptedException ignored) {
        }
    }

    private Tile getNextTileInPath(HashMap<Tile, Tile> path, Tile start) {
        Tile current = player.getLocation();
        while (!path.get(current).equals(start)) {
            current = path.get(current);
        }
        return current;
    }

    public boolean playerDied() {
        return player.isDead();
    }

    public boolean enemyDied(int i) {
        return i >= 0 && i < enemy.length && enemy[i].isDead();
    }

    public boolean allEnemyDied() {
        for (Enemy e : enemy) {
            if (!e.isDead()) {
                return false;
            }
        }
        return true;
    }

    public int getNumEnemies() {
        return enemy.length;
    }

    public boolean isValid(int x, int y) {
        return isValidMovement(x, y) && isWithinBounds(x, y) && isWalkable(x, y);
    }

    private boolean isValidMovement(int x, int y) {
        return Math.abs(x) <= 1 && Math.abs(y) <= 1 && !(x != 0 && y != 0);
    }

    private boolean isWithinBounds(int x, int y) {
        Tile playerLoc = player.getLocation();
        int newX = playerLoc.getX() + x, newY = playerLoc.getY() + y;
        return newX >= 0 && newY >= 0 && newX < width && newY < height;
    }

    private boolean isWalkable(int x, int y) {
        Tile playerLoc = player.getLocation();
        return grid[playerLoc.getX() + x][playerLoc.getY() + y].isWalkable();
    }

    public boolean checkReachedEnd() {
        Tile playerLoc = player.getLocation();
        if (player.isDead() || !(playerLoc.getX() == width - 2 && playerLoc.getY() == height - 2)) {
            return false;
        }
        if (itemCollected(Player.KEY)) {
            Tile exit = grid[width - 2][height - 1];
            exit.setType(Tile.PATH);
            exit.setWalkable();
        }
        return true;
    }

    public boolean atStart() {
        Tile playerLoc = player.getLocation();
        return !player.isDead() && playerLoc.getX() == 1 && playerLoc.getY() == 1;
    }

    public boolean exitedMaze() {
        Tile playerLoc = player.getLocation();
        return !player.isDead() && playerLoc.getX() == width - 2 && playerLoc.getY() == height - 1;
    }

    public int getNumTreasureCollected() {
        return player.getNumTreasureCollected();
    }

    public boolean itemCollected(int itemNum) {
        return player.isItemCollected(itemNum);
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            return grid[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getGrid() {
        return grid;
    }
}