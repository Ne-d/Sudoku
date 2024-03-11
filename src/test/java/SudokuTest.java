import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.prepro.model.Grid;
import org.prepro.model.RowOrColumn.RowOrColumnEnum;

import org.prepro.model.RowOrColumn;
import org.prepro.model.solver.RulesOneToThree;

import java.util.List;
import java.util.Optional;

public class SudokuTest {

    /**
     * Test the 6th rule with an example
     */
    @Test
    public void testPair() {
        Grid test = TestGrids.pair();

        test.print();
        test.afficheNote(3, 0);
        test.afficheNote(3, 1);
        test.afficheNote(5, 1);
        test.afficheNote(5, 2);

        Assertions.assertTrue(test.k_upletsTest(2, 3, 0, 5, 2));
    }

    @Test
    public void testPair2() {
        Grid test = TestGrids.pair2();

        test.print();
        test.afficheNote(0, 1);
        test.afficheNote(1, 1);
        test.afficheNote(2, 1);
        test.afficheNote(7, 1);

        Assertions.assertTrue(test.k_upletsTest(2, 0, 1, 8, 1));
    }

    /**
     * Test the 7th rule with an example
     */
    @Test
    public void testTriplet() {
        Grid test = TestGrids.triplets();

        test.print();
        Assertions.assertTrue(test.k_upletsTest(3, 0, 0, 2, 2));
    }

    /**
     * Test the 9th rule with an example
     */
    @Test
    public void testHiddenPair() {
        Grid test = TestGrids.hiddenPair();

        test.print();
        System.out.println(test.k_upletsTest(2, 0, 0, 2, 2)); //TODO A REVOIR ! -JM
    }

    /**
     * Test the 10th rule with an example
     */
    @Test
    public void testHiddenTriplet() {
        Grid test = TestGrids.hiddenTriplet();

        test.print();
        System.out.println(test.k_upletsTest(2, 0, 0, 2, 2)); //TODO A REVOIR ! -JM
    }

    /**
     * Test for a grid in parameters the 4 first rules of SUDOKU
     */
    @Test
    public void testingRulesOneToThree() {
        Grid grid = TestGrids.grid1();
        grid.print();
        System.out.println("La grille est " + (grid.isValid() ? "valide." : "invalide."));

        RulesOneToThree.solve(grid);

        grid.print();
        System.out.println(grid.isValid() ? "La grille est valide." : "La grille est invalide.");
    }

    @Test
    public void allRulesSanityCheck() {
        Grid g = TestGrids.grid2();

        g.print();

        System.out.println("Application des règles.");
        g.allRules();

        g.print();

        boolean valid = g.isValid();

        System.out.println("La grille est " + (valid ? "valide" : "invalide") + ".");

        Assertions.assertTrue(valid);
    }

    @Test
    public void testBox2ReductionColumn() {
        Grid g = TestGrids.boxReductionGrid();
        g.print();

        Optional<List<int[]>> coordsOpt = g.findBoxReduction(2, 7, new RowOrColumn(RowOrColumnEnum.Column, 1), 1);

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

        g.solveBoxReduction(2, 7, new RowOrColumn(RowOrColumnEnum.Column, 1), 1);
        Assertions.assertFalse(g.isNotePresent(7, 0, 2));
        Assertions.assertFalse(g.isNotePresent(7, 2, 0));

        g.printWithNotes();

    }

    @Test
    public void testBox2ReductionColumnImpossible() {
        Grid g = TestGrids.boxReductionGrid();
        g.print();

        System.out.println("Trying to do a box-2 reduction on a note that is not applicable (also present outside the block).");

        Optional<List<int[]>> coordsOpt = g.findBoxReduction(2, 1, new RowOrColumn(RowOrColumnEnum.Column, 1), 1);

        Assertions.assertFalse(coordsOpt.isPresent());
    }

    @Test
    public void testBox3ReductionColumn() {
        Grid g = TestGrids.grid1();
        g.print();
        g.printWithNotes();

        Optional<List<int[]>> coordsOpt = g.findBoxReduction(3, 6, new RowOrColumn(RowOrColumnEnum.Column, 4), 8);
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

        g.solveBoxReduction(3, 6, new RowOrColumn(RowOrColumnEnum.Column, 4), 8);

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

        Optional<List<int[]>> coordsOpt = g.findPointingKTuple(2, 5, new RowOrColumn(RowOrColumnEnum.Row, 4), 4);
        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{1, 4});
        Assertions.assertArrayEquals(coords.get(1), new int[]{2, 4});

        g.solvePointingKTuple(2, 5, new RowOrColumn(RowOrColumnEnum.Row, 4), 4);
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

        Optional<List<int[]>> coordsOpt = g.findPointingKTuple(2, 1, new RowOrColumn(RowOrColumnEnum.Column, 3), 8);
        List<int[]> coords;

        if (coordsOpt.isPresent()) {
            coords = coordsOpt.get();
        } else {
            Assertions.fail();
            return;
        }

        Assertions.assertArrayEquals(coords.get(0), new int[]{3, 7});
        Assertions.assertArrayEquals(coords.get(1), new int[]{3, 8});

        g.solvePointingKTuple(2, 1, new RowOrColumn(RowOrColumnEnum.Column, 3), 8);

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

        Optional<List<int[]>> coordsOpt = g.findPointingKTuple(3, 1, new RowOrColumn(RowOrColumnEnum.Row, 6), 9);
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

        g.solvePointingKTuple(3, 1, new RowOrColumn(RowOrColumnEnum.Row, 6), 9);

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
        g.ruleThirteen();
        System.out.println("done");

        g.printWithNotes();
    }

    @Test
    public void allRulesTest() {
        Grid g = TestGrids.allRules();

        g.print();
        g.allRules();
        g.print();
        g.printWithNotes();
        System.out.println("La grille est " + (g.isValid() ? "valide." : "invalide."));
    }

    // TODO: Tester que les box-réductions pour k=3 dans un bloc avec seulement 2 notes vont bien fail.
    // TODO: Tester les box-réductions sur les lignes.
}