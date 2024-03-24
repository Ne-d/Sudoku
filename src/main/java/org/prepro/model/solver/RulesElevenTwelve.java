package org.prepro.model.solver;

import org.prepro.model.Grid;
import org.prepro.model.RowOrColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.prepro.model.RowOrColumn.RowOrColumnType.Row;

/**
 * Contains static functions to solve a grid using pointing k-tuples and box-reductions.
 */
public class RulesElevenTwelve {

    /**
     * Solves one reduction in the chosen grid.
     *
     * @param g The grid to solve a reduction in.
     * @return True if the grid has changed, otherwise false.
     */
    public static boolean solve(Grid g) {
        for (int k = 2; k <= 3; k++) {
            for (int notes = 1; notes <= g.SIZE; notes++) {
                for (int block = 1; block <= g.SIZE; block++) {
                    for (int nbRowOrColumn = 0; nbRowOrColumn < g.SIZE; nbRowOrColumn++) {

                        // Solve pointing pair on a row
                        RowOrColumn rowOrColumn = new RowOrColumn(Row, nbRowOrColumn);
                        if (solvePointingKTuple(g, k, notes, rowOrColumn, block))
                            return true;

                        // Solve pointing pair on a column
                        rowOrColumn = new RowOrColumn(RowOrColumn.RowOrColumnType.Column, nbRowOrColumn);
                        if (solvePointingKTuple(g, k, notes, rowOrColumn, block))
                            return true;

                        // Solve box reduction on a row
                        rowOrColumn = new RowOrColumn(Row, nbRowOrColumn);
                        if (solveBoxReduction(g, k, notes, rowOrColumn, block))
                            return true;

                        // Solve box reduction on a column
                        rowOrColumn = new RowOrColumn(RowOrColumn.RowOrColumnType.Column, nbRowOrColumn);
                        if (solveBoxReduction(g, k, notes, rowOrColumn, block))
                            return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Solves a pointing k-tuple in the chosen grid.
     *
     * @param g     The grid to solve a pointing k-tuple in.
     * @param k     The amount of members in the k-tuple (2 for a pair, 3 for a triplet, etc.)
     * @param note  The note to look for in the k-tuple.
     * @param rc    The row or column to look in.
     * @param block The block to look in.
     * @return True if the grid has changed, otherwise false.
     */
    public static boolean solvePointingKTuple(Grid g, int k, int note, RowOrColumn rc, int block) {
        Optional<List<int[]>> coordsOptional = findPointingKTuple(g, k, note, rc, block);
        int tempCoordinate = -1;
        String typeRC = "null";

        // If the method to find a pointing k-tuple returned Optional.empty(), there is no pointing k-tuple.
        if (coordsOptional.isEmpty()) {
            return false;
        }

        boolean hasChanged = false;

        if (rc.type == Row) { // If we are solving a pointing k-tuple in a row.
            for (int x = 0; x < g.SIZE; x++) { // For every cell in the row.
                if (!g.isInBlock(x, rc.number, block)) { // If the current cell is not in the block of the pointing k-tuple.
                    if (g.deleteNote(x, rc.number, note)) { // Delete the note, if it exists, then ...
                        hasChanged = true;
                        tempCoordinate = rc.number;
                        typeRC = "row";
                    }
                }
            }
        } else { // If we are solving a pointing k-tuple in a column.
            for (int y = 0; y < g.SIZE; y++) { // For every cell in the column.
                if (!g.isInBlock(rc.number, y, block)) { // If the current cell is not in the block of the pointing k-tuple.
                    if (g.deleteNote(rc.number, y, note)) { // Delete the note, if it exists, then ...
                        hasChanged = true;
                        tempCoordinate = rc.number;
                        typeRC = "column";
                    }
                }
            }
        }

        if (hasChanged) {
            System.out.println();
            System.out.printf("Apply the pointing k-tuple rule with k = %d on %s %d for the note %d", k, typeRC, tempCoordinate, note);
            System.out.println();
        }
        return hasChanged;
    }

    /**
     * Find if there is a pointing k-tuple in the block and give the line or column it is on, if it exists.
     *
     * @param g     The grid to look in.
     * @param k     The amount of members in the k-tuple
     * @param note  The note to look for in k-tuples
     * @param rc    The row or column to look in.
     * @param block The block to search in
     * @return If there is a pointing k-tuple, return whether it is on a line, or a column, and the number of that line or column. Else, return Optional.empty()
     */
    public static Optional<List<int[]>> findPointingKTuple(Grid g, int k, int note, RowOrColumn rc, int block) {
        int nbFound = 0;
        List<int[]> coords = new ArrayList<>();

        // For every cell in the block, stopping if we found more notes than k.
        for (int x = g.blockStartX(block); x < g.blockEndX(block) && nbFound <= k; x++) {
            for (int y = g.blockStartY(block); y < g.blockEndY(block) && nbFound <= k; y++) {
                if (g.isNotePresent(note, x, y)) {
                    nbFound++;
                    coords.add(new int[]{x, y});
                }
            }
        }

        // If there are not exactly k cells found, then we don't have a pointing k-tuple.
        if (coords.size() != k) {
            return Optional.empty();
        }

        // For each coordinate in the list, check if they are on the same line or column
        if (rc.type == Row) {
            for (int[] c : coords) {
                if (c[1] != rc.number) {
                    return Optional.empty();
                }
            }
        } else {
            for (int[] c : coords) {
                if (c[0] != rc.number) {
                    return Optional.empty();
                }
            }
        }

        return Optional.of(coords);
    }

    /**
     * Solve a box reduction.
     *
     * @param g     The grid to solve a box reduction in.
     * @param k     The amount of members in the k-tuple (2 for a pair, 3 for a triplet, etc.).
     * @param note  The note to look for in the tuple.
     * @param rc    The Row or Column to search in.
     * @param block The block to search in.
     * @return True if the grid has changed, false otherwise.
     */
    public static boolean solveBoxReduction(Grid g, int k, int note, RowOrColumn rc, int block) {
        Optional<List<int[]>> coordsOptional = findBoxReduction(g, k, note, rc, block);
        int tempCoordinate = -1;
        String typeRC = "null";

        if (coordsOptional.isEmpty()) {
            return false;
        }

        boolean hasChanged = false;

        for (int x = g.blockStartX(block); x < g.blockEndX(block); x++) {
            for (int y = g.blockStartY(block); y < g.blockEndY(block); y++) {

                if (rc.type == Row && y != rc.number) {
                    if (g.deleteNote(x, y, note)) {
                        hasChanged = true;
                        tempCoordinate = x;
                        typeRC = "row";
                    }
                } else if (x != rc.number) {
                    if (g.deleteNote(x, y, note)) {
                        hasChanged = true;
                        tempCoordinate = y;
                        typeRC = "column";
                    }
                }
            }
        }
        //Print in console where the rule is applied
        if (hasChanged) {
            System.out.println();
            System.out.printf("Apply the Box-Reduction rule with k = %d on %s %d for the note %d", k, typeRC, tempCoordinate, note);
            System.out.println();
        }

        return hasChanged;
    }

    /**
     * Finds a box reduction.
     *
     * @param g     The grid to look in.
     * @param k     The amount of members in the k-tuple (2 for a pair, 3 for a triplet, etc.).
     * @param note  The note to look for.
     * @param lc    The row or column to look in.
     * @param block The block to look in.
     * @return A list containing the coordinates of the cells for the box-reduction.
     */
    public static Optional<List<int[]>> findBoxReduction(Grid g, int k, int note, RowOrColumn lc, int block) {
        int nbFound = 0;
        List<int[]> coords = new ArrayList<>();

        // For every cell in the row or column, stopping if we found more notes than k.
        if (lc.type == Row) { // If we are looking in a row
            for (int x = 0; x < g.SIZE && nbFound <= k; x++) {
                if (g.isNotePresent(note, x, lc.number)) {
                    nbFound++;
                    coords.add(new int[]{x, lc.number});
                }
            }
        } else { // If we are looking in a column
            for (int y = 0; y < g.SIZE && nbFound <= k; y++) {
                if (g.isNotePresent(note, lc.number, y)) {
                    nbFound++;
                    coords.add(new int[]{lc.number, y});
                }
            }
        }

        // If there are not exactly k coordinates found, we can't use box-k reduction
        if (coords.size() != k) {
            return Optional.empty();
        }

        if (lc.type == Row) { // If we are looking in a row
            for (int[] coord : coords) {
                if (!g.isInBlock(coord[0], lc.number, block)) { // If the coordinate is not in the block
                    return Optional.empty();
                }
            }
        } else { // If we are looking in a column
            for (int[] coord : coords) {
                if (!g.isInBlock(lc.number, coord[1], block)) {  // If the coordinate is not in the block
                    return Optional.empty();
                }
            }
        }

        // Return the list of coordinates in the k-tuple
        return Optional.of(coords);
    }
}
