package org.prepro.model.solver;


import org.prepro.model.Grid;
import org.prepro.model.Notes;

import java.util.Optional;

/**
 * Implementation of a backtracking algorithm to solve a grid of Sudoku.
 */
public class Backtracking {
    /**
     * Solve the grid using a backtracking algorithm.
     * Slower than using the heuristic-based approach, but works on every grid.
     *
     * @param g The grid to solve.
     * @return True if the algorithm succeeded in solving the grid, otherwise false (the grid does not have a solution).
     */
    public static boolean solve(Grid g) {
        // Find a cell in the grid that doesn't have a value.
        Optional<int[]> emptyCell = g.findEmptyCell();

        // If no empty cell has been found, the grid is solved.
        if (emptyCell.isEmpty())
            return true;

        int x = emptyCell.get()[0];
        int y = emptyCell.get()[1];

        // For every value.
        for (int val = 1; val <= (g.SIZE); val++) {
            // If it is possible to place this value in the current cell
            if (checkColumn(g, x, val) && checkRow(g, y, val) && checkBlock(g, x, y, val)) {
                // Place the value in the cell (not affecting the notes as they are irrelevant once we get to backtracking).
                g.setUniqueNote(x, y, val);

                // Solve the grid using recursion magic.
                if (solve(g)) {
                    return true;
                }

                // If we couldn't solve the grid in a deeper recursive call,
                // the value we placed in the current cell must be incorrect, therefore, we remove it.
                // As previously stated, the contents of the notes is irrelevant, only that there is more than one.
                g.getBoard()[x][y].setNotes(new Notes(g.SIZE));
            }
        } // We tried every value in the current cell.

        return false; // No values were possible, the grid cannot be completed.
    }

    /**
     * Checks if value "val" can be placed in row "row" of grid g.
     *
     * @param g   The grid to check in.
     * @param row The row to check.
     * @param val The value to check for.
     * @return True if the value can be placed in this row, otherwise false.
     */
    public static boolean checkRow(Grid g, int row, int val) {
        // For every cell in the row.
        for (int i = 0; i < g.SIZE; i++) {
            // If the value is found anywhere in the column, it cannot be added anywhere in the column.
            if (g.getVal(i, row) == val) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if value "val" can be placed in column "column" of grid g.
     *
     * @param g      The grid to check in.
     * @param column The column to check.
     * @param val    The value to check for.
     * @return True if the value can be placed in this column, otherwise false.
     */
    public static boolean checkColumn(Grid g, int column, int val) {
        // For every cell in the column.
        for (int i = 0; i < g.SIZE; i++) {
            // If the value is found anywhere in the column, it cannot be added anywhere in the column.
            if (g.getVal(column, i) == val) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if value "val" can be placed in the block which the given cell belongs to.
     *
     * @param g      The grid to check in.
     * @param column A column that contains the target block.
     * @param row    A row that contains the target block.
     * @param val    The value to check for.
     * @return True if the value van be placed in this block, otherwise false.
     */
    public static boolean checkBlock(Grid g, int column, int row, int val) {
        // Find which block the selected cell belongs to.
        int block = g.findBlock(column, row);

        // For every cell in the selected block.
        for (int y = g.blockStartY(block); y < g.blockEndY(block); y++) {
            for (int x = g.blockStartX(block); x < g.blockEndX(block); x++) {
                // If the value is found anywhere in the block, it cannot be added anywhere in the block.
                if (g.getVal(x, y) == val)
                    return false;
            }
        }

        return true;
    }
}
