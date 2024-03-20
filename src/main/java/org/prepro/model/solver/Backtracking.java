package org.prepro.model.solver;


import org.prepro.model.Grid;

/**
 * Class implémentant le backtracking
 */
public class Backtracking {

    //TODO : Finaliser l'algo (celui ci est basé sur celui du prof)
    // et vérifier s'il fonctionne
    /**
     * Algo backtracking
     * @param g Grid given
     */
    public static void solve(Grid g) {

        //For every cell in the grid
        for (int y = 0; y < g.SIZE; y++) {
            for (int x = 0; x < g.SIZE; x++){

                //Cell not empty
                if (g.getNbNotes(x, y) > 1){
                    for (int val = 1; val <= 9; val++){
                        //add the value
                        g.addValue(x, y, val);
                        //Appel récursif : est-ce valable en java ???
                        solve(g);

                        //back to an empty cell
                        for (int note = 1; note <= 9; note++){
                            g.addNote(x, y, note);
                        }
                    }
                }
            }
        }
        //Print the final grid
        g.print();
    }
}
