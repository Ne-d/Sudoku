package org.prepro;

import org.prepro.model.Grid;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static boolean useRule(Grid grid, Scanner scanner){
        String command = "";
        String commandRecognized = "complete|elementaire|singleton|paire|triplet|QUIT"; //number of the recognize rule
        String commandRecognized2 = "block|ligne|colonne";
        String argv = "";
        int i = -1;
        try{
            
            System.out.print( "put your command: " );
            if (scanner.hasNext()) {command = scanner.next(commandRecognized);}           
            
            switch(command) {
                case "complete":
                    grid.allRules();
                    grid.print();
                    break;
                case "elementaire":
                    grid.rulesOneTwoThree();
                    grid.print();
                    grid.printWithNotes();
                    break;
                case "singleton":
                    System.out.print( "put your command among "+ commandRecognized2+" : " );
                    if (scanner.hasNext()) {command = scanner.next(commandRecognized2);}
                    System.out.print("put the number: ");
                    if(scanner.hasNext()){argv = scanner.next();}
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(1, 0, i, grid.SIZE-1, i));
                            break;
                        case "colonne":
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(1, i, 0, i, grid.SIZE-1));
                            break;
                        case "block":
                            int x = (i%grid.SQRTSIZE)*grid.SQRTSIZE;
                            int y = (i/grid.SQRTSIZE)*grid.SQRTSIZE;
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(1, x, y, x + grid.SQRTSIZE -1, y + grid.SQRTSIZE -1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "paire":
                    System.out.print( "put your command among "+ commandRecognized2+" : " );
                    if (scanner.hasNext()) {command = scanner.next(commandRecognized2);}
                    System.out.print("put the number: ");
                    if(scanner.hasNext()){argv = scanner.next();}
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                        System.out.println("la grille a été modifié : "+ grid.k_upletsTest(2, 0, i, grid.SIZE-1, i));
                            break;
                        case "colonne":
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(2, i, 0, i, grid.SIZE-1));
                            break;
                        case "block":
                            int x = (i%grid.SQRTSIZE)*grid.SQRTSIZE;
                            int y = (i/grid.SQRTSIZE)*grid.SQRTSIZE;
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(2, x, y, x + grid.SQRTSIZE -1, y + grid.SQRTSIZE -1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "triplet":
                    System.out.print( "put your command among "+ commandRecognized2+" : " );
                    if (scanner.hasNext()) {command = scanner.next(commandRecognized2);}
                    System.out.print("put the number: ");
                    if(scanner.hasNext()){argv = scanner.next();}
                    i = Integer.parseInt(argv);
                    switch (command) {
                        case "ligne":
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(3, 0, i, grid.SIZE-1, i));
                            break;
                        case "colonne":
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(3, i, 0, i, grid.SIZE-1));
                            break;
                        case "block":
                            int x = (i%grid.SQRTSIZE)*grid.SQRTSIZE;
                            int y = (i/grid.SQRTSIZE)*grid.SQRTSIZE;
                            System.out.println("la grille a été modifié : "+ grid.k_upletsTest(3, x, y, x + grid.SQRTSIZE -1, y + grid.SQRTSIZE -1));
                            break;
                    }
                    //grid.print();
                    grid.printWithNotes();
                    break;
                case "QUIT":
                    return false;
            }
        }catch(NoSuchElementException e){
            System.out.println("list of command : "+ commandRecognized);
            if(scanner.hasNext())scanner.next(); //avoid infinity loop
        }
        catch(Exception e){
            System.out.println("An error was occured : " + e+ "\n\t"+ e.getMessage());
        }
        return true;
    }
    public static void main(String[] args) {
        /*
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

        grid2.print();

        System.out.println(grid2.isValid());
        grid2.allRules();
        grid2.print();
        System.out.println(grid2.isValid());
        */


        /*
        grid1.print();
        System.out.println("La grille est " + (grid1.isValid() ? "valide." : "invalide."));
        grid1.afficheNote(0, 1);
        grid1.afficheNote(2, 1);
        grid1.afficheNote(1, 2);
        grid1.afficheNote(0, 0);
        grid1.afficheNote(3, 0);
        grid1.rulesOneTwoThreeVerification();
        grid1.afficheNote(7, 1);
        System.out.println();
        grid1.print();
        System.out.println(grid1.isValid() ? "La grille est valide." : "La grille est invalide.");
        

        System.out.println();
        grid2.print();
        System.out.println("La grille est " + (grid2.isValid() ? "valide." : "invalide."));
        grid2.rulesOneTwoThreeVerification();
        System.out.println();
        grid2.print();
        System.out.println(grid2.isValid() ? "La grille est valide." : "La grille est invalide.");
        */

        /*
        //test k_uplet
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

        test.print();

        Scanner scanner = new Scanner(System.in);
        while (useRule(test,scanner)) {}
        scanner.close();
        */

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
        g.addValue(5, 2, 4);
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

        // Test X-Wing
        g.print();
        g.printWithNotes();

        // TODO: On a oublié nbFound pour vérifier que la ligne ne contient pas d'autres candidats.

        System.out.print("Solving X-Wing ... ");
        g.ruleThirteen();
        System.out.println("done");

        g.printWithNotes();

    }
}
