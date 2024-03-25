package org.prepro.model.solver;

import org.prepro.model.Grid;

public class RuleOneThree {
    public static void apply(Grid g, int xPos, int yPos) {
        // If the cell has no value (more than one note), do nothing.
        if (g.getNbNotes(xPos, yPos) > 1) {
            return;
        }

        // Else, we know the cell has a value, and we store it here.
        int val = g.getVal(xPos, yPos);

        solveRow(g, xPos, yPos, val);
        solveColumn(g, xPos, yPos, val);
        solveBlock(g, xPos, yPos, val);
    }

    public static void solveRow(Grid g, int xPos, int yPos, int val) {
        // Delete all notes that become invalid in the row.
        for (int x = 0; x < g.SIZE; x++) {
            if (x != xPos) {// All cells except the one we are adding a value to.
                boolean hadSeveralNotes = g.getNbNotes(x, yPos) > 1;
                g.deleteNote(x, yPos, val);

                // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                if (hadSeveralNotes && g.getNbNotes(x, yPos) == 1)
                    apply(g, x, yPos);
            }
        }
    }

    public static void solveColumn(Grid g, int xPos, int yPos, int val) {
        // Delete all notes that become invalid in the column.
        for (int y = 0; y < g.SIZE; y++) {
            if (y != yPos) { // All cells except the one we are adding a value to.
                boolean hadSeveralNotes = g.getNbNotes(xPos, y) > 1;
                g.deleteNote(xPos, y, val);

                // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                if (hadSeveralNotes && g.getNbNotes(xPos, y) == 1)
                    apply(g, xPos, y);
            }
        }
    }

    public static void solveBlock(Grid g, int xPos, int yPos, int val) {
        int block = g.findBlock(xPos, yPos);
        int blockStartX = g.blockStartX(block);
        int blockEndX = g.blockEndX(block);
        int blockStartY = g.blockStartY(block);
        int blockEndY = g.blockEndY(block);

        for (int x = blockStartX; x < blockEndX; x++) {
            for (int y = blockStartY; y < blockEndY; y++) {
                if (x != xPos && y != yPos) { // All cells except the one wa are adding a value to.
                    boolean hadSeveralNotes = g.getNbNotes(x, y) > 1;
                    g.deleteNote(x, y, val);

                    // If the current cell had several notes, but now has only one, we must apply the rule to it as well.
                    if (hadSeveralNotes && g.getNbNotes(x, y) == 1)
                        apply(g, x, y);
                }
            }
        }
    }
}
