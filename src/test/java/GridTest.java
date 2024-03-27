import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.prepro.model.Grid;
import org.prepro.model.RowOrColumn;
import org.prepro.model.RowOrColumn.RowOrColumnType;
import org.prepro.model.solver.*;

import java.util.List;
import java.util.Optional;

public class GridTest {

    /**
     * Test the 6th rule with an example
     */
    @Test
    public void testPair() {
        Grid test = TestGrids.pair();

        test.print();
        test.printWithNotes();

        Assertions.assertTrue(RulesFiveToTen.k_upletsTest(test, 2, 3, 0, 5, 2));
        test.printWithNotes();
    }

    @Test
    public void testPair2() {
        Grid test = TestGrids.pair2();

        test.print();
        test.printWithNotes();

        Assertions.assertTrue(RulesFiveToTen.k_upletsTest(test, 2, 0, 1, 8, 1));
    }

    /**
     * Test the 7th rule with an example
     */
    @Test
    public void testTriplet() {
        Grid test = TestGrids.triplets();

        test.print();
        test.printWithNotes();

        Assertions.assertTrue(RulesFiveToTen.k_upletsTest(test, 3, 0, 0, 2, 2));
    }

    /**
     * Test the 9th rule with an example
     */
    @Test
    public void testHiddenPair() {
        Grid test = TestGrids.hiddenPair();

        test.print();
        test.printWithNotes();

        Assertions.assertTrue(RulesFiveToTen.k_upletsTest(test, 2, 0, 3, 2, 5)); //TODO A REVOIR ! -JM
    }

    /**
     * Test the 10th rule with an example
     */
    @Test
    public void testHiddenTriplet() {
        Grid test = TestGrids.hiddenTriplet();

        test.print();
        test.printWithNotes();

        Assertions.assertTrue(RulesFiveToTen.k_upletsTest(test, 2, 0, 0, 2, 2)); //TODO A REVOIR ! -JM
    }

    /**
     * Test for a grid in parameters the 4 first rules of SUDOKU
     */
    @Test
    public void testRuleTwo() {
        Grid grid = TestGrids.ruleTwo();

        grid.print();
        grid.printWithNotes();
        Assertions.assertTrue(grid.isValid());

        System.out.println("Solving.");
        Assertions.assertTrue(RuleTwo.solve(grid));
        System.out.println("Done.");

        Assertions.assertEquals(8, grid.getVal(0, 7));

        grid.print();
        grid.printWithNotes();
        Assertions.assertTrue(grid.isValid());
    }

    //@Test
    public void allRulesSanityCheck() {
        Grid g = TestGrids.grid2();

        g.print();

        System.out.println("Application des règles.");
        Solver.solve(g);

        g.print();

        Assertions.assertTrue(g.isValid());
    }

    @Test
    public void testBox2ReductionColumn() {
        Grid g = TestGrids.boxReductionGrid();
        g.print();

        Optional<List<int[]>> coordsOpt = RulesElevenTwelve.findBoxReduction(g, 2, 7, new RowOrColumn(RowOrColumnType.Column, 1), 1);

        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{1, 0});
        Assertions.assertArrayEquals(coords.get(1), new int[]{1, 2});

        for (int[] c : coords) {
            System.out.println(c[0] + ", " + c[1]);
        }

        RulesElevenTwelve.solveBoxReduction(g, 2, 7, new RowOrColumn(RowOrColumnType.Column, 1), 1);
        Assertions.assertFalse(g.isNotePresent(7, 0, 2));
        Assertions.assertFalse(g.isNotePresent(7, 2, 0));

        g.printWithNotes();
    }

    @Test
    public void testBox2ReductionColumnImpossible() {
        Grid g = TestGrids.boxReductionGrid();
        g.print();

        System.out.println("Trying to do a box-2 reduction on a note that is not applicable (also present outside the block).");

        Optional<List<int[]>> coordsOpt = RulesElevenTwelve.findBoxReduction(g, 2, 1, new RowOrColumn(RowOrColumnType.Column, 1), 1);

        Assertions.assertFalse(coordsOpt.isPresent());
    }

    @Test
    public void testBox3ReductionColumn() {
        Grid g = TestGrids.grid1();
        g.print();
        g.printWithNotes();

        Optional<List<int[]>> coordsOpt = RulesElevenTwelve.findBoxReduction(g, 3, 6, new RowOrColumn(RowOrColumnType.Column, 4), 8);
        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{4, 6});
        Assertions.assertArrayEquals(coords.get(1), new int[]{4, 7});
        Assertions.assertArrayEquals(coords.get(2), new int[]{4, 8});

        RulesElevenTwelve.solveBoxReduction(g, 3, 6, new RowOrColumn(RowOrColumnType.Column, 4), 8);

        Assertions.assertFalse(g.isNotePresent(6, 3, 6));
        Assertions.assertFalse(g.isNotePresent(6, 3, 7));

        for (int[] c : coords) {
            System.out.println(c[0] + ", " + c[1]);
        }
    }

    @Test
    public void testPointingPairRow() {
        Grid g = TestGrids.pointingPairGrid();
        g.print();

        Optional<List<int[]>> coordsOpt = RulesElevenTwelve.findPointingKTuple(g, 2, 5, new RowOrColumn(RowOrColumnType.Row, 4), 4);
        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{1, 4});
        Assertions.assertArrayEquals(coords.get(1), new int[]{2, 4});

        RulesElevenTwelve.solvePointingKTuple(g, 2, 5, new RowOrColumn(RowOrColumnType.Row, 4), 4);
        g.printWithNotes();

        Assertions.assertFalse(g.isNotePresent(5, 3, 4));
        Assertions.assertFalse(g.isNotePresent(5, 4, 4));
        Assertions.assertFalse(g.isNotePresent(5, 8, 4));

        for (int[] c : coords) {
            System.out.println(c[0] + ", " + c[1]);
        }
    }

    @Test
    public void testPointingPairColumn() {
        Grid g = TestGrids.pointingPairGrid();
        g.print();

        Optional<List<int[]>> coordsOpt = RulesElevenTwelve.findPointingKTuple(g, 2, 1, new RowOrColumn(RowOrColumnType.Column, 3), 8);
        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{3, 7});
        Assertions.assertArrayEquals(coords.get(1), new int[]{3, 8});

        RulesElevenTwelve.solvePointingKTuple(g, 2, 1, new RowOrColumn(RowOrColumnType.Column, 3), 8);

        Assertions.assertFalse(g.isNotePresent(1, 3, 0));
        Assertions.assertFalse(g.isNotePresent(1, 3, 4));

        for (int[] c : coords) {
            System.out.println(c[0] + ", " + c[1]);
        }
    }

    @Test
    public void testPointingTripletRow() {
        Grid g = TestGrids.pointingTripletGrid();
        g.print();

        Optional<List<int[]>> coordsOpt = RulesElevenTwelve.findPointingKTuple(g, 3, 1, new RowOrColumn(RowOrColumnType.Row, 6), 9);
        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{6, 6});
        Assertions.assertArrayEquals(coords.get(1), new int[]{7, 6});
        Assertions.assertArrayEquals(coords.get(2), new int[]{8, 6});

        RulesElevenTwelve.solvePointingKTuple(g, 3, 1, new RowOrColumn(RowOrColumnType.Row, 6), 9);

        Assertions.assertFalse(g.isNotePresent(1, 3, 6));
        Assertions.assertFalse(g.isNotePresent(1, 5, 6));

        for (int[] c : coords) {
            System.out.println(c[0] + ", " + c[1]);
        }
    }

    @Test
    public void testXWingRow() {
        Grid g = TestGrids.xWing();
        g.print();
        g.printWithNotes();

        System.out.print("Solving X-Wing ... ");
        Assertions.assertTrue(RuleThirteen.solve(g));
        System.out.println("done");

        Assertions.assertTrue(g.isValid());

        g.printWithNotes();
    }

    //@Test
    public void allRulesTest() {
        Grid g = TestGrids.allRules();

        g.print();
        Solver.solve(g);
        g.print();
        g.printWithNotes();
        Assertions.assertTrue(g.isValid());
    }

    // TODO: Tester que les box-réductions pour k=3 dans un bloc avec seulement 2 notes vont bien fail.
    // TODO: Tester les box-réductions sur les lignes.

    @Test
    public void testBacktracking() {
        Grid g = TestGrids.grid1();

        g.print();
        Assertions.assertTrue(Backtracking.solve(g));
        g.print();
        g.printWithNotes();
        Assertions.assertTrue(g.isValid());
    }
}