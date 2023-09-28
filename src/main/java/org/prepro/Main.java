package org.prepro;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        grid.print();
        grid.delete_note(4, 4, 2);
        grid.addNote(4, 4, 2);
        grid.afficheNote(4,4);
        System.out.println("La grille est " + (grid.isValid() ? "valide." : "invalide."));
    }
}