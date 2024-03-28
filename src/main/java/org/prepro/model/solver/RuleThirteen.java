package org.prepro.model.solver;

import org.prepro.model.Grid;
import org.prepro.model.RowOrColumn;

import java.util.List;
import java.util.Optional;

public class RuleThirteen {
    /**
     * Try to solve an X-Wing
     *
     * @param g The grid to solve in.
     * @return True if the grid has changed (an X-Wing has been found and solved), otherwise false.
     */
    public static boolean solve(Grid g) {
        boolean hasChanged = false;

        for (int note = 1; note <= g.SIZE && !hasChanged; note++) {
            List<int[]> combinations = Solver.combinations(9, 2);

            // Try to solve an X-Wing in any row.
            for (int[] combination : combinations) {
                RowOrColumn row1 = new RowOrColumn(RowOrColumn.RowOrColumnType.Row, combination[0] - 1);
                RowOrColumn row2 = new RowOrColumn(RowOrColumn.RowOrColumnType.Row, combination[1] - 1);

                Optional<int[][]> coords = checkXWing(g, note, row1, row2);

                if (coords.isPresent()) {
                    hasChanged = solveXWing(g, note, RowOrColumn.RowOrColumnType.Row, coords.get());

                    if (hasChanged) {
                        break;
                    }
                }
            }

            // Try to solve an X-Wing in any column
            for (int combIndex = 0; combIndex < combinations.size() && !hasChanged; combIndex++) {
                RowOrColumn column1 = new RowOrColumn(RowOrColumn.RowOrColumnType.Column, combinations.get(combIndex)[0] - 1);
                RowOrColumn column2 = new RowOrColumn(RowOrColumn.RowOrColumnType.Column, combinations.get(combIndex)[1] - 1);

                Optional<int[][]> coords = checkXWing(g, note, column1, column2);

                if (coords.isPresent()) {
                    hasChanged = solveXWing(g, note, RowOrColumn.RowOrColumnType.Column, coords.get());

                    if (hasChanged) {
                        break;
                    }
                }
            }
        }

        return hasChanged;
    }

    /**
     * Checks if the given rows or columns contain an X-Wing.
     *
     * @param note The note to look for.
     * @param rc1  The first row or column to check.
     * @param rc2  The second row or column to check.
     * @return The coordinates of the X-Wing's candidates (the corners of the X-Wing).
     */

    public static Optional<int[][]> checkXWing(Grid g, int note, RowOrColumn rc1, RowOrColumn rc2) {
        // Find a row or column that could be in an X-Wing.
        Optional<int[][]> first = xWingGetCoordinates(g, note, rc1);

        // If we haven't found a suitable row or column, return early.
        if (first.isEmpty()) {
            return Optional.empty();
        }

        // Find another suitable row or column.
        Optional<int[][]> second = xWingGetCoordinates(g, note, rc2);

        // If we haven't found a second suitable row or column, return early.
        if (second.isEmpty()) {
            return Optional.empty();
        } else {
            // Check that the candidates in both rows or columns are properly aligned for an X-Wing.
            boolean candidatesAligned = xWingAreRowsOrColumnsAligned(rc1.type, first.get(), second.get());
            if (!candidatesAligned) {
                return Optional.empty();
            }

            // Make a list of all the coordinates of the X-Wing to return.
            int[][] coordinates = new int[2][2];
            coordinates[0] = first.get()[0];
            coordinates[1] = second.get()[1];

            return Optional.of(coordinates);
        }
    }

    /**
     * Finds if the given Row or Column could be in an X-Wing (has the candidate note exactly twice).
     *
     * @param note The note to look for
     * @param rc   Whether we are looking in a Row or a Column
     * @return If there are exactly two candidates in the row or column, return their coordinates. Else return Empty.
     */
    public static Optional<int[][]> xWingGetCoordinates(Grid g, int note, RowOrColumn rc) {
        int nbFound = 0;

        int[][] coords = new int[2][2];

        //For every row or column in the grid, stopping if we found more notes than 2
        if (rc.type == RowOrColumn.RowOrColumnType.Row) { // If we are looking in a row
            for (int x = 0; x < g.SIZE; x++) {
                if (g.isNotePresent(note, x, rc.number)) {
                    if (nbFound < 2) {
                        nbFound++;
                        coords[nbFound - 1] = new int[]{x, rc.number};
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } else { // If we are looking in a column
            for (int y = 0; y < g.SIZE; y++) {
                if (g.isNotePresent(note, rc.number, y)) {
                    if (nbFound < 2) {
                        nbFound++;
                        coords[nbFound - 1] = new int[]{rc.number, y};
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }

        // Return the list of coordinates in the k-tuple
        return Optional.of(coords);
    }

    /**
     * Finds if the rows or columns suitable for an X-Wing have their candidates aligned.
     *
     * @param lce    Whether we are looking in rows or columns.
     * @param first  The first row or column's list of coordinates.
     * @param second The second row or column's list of coordinates.
     * @return True if the candidates are aligned, false otherwise.
     */
    public static boolean xWingAreRowsOrColumnsAligned(RowOrColumn.RowOrColumnType lce, int[][] first, int[][] second) {
        if (lce == RowOrColumn.RowOrColumnType.Row) { // If we are looking in a Row
            boolean sameXSoFar = true;
            boolean differentYSoFar = true;
            for (int i = 0; i < 2 && sameXSoFar && differentYSoFar; i++) {
                // Check if the X coordinate of the candidates are aligned.
                sameXSoFar = (first[i][0] == second[i][0]);
                // Check if the Y coordinates of the candidates are different (not on the same row)
                differentYSoFar = (first[i][1] != second[i][1]);
            }

            return sameXSoFar && differentYSoFar;
        } else { // If we are looking in a Column
            boolean sameYSoFar = true;
            boolean differentXSoFar = true;
            for (int i = 0; i < 2 && sameYSoFar && differentXSoFar; i++) {
                // Check if the X coordinate of the candidates are aligned.
                sameYSoFar = (first[i][1] == second[i][1]);
                // Check if the X coordinates of the candidates are different (not on the same row)
                differentXSoFar = (first[i][0] != second[i][0]);
            }

            return sameYSoFar && differentXSoFar;
        }
    }

    /**
     * Solve X-wing
     *
     * @param note The note to look for in the tuple
     * @param lce  Whether we are looking in rows or columns
     * @return Whether the grid has been changed by solving the X-Wing.
     */
    public static boolean solveXWing(Grid g, int note, RowOrColumn.RowOrColumnType lce, int[][] coordinates) {
        boolean hasChanged = false;
        int rc1 = -1;
        int rc2 = -1;
        String typeRC = "null";

        int x1 = coordinates[0][0];
        int x2 = coordinates[1][0];

        int y1 = coordinates[0][1];
        int y2 = coordinates[1][1];

        if (lce == RowOrColumn.RowOrColumnType.Row) { // If we are working in a row
            // Delete all candidates in the columns, except the ones in the X-Wing.
            for (int i = 0; i < g.SIZE; i++) {
                if (i != y1 && i != y2) {
                    // Using the binary OR operator to make hasChanged true if there is any change, but never return to false.
                    hasChanged |= g.deleteNote(x1, i, note);
                    rc1 = x1;
                    hasChanged |= g.deleteNote(x2, i, note);
                    rc2 = x2;
                    typeRC = "columns";
                }
            }
        } else { // If we are working in a column
            // Delete all candidates in the row, except the ones in the X-Wing.
            for (int i = 0; i < g.SIZE; i++) {
                if (i != x1 && i != x2) {
                    hasChanged |= g.deleteNote(i, y1, note);
                    rc1 = y1;
                    hasChanged |= g.deleteNote(i, y2, note);
                    rc2 = y2;
                    typeRC = "rows";
                }
            }
        }

        //Print in console where the rules is applied
        if (Solver.PRINT_ENABLED && hasChanged) {
            System.out.println();
            System.out.printf("Apply Rule 13 on the %s %d and %d for the note %d", typeRC, rc1, rc2, note);
            System.out.println();
        }
        return hasChanged;
    }

}
