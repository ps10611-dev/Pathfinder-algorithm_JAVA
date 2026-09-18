JAVA PATHFINDING PROJECT
=========================

A command-line Java program that finds a path across a grid ("field")
using four classic search algorithms: DFS, BFS, Greedy Best-First Search,
and A*. No GUI -- all output is printed to the console.

WHAT IT DOES
------------
Main.java builds a sample grid, runs all four algorithms on it, and
prints:
  - the field with each algorithm's path overlaid as '*'
  - the path length, number of nodes explored, and time taken for each
  - a comparison table summarizing all four side by side

FIELD LEGEND
------------
  S  = Start
  G  = Goal
  #  = Wall (blocked)
  .  = Open cell

FILES
-----
  Node.java             A grid cell: position, costs (g/h), parent pointer
  Grid.java             Parses the field, finds neighbors, prints the grid
  SearchResult.java     Holds a run's path, nodes-explored count, timing
  SearchAlgorithms.java DFS, BFS, Greedy Best-First Search, A* implementations
  Main.java             Demo entry point -- builds the field and runs everything

ALGORITHM NOTES
----------------
  DFS      - Explores as deep as possible before backtracking. Finds
             *a* path, not necessarily the shortest.
  BFS      - Explores level by level. Guarantees the shortest path on
             an unweighted grid.
  Greedy   - Always expands the node closest to the goal (Manhattan
             distance). Fast, but not guaranteed shortest.
  A*       - Combines cost-so-far (g) and estimated cost-to-go (h).
             Guarantees the shortest path while typically exploring
             far fewer nodes than BFS.

All four use 4-directional movement (up/down/left/right).

HOW TO RUN
----------
  javac pathfinding/*.java
  java pathfinding.Main

No external libraries, arguments, or input files required. To try a
different maze, edit the "field" array in Main.java.

REQUIREMENTS
------------
  Java 8 or later (uses only java.util -- no dependencies)
