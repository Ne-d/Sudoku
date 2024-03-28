import org.prepro.ExecutionTime;
import org.prepro.model.Grid;
import org.prepro.model.solver.Backtracking;
import org.prepro.model.solver.Solver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to measure the time each solving method takes and generate a report.
 * All values are given in nanoseconds.
 */
public class TestSolvingTime {
    /**
     * Measures the time it takes to solve five different grids with rules and backtracking.
     * Performs one hundred iterations and averages out the results.
     *
     * @param args The command line arguments given to the program. They do literally nothing.
     */
    public static void main(String[] args) {
        // Generate a list of all the grids to solve.
        List<String> filenames = new ArrayList<>();
        filenames.add("EasyGrid1.txt");
        filenames.add("HardGrid1.txt");
        filenames.add("ExpertGrid1.txt");
        filenames.add("MasterGrid1.txt");
        filenames.add("DiabolicalGrid1.txt");

        List<long[]> solvingTimes = new ArrayList<>();

        // Perform the measurements.
        for (Grid g : getGrids(filenames)) {
            solvingTimes.add(calculateSolvingTime(g, 100));
        }

        System.out.println("\nSolving time report:");

        // Print the report.
        for (int i = 0; i < solvingTimes.size(); i++) {
            printSolvingTime(filenames.get(i), solvingTimes.get(i));
        }
    }

    /**
     * Get a list of grids based on the given filenames.
     *
     * @param filenames The name of the grids to put in the list.
     * @return A list of grids based on the given filenames.
     */
    public static List<Grid> getGrids(List<String> filenames) {
        List<Grid> grids = new ArrayList<>();


        for (String file : filenames) {
            Grid grid = Grid.loadFromFile("src/grids/" + file, false);
            grids.add(grid);
        }

        return grids;
    }

    /**
     * Calculates the time it takes to solve a grid. All values are given in nanoseconds.
     *
     * @param grid       The grid to solve.
     * @param iterations The amount of time the grid will be solved to calculate an average.
     * @return An array containing the teh heuristics time (index 0), and the backtracking time (index 1).
     */
    public static long[] calculateSolvingTime(Grid grid, int iterations) {
        return new long[]{
                calculateHeuristicsTime(grid, iterations),
                calculateBacktrackingTime(grid, iterations)
        };
    }

    /**
     * Calculates the time it takes to solve a grid using heuristics (rules).
     *
     * @param grid       The grid to solve.
     * @param iterations The amount of time the grid will be solved to calculate an average.
     * @return The time it takes to solve the grid in nanoseconds.
     */
    public static long calculateHeuristicsTime(Grid grid, int iterations) {
        Grid newGrid = new Grid(grid); // Thanks Java.

        try {
            long heuristicsSum = 0;
            for (int i = 0; i < iterations; i++) {
                long heuristicsTime = ExecutionTime.measureNanosecond(
                        null,
                        Solver.class.getMethod("solve", Grid.class),
                        newGrid
                );

                heuristicsSum += heuristicsTime;
            }

            newGrid.printWithNotes();
            return heuristicsSum / iterations;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return -1;
    }

    /**
     * Calculates the time it takes to solve a grid using backtracking.
     *
     * @param grid       The grid to solve.
     * @param iterations The amount of time the grid will be solved to calculate an average.
     * @return The time it takes to solve the grid in nanoseconds.
     */
    public static long calculateBacktrackingTime(Grid grid, int iterations) {
        try {
            long backtrackingSum = 0;
            for (int i = 0; i < iterations; i++) {
                long backtrackingTime = ExecutionTime.measureNanosecond(
                        null,
                        Backtracking.class.getMethod("solve", Grid.class),
                        new Grid(grid)
                );

                backtrackingSum += backtrackingTime;
            }

            return backtrackingSum / iterations;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Print a report of the time it took to solve a grid.
     *
     * @param filename The name of the grid.
     * @param times    An array containing the heuristics time (index 0) and the backtracking time (index 1).
     */
    public static void printSolvingTime(String filename, long[] times) {
        System.out.printf("Solving %s:\n", filename);
        System.out.printf("Rules + backtracking: %,d ns.\n", times[0]);
        System.out.printf("Backtracking only: %,d ns.\n", times[1]);
        System.out.println();
    }
}
