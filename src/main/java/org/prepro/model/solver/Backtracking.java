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
        if (optionalCell.isPresent()) {
            int x = optionalCell.get()[0];
            int y = optionalCell.get()[1];

            //If the cell is empty
            if (g.getNbNotes(x, y) > 1) {
                for (int val = 1; val <= (g.SIZE); val++) {

                    Notes tmpNotesCell = new Notes(g.getNotes(x, y));

                    //If val is present in the notes of the cell
                    if (g.isNotePresent(val, x, y)) {
                        g.addValue(x, y, val);

                        if (solve(g)){
                            return true;
                        }

                        g.getBoard()[x][y].setNotes(tmpNotesCell);
                    }
                }
                return false;
            }
        }
        return true;
    }
}
