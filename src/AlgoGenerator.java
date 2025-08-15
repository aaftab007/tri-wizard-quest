import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AlgoGenerator implements MazeGenerator {

    public Tile[][] genMaze(int width, int height) {
        Tile[][] grid = initializeGrid(width, height);
        double[][] weights = initializeWeights(width, height);

        PriorityQueue<TileBorder> edges = initializeEdges(weights, grid);
        HashSet<TileBorder> edgesAdded = new HashSet<>();
        HashSet<Tile> visited = new HashSet<>();
        ArrayList<TileBorder> neighbours = getNeighbouringEdges(grid[1][1], grid);

        addInitialEdges(edges, edgesAdded, neighbours);

        visited.add(grid[1][1]);
        int numVertices = ((width - 1) / 2) * ((height - 1) / 2);

        generateMaze(grid, edges, edgesAdded, visited, numVertices);

        return grid;
    }

    private Tile[][] initializeGrid(int width, int height) {
        Tile[][] grid = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Tile t = new Tile(false, i, j);
                grid[i][j] = t;
            }
        }
        return grid;
    }

    private double[][] initializeWeights(int width, int height) {
        double[][] weights = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                weights[i][j] = Math.random();
            }
        }
        return weights;
    }

    private PriorityQueue<TileBorder> initializeEdges(double[][] weights, Tile[][] grid) {
        return new PriorityQueue<>(100, new Comparator<TileBorder>() {
            public int compare(TileBorder e1, TileBorder e2) {
                double weightDiff = (weights[e1.getTile0().getX()][e1.getTile0().getY()] +
                        weights[e1.getTile1().getX()][e1.getTile1().getY()] +
                        weights[e1.getTile2().getX()][e1.getTile2().getY()])
                        - (weights[e2.getTile0().getX()][e2.getTile0().getY()]
                        + weights[e2.getTile1().getX()][e2.getTile1().getY()]
                        + weights[e2.getTile2().getX()][e2.getTile2().getY()]);
                if (weightDiff > 0) return 1;
                if (weightDiff < 0) return -1;
                return 0;
            }
        });
    }

    private void addInitialEdges(PriorityQueue<TileBorder> edges, HashSet<TileBorder> edgesAdded, ArrayList<TileBorder> neighbours) {
        for (TileBorder neighbour : neighbours) {
            edges.add(neighbour);
            edgesAdded.add(neighbour);
        }
    }

    private void generateMaze(Tile[][] grid, PriorityQueue<TileBorder> edges, HashSet<TileBorder> edgesAdded, HashSet<Tile> visited, int numVertices) {
        while (visited.size() < numVertices) {
            TileBorder curr = edges.remove();
            if ((visited.contains(curr.getTile0()) && visited.contains(curr.getTile2()))
                    || (!visited.contains(curr.getTile0()) && !visited.contains(curr.getTile2()))) {
                continue;
            }
            Tile nextTile = visited.contains(curr.getTile0()) ? curr.getTile2() : curr.getTile0();
            ArrayList<TileBorder> newNeighbours = getNeighbouringEdges(nextTile, grid);
            visited.add(nextTile);
            for (TileBorder neighbour : newNeighbours) {
                if (!edgesAdded.contains(neighbour)) {
                    edges.add(neighbour);
                    edgesAdded.add(neighbour);
                }
            }
            curr.getTile0().setWalkable();
            curr.getTile1().setWalkable();
            curr.getTile2().setWalkable();
        }
    }

    public ArrayList<TileBorder> getNeighbouringEdges(Tile curr, Tile[][] grid) {
        ArrayList<TileBorder> neighbouringEdges = new ArrayList<>();
        int width = grid[0].length;
        int height = grid.length;
        for (int i = Math.max(curr.getX() - 2, 1); i <= Math.min(curr.getX() + 2, width - 1); i += 2) {
            for (int j = Math.max(curr.getY() - 2, 1); j <= Math.min(curr.getY() + 2, height - 1); j += 2) {
                TileBorder newEdge = null;
                if (curr.getX() - i == 2 && curr.getY() - j == 0) {
                    newEdge = new TileBorder(grid[i][curr.getY()],
                            grid[i + 1][curr.getY()], curr);
                } else if (curr.getX() - i == -2 && curr.getY() - j == 0) {
                    newEdge = new TileBorder(curr, grid[i - 1][curr.getY()],
                            grid[i][curr.getY()]);
                } else if (curr.getX() - i == 0 && curr.getY() - j == 2) {
                    newEdge = new TileBorder(grid[curr.getX()][j],
                            grid[curr.getX()][j + 1], curr);
                } else if (curr.getX() - i == 0 && curr.getY() - j == -2) {
                    newEdge = new TileBorder(curr, grid[curr.getX()][j - 1],
                            grid[curr.getX()][j]);
                } else {
                    continue;
                }
                neighbouringEdges.add(newEdge);
            }
        }
        return neighbouringEdges;
    }
}
