package org.prepro;

public class Grid {
    private Box[][] board;
    private final int XSIZE, YSIZE;

    public Grid() {
        board = new Box[9][9];
        XSIZE = 9;
        YSIZE = 9;
        for(int x = 0; x < XSIZE; x++) {
            for(int y = 0; y < YSIZE; y++) {
                board[x][y] = new Box();
            }
        }
    }

    public void print() {
        for(int x = 0; x < XSIZE; x++) {
            if(x % 3 == 0 && x != 0) {
                System.out.println("---------------------");
            }
            for(int y = 0; y < YSIZE; y++) {
                if(y % 3 == 0 && y != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[x][y].getVal() == 0 ? "0 " : board[x][y].getVal() + " ");
            }
            System.out.print("\n");
        }
    }
}
