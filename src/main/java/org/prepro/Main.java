package org.prepro;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        grid.addValue(0, 0, 5);
        grid.addValue(1, 0, 3);
        grid.addValue(0, 1, 6);
        grid.addValue(5, 6, 9);

        grid.print();
        System.out.println("La grille est " + (grid.isValid() ? "valide." : "invalide."));
    }
}