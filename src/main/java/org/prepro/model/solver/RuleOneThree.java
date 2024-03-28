package org.prepro.model.solver;

import org.prepro.model.Grid;

/**
 * Solves a grid using rules one and three.
 */
public class RuleOneThree {
    /**
     * Enable this at compile-time to solve rules one and three recursively.
     */
    public static final boolean SOLVE_RECURSIVELY = true;

    /**
     * Solve a grid using rules one and three.
     *
     * @param g The grid to solve.
     * @return True if the grid changed, otherwise false.
     */
    public static boolean solve(Grid g) {
        for (int x = 0; x < g.SIZE; x++) {
            for (int y = 0; y < g.SIZE; y++) {
                if (apply(g, x, y))
                    return true;
            }
        }

        return false;
    }

    /**
     * Apply rules one and three on a cell of the grid.
     *
     * @param g    The grid to apply the rule in.
     * @param xPos The x coordinate of the cell to apply the rule to.
     * @param yPos The x coordinate of the cell to apply the rule to.
     * @return True if the grid has changed, otherwise false.
     */
    public static boolean apply(Grid g, int xPos, int yPos) {
        boolean hasChanged = false;

        // If the cell has no value (more than one note), do nothing.
        if (g.getNbNotes(xPos, yPos) > 1) {
            return false;
        }

        // Else, we know the cell has a value, and we store it here.
        int val = g.getVal(xPos, yPos);

        hasChanged |= solveRow(g, xPos, yPos, val);
        hasChanged |= solveColumn(g, xPos, yPos, val);
        hasChanged |= solveBlock(g, xPos, yPos, val);

        if (Solver.PRINT_ENABLED && hasChanged)
            System.out.printf("Apply rules one and three on cell %d, %d.\n", xPos, yPos);

        return hasChanged;
    }

    /**
     * Removes all notes that should be removed according to rules one and three in the row of the given cell.
     * This should be called upon adding a value to the given cell.
     *
     * @param g    The grid to solve in.
     * @param xPos The x coordinate of the cell.
     * @param yPos The y coordinate of the cell.
     * @param val  The value that was added to the given cell.
     * @return True if the grid has changed, otherwise false.
     */
    // FIXME: Having the value as an argument seems redundant.
    private static boolean solveRow(Grid g, int xPos, int yPos, int val) {
        boolean hasChanged = false;

        // Delete all notes that become invalid in the row.
        for (int x = 0; x < g.SIZE; x++) {
            if (x != xPos) {// All cells except the one we are adding a value to.
                boolean hadSeveralNotes = g.getNbNotes(x, yPos) > 1;
                hasChanged |= g.deleteNote(x, yPos, val);

                // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                if (SOLVE_RECURSIVELY && hadSeveralNotes && g.getNbNotes(x, yPos) == 1)
                    apply(g, x, yPos);
            }
        }

        return hasChanged;
    }

    /**
     * Removes all notes that should be removed according to rules one and three in the column of the given cell.
     * This should be called upon adding a value to the given cell.
     *
     * @param g    The grid to solve in.
     * @param xPos The x coordinate of the cell.
     * @param yPos The y coordinate of the cell.
     * @param val  The value that was added to the given cell.
     * @return True if the grid has changed, otherwise false.
     */
    // FIXME: Having the value as an argument seems redundant (bis).
    public static boolean solveColumn(Grid g, int xPos, int yPos, int val) {
        boolean hasChanged = false;

        // Delete all notes that become invalid in the column.
        for (int y = 0; y < g.SIZE; y++) {
            if (y != yPos) { // All cells except the one we are adding a value to.
                boolean hadSeveralNotes = g.getNbNotes(xPos, y) > 1;
                hasChanged |= g.deleteNote(xPos, y, val);

                // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                if (SOLVE_RECURSIVELY && hadSeveralNotes && g.getNbNotes(xPos, y) == 1)
                    apply(g, xPos, y);
            }
        }

        return hasChanged;
    }

    /**
     * Removes all notes that should be removed according to rules one and three in the block of the given cell.
     * This should be called upon adding a value to the given cell.
     *
     * @param g    The grid to solve in.
     * @param xPos The x coordinate of the cell.
     * @param yPos The y coordinate of the cell.
     * @param val  The value that was added to the given cell.
     * @return True if the grid has changed, otherwise false.
     */
    // FIXME: Having the value as an argument seems redundant (ter).
    public static boolean solveBlock(Grid g, int xPos, int yPos, int val) {
        boolean hasChanged = false;

        int block = g.findBlock(xPos, yPos);
        int blockStartX = g.blockStartX(block);
        int blockEndX = g.blockEndX(block);
        int blockStartY = g.blockStartY(block);
        int blockEndY = g.blockEndY(block);

        for (int x = blockStartX; x < blockEndX; x++) {
            for (int y = blockStartY; y < blockEndY; y++) {
                if (x != xPos && y != yPos) { // All cells except the one wa are adding a value to.
                    boolean hadSeveralNotes = g.getNbNotes(x, y) > 1;
                    hasChanged |= g.deleteNote(x, y, val);

                    // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                    if (SOLVE_RECURSIVELY && hadSeveralNotes && g.getNbNotes(x, y) == 1)
                        apply(g, x, y);
                }
            }
        }

        return hasChanged;
    }
}
