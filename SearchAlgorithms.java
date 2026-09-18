package pathfinding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Implements four classic pathfinding/search algorithms over a Grid:
 *   - Depth-First Search (DFS)         - not guaranteed shortest
 *   - Breadth-First Search (BFS)       - guaranteed shortest on unweighted grids
 *   - Greedy Best-First Search         - fast, not guaranteed shortest
 *   - A* Search                        - guaranteed shortest
 */

public class SearchAlgorithms {

    
    private static int heuristic(Node a, Node b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    private static List<Node> reconstructPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node current = goalNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    
    // Breadth-First Search
   
    public static SearchResult bfs(Grid grid) {
        long startTime = System.nanoTime();

        Node start = grid.getStart();
        Node goal = grid.getGoal();

        Queue<Node> frontier = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();

        frontier.add(start);
        visited.add(start);
        int nodesExplored = 0;

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            nodesExplored++;

            if (current.equals(goal)) {
                long elapsed = System.nanoTime() - startTime;
                return new SearchResult("Breadth-First Search", true,
                        reconstructPath(current), nodesExplored, elapsed);
            }

            for (Node neighbor : grid.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    neighbor.parent = current;
                    visited.add(neighbor);
                    frontier.add(neighbor);
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;
        return new SearchResult("Breadth-First Search", false, null, nodesExplored, elapsed);
    }

    
    // Depth-First Search
    
    public static SearchResult dfs(Grid grid) {
        long startTime = System.nanoTime();

        Node start = grid.getStart();
        Node goal = grid.getGoal();

        Deque<Node> frontier = new ArrayDeque<>(); 
        Set<Node> visited = new HashSet<>();

        frontier.push(start);
        int nodesExplored = 0;

        while (!frontier.isEmpty()) {
            Node current = frontier.pop();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            nodesExplored++;

            if (current.equals(goal)) {
                long elapsed = System.nanoTime() - startTime;
                return new SearchResult("Depth-First Search", true,
                        reconstructPath(current), nodesExplored, elapsed);
            }

            for (Node neighbor : grid.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    neighbor.parent = current;
                    frontier.push(neighbor);
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;
        return new SearchResult("Depth-First Search", false, null, nodesExplored, elapsed);
    }

    
    // Greedy Best-First Search  (orders purely by heuristic h(n))
    
    public static SearchResult greedyBestFirstSearch(Grid grid) {
        long startTime = System.nanoTime();

        Node start = grid.getStart();
        Node goal = grid.getGoal();

        PriorityQueue<Node> frontier = new PriorityQueue<>((a, b) -> Integer.compare(a.hCost, b.hCost));
        Set<Node> visited = new HashSet<>();

        start.hCost = heuristic(start, goal);
        frontier.add(start);
        int nodesExplored = 0;

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            nodesExplored++;

            if (current.equals(goal)) {
                long elapsed = System.nanoTime() - startTime;
                return new SearchResult("Greedy Best-First Search", true,
                        reconstructPath(current), nodesExplored, elapsed);
            }

            for (Node neighbor : grid.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    neighbor.hCost = heuristic(neighbor, goal);
                    neighbor.parent = current;
                    frontier.add(neighbor);
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;
        return new SearchResult("Greedy Best-First Search", false, null, nodesExplored, elapsed);
    }

    
    // A* Search  (orders by f(n) = g(n) + h(n))
    
    public static SearchResult aStar(Grid grid) {
        long startTime = System.nanoTime();

        Node start = grid.getStart();
        Node goal = grid.getGoal();

        PriorityQueue<Node> frontier = new PriorityQueue<>((a, b) -> Integer.compare(a.getFCost(), b.getFCost()));
        Map<Node, Integer> bestGCost = new HashMap<>();
        Set<Node> closed = new HashSet<>();

        start.gCost = 0;
        start.hCost = heuristic(start, goal);
        frontier.add(start);
        bestGCost.put(start, 0);
        int nodesExplored = 0;

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (closed.contains(current)) {
                continue;
            }
            closed.add(current);
            nodesExplored++;

            if (current.equals(goal)) {
                long elapsed = System.nanoTime() - startTime;
                return new SearchResult("A* Search", true,
                        reconstructPath(current), nodesExplored, elapsed);
            }

            for (Node neighbor : grid.getNeighbors(current)) {
                if (closed.contains(neighbor)) {
                    continue;
                }
                int tentativeG = current.gCost + 1; // uniform cost of 1 per step

                Integer knownG = bestGCost.get(neighbor);
                if (knownG == null || tentativeG < knownG) {
                    neighbor.gCost = tentativeG;
                    neighbor.hCost = heuristic(neighbor, goal);
                    neighbor.parent = current;
                    bestGCost.put(neighbor, tentativeG);
                    frontier.add(neighbor);
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;
        return new SearchResult("A* Search", false, null, nodesExplored, elapsed);
    }
}
