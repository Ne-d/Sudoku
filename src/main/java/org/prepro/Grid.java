package org.prepro;

public class Grid {
    private Box[][] board;
    private final int SIZE; // Number of columns and line
    private final int SQRTSIZE; //Number for a block
    private final int DIFFICULTY; // Number of boxes to be given a value when generating a grid.

    /**
     * Generates a new grid with dimensions of 9 by 9, initializes all boxes and fills 17 of them.
     */
    public Grid() {
        this.board = new Box[9][9];
        this.SIZE = 9;
        this.SQRTSIZE = ((int)Math.sqrt(SIZE));
        this.DIFFICULTY = 17;

        // Initialize all boxes.
        for(int x = 0; x < SIZE; x++) {
            for(int y = 0; y < SIZE; y++) {
                this.board[x][y] = new Box();
            }
        }
        //addValue(4,4, 4);
        //addValue(8,4,4);
       this.generateNumber();
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
        return (this.getVal(xPos, yPos)== 0);
    }


    /**
     * @param val Adds a value to the box of given coordinates, only if the box had no value.
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
    
    /**
     * Sets the value val to the box of given coordinates.
     */
    public void replaceValue(int xPos, int yPos, int val) {
        this.board[xPos][yPos].setVal(val);
    }

    /**
     * Removes the value of the box at the given coordinates.
     * @return Returns true if the box has been modified (had a value before). Returns false if the box has not been modified (already had no value).
     */
    public boolean removeValue(int xPos, int yPos) {
        if(!isBoxEmpty(xPos, yPos)) {
            this.board[xPos][yPos].setVal(0);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return Returns true if a row doesn't ever have the same number more than once.
     */
    public boolean isRowValid(int row) {
        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the row, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[9];

        for (int i = 0; i < this.SIZE; i++) { // For each box in the row
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

    /**
     * @return Returns true if a column doesn't ever have the same number more than once.
     */
    public boolean isColumnValid(int column) {
        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the column, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[9];

        for (int i = 0; i < this.SIZE; i++) { // For each box in the column.
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

    /**
     * @param xBlock position X du block
     * @param yBlock position Y du block
     * @return retourne si le bloc est valide ou non
     */
    public boolean isBlockValid(int xBlock, int yBlock) {
        int xStart = SQRTSIZE * xBlock;
        int yStart = SQRTSIZE * yBlock;

        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the column, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[9];

        for (int x = xStart; x < xStart + SQRTSIZE; x++) {
            for (int y = yStart; y < yStart + SQRTSIZE; y++) { // For each box in the block
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

        for (int i = 0; i < SIZE && validSoFar; i++) { // Check the validity of every row.
            validSoFar = isRowValid(i);
        }

        for (int i = 0; i < SIZE && validSoFar; i++) { // Check the validity of every column.
            validSoFar = isColumnValid(i);
        }

        for (int x = 0; x < SQRTSIZE && validSoFar; x++) {
            for (int y = 0; y < SQRTSIZE && validSoFar; y++) {  // Check the validity of every block.
                validSoFar = isBlockValid(x, y);        
            }
        }

        return validSoFar;
    }

    /**
     * @return true if the value's position are possible else return false
     */
    public boolean validVal(int posX, int posY){
        if(!isRowValid(posX)) {return false;}

        if(!isColumnValid(posY)){return false;}

        return isBlockValid(posX % SQRTSIZE, posY % SQRTSIZE);
    }

    /**
     * Fills boxes with a value. The number of boxes filled is equal to DIFFICULTY.
     */
    private void generateNumber(){
        int posX = 0;
        int posY = 0;
        int val;
        for(int i = 0; i < this.DIFFICULTY; i++) {
            do { // While a correct value has not yet been found
                // Find random coordinates
                replaceValue(posY, posX, 0);
                posX = (int)(Math.random() * (this.SIZE));
                posY = (int)(Math.random() * (this.SIZE));
                //Find random value
                val = (int)(Math.random() * (Math.max(this.SIZE,this.SIZE) )+1);
                
                addValue(posX, posY, val);
         
            }while(!validVal(posX, posY));
        System.out.println(i+" "+this.getVal(posX, posY) +"(" + posX + "," + posY +") ");
        }
    }

    /**
     * println ( nbTiret )
     * @param nbTiret the number of tiret do you want.
     */
    private void printLine(int nbTiret){
        for(int i = 0; i < nbTiret -1; i++ ){
            System.out.print("-");
        }
        System.out.println("-");

    }
    /**
     * Prints out a graphical representation of the grid to standard output.
     */
    public void print() {
        for(int x = 0; x < SIZE; x++) {
            if(x % SQRTSIZE == 0) {
                printLine(25);
            }
            for(int y = 0; y < SIZE; y++) {
                if(y % SQRTSIZE == 0) {
                    System.out.print("| ");
                }
                System.out.print(this.getVal(x, y) == 0 ? "  " : this.getVal(x, y) + " ");
            }
            System.out.print("| ");
            System.out.print("\n");
        }
         printLine(25 );
    }


}

