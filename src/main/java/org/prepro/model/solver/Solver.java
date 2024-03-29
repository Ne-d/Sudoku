package org.prepro.model.solver;

import org.prepro.model.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to solve the grid using all available methods (heuristics and backtracking).
 */
public class Solver {
    /**
     * Set this at compile-time to enable or disable information messages in the console when applying rules.
     */
    public static final boolean PRINT_ENABLED = true;

    /**
     * Set this at compile-time to enable or disable the application of rules one and three when placing a value.
     */
    public static final boolean RULE_ONE_THREE_ON_ADD_VALUE = true;

    /**
     * Solve the grid with all available rules, in the correct order.
     * If this fails to complete the grid, attempt backtracking.
     *
     * @param g The grid to solve.
     */
    public static void solve(Grid g) {
        System.out.println("\n================================ Solving the grid. ================================");

        // Try to solve the grid using heuristics.
        while (true) {
            //System.out.println("\n================================ New solving step. ================================\n");
            //System.out.println("The grid is " + (g.isValid() ? "valid" : "NOT VALID") + ".");

            if (RuleOneThree.solve(g))
                continue;

            if (RuleTwo.solve(g))
                continue;

            if (RulesFiveToTen.solve(g))
                break;

            if (RulesElevenTwelve.solve(g)){
                g.printWithNotes();
                continue;
            }

            if (!RuleThirteen.solve(g))
                break;
        }

        if (g.findEmptyCell().isPresent()) {
            System.out.println("Couldn't solve the grid with heuristics, attempting backtracking.");

            if (Backtracking.solve(g))
                System.out.println("Solved the grid using backtracking.");
            else
                System.out.println("Failed to solve the grid using backtracking: no solution exists.");
        }
    }

    /**
     * Finds all the combinations (k-tuples) of k numbers in a list from 1 to size.
     *
     * @param size          The max value of the numbers.
     * @param len           The amount of numbers to take.
     * @param startPosition The minimum value of the numbers.
     * @param result        The tuple currently being built by the function.
     * @param resultList    The list of tuples to be returned.
     */
    private static void combinations_aux(int size, int len, int startPosition, int[] result, List<int[]> resultList) {
        if (len == 0) {
            resultList.add(result.clone());
            return;
        }
        for (int i = startPosition; i <= size + 1 - len; i++) {
            result[result.length - len] = i;
            combinations_aux(size, len - 1, i + 1, result, resultList);
        }
    }

    /**
     * Finds all the combinations (k-tuples) of k numbers in a list from 1 to size.
     *
     * @param size The max value of the numbers.
     * @param k    The amount of numbers to take.
     * @return A list of tuples with k elements from 1 to size.
     */
    public static List<int[]> combinations(int size, int k) {
        List<int[]> res = new ArrayList<>();

        combinations_aux(size, k, 1, new int[k], res);

        return res;
    }
}
