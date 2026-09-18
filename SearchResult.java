package pathfinding;

import java.util.Collections;
import java.util.List;


public class SearchResult {

    public final String algorithmName;
    public final boolean pathFound;
    public final List<Node> path;
    public final int nodesExplored;
    public final long timeTakenNanos;

    public SearchResult(String algorithmName, boolean pathFound, List<Node> path,
                         int nodesExplored, long timeTakenNanos) {
        this.algorithmName = algorithmName;
        this.pathFound = pathFound;
        this.path = pathFound ? path : Collections.emptyList();
        this.nodesExplored = nodesExplored;
        this.timeTakenNanos = timeTakenNanos;
    }

    public int getPathLength() {
        // number of steps taken (edges), not number of nodes
        return pathFound ? path.size() - 1 : -1;
    }

    public void printSummary() {
        System.out.println("== " + algorithmName + " ==");
        if (pathFound) {
            System.out.println("Path found! Length (steps): " + getPathLength());
        } else {
            System.out.println("No path found.");
        }
        System.out.println("Nodes explored: " + nodesExplored);
        System.out.printf("Time taken: %.3f ms%n", timeTakenNanos / 1_000_000.0);
        System.out.println();
    }
}
