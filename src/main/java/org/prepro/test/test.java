package org.prepro.test;

import org.prepro.Grid;

public class test {
    public void testPair(){
        Grid test = new Grid();
        test.addValue(0, 0, 9);
        test.addValue(1, 0, 7);

        test.addValue(0, 1, 8);
        test.addValue(1, 1, 6);
        test.addValue(2, 1, 4);

        test.addValue(0, 2, 3);
        test.addValue(2, 2, 2);

        test.print();
        System.out.println(test.k_upletsTest(2,0,0,2,2)); //doit etre vrai
    }
        public void testtriplet(){
            Grid test = new Grid();
            test.addValue(0, 0, 9);
            test.addValue(1, 0, 7);

            test.addValue(0, 1, 8);
            test.addValue(1, 1, 6);


            test.addValue(0, 2, 3);
            test.addValue(2, 2, 2);

            test.print();
            System.out.println(test.k_upletsTest(3,0,0,2,2)); //doit etre vrai
    }


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
