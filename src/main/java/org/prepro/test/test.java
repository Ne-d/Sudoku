package org.prepro.test;

import org.prepro.Grid;

public class test {

    /**
     * Test the 6th rule with an example
     */
    public void testPair(){
        Grid test = new Grid();
        test.addValue(0, 2, 2);
        test.addValue(0, 4, 8);
        test.addValue(0, 5, 5);
        test.addValue(0, 8, 4);

        test.addValue(1, 4, 3);
        test.addValue(1, 7, 6);

        test.addValue(2, 2, 4);
        test.addValue(2, 3, 2);
        test.addValue(2, 4, 1);
        test.addValue(2, 7, 3);


        test.addValue(3, 7, 5);
        test.addValue(3, 8, 2);

        test.addValue(4, 6, 3);
        test.addValue(4, 7, 1);

        test.addValue(5, 0, 9);

        test.addValue(6, 0, 8);
        test.addValue(6, 5, 6);

        test.addValue(7, 0, 2);
        test.addValue(7, 1, 5);
        test.addValue(7, 3, 4);
        test.addValue(7, 8, 8);

        test.addValue(8, 5, 1);
        test.addValue(8, 6, 6);

        test.print();
        System.out.println(test.k_upletsTest(2,0,0,2,2)); //TODO doit etre vrai
    }
    /**
     * Test the 7th rule with an example
     */
    public void testTriplet(){
        Grid test = new Grid();
        test.addValue(0, 0, 3);
        test.addValue(0, 1, 7);
        test.addValue(0, 7, 9);

        test.addValue(1, 0, 9);
        test.addValue(1, 4, 7);

        test.addValue(2, 3, 4);
        test.addValue(2, 4, 2);
        test.addValue(2, 8, 6);

        test.addValue(3, 2, 1);
        test.addValue(3, 4, 8);
        test.addValue(3, 5, 4);
        test.addValue(3, 6, 2);



        test.addValue(5, 0, 8);
        test.addValue(5, 3, 6);
        test.addValue(5, 7, 5);

        test.addValue(6, 2, 6);
        test.addValue(6, 5, 2);
        test.addValue(6, 7, 1);

        test.addValue(7, 7, 3);
        test.addValue(7, 8, 9);

        test.addValue(8, 1, 5);
        test.addValue(8, 6, 4);

        test.print();
        System.out.println(test.k_upletsTest(3,0,0,2,2)); //TODO doit etre vrai
    }

    /**
     * Test the 9th rule with an example
     */
    public void testHiddenPair(){
        Grid test = new Grid();

        test.addValue(0, 2, 9);
        test.addValue(0, 4, 3);
        test.addValue(0, 5, 2);

        test.addValue(1, 3, 7);

        test.addValue(2, 0, 1);
        test.addValue(2, 1, 6);
        test.addValue(2, 2, 2);

        test.addValue(3, 1, 1);
        test.addValue(3, 4, 2);
        test.addValue(3, 6, 5);
        test.addValue(3, 7, 6);

        test.addValue(4, 3, 9);

        test.addValue(5, 1, 5);
        test.addValue(5, 6, 1);
        test.addValue(5, 8, 7);

        test.addValue(6, 6, 4);
        test.addValue(6, 8, 3);

        test.addValue(7, 1, 2);
        test.addValue(7, 2, 6);
        test.addValue(7, 5, 9);

        test.addValue(8, 2, 5);
        test.addValue(8, 3, 8);
        test.addValue(8, 4, 7);

        test.print();
        System.out.println(test.k_upletsTest(2,0,0,2,2)); //TODO A REVOIR ! -JM
    }

    /**
     * Test the 10th rule with an example
     */
    public void testHiddenTriplet(){
        Grid test = new Grid();

        test.addValue(0, 2, 8);
        test.addValue(0, 5, 7);

        test.addValue(1, 1, 4);
        test.addValue(1, 2, 2);
        test.addValue(1, 5, 5);



        test.addValue(3, 2, 3);
        test.addValue(3, 5, 6);
        test.addValue(3, 6, 8);
        test.addValue(3, 8, 1);

        test.addValue(4, 8, 6);

        test.addValue(5, 0, 9);

        test.addValue(6, 1, 8);
        test.addValue(6, 3, 1);
        test.addValue(6, 4, 3);
        test.addValue(6, 6, 4);
        test.addValue(6, 7, 7);

        test.addValue(7, 4, 9);

        test.addValue(8, 1, 1);

        test.print();
        System.out.println(test.k_upletsTest(2,0,0,2,2)); //TODO A REVOIR ! -JM
    }

    /**
     * Generates and returns a grid
     */
    public Grid testGrid() {
        Grid grid1 = new Grid();
        grid1.addValue(0, 0, 7); //line 1
        grid1.addValue(1, 0, 2);
        grid1.addValue(2, 0, 6);
        grid1.addValue(4, 0, 1);
        grid1.addValue(5, 0, 8);
        grid1.addValue(6, 0, 3);
        grid1.addValue(7, 0, 4);

        grid1.addValue(1, 1, 9); // line 2
        grid1.addValue(4, 1, 5);
        grid1.addValue(5, 1, 2);

        grid1.addValue(0, 2, 5); //line 3
        grid1.addValue(2, 2, 4);
        grid1.addValue(4, 2, 3);
        grid1.addValue(5, 2, 6);
        grid1.addValue(6, 2, 9);
        grid1.addValue(7, 2, 8);
        grid1.addValue(8, 2, 2);

        grid1.addValue(0, 3, 6); // line 4
        grid1.addValue(3, 3, 3);
        grid1.addValue(7, 3, 2);
        grid1.addValue(8, 3, 1);

        grid1.addValue(1, 4, 7); // line 5
        grid1.addValue(5, 4, 4);

        grid1.addValue(3, 5, 6); // line 6
        grid1.addValue(5, 5, 9);
        grid1.addValue(6, 5, 4);
        grid1.addValue(7, 5, 5);

        grid1.addValue(1, 6, 3); // line 7
        grid1.addValue(2, 6, 7);
        grid1.addValue(7, 6, 9);
        grid1.addValue(8, 6, 4);

        grid1.addValue(0, 7, 4); // line 8
        grid1.addValue(2, 7, 1);
        grid1.addValue(5, 7, 3);

        grid1.addValue(3, 8, 2); // line 9
        grid1.addValue(6, 8, 1);

        return grid1;
    }

    /**
     * Generates and returns another grid
     */
    public Grid testGrid2() {
        Grid grid2 = new Grid();
        grid2.addValue(0, 0, 5); //line 1
        grid2.addValue(2, 0, 4);
        grid2.addValue(4, 0, 1);

        grid2.addValue(8, 1, 6); // line 2

        grid2.addValue(1, 2, 8);//line 3
        grid2.addValue(3, 2, 9);
        grid2.addValue(6, 2, 7);
        grid2.addValue(7, 2, 2);

        grid2.addValue(7, 3, 3); // line 4

        grid2.addValue(0, 4, 2); // line 5
        grid2.addValue(5, 4, 9);

        grid2.addValue(1, 5, 7); // line 6
        grid2.addValue(3, 5, 2);
        grid2.addValue(6, 5, 5);
        grid2.addValue(7, 5, 8);

        grid2.addValue(2, 6, 8); // line 7
        grid2.addValue(5, 6, 4);
        grid2.addValue(6, 6, 6);
        grid2.addValue(7, 6, 5);

        grid2.addValue(3, 7, 5); // line 8
        grid2.addValue(8, 7, 3);


        grid2.addValue(1, 8, 6); // line 9
        grid2.addValue(8, 8, 9);
        return grid2;
    }

    /**
     * Test for a grid in parameters the 4 first rules of SUDOKU
     */
    public void testingRules1to4(Grid grid) {
        System.out.println();
        grid.print();
        System.out.println("La grille est " + (grid.isValid() ? "valide." : "invalide."));
        grid.rulesOneTwoThreeVerification();
        System.out.println();
        grid.print();
        System.out.println(grid.isValid() ? "La grille est valide." : "La grille est invalide.");
    }
}
