package org.prepro.model.solver;

import org.prepro.model.Grid;

public class RuleOneThree {
    /**
     * Return if the grid is changed by the rules 1 & 3
     * @param g the grid
     * @return true or false depending on if the grid changed
     */
    public static boolean solve(Grid g) {
        boolean hasChanged = false;

        for (int x = 0; x < g.SIZE; x++) {
            for (int y = 0; y < g.SIZE; y++) {
                hasChanged |= apply(g, x, y);
            }
        }

        return hasChanged;
    }

    /**
     * Return if the rule 1 & 3 were applied or not on a coordinate
     * @param g the grid
     * @param xPos
     * @param yPos
     * @return tur oe false depending on if the (x,y) positions was changed by rules 1 & 3
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

        if (hasChanged)
            System.out.printf("Apply rules one and three on cell %d, %d.\n", xPos, yPos);

        return hasChanged;
    }

    public static boolean solveRow(Grid g, int xPos, int yPos, int val) {
        boolean hasChanged = false;

        // Delete all notes that become invalid in the row.
        for (int x = 0; x < g.SIZE; x++) {
            if (x != xPos) {// All cells except the one we are adding a value to.
                boolean hadSeveralNotes = g.getNbNotes(x, yPos) > 1;
                hasChanged |= g.deleteNote(x, yPos, val);

                // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                if (hadSeveralNotes && g.getNbNotes(x, yPos) == 1)
                    apply(g, x, yPos);
            }
        }

        return hasChanged;
    }

    public static boolean solveColumn(Grid g, int xPos, int yPos, int val) {
        boolean hasChanged = false;

        // Delete all notes that become invalid in the column.
        for (int y = 0; y < g.SIZE; y++) {
            if (y != yPos) { // All cells except the one we are adding a value to.
                boolean hadSeveralNotes = g.getNbNotes(xPos, y) > 1;
                hasChanged |= g.deleteNote(xPos, y, val);

                // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                if (hadSeveralNotes && g.getNbNotes(xPos, y) == 1)
                    apply(g, xPos, y);
            }
        }

        return hasChanged;
    }

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
                    if (hadSeveralNotes && g.getNbNotes(x, y) == 1)
                        apply(g, x, y);
                }
            }
        }

        return hasChanged;
    }
}
