package org.prepro;

public class Grid {
    private Box[][] board;
    private final int XSIZE; // Number of columns
    private final int YSIZE; // Number of rows
    private final int DIFFICULTY; // Number of boxes to be given a value when generating a grid.

    /**
     * Generates a new grid with dimensions of 9 by 9, initializes all boxes and fills 19 of them.
     */
    public Grid() {
        this.board = new Box[9][9];
        this.XSIZE = 9;
        this.YSIZE = 9;
        this.DIFFICULTY = 17;

        // Initialize all boxes.
        for(int x = 0; x < XSIZE; x++) {
            for(int y = 0; y < YSIZE; y++) {
                this.board[x][y] = new Box();
            }
        }
        this.generate_number();
    }
    /**
     * @return Returns the value of the box at the given coordinates
     */
    public int getVal(int xPos, int yPos) {
        return this.board[xPos][yPos].getVal();
    }


    /**
     * @return Returns true if the box at the given coordinates is empty, false if it has a value.
     */
    public boolean isBoxEmpty(int xPos, int yPos) {
        return (this.board[xPos][yPos].getVal() == 0);
    }


    /**
     * @val Adds a value to the box of given coordinates, only if the box had no value.
     * @return Returns true if the box has been modified (had no value before).
     *  Returns false if the box has not been modified (already had a value).
     */
    public boolean addValue(int xPos, int yPos, int val) {
        Box box = this.board[xPos][yPos];
        if(box.getVal() == 0) {
            box.setVal(val);
            return true;
        }
        return false;
    }
    
    // Sets the value val to the box of given coordinates.
    public void replaceValue(int xPos, int yPos, int val) {
        this.board[xPos][yPos].setVal(val);
    }

    // Removes the value of the box at the given coordinates.
    // Returns true if the box has been modified (had a value before).
    // Returns false if the box has not been modified (already had no value).
    public boolean removeValue(int xPos, int yPos) {
        if(!isBoxEmpty(xPos, yPos)) {
            this.board[xPos][yPos].setVal(0);
            return true;
        }
        else {
            return false;
        }
    }

    // Returns true if a row doesn't ever have the same number more than once.
    public boolean isRowValid(int row) {
        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the row, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[9];

        for (int i = 0; i < this.XSIZE; i++) { // For each box in the row
            if(!isBoxEmpty(i, row)) {
                if(presentNumbers[getVal(i, row) - 1]) { // If the value has already been found, return false.
                    return false;
                }
                else { // Else, add it to the array.
                    presentNumbers[getVal(i, row) - 1] = true;
                }
            }
        }
        return true; // If we went through the entire row without finding duplicates, the row is valid.
    }

    // Returns true if a column doesn't ever have the same number more than once.
    public boolean isColumnValid(int column) {
        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the column, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[9];

        for (int i = 0; i < this.YSIZE; i++) { // For each box in the column.
            if(!isBoxEmpty(column, i)) {
                if(presentNumbers[getVal(column, i) - 1]) { // If the value has already been found, return false.
                    return false;
                }
                else { // Else, add it to the array.
                    presentNumbers[getVal(column, i) - 1] = true;
                }
            }
        }
        return true; // If we went through the entire column without finding duplicates, the column is valid.
    }

    public boolean isBlockValid(int xBlock, int yBlock) {
        int xStart = 3 * xBlock;
        int yStart = 3 * yBlock;

        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the column, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[9];

        for (int x = xStart; x < xStart + 3; x++) {
            for (int y = yStart; y < yStart + 3; y++) { // For each box in the block
                if (!isBoxEmpty(x, y)) {
                    if (presentNumbers[getVal(x, y) - 1]) { // If the value has already been found, return false.
                        return false;
                    } else { // Else, add it to the array.
                        presentNumbers[getVal(x, y) - 1] = true;
                    }
                }
            }
        }
        return true; // If we went through the entire block without finding duplicates, the block is valid.
    }

    public boolean isValid() {
        boolean validSoFar = true;

        for (int i = 0; i < YSIZE && validSoFar; i++) { // Check the validity of every row.
            validSoFar = isRowValid(i);
        }

        for (int i = 0; i < XSIZE && validSoFar; i++) {
            validSoFar = isColumnValid(i);
        }

        for (int x = 0; x < 3 && validSoFar; x++) {
            for (int y = 0; y < 3 && validSoFar; y++) {
                validSoFar = isBlockValid(x, y);
            }
        }

        return validSoFar;
    }
 
    /**
     * Fills boxes with a value. The number of boxes filled is equal to DIFFICULTY.
     */
    private void generate_number(){
        int pos_x, pos_y;
        boolean set_val;

        for(int i = 0; i < this.DIFFICULTY; i++) {
            do { // While a correct value has not yet been found
                set_val = false;
                System.out.print("k");
                // Find random coordinates
                pos_x = (int)(Math.random() * (this.XSIZE));
                pos_y = (int)(Math.random() * (this.YSIZE));
                //Find random value
                int val = (int)(Math.random() * (Math.max(this.XSIZE,this.YSIZE) + 1 ));
                
                if(valid_val(pos_x, pos_y)) {set_val = this.addValue(pos_x,pos_y,val);}
                              
            }while(!valid_val(pos_x, pos_y) && !set_val);
        System.out.println(this.board[pos_x][pos_y].getVal() +"(" + pos_x + "," + pos_y +") ");
        }
    }

    // Prints out a graphical representation of the grid to standard output.
    public void print() {
        for(int x = 0; x < XSIZE; x++) {
            if(x % 3 == 0) {
                System.out.println("-------------------------");
            }
            for(int y = 0; y < YSIZE; y++) {
                if(y % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[y][x].getVal() == 0 ? "  " : board[y][x].getVal() + " ");
            }
            System.out.print("| ");
            System.out.print("\n");
        }
        System.out.println("-------------------------");
    }

    public boolean valid_grid(){
        for(int i =0; i< XSIZE; i++){
            for(int j =0; i< YSIZE; i++){
               if(!this.valid_val(j, i)){return false;} 
            }
        }
        return true;
    }

    public boolean valid_val(int line, int column){
        int val = this.board[column][line].getVal();
        for(int i =0; i< 9; i++){
            if(this.board[i][column].getVal() == val && this.board[line][i].getVal() != 0) { return false; }
            if(this.board[line][i].getVal() == val && this.board[line][i].getVal() != 0) { return false; }
        }
        for(int i =0; i< 3; i++){
            for(int j =0; i< 3; i++){
               if(this.board[i][j].getVal() == val && this.board[line][i].getVal() != 0) { return false; }
            }
        }
        return true;
    }

}
