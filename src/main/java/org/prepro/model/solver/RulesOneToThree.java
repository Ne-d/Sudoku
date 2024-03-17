package org.prepro.model.solver;

import org.prepro.model.Grid;

/**
 * A class containing static methods to solve a grid with the first three rules.
 */
public class RulesOneToThree {

    /**
     * Try to solve one zone with the first three rules.
     *
     * @param g The grid to solve.
     * @return True if the grid has changed, otherwise false.
     */
    public static boolean solve(Grid g) {
        boolean hasChanged = false;

        for (int x = 0; x < g.SIZE; x++) {
            for (int y = 0; y < g.SIZE; y++) {
                boolean continueColumn;
                boolean continueRow;
                boolean continueBlock;
                do {
                    continueColumn = applyRulesOneToThree(g, x, 0, x, 8);
                    continueRow = applyRulesOneToThree(g, 0, y, 8, y);
                    continueBlock = applyRulesOneToThree(g, (x / g.SQRTSIZE) * g.SQRTSIZE,
                            (y / g.SQRTSIZE) * g.SQRTSIZE,
                            (1 + x / g.SQRTSIZE) * g.SQRTSIZE - 1,
                            (1 + y / g.SQRTSIZE) * g.SQRTSIZE - 1);
                    // TODO: On exécute la règle pour les blocs à chaque case donc 9 fois pour chaque bloc,
                    //  faut changer ça je suppose

                    if (continueColumn || continueRow || continueBlock) {
                        hasChanged = true;
                    }
                } while (continueColumn && continueRow && continueBlock);
            }
        }

        return hasChanged;
    }

    /**
     * Completes a zone with the first three rules.
     * WARNING: the rectangle must be a zone of the grid (row, column or square).
     *
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX   X coordinate of the end of the rectangle.
     * @param endY   Y coordinate of the end of the rectangle.
     * @return True if the grid has been modified, false otherwise.
     */
    public static boolean applyRulesOneToThree(Grid g, int startX, int startY, int endX, int endY) {
        int[] nbNotesRec = new int[g.SIZE];
        int nbNotes;
        int j;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                nbNotes = g.getNbNotes(x, y);
                j = 0;
                for (int i = 1; i <= 9 && j != nbNotes; i++) {
                    if (g.isNotePresent(i, x, y)) {
                        nbNotesRec[i - 1]++;
                        j++;
                        if (nbNotes == 1) {
                            g.addValue(x, y, i);
                            System.out.println("Apply Rules OneTwoThree on cell(" + x + ", " + y + ") with note " + i);
                            return true;
                        }
                    }
                }
            }
        }

        return g.isNotePresentOnce(startX, startY, endX, endY, nbNotesRec);
    }
}
