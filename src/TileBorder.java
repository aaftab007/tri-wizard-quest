public class TileBorder {
    private Tile tile0;
    private Tile tile1;
    private Tile tile2;

    public TileBorder(Tile tile0, Tile tile1, Tile tile2) {
        this.tile0 = tile0;
        this.tile1 = tile1;
        this.tile2 = tile2;
    }

    public boolean containsTile(Tile tile) {
        return this.tile0.equals(tile) || this.tile1.equals(tile) || this.tile2.equals(tile);
    }

    public Tile getTile0() {
        return tile0;
    }

    public Tile getTile1() {
        return tile1;
    }

    public Tile getTile2() {
        return tile2;
    }

    public void showEdge() {
        System.out.println(tile0.getX() + " " + tile0.getY() + " " + tile2.getX() + " " + tile2.getY());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        Tile minTile = tile0;
        Tile maxTile = tile2;
        if (tile0.hashCode() > tile2.hashCode()) {
            minTile = tile2;
            maxTile = tile0;
        }
        result = prime * result + ((minTile == null) ? 0 : minTile.hashCode());
        result = prime * result + ((tile1 == null) ? 0 : tile1.hashCode());
        result = prime * result + ((maxTile == null) ? 0 : maxTile.hashCode());
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
        TileBorder other = (TileBorder) obj;
        if (tile0 == null && tile2 != null) {
            if (other.tile0 != null && other.tile2 != null)
                return false;
        } else if (!tile0.equals(other.tile0) && !tile0.equals(other.tile2))
            return false;
        if (tile1 == null) {
            if (other.tile1 != null)
                return false;
        } else if (!tile1.equals(other.tile1))
            return false;
        if (tile2 == null && tile0 != null) {
            if (other.tile2 != null && other.tile0 != null)
                return false;
        } else if (!tile2.equals(other.tile2) && !tile2.equals(other.tile2))
            return false;
        return true;
    }
}
