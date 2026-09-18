package pathfinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the field to traverse.
 *
 * Legend:
 *   'S' - start position
 *   'G' - goal position
 *   '#' - wall / obstacle (not walkable)
 *   '.' - open, walkable cell
 */
public class Grid {

    private final char[][] cells;
    private final int rows;
    private final int cols;
    private final Node start;
    private final Node goal;

    // 4-directional movement: up, down, left, right
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    public Grid(String[] fieldRows) {
        this.rows = fieldRows.length;
        this.cols = fieldRows[0].length();
        this.cells = new char[rows][cols];

        Node foundStart = null;
        Node foundGoal = null;

        for (int r = 0; r < rows; r++) {
            String line = fieldRows[r];
            if (line.length() != cols) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                cells[r][c] = ch;
                if (ch == 'S') {
                    foundStart = new Node(r, c);
                } else if (ch == 'G') {
                    foundGoal = new Node(r, c);
                }
            }
        }

        if (foundStart == null || foundGoal == null) {
            throw new IllegalArgumentException("Field must contain exactly one 'S' and one 'G'.");
        }

        this.start = foundStart;
        this.goal = foundGoal;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Node getStart() {
        return start;
    }

    public Node getGoal() {
        return goal;
    }

    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isWalkable(int row, int col) {
        return isInBounds(row, col) && cells[row][col] != '#';
    }

    /**
     * Returns the walkable neighbors of the given node (4-directional).
     */
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (int[] dir : DIRECTIONS) {
            int newRow = node.row + dir[0];
            int newCol = node.col + dir[1];
            if (isWalkable(newRow, newCol)) {
                neighbors.add(new Node(newRow, newCol));
            }
        }
        return neighbors;
    }

    /**
     * Prints the grid to the console, overlaying the given path (if any)
     * with '*' characters, excluding the start/goal cells.
     */
    public void printWithPath(List<Node> path) {
        char[][] display = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            display[r] = cells[r].clone();
        }

        if (path != null) {
            for (Node n : path) {
                if (display[n.row][n.col] == '.') {
                    display[n.row][n.col] = '*';
                }
            }
        }

        for (int r = 0; r < rows; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < cols; c++) {
                sb.append(display[r][c]);
            }
            System.out.println(sb);
        }
    }
}
