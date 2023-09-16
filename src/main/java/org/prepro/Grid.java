package org.prepro;

public class Grid {
    private Box[][] board;
    private final int XSIZE, YSIZE;     // nombrede case XSIZE: ligne , YSIZE: colonne
    private final int DIFFICULTY;       // nombre de case affecter au début 

// génère une grille avec comme taille 9*9 et 19 case inisializé au départ    
    public Grid() {
        this.board = new Box[9][9];
        this.XSIZE = 9;
        this.YSIZE = 9;
        this.DIFFICULTY = 19;
        for(int x = 0; x < XSIZE; x++) {
            for(int y = 0; y < YSIZE; y++) {
                this.board[x][y] = new Box();
            }
        }
        this.generate_number();
    }
    // ajoute DIFFICULTY nombre sur la grille de départ
    private void generate_number(){
        int pos_x,pos_y;
        boolean taken;
        for(int i = 0; i< this.DIFFICULTY; i++ ){
            do{
            taken = true;
            System.out.print("k");
            pos_x = (int)(Math.random() * (this.XSIZE));
            pos_y = (int)(Math.random() * (this.YSIZE));
            if (this.board[pos_x][pos_y].getVal() == 0){
            this.board[pos_x][pos_y].setVal( (int)(Math.random() * (Math.max(this.XSIZE,this.YSIZE) +1 )) );
            taken = false;
            System.out.print(" " + this.board[pos_x][pos_y].getVal() +"(" + pos_x + "," + pos_y +")");
            }
            }while(valid_val(pos_x, pos_y) && !taken);
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

    public boolean valid_grid(){
        for(int i =0; i< XSIZE; i++){
            for(int j =0; i< YSIZE; i++){
               if(!this.valid_val(j, i)){return false;} 
            }
        }
        return true;
    }

    public boolean valid_val( int line, int column){
        int val = this.board[column][line].getVal();
        for(int i =0; i< 9; i++){
            if(this.board[i][column].getVal() == val && this.board[line][i].getVal() != 0){return false;}
            if(this.board[line][i].getVal() == val && this.board[line][i].getVal() != 0){return false;}
        }
        for(int i =0; i< 3; i++){
            for(int j =0; i< 3; i++){
               if(this.board[i][j].getVal() == val && this.board[line][i].getVal() != 0){return false;} 
            }
        }
        return true;
    }

}
