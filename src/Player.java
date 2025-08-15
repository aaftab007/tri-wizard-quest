import java.util.ArrayList;

public class Player {
    private String name;
    private String character;
    private Tile location;
    private boolean isDead;
    private ArrayList<Boolean> inventory;
    private int numTreasureCollected;
    private int enemyKilled;

    public static final int KEY = 0;
    public static final int SWORD = 1;
    public static final int ICE_POWER = 2;
    public static final int NUM_INVENTORY_ITEMS = 3;

    public Player(String name, String character) {
        this.name = name;
        this.character = character;
        this.inventory = new ArrayList<>();
        initializeInventory();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Tile getLocation() {
        return location;
    }

    public void setLocation(Tile location) {
        this.location = location;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isItemCollected(int itemNum) {
        return inventory.get(itemNum);
    }

    public void setItemCollected(int itemNum, boolean collected) {
        inventory.set(itemNum, collected);
    }

    public int getNumTreasureCollected() {
        return numTreasureCollected;
    }

    public void addNumTreasureCollected() {
        this.numTreasureCollected++;
    }

    public int getEnemyKilled() {
        return enemyKilled;
    }

    public void addEnemyKilled() {
        this.enemyKilled++;
    }

    public void clearStats() {
        location = null;
        isDead = false;
        clearInventory();
        numTreasureCollected = 0;
        enemyKilled = 0;
    }

    private void initializeInventory() {
        for (int i = 0; i < NUM_INVENTORY_ITEMS; i++) {
            inventory.add(false);
        }
    }

    public void clearInventory() {
        for (int i = 0; i < NUM_INVENTORY_ITEMS; i++) {
            inventory.set(i, false);
        }
    }
}
