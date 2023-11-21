package org.prepro;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid();

        grid.addValue(0, 0, 7); //line 1
        grid.addValue(1, 0, 2);
        grid.addValue(2, 0, 6);
        grid.addValue(4, 0, 1);
        grid.addValue(5, 0, 8);
        grid.addValue(6, 0, 3);
        grid.addValue(7, 0, 4);

        grid.addValue(1, 1, 9); // line 2
        grid.addValue(4, 1, 5);
        grid.addValue(5, 1, 2);

        grid.addValue(0, 2, 5); //line 3
        grid.addValue(2, 2, 4);
        grid.addValue(4, 2, 3);
        grid.addValue(5, 2, 6);
        grid.addValue(6, 2, 9);
        grid.addValue(7, 2, 8);
        grid.addValue(8, 2, 2);

        grid.addValue(0, 3, 6); // line 4
        grid.addValue(3, 3, 3);
        grid.addValue(7, 3, 2);
        grid.addValue(8, 3, 1);

        grid.addValue(1, 4, 7); // line 5
        grid.addValue(5, 4, 4);

        grid.addValue(3, 5, 6); // line 6
        grid.addValue(5, 5, 9);
        grid.addValue(6, 5, 4);
        grid.addValue(7, 5, 5);

        grid.addValue(1, 6, 3); // line 7
        grid.addValue(2, 6, 7);
        grid.addValue(7, 6, 9);
        grid.addValue(8, 6, 4);

        grid.addValue(0, 7, 4); // line 8
        grid.addValue(2, 7, 1);
        grid.addValue(5, 7, 3);

        grid.addValue(3, 8, 2); // line 9
        grid.addValue(6, 8, 1);

        grid.print();
        System.out.println("La grille est " + (grid.isValid() ? "valide." : "invalide."));
        grid.afficheNote(0, 1);
        grid.afficheNote(2, 1);
        grid.afficheNote(1, 2);
        grid.afficheNote(0, 0);
        grid.afficheNote(3, 0);

        for(int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                boolean continueColumn;
                boolean continueRow;
                boolean continueBlock;
                do {
                    continueColumn = grid.simplerule(x, 0, x, 8);
                    continueRow = grid.simplerule(0, y, 8, y);
                    continueBlock = grid.simplerule((x / 3) * 3, (y / 3) * 3, (1 + x / 3) * 3 - 1, (1 + y / 3) * 3 - 1);
                }
                while (continueColumn && continueRow && continueBlock);
            }
        }

        grid.afficheNote(7, 1);
        grid.print();
        System.out.println(grid.isValid() ? "La grille est valide." : "La grille est invalide.");
    }
}
