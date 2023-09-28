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
        addValue(4,4, 4);
        addValue(8,4,4);
       //this.generateNumber();
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
     * @param xPos The x coordinate of the chosen box.
     * @param yPos The y coordinate of the chosen box.
     * @param val The value to put in the chosen box.
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
     * Determines if a rectangle is correct (has no duplicate values).
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @return True if the rectangle has no duplicate values, false otherwise.
     */
    public boolean isValidRect(int startX, int startY, int endX, int endY) {
        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the row, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[this.SIZE];
        int val;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                val = getVal(x, y);
                if (val !=0){
                    if(presentNumbers[val -1]) {
                        return false;
                    }
                    else {presentNumbers[val -1] = true; }
                }

            }
        }
        return true;
    }

    /**
     * @param row The number of the row to check.
     * @return True if the row doesn't have any duplicate numbers, false otherwise.
     */
    public boolean isRowValid(int row) {
        return isValidRect(0, row - 1, this.SIZE - 1, row - 1);
    }

    /**
     * @param column The number of the column to check.
     * @return True if the column doesn't have any duplicate numbers, false otherwise.
     */
    public boolean isColumnValid(int column) {
        return isValidRect(column - 1, 0, column - 1, this.SIZE - 1);
    }

    /**
     * @param xBlock X coordinate of the block (not the first cell)
     * @param yBlock y coordinate of the block (not the first cell)
     * @return True if the block doesn't have any duplicate numbers, false otherwise.
     */
    public boolean isBlockValid(int xBlock, int yBlock) {
        return isValidRect(xBlock * this.SQRTSIZE,
                yBlock * this.SQRTSIZE,
                xBlock * this.SQRTSIZE + this.SQRTSIZE -1,
                xBlock * this.SQRTSIZE + this.SQRTSIZE -1);
    }

    /**
     * @return yes if the grid is valid else false
     */
    public boolean isValid(){
        boolean canceled = true;

        for (int i = 1; i <= this.SIZE && canceled; i++){
            canceled = isColumnValid(i);
            canceled = isRowValid(i) && canceled;
            }

        for (int i = 0; i < this.SQRTSIZE && canceled; i++){
            for (int j = 0; j < this.SQRTSIZE && canceled; j++){
                canceled = isBlockValid(i, j);
            }
            
        }
        return canceled;

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
        printLine(25);
    }


    /**
     * @return true if the value's position are possible else return false
     */
    public boolean validVal(int posX, int posY) {
        if(!isRowValid(posX)) {return false;}

        if(!isColumnValid(posY)){return false;}

        return isBlockValid(posX, posY);
    }
    public void afficheNote(int posX,int posY){
        this.board[posX][posY].afficheNote();
    }

    public void delete_note(int posX,int posY,int note){
        this.board[posX][posY].delete_note(note);
    }

    public void addNote(int posX,int posY,int note){
       this.board[posX][posY].addNote(note);
    }
}