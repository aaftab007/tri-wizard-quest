public class Tile {
    public static final int WALL = 0;
    public static final int PATH = 1;
    public static final int KEY = 2;
    public static final int TREASURE = 3;
    public static final int DOOR = 4;
    public static final int SWORD = 5;
    public static final int ICE_POWER = 6;

    private int x;
    private int y;
    private boolean isWalkable;
    private int type;

    public Tile(boolean isWalkable, int x, int y) {
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
        this.type = WALL;
    }

    public Tile() {

    }

    public int getType() {
        return type;
    }

    public boolean setType(int newType) {
        if (newType >= 0 && newType <= 6) {
            this.type = newType;
            return true;
        }
        return false;
    }

    public void setWalkable() {
        this.isWalkable = true;
        this.type = PATH;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
