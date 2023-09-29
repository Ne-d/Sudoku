package org.prepro;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        grid.addValue(3, 6, 9);
        grid.print();

        //grid.addNote(4, 4, 2);
        grid.deleteNote(4, 4, 2);
        grid.deleteNote(4, 4, 5);
        grid.afficheNote(4,4);

        System.out.println("La grille est " + (grid.isValid() ? "valide." : "invalide."));
        System.out.println("La colonne 5 est " + (grid.isColumnValid(5) ? "valide." : "invalide."));
    }
}
