package org.prepro;


public class Grid {
    private final Box[][] board;
    private final int SIZE; // Number of columns and line
    private final int SQRTSIZE; //Number for a block
    private final int DIFFICULTY; // Number of boxes to be given a value when generating a grid.


    /**
     * Generates a new grid with dimensions of 9 by 9, initializes all boxes and fills 17 of them.
     */
    public Grid() {

        this.SIZE = 9;
        this.SQRTSIZE = ((int)Math.sqrt(SIZE));
        this.DIFFICULTY = 17;
        this.board = new Box[SIZE][SIZE];
        
        // Initialize all boxes.
        for(int x = 0; x < SIZE; x++) {
            for(int y = 0; y < SIZE; y++) {
                this.board[x][y] = new Box(this.SIZE);
            }
        }
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
        return (this.getVal(xPos, yPos) == 0);
    }


    /**
     * Adds a value to the chosen box if it was empty.
     * @param val Adds a value to the box of given coordinates, only if the box had no value.
     * @return Returns true if the box has been modified (had no value before), false otherwise.
     */
    public boolean addValue(int xPos, int yPos, int val) {
    	Box box = this.board[xPos][yPos];
        if(box.getVal() != 0) {return false;}
        box.setVal(val);
        this.deletenotes(xPos, 0, xPos, this.SIZE-1, val); //delete note column
        this.deletenotes(0, yPos, this.SIZE-1, yPos, val); //delete note line
        this.deletenotes((xPos/this.SQRTSIZE)*this.SQRTSIZE, (yPos/this.SQRTSIZE)*this.SQRTSIZE, (1 + xPos/this.SQRTSIZE)*this.SQRTSIZE -1, (1 + yPos/this.SQRTSIZE)*this.SQRTSIZE - 1, val); //delete note square
        this.deleteAllNote(xPos, yPos);
        return true;
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
     * @param endX   X coordinate of the end of the rectangle.
     * @param endY   Y coordinate of the end of the rectangle.
     * @return True if the rectangle has no duplicate values, false otherwise.
     */
    public boolean isValidRect(int startX, int startY, int endX, int endY) {
        // This array will store a boolean for each possible value (1 to 9).
        // If the value is in the row, the corresponding boolean will be set to true.
        boolean[] presentNumbers = new boolean[(endX - startX + 1) * (endY - startY + 1)];
        int val;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                val = getVal(x, y);
                if (val != 0) {
                    if (presentNumbers[val - 1]) {
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
                xBlock * this.SQRTSIZE + this.SQRTSIZE - 1,
                yBlock * this.SQRTSIZE + this.SQRTSIZE - 1);
    }


    /**
     * @return yes if the grid is valid else false
     */
    public boolean isValid() {
        boolean canceled = true;

        for (int i = 1; i <= this.SIZE && canceled; i++) {
            canceled = isColumnValid(i);
            canceled = canceled && isRowValid(i);
        }

        for (int i = 0; i < this.SQRTSIZE && canceled; i++) {
            for (int j = 0; j < this.SQRTSIZE && canceled; j++) {
                canceled = isBlockValid(i, j);
            }
            
        }
        return canceled;
    }


    /**
     * Fills boxes with a value. The number of boxes filled is equal to DIFFICULTY.
     */
    private void generateNumber() {
        int posX = 0;
        int posY = 0;
        int val;
        boolean foundValid;

        for(int i = 0; i < this.DIFFICULTY; i++) {
            foundValid = false;
            while(!foundValid) { // While a correct value has not yet been found
                // Find random coordinates
                if (foundValid) { replaceValue(posX, posY, 0); }
                posX = (int)(Math.random() * (this.SIZE));
                posY = (int)(Math.random() * (this.SIZE));
                //Find random value
                val = (int)(Math.random() * (this.SIZE + 1));
                
                addValue(posX, posY, val);

                foundValid = validVal(posX, posY);

                this.print();
            }
            System.out.println(i + " " + this.getVal(posX, posY) + "(" + posX + "," + posY + ") ");
        }
    }


    /**
     * Prints nbDash number of dashes
     * @param nbDash the amount of dashes to be printed
     */
    private void printLine(int nbDash) {
        for (int i = 0; i < nbDash - 1; i++) {
            System.out.print("-");
        }
        System.out.println("-");

    }


    /**
     * Prints out a graphical representation of the grid to standard output.
     */
    public void print() {
        for(int y = 0; y < SIZE; y++) {
            if(y % SQRTSIZE == 0) {
                printLine(25);
            }
            for(int x = 0; x < SIZE; x++) {
                if(x % SQRTSIZE == 0) {
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
    public boolean validVal(int xPos, int yPos) {
        if(!isRowValid(xPos + 1)) {return false;}

        if(!isColumnValid(yPos + 1)){return false;}

        return isBlockValid(xPos / this.SQRTSIZE, yPos / this.SQRTSIZE);
    }


    /**
     * Prints the notes of the chosen box
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     */
    public void afficheNote(int xPos, int yPos) {
        System.out.print("Notes case (" + xPos + ", " + yPos + "): ");
        this.board[xPos][yPos].afficheNote();
    }


    /**
     * Deletes a note of the chosen box
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     * @param note The note to remove
     */
    public void deleteNote(int xPos, int yPos, int note) {
        this.board[xPos][yPos].deleteNote(note);
    }

    /**
     * Deletes a note of the chosen box
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     */
    public void deleteAllNote(int xPos, int yPos){
        this.board[xPos][yPos].deleteAllNote();
    }
    /**
     * delete all note in the field witch is equals to  val
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @param val value of the note witch is delete
     */
    private void deletenotes(int startX, int startY, int endX, int endY, int val) {
	    for (int x = startX; x <= endX; x++) {
	        for (int y = startY; y <= endY; y++) {
	        	this.deleteNote(x, y, val);
	        }
	    }
    }

    /**
     * Adds a note to the chosen box
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     * @param note The note to add
     */
    public void addNote(int xPos, int yPos, int note) {
        this.board[xPos][yPos].addNote(note);
    }
    /**
     *
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @return if the grid has been modified
     */
    private boolean isNotePresentOnce(int startX, int startY, int endX, int endY, int[] nbNotesRec) {
        for (int oc = 0; oc < this.SIZE; oc++) { //parcours du tableau des notes du block

            if (nbNotesRec[oc] == 1) { // regarde si une note est présente qu'une seule fois dans le rectangle

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {

                        for (int i = 1; i <= 9; i++) { //cherche la note

                            if (board[x][y].isNotePresent(i) && i==oc+1) {
                                this.addValue(x, y, i);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * complete a rectangle with some simple rule. WARNING the rectangle must be a row or a column or a square
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @return if the grid has been modified
     */
    public boolean simplerule(int startX, int startY, int endX, int endY) {
        int[] nbNotesRec = new int[this.SIZE];
        int nbNotes;
        int j;
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
            	nbNotes = board[x][y].getNbNote();
            	j=0;
	            for(int i=1; i<=9 && j != nbNotes; i++) {
	            	if( board[x][y].isNotePresent(i)) {
                        nbNotesRec[i-1]++;
	            		j++;
                        if(nbNotes == 1){ 
                        	this.addValue(x, y, i);
                        	return true;
                        }
	            	}
	            }
            }
        }
        return isNotePresentOnce(startX,startY,endX,endY,nbNotesRec);
    }

    /**
     * Do the verification of the three first rules of Sudoku with a print in the console
     */
    public void rulesOneTwoThreeVerification() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                boolean continueColumn;
                boolean continueRow;
                boolean continueBlock;
                do {
                    continueColumn = this.simplerule(x, 0, x, 8);
                    continueRow = this.simplerule(0, y, 8, y);
                    continueBlock = this.simplerule((x / 3) * 3, (y / 3) * 3, (1 + x / 3) * 3 - 1, (1 + y / 3) * 3 - 1);
                }
                while (continueColumn && continueRow && continueBlock);
            }
        }
    }

}