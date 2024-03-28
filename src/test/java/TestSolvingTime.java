import org.prepro.ExecutionTime;
import org.prepro.model.Grid;
import org.prepro.model.solver.Backtracking;
import org.prepro.model.solver.Solver;

import java.util.ArrayList;
import java.util.List;

public class TestSolvingTime {
    public static void main(String[] args) {
        List<String> filenames = new ArrayList<>();
        filenames.add("EasyGrid1.txt");
        filenames.add("HardGrid1.txt");
        filenames.add("ExpertGrid1.txt");
        filenames.add("MasterGrid1.txt");
        filenames.add("DiabolicalGrid1.txt");

        List<long[]> solvingTimes = new ArrayList<>();

        for (Grid g : getGrids(filenames)) {
            solvingTimes.add(calculateSolvingTime(g, 100));
        }

        System.out.println("\nSolving time report:");

        for (int i = 0; i < solvingTimes.size(); i++) {
            printSolvingTime(filenames.get(i), solvingTimes.get(i));
        }
    }

    public static List<Grid> getGrids(List<String> filenames) {
        List<Grid> grids = new ArrayList<>();


        for (String file : filenames) {
            Grid grid = Grid.loadFromFile("src/grids/" + file, false);
            grids.add(grid);
        }

        return grids;
    }

    public static long[] calculateSolvingTime(Grid grid, int iterations) {
        return new long[]{
                calculateHeuristicsTime(grid, iterations),
                calculateBacktrackingTime(grid, iterations)
        };
    }

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

    public static void printSolvingTime(String filename, long[] times) {
        System.out.printf("Solving %s:\n", filename);
        System.out.printf("Rules + backtracking: %,d ns.\n", times[0]);
        System.out.printf("Backtracking only: %,d ns.\n", times[1]);
        System.out.println();
    }
}
