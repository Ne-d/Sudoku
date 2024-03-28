package org.prepro.model.solver;

import org.prepro.model.Grid;

import java.util.List;

/**
 * A class containing static methods to solve a grid with the rules on k-tuples.
 */
public class RulesFiveToTen {
    /**
     * Solve a k-tuple in the grid (the first one found).
     *
     * @param g The grid to solve.
     * @return True if the grid has changed, otherwise false.
     */
    public static boolean solve(Grid g) {
        boolean hasChanged = false;

        for (int k = 2; k <= 3; k++) {
            for (int x = 0; x < g.SIZE; x++) {
                for (int y = 0; y < g.SIZE; y++) {

                    boolean continueColumn = k_upletsTest(g, k, x, 0, x, g.SIZE - 1);
                    boolean continueRow = k_upletsTest(g, k, 0, y, g.SIZE - 1, y);
                    boolean continueBlock = k_upletsTest(g, k,
                            (x / 3) * 3,
                            (y / 3) * 3,
                            (1 + x / 3) * 3 - 1,
                            (1 + y / 3) * 3 - 1);

                    if (continueRow || continueColumn || continueBlock) {
                        return true;
                    }
                    //System.out.println("x :" + x + " y :"+y);
                }
            }
        }

        return hasChanged;
    }

    /**
     * Check if there are k-tuples in the given rectangle.
     *
     * @param g      The grid to solve in.
     * @param k      The amount of members in the k-tuple (2 for a pair, 3 for a triplet, etc.).
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX   X coordinate of the end of the rectangle.
     * @param endY   Y coordinate of the end of the rectangle.
     * @return True if a k-tuple is found, otherwise false.
     */
    public static boolean k_upletsTest(Grid g, int k, int startX, int startY, int endX, int endY) {

        List<int[]> tuples = Solver.combinations(g.SIZE, k);
        for (int[] tuple : tuples) {
            int nbelt = 0;
            boolean[][] tab = new boolean[k][g.SIZE];
            boolean hidden = true;
            for (int j = 0; j < k; j++) { // Every member of the k-tuple
                int numcase = 0;

                for (int x = startX; x <= endX; x++) { // For every cell in the rectangle
                    for (int y = startY; y <= endY; y++) {
                        tab[j][numcase] = g.isNotePresent(tuple[j], x, y);
                        if (tab[j][numcase]) {
                            nbelt++;
                        }
                        hidden = !tab[j][numcase] || g.getNbNotes(x, y) != k || !hidden;
                        numcase++;
                    }
                }
            }
            nbelt = nbelt / k;


            int nbFound = 0; // Number of notes corresponding to the k-tuple
            int[] pos = new int[k];
            int ajouter = 0;
            int largeur = endY - startY + 1;
            List<int[]> comb = Solver.combinations(k, 2);

            for (int t = 0; t < g.SIZE; t++) { // For each cell in the zone
                for (int[] ints : comb) { // For each combination of columns in tab

                    if (tab[ints[0] - 1][t] && tab[ints[1] - 1][t]) { // Compares the values for each combination
                        if (hidden || g.getNbNotes((t / largeur) + startX, (t % largeur) + startY) == k) {
                            if (!checkIsPresent(pos, t) && ajouter != k) {
                                pos[ajouter] = t;
                                ajouter++;
                            }
                            nbFound++;
                        }
                    }
                }
            }
            //System.out.println("nbFound " + nbFound + " hidden:" + hidden + " nbElt:" + nbElt);
            if (nbFound == k * comb.size() && (!hidden || nbelt == k)) {
                //System.out.println(Integer.valueOf(nbElt).toString() + hidden);
                return k_uplet_delNotes(g, pos, tuple, startX, startY, endX, endY);
            }
        }
        return false;
    }

    /**
     * Checks if the given value is found in the array.
     *
     * @param tab The array to look in.
     * @param val The value to look for.
     * @return True if the value is present in the array, otherwise false.
     */
    public static boolean checkIsPresent(int[] tab, int val) {
        for (int j : tab) {
            if (j == val) {
                return true;
            }
        }

        return false;
    }

    /**
     * Solves the given k-tuple in a grid.
     *
     * @param g        The grid to solve in.
     * @param pK_uplet Coordinates of the k-tuple.
     * @param notes    The note of the k-tuple.
     * @param startX   X coordinate of the beginning of the rectangle.
     * @param startY   Y coordinate of the beginning of the rectangle.
     * @param endX     X coordinate of the end of the rectangle.
     * @param endY     Y coordinate of the end of the rectangle.
     * @return True if the grid has been changed, otherwise false.
     */
    public static boolean k_uplet_delNotes(Grid g, int[] pK_uplet, int[] notes, int startX, int startY, int endX, int endY) {
        boolean gridModif = false;
        int k = pK_uplet.length;
        int largeur = endY - startY + 1;

        int x, y;
        for (int i = 0; i < g.SIZE; i++) {
            x = i / largeur + startX;
            y = i % largeur + startY;

            boolean delete = true;
            for (int value : pK_uplet) {
                if (value == i) {
                    delete = false;
                    if (g.getBoard()[x][y].deleteAllNote(notes)) {
                        gridModif = true;
                    }
                }
            }
            if (delete) {
                for (int j = 0; j < k; j++) {
                    if (g.getBoard()[x][y].deleteNote(notes[j])) {
                        gridModif = true;
                    }
                }
            }
        }
        if (Solver.PRINT_ENABLED && gridModif) {
            /* print the grid modified and the k-tuple use */
            //g.printWithNotes();
            System.out.print("k-tuple rules apply with ");
            for (int i = 0; i < k; i++) {
                System.out.print(notes[i]);
            }
            System.out.println(" (" + startX + ", " + startY + ") (" + endX + ", " + endY + ")");
        }
        return gridModif;
    }
}
