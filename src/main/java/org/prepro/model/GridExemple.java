package org.prepro.model;

public enum GridExemple {
    empty,
    grid1,
    grid2,
    grid3,
    gridInvalid;
    public Grid getGrid(){
        Grid grid = new Grid();
        switch (this){
            case empty -> {}
            case grid1 -> {
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
            }
            case grid2 -> {
                grid.addValue(2, 0, 3);
                grid.addValue(3, 0, 8);
                grid.addValue(6, 0, 5);
                grid.addValue(7, 0, 1);

                grid.addValue(2, 1, 8);
                grid.addValue(3, 1, 7);
                grid.addValue(6, 1, 9);
                grid.addValue(7, 1, 3);


                grid.addValue(0, 2, 1);
                grid.addValue(3, 2, 3);
                grid.addValue(5, 2, 5);
                grid.addValue(6, 2, 7);
                grid.addValue(7, 2, 2);
                grid.addValue(8, 2, 8);

                grid.addValue(3, 3, 2);
                grid.addValue(6, 3, 8);
                grid.addValue(7, 3, 4);
                grid.addValue(8, 3, 9);

                grid.addValue(0, 4, 8);
                grid.addValue(2, 4, 1);
                grid.addValue(3, 4, 9);
                grid.addValue(5, 4, 6);
                grid.addValue(6, 4, 2);
                grid.addValue(7, 4, 5);
                grid.addValue(8, 4, 7);

                grid.addValue(3, 5, 5);
                grid.addValue(6, 5, 1);
                grid.addValue(7, 5, 6);
                grid.addValue(8, 5, 3);

                grid.addValue(0, 6, 9);
                grid.addValue(1, 6, 6);
                grid.addValue(2, 6, 4);
                grid.addValue(3, 6, 1);
                grid.addValue(4, 6, 2);
                grid.addValue(5, 6, 7);
                grid.addValue(6, 6, 3);
                grid.addValue(7, 6, 8);
                grid.addValue(8, 6, 5);

                grid.addValue(0, 7, 3);
                grid.addValue(1, 7, 8);
                grid.addValue(2, 7, 2);
                grid.addValue(3, 7, 6);
                grid.addValue(4, 7, 5);
                grid.addValue(5, 7, 9);
                grid.addValue(6, 7, 4);
                grid.addValue(7, 7, 7);
                grid.addValue(8, 7, 1);

                grid.addValue(1, 8, 1);
                grid.addValue(3, 8, 4);
                grid.addValue(6, 8, 6);
                grid.addValue(7, 8, 9);
                grid.addValue(8, 8, 2);
            }
            case grid3 -> {
                grid.addValue(0, 0, 5); //line 1
                grid.addValue(2, 0, 4);
                grid.addValue(4, 0, 1);
                grid.addValue(8, 1, 6); // line 2
                grid.addValue(1, 2, 8); // line 3
                grid.addValue(3, 2, 9);
                grid.addValue(6, 2, 7);
                grid.addValue(7, 2, 2);
                grid.addValue(7, 3, 3); // line 4
                grid.addValue(0, 4, 2); // line 5
                grid.addValue(5, 4, 9);
                grid.addValue(1, 5, 7); // line 6
                grid.addValue(3, 5, 2);
                grid.addValue(6, 5, 5);
                grid.addValue(7, 5, 8);
                grid.addValue(2, 6, 8); // line 7
                grid.addValue(5, 6, 4);
                grid.addValue(6, 6, 6);
                grid.addValue(7, 6, 5);
                grid.addValue(3, 7, 5); // line 8
                grid.addValue(8, 7, 3);
                grid.addValue(1, 8, 6); // line 9
                grid.addValue(8, 8, 9);
            }
            case gridInvalid -> {
                grid.addValue(0, 0, 9);
                grid.addValue(1, 0, 7);
                grid.addValue(2, 0, 2);
                grid.addValue(4, 0, 8);
                grid.addValue(5, 0, 5);
                grid.addValue(8, 0, 4);

                grid.addValue(0, 1, 8);
                grid.addValue(1, 1, 6);
                grid.addValue(4, 1, 3);
                grid.addValue(7, 1, 6);

                grid.addValue(2, 2, 4);
                grid.addValue(3, 2, 2);
                grid.addValue(4, 2, 1);
                grid.addValue(7, 2, 3);

                grid.addValue(0, 2, 3);
                grid.addValue(2, 2, 2);

                grid.addValue(7, 3, 5);
                grid.addValue(8, 3, 2);

                grid.addValue(6, 4, 3);
                grid.addValue(7, 4, 1);

                grid.addValue(0, 5, 9);

                grid.addValue(0, 6, 8);
                grid.addValue(5, 6, 6);

                grid.addValue(0, 7, 2);
                grid.addValue(1, 7, 5);
                grid.addValue(3, 7, 4);
                grid.addValue(8, 7, 8);

                grid.addValue(5, 8, 1);
                grid.addValue(6, 8, 6);
            }
        }
        return grid;
    }
}
