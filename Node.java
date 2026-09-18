package pathfinding;

import java.util.Objects;

/**
 * Represents a single cell/node in the grid during a search.
 * Stores the position, the cost so far (g), the heuristic estimate (h),
 * and a back-pointer to the parent node so the final path can be
 * reconstructed once the goal is reached.
 */
public class Node {

    public final int row;
    public final int col;

    public int gCost;      // cost from the start node to this node
    public int hCost;      // heuristic estimate from this node to the goal
    public Node parent;    // used to reconstruct the path

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getFCost() {
        return gCost + hCost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
