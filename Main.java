package pathfinding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final String[] DEFAULT_FIELD = {
            "S..........",
            ".####.####.",
            ".#....#...#",
            ".#.####.#.#",
            ".#......#.#",
            ".######.#.#",
            ".......#...",
            "#####.#.###",
            "....#......",
            ".##.####.#.",
            ".#........#",
            ".########.#",
            "..........G"
    };

    public static void main(String[] args) {

        String[] field = loadField(args);

        Grid grid;
        try {
            grid = new Grid(field);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid maze: " + e.getMessage());
            System.out.println("Falling back to the built-in default maze.");
            grid = new Grid(DEFAULT_FIELD);
        }

        System.out.println("Field (S = start, G = goal, # = wall):");
        grid.printWithPath(null);
        System.out.println();

        SearchResult dfsResult = SearchAlgorithms.dfs(grid);
        SearchResult bfsResult = SearchAlgorithms.bfs(grid);
        SearchResult greedyResult = SearchAlgorithms.greedyBestFirstSearch(grid);
        SearchResult aStarResult = SearchAlgorithms.aStar(grid);

        printResult(grid, dfsResult);
        printResult(grid, bfsResult);
        printResult(grid, greedyResult);
        printResult(grid, aStarResult);

        printComparisonTable(dfsResult, bfsResult, greedyResult, aStarResult);
    }

    
    private static String[] loadField(String[] args) {
        if (args.length > 0) {
            return readFieldFromFile(args[0]);
        }
        return readFieldFromConsole();
    }

    private static String[] readFieldFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            lines.removeIf(String::isBlank);
            if (lines.isEmpty()) {
                System.out.println("File was empty. Falling back to the built-in default maze.");
                return DEFAULT_FIELD;
            }
            System.out.println("Loaded maze from file: " + filePath);
            return lines.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Could not read file '" + filePath + "': " + e.getMessage());
            System.out.println("Falling back to the built-in default maze.");
            return DEFAULT_FIELD;
        }
    }

    /**
     * Prompts the user to type the maze row by row in the console.
     * Enter a blank line to finish. Pressing Enter immediately (no rows
     * typed at all) skips input and uses the built-in default maze.
     */
    private static String[] readFieldFromConsole() {
        System.out.println("Enter your maze row by row (S = start, G = goal, # = wall, . = open).");
        System.out.println("Press Enter on a blank line when done, or leave it blank right away to use the default maze:");

        Scanner scanner = new Scanner(System.in);
        List<String> rows = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            rows.add(line);
        }

        if (rows.isEmpty()) {
            System.out.println("No maze entered. Using the built-in default maze.");
            return DEFAULT_FIELD;
        }
        return rows.toArray(new String[0]);
    }

    private static void printResult(Grid grid, SearchResult result) {
        result.printSummary();
        grid.printWithPath(result.path);
        System.out.println();
    }

    private static void printComparisonTable(SearchResult... results) {
        System.out.println("==================== Comparison ====================");
        System.out.printf("%-25s %-12s %-16s %-12s%n", "Algorithm", "Path Length", "Nodes Explored", "Time (ms)");
        for (SearchResult r : results) {
            String pathLen = r.pathFound ? String.valueOf(r.getPathLength()) : "N/A";
            System.out.printf("%-25s %-12s %-16d %-12.3f%n",
                    r.algorithmName, pathLen, r.nodesExplored, r.timeTakenNanos / 1_000_000.0);
        }
        System.out.println("=====================================================");
    }
}
