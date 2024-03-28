package org.prepro;

import org.prepro.model.Grid;
import org.prepro.model.GridExample;
import org.prepro.model.solver.RuleTwo;
import org.prepro.model.solver.RulesFiveToTen;
import org.prepro.model.solver.Solver;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The main class of the application, to run in the command line.
 */
public class Main {

    /**
     * Runs an interactive shell to solve a grid using various rules.
     *
     * @param grid    The grid to solve.
     * @param scanner The scanner to use for interaction with the user.
     * @return False if the user quits, otherwise true.
     */
    public static boolean useRule(Grid grid, Scanner scanner) {
        String command = "";
        String commandRecognized = "complete|elementaire|singleton|paire|triplet|QUIT"; //number of the recognize rule
        String commandRecognized2 = "block|ligne|colonne";
        String argv = "";
        int i;
        try {

            System.out.print("Put your command: ");
            if (scanner.hasNext()) {
                command = scanner.next(commandRecognized);
            }

            switch (command) {
                case "complete":
                    Solver.solve(grid);
                    grid.print();
                    grid.printWithNotes();
                    break;
                case "elementaire":
                    RuleTwo.solve(grid);
                    grid.print();
                    grid.printWithNotes();
                    break;
                case "singleton":
                    System.out.print("Put your command among " + commandRecognized2 + " : ");
                    if (scanner.hasNext()) {
                        command = scanner.next(commandRecognized2);
                    }
                    System.out.print("Put the number: ");
                    if (scanner.hasNext()) {
                        argv = scanner.next();
                    }
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 1, 0, i, grid.SIZE - 1, i));
                            break;
                        case "colonne":
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 1, i, 0, i, grid.SIZE - 1));
                            break;
                        case "block":
                            int x = (i % grid.SQRTSIZE) * grid.SQRTSIZE;
                            int y = (i / grid.SQRTSIZE) * grid.SQRTSIZE;
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 1, x, y, x + grid.SQRTSIZE - 1, y + grid.SQRTSIZE - 1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "paire":
                    System.out.print("Put your command among " + commandRecognized2 + " : ");
                    if (scanner.hasNext()) {
                        command = scanner.next(commandRecognized2);
                    }
                    System.out.print("Put the number: ");
                    if (scanner.hasNext()) {
                        argv = scanner.next();
                    }
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 2, 0, i, grid.SIZE - 1, i));
                            break;
                        case "colonne":
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 2, i, 0, i, grid.SIZE - 1));
                            break;
                        case "block":
                            int x = (i % grid.SQRTSIZE) * grid.SQRTSIZE;
                            int y = (i / grid.SQRTSIZE) * grid.SQRTSIZE;
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 2, x, y, x + grid.SQRTSIZE - 1, y + grid.SQRTSIZE - 1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "triplet":
                    System.out.print("Put your command among " + commandRecognized2 + " : ");
                    if (scanner.hasNext()) {
                        command = scanner.next(commandRecognized2);
                    }
                    System.out.print("Put the number: ");
                    if (scanner.hasNext()) {
                        argv = scanner.next();
                    }
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 3, 0, i, grid.SIZE - 1, i));
                            break;
                        case "colonne":
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 3, i, 0, i, grid.SIZE - 1));
                            break;
                        case "block":
                            int x = (i % grid.SQRTSIZE) * grid.SQRTSIZE;
                            int y = (i / grid.SQRTSIZE) * grid.SQRTSIZE;
                            System.out.println("The Grid has been modified : " + RulesFiveToTen.k_upletsTest(grid, 3, x, y, x + grid.SQRTSIZE - 1, y + grid.SQRTSIZE - 1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "QUIT":
                    return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("List of command : " + commandRecognized);
            if (scanner.hasNext()) scanner.next(); //avoid infinity loop
        } catch (Exception e) {
            System.out.println("An error has occurred : " + e + "\n\t" + e.getMessage());
        }
        return true;
    }

    /**
     * The main method of the program's command line mode, runs an interactive shell to solve a grid.
     *
     * @param args The command line arguments given to the program. They do absolutely nothing.
     */
    public static void main(String[] args) {
        Grid g = GridExample.grid3.getGrid();

        g.print();
        g.printWithNotes();

        Scanner sc = new Scanner(System.in);
        while (useRule(g, sc)) {
            //What the Hell is going on here
            // I'm waking up, to ash and dust, I wipe my brow and I sweat my rust. - Ned
        }
        System.out.println("Done");

        g.printWithNotes();
    }
}
