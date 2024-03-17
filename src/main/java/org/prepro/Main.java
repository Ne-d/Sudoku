package org.prepro;

import org.prepro.model.Grid;
import org.prepro.model.GridExemple;
import org.prepro.model.solver.RulesFiveToTen;
import org.prepro.model.solver.RulesOneToThree;
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

            System.out.print("put your command: ");
            if (scanner.hasNext()) {
                command = scanner.next(commandRecognized);
            }

            switch (command) {
                case "complete":
                    Solver.solve(grid);
                    grid.print();
                    break;
                case "elementaire":
                    RulesOneToThree.solve(grid);
                    grid.print();
                    grid.printWithNotes();
                    break;
                case "singleton":
                    System.out.print("put your command among " + commandRecognized2 + " : ");
                    if (scanner.hasNext()) {
                        command = scanner.next(commandRecognized2);
                    }
                    System.out.print("put the number: ");
                    if (scanner.hasNext()) {
                        argv = scanner.next();
                    }
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 1, 0, i, grid.SIZE - 1, i));
                            break;
                        case "colonne":
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 1, i, 0, i, grid.SIZE - 1));
                            break;
                        case "block":
                            int x = (i % grid.SQRTSIZE) * grid.SQRTSIZE;
                            int y = (i / grid.SQRTSIZE) * grid.SQRTSIZE;
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 1, x, y, x + grid.SQRTSIZE - 1, y + grid.SQRTSIZE - 1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "paire":
                    System.out.print("put your command among " + commandRecognized2 + " : ");
                    if (scanner.hasNext()) {
                        command = scanner.next(commandRecognized2);
                    }
                    System.out.print("put the number: ");
                    if (scanner.hasNext()) {
                        argv = scanner.next();
                    }
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 2, 0, i, grid.SIZE - 1, i));
                            break;
                        case "colonne":
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 2, i, 0, i, grid.SIZE - 1));
                            break;
                        case "block":
                            int x = (i % grid.SQRTSIZE) * grid.SQRTSIZE;
                            int y = (i / grid.SQRTSIZE) * grid.SQRTSIZE;
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 2, x, y, x + grid.SQRTSIZE - 1, y + grid.SQRTSIZE - 1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "triplet":
                    System.out.print("put your command among " + commandRecognized2 + " : ");
                    if (scanner.hasNext()) {
                        command = scanner.next(commandRecognized2);
                    }
                    System.out.print("put the number: ");
                    if (scanner.hasNext()) {
                        argv = scanner.next();
                    }
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 3, 0, i, grid.SIZE - 1, i));
                            break;
                        case "colonne":
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 3, i, 0, i, grid.SIZE - 1));
                            break;
                        case "block":
                            int x = (i % grid.SQRTSIZE) * grid.SQRTSIZE;
                            int y = (i / grid.SQRTSIZE) * grid.SQRTSIZE;
                            System.out.println("la grille a été modifié : " + RulesFiveToTen.k_upletsTest(grid, 3, x, y, x + grid.SQRTSIZE - 1, y + grid.SQRTSIZE - 1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "QUIT":
                    return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("list of command : " + commandRecognized);
            if (scanner.hasNext()) scanner.next(); //avoid infinity loop
        } catch (Exception e) {
            System.out.println("An error has occurred : " + e + "\n\t" + e.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {
        Grid g = GridExemple.grid2.getGrid();

        g.print();
        g.printWithNotes();

        Scanner sc = new Scanner( System.in );
        while ( useRule(g,sc) ){}
        System.out.println("done");

        g.printWithNotes();
    }
}
