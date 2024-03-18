import org.prepro.model.Grid;

public class TestGrids {
    /**
     * Generates and returns a grid
     */
    public static Grid grid1() {
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
    public static Grid grid2() {
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

    public static Grid ruleTwo() {
        Grid g = new Grid();

        g.addValue(1, 7, 6);
        g.addValue(0, 8, 9);
        g.addValue(1, 8, 1);

        g.addValue(2, 3, 8);
        g.addValue(4, 6, 8);

        return g;
    }

    /**
     * Test the 6th rule with an example
     */
    public static Grid pair() {
        Grid test = new Grid();
        test.addValue(2, 0, 2);
        test.addValue(4, 0, 8);
        test.addValue(5, 0, 5);
        test.addValue(8, 0, 4);

        test.addValue(4, 1, 3);
        test.addValue(7, 1, 6);

        test.addValue(2, 2, 4);
        test.addValue(3, 2, 2);
        test.addValue(4, 2, 1);
        test.addValue(7, 2, 3);


        test.addValue(7, 3, 5);
        test.addValue(8, 3, 2);

        test.addValue(6, 4, 3);
        test.addValue(7, 4, 1);

        test.addValue(0, 5, 9);

        test.addValue(0, 6, 8);
        test.addValue(5, 6, 6);

        test.addValue(0, 7, 2);
        test.addValue(1, 7, 5);
        test.addValue(3, 7, 4);
        test.addValue(8, 7, 8);

        test.addValue(5, 8, 1);
        test.addValue(6, 8, 6);

        return test;
    }

    public static Grid pair2() {
        Grid test = new Grid();

        test.addValue(0, 0, 8);
        test.addValue(1, 0, 4);
        test.addValue(3, 0, 2);
        test.addValue(7, 0, 9);
        test.addValue(8, 0, 1);

        test.addValue(3, 1, 1);
        test.addValue(4, 1, 6);
        test.addValue(5, 1, 9);
        test.addValue(6, 1, 4);
        test.addValue(8, 1, 8);

        test.addValue(0, 2, 1);
        test.addValue(2, 2, 9);
        test.addValue(3, 2, 4);
        test.addValue(7, 2, 5);
        test.addValue(8, 2, 7);

        test.addValue(0, 3, 7);
        test.addValue(1, 3, 1);
        test.addValue(2, 3, 8);
        test.addValue(3, 3, 3);
        test.addValue(4, 3, 2);
        test.addValue(5, 3, 4);
        test.addValue(7, 3, 6);

        test.addValue(2, 4, 2);
        test.addValue(5, 4, 6);

        test.addValue(2, 6, 1);
        test.addValue(3, 6, 6);
        test.addValue(4, 6, 9);
        test.addValue(7, 6, 8);

        test.addValue(1, 7, 9);
        test.addValue(3, 7, 8);
        test.addValue(6, 7, 1);

        test.addValue(0, 8, 5);
        test.addValue(1, 8, 8);
        test.addValue(3, 8, 7);
        test.addValue(4, 8, 4);
        test.addValue(5, 8, 1);

        return test;
    }

    public static Grid triplets() {
        Grid test = new Grid();
        test.addValue(0, 0, 3);
        test.addValue(1, 0, 7);
        test.addValue(7, 0, 9);

        test.addValue(0, 1, 9);
        test.addValue(4, 1, 7);

        test.addValue(3, 2, 4);
        test.addValue(4, 2, 2);
        test.addValue(8, 2, 6);

        test.addValue(2, 3, 1);
        test.addValue(4, 3, 8);
        test.addValue(5, 3, 4);
        test.addValue(6, 3, 2);


        test.addValue(0, 5, 8);
        test.addValue(3, 5, 6);
        test.addValue(7, 5, 5);

        test.addValue(2, 6, 6);
        test.addValue(5, 6, 2);
        test.addValue(7, 6, 1);

        test.addValue(7, 7, 3);
        test.addValue(8, 7, 9);

        test.addValue(1, 8, 5);
        test.addValue(6, 8, 4);

        return test;
    }

    public static Grid hiddenPair() {
        Grid test = new Grid();

        test.addValue(2, 0, 9);
        test.addValue(4, 0, 3);
        test.addValue(5, 0, 2);

        test.addValue(3, 1, 7);

        test.addValue(0, 2, 1);
        test.addValue(1, 2, 6);
        test.addValue(2, 2, 2);

        test.addValue(1, 3, 1);
        test.addValue(4, 3, 2);
        test.addValue(6, 3, 5);
        test.addValue(7, 3, 6);

        test.addValue(3, 4, 9);

        test.addValue(1, 5, 5);
        test.addValue(6, 5, 1);
        test.addValue(8, 5, 7);

        test.addValue(6, 6, 4);
        test.addValue(8, 6, 3);

        test.addValue(1, 7, 2);
        test.addValue(2, 7, 6);
        test.addValue(5, 7, 9);

        test.addValue(2, 8, 5);
        test.addValue(3, 8, 8);
        test.addValue(4, 8, 7);

        return test;
    }

    public static Grid hiddenTriplet() {
        Grid test = new Grid();

        test.addValue(2, 0, 8);
        test.addValue(5, 0, 7);

        test.addValue(1, 1, 4);
        test.addValue(2, 1, 2);
        test.addValue(5, 1, 5);


        test.addValue(2, 2, 3);
        test.addValue(5, 2, 6);
        test.addValue(6, 2, 8);
        test.addValue(8, 2, 1);

        test.addValue(8, 4, 6);

        test.addValue(0, 5, 9);

        test.addValue(1, 6, 8);
        test.addValue(3, 6, 1);
        test.addValue(4, 6, 3);
        test.addValue(6, 6, 4);
        test.addValue(7, 6, 7);

        test.addValue(4, 7, 9);

        test.addValue(1, 8, 1);

        return test;
    }

    public static Grid boxReductionGrid() {
        Grid g = new Grid();

        // Grille : Sudoku Extrême n°74 p.69
        g.addValue(0, 0, 3);
        g.addValue(3, 0, 5);
        g.addValue(5, 0, 8);
        g.addValue(7, 0, 2);

        g.addValue(0, 1, 8);
        g.addValue(2, 1, 2);
        g.addValue(3, 1, 9);
        g.addValue(5, 1, 3);
        g.addValue(7, 1, 7);

        g.addValue(2, 2, 5);
        g.addValue(3, 2, 2);
        g.addValue(5, 2, 4);
        g.addValue(6, 2, 3);
        g.addValue(7, 2, 8);
        g.addValue(8, 2, 1);

        g.addValue(0, 3, 4);
        g.addValue(1, 3, 8);
        g.addValue(2, 3, 3);
        g.addValue(3, 3, 1);
        g.addValue(4, 3, 5);
        g.addValue(5, 3, 9);
        g.addValue(6, 3, 7);
        g.addValue(7, 3, 6);
        g.addValue(8, 3, 2);

        g.addValue(1, 4, 2);
        g.addValue(3, 4, 3);
        g.addValue(4, 4, 8);
        g.addValue(5, 4, 6);
        g.addValue(7, 4, 4);

        g.addValue(0, 5, 5);
        g.addValue(1, 5, 6);
        g.addValue(2, 5, 9);
        g.addValue(6, 5, 8);
        g.addValue(7, 5, 1);
        g.addValue(8, 5, 3);

        g.addValue(1, 6, 3);
        g.addValue(5, 6, 1);
        g.addValue(6, 6, 4);
        g.addValue(7, 6, 5);
        g.addValue(8, 6, 8);

        g.addValue(1, 7, 5);
        g.addValue(2, 7, 4);
        g.addValue(3, 7, 8);
        g.addValue(4, 7, 3);
        g.addValue(6, 7, 1);
        g.addValue(7, 7, 9);
        g.addValue(8, 7, 6);

        g.addValue(2, 8, 8);
        g.addValue(5, 8, 5);
        g.addValue(6, 8, 2);
        g.addValue(7, 8, 3);
        g.addValue(8, 8, 7);

        return g;
    }

    public static Grid pointingPairGrid() {
        Grid g = new Grid();

        g.addValue(0, 0, 2);
        g.addValue(1, 0, 3);
        g.addValue(2, 0, 4);
        g.addValue(6, 0, 6);
        g.addValue(7, 0, 9);
        g.addValue(8, 0, 8);

        g.addValue(0, 1, 9);
        g.addValue(1, 1, 6);
        g.addValue(2, 1, 8);
        g.addValue(3, 1, 2);
        g.addValue(4, 1, 3);
        g.addValue(5, 1, 4);
        g.addValue(8, 1, 1);

        g.addValue(0, 2, 5);
        g.addValue(1, 2, 7);
        g.addValue(2, 2, 1);
        g.addValue(3, 2, 6);
        g.addValue(4, 2, 8);
        g.addValue(5, 2, 9);
        g.addValue(8, 2, 4);

        g.addValue(0, 3, 7);
        g.addValue(1, 3, 1);
        g.addValue(2, 3, 9);
        g.addValue(7, 3, 6);


        g.addValue(6, 4, 9);
        g.addValue(7, 4, 4);

        g.addValue(1, 5, 4);
        g.addValue(2, 5, 2);
        g.addValue(4, 5, 9);
        g.addValue(6, 5, 1);
        g.addValue(7, 5, 8);

        g.addValue(0, 6, 1);
        g.addValue(1, 6, 2);
        g.addValue(2, 6, 6);

        g.addValue(4, 7, 2);
        g.addValue(5, 7, 5);
        g.addValue(6, 7, 4);
        g.addValue(8, 7, 6);

        g.addValue(0, 8, 4);
        g.addValue(4, 8, 6);
        g.addValue(5, 8, 3);
        g.addValue(6, 8, 8);

        return g;
    }

    public static Grid pointingTripletGrid() {
        Grid g = new Grid();

        g.addValue(2, 0, 9);
        g.addValue(4, 0, 7);

        g.addValue(1, 3, 8);
        g.addValue(3, 1, 4);

        g.addValue(2, 2, 3);
        g.addValue(7, 2, 2);
        g.addValue(8, 2, 8);


        g.addValue(0, 3, 1);
        g.addValue(6, 3, 6);
        g.addValue(7, 3, 7);

        g.addValue(1, 4, 2);
        g.addValue(4, 4, 1);
        g.addValue(5, 4, 3);
        g.addValue(7, 4, 4);

        g.addValue(1, 5, 4);
        g.addValue(5, 5, 7);
        g.addValue(6, 5, 8);


        g.addValue(0, 6, 6);
        g.addValue(4, 6, 3);

        g.addValue(1, 7, 1);

        g.addValue(6, 8, 2);
        g.addValue(7, 8, 8);
        g.addValue(8, 8, 4);

        return g;
    }

    public static Grid xWing() {
        Grid g = new Grid();

        g.addValue(2, 0, 3);
        g.addValue(3, 0, 8);
        g.addValue(6, 0, 5);
        g.addValue(7, 0, 1);

        g.addValue(2, 1, 8);
        g.addValue(3, 1, 7);
        g.addValue(6, 1, 9);
        g.addValue(7, 1, 3);


        g.addValue(0, 2, 1);
        g.addValue(3, 2, 3);
        g.addValue(5, 2, 5);
        g.addValue(6, 2, 7);
        g.addValue(7, 2, 2);
        g.addValue(8, 2, 8);

        g.addValue(3, 3, 2);
        g.addValue(6, 3, 8);
        g.addValue(7, 3, 4);
        g.addValue(8, 3, 9);

        g.addValue(0, 4, 8);
        g.addValue(2, 4, 1);
        g.addValue(3, 4, 9);
        g.addValue(5, 4, 6);
        g.addValue(6, 4, 2);
        g.addValue(7, 4, 5);
        g.addValue(8, 4, 7);

        g.addValue(3, 5, 5);
        g.addValue(6, 5, 1);
        g.addValue(7, 5, 6);
        g.addValue(8, 5, 3);

        g.addValue(0, 6, 9);
        g.addValue(1, 6, 6);
        g.addValue(2, 6, 4);
        g.addValue(3, 6, 1);
        g.addValue(4, 6, 2);
        g.addValue(5, 6, 7);
        g.addValue(6, 6, 3);
        g.addValue(7, 6, 8);
        g.addValue(8, 6, 5);

        g.addValue(0, 7, 3);
        g.addValue(1, 7, 8);
        g.addValue(2, 7, 2);
        g.addValue(3, 7, 6);
        g.addValue(4, 7, 5);
        g.addValue(5, 7, 9);
        g.addValue(6, 7, 4);
        g.addValue(7, 7, 7);
        g.addValue(8, 7, 1);

        g.addValue(1, 8, 1);
        g.addValue(3, 8, 4);
        g.addValue(6, 8, 6);
        g.addValue(7, 8, 9);
        g.addValue(8, 8, 2);

        return g;
    }

    public static Grid allRules() {
        Grid g = new Grid();

        g.addValue(1, 0, 5);
        g.addValue(4, 0, 1);
        g.addValue(7, 0, 4);

        g.addValue(0, 1, 2);
        g.addValue(7, 1, 3);

        g.addValue(1, 2, 6);
        g.addValue(5, 2, 9);
        g.addValue(6, 2, 1);
        g.addValue(8, 2, 2);

        g.addValue(0, 3, 7);
        g.addValue(4, 3, 5);
        g.addValue(6, 3, 6);
        g.addValue(8, 3, 1);

        g.addValue(2, 4, 5);
        g.addValue(5, 4, 3);

        g.addValue(6, 5, 4);

        g.addValue(1, 6, 9);
        g.addValue(3, 6, 8);

        g.addValue(0, 7, 6);
        g.addValue(4, 7, 3);
        g.addValue(6, 7, 5);
        g.addValue(8, 7, 7);

        g.addValue(7, 8, 2);

        return g;
    }
}
