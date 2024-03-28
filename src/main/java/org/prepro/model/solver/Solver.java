package org.prepro.model.solver;

import org.prepro.model.Grid;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    public static final boolean PRINT_ENABLED = true;
    public static final boolean RULE_ONE_THREE_ON_ADD_VALUE = true;

    /**
     * Solve the grid with all the rules.
     *
     * @param g The grid to solve.
     */
    public static void solve(Grid g) {
        System.out.println("\n================================ Solving the grid. ================================");

        // Try to solve the grid using heuristics.
        while (true) {
            System.out.println("\n================================ New solving step. ================================\n");
            System.out.println("The grid is " + (g.isValid() ? "valid" : "NOT VALID") + ".");
            //g.printWithNotes();

            if (RuleOneThree.solve(g))
                continue;

            if (RuleTwo.solve(g))
                continue;

            if (RulesFiveToTen.solve(g))
                continue;

            if (RulesElevenTwelve.solve(g))
                continue;

            if (!RuleThirteen.solve(g))
                break;
        }

        // If heuristics failed, try backtracking.
        System.out.println("Couldn't solve the grid with heuristics, attempting backtracking.");
        Backtracking.solve(g);
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
