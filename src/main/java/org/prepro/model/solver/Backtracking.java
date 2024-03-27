package org.prepro.model.solver;


import org.prepro.model.Grid;
import org.prepro.model.Notes;

import java.util.Optional;

/**
 * Class implémentant le backtracking
 */
public class Backtracking {

    //TODO

    /**
     * Algo backtracking
     *
     * @param g Grid given
     * @return If the grid is solved or not
     */
    public static boolean solve(Grid g) {
        Optional<int[]> optionalCell = g.findEmptyCell();

        if (optionalCell.isEmpty())
            return true;

        int x = optionalCell.get()[0];
        int y = optionalCell.get()[1];

        // For every value.
        for (int val = 1; val <= (g.SIZE); val++) {
            //If val is present in the notes of the cell/
            if (checkColumn(g, x, val) && checkRow(g, y, val) && checkBlock(g, x, y, val)) {
                g.setUniqueNote(x, y, val);

                if (solve(g)) {
                    return true;
                }

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

        System.out.printf("checkRow - Value %d can be placed in row %d.\n", val, row);
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

        System.out.printf("checkColumn - Value %d can be placed in column %d.\n", val, column);
        return true;
    }

    /**
     * Check if value "val" can be placed in the block of the given coordinates.
     *
     * @param g      The grid to check in.
     * @param column A column that contains the target block.
     * @param row    A row that contains the target block.
     * @param val    The value to check for.
     * @return True if the value van be placed in this block, otherwise false.
     */
    public static boolean checkBlock(Grid g, int column, int row, int val) {
        int block = g.findBlock(column, row);

        for (int y = g.blockStartY(block); y < g.blockEndY(block); y++) {
            for (int x = g.blockStartX(block); x < g.blockEndX(block); x++) {
                if (g.getVal(x, y) == val)
                    return false;
            }
        }

        System.out.printf("checkBlock - Value %d can be placed in block %d.\n", val, block);
        return true;
    }
}
