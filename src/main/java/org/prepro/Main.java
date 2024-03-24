package org.prepro;

import org.prepro.model.Grid;
import org.prepro.model.solver.RuleTwo;
import org.prepro.model.solver.RulesFiveToTen;
import org.prepro.model.solver.Solver;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static boolean useRule(Grid grid, Scanner scanner) {
        String command = "";
        String commandRecognized = "complete|elementaire|singleton|paire|triplet|QUIT"; //number of the recognize rule
        String commandRecognized2 = "block|ligne|colonne";
        String argv = "";
        int i = -1;
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

    public static void main(String[] args) {
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

        g.print();
        g.printWithNotes();

        Scanner sc = new Scanner(System.in);
        while (useRule(g, sc)) {
            //What the Hell is going on here
        }
        System.out.println("Done");

        g.printWithNotes();
    }
}
