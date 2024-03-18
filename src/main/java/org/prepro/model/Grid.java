package org.prepro.model;


public class Grid {
    /**
     * The 2D array of boxes that constitutes the grid.
     */
    private final Box[][] board;

    /**
     * The size of the grid (number of rows and columns).
     */
    public final int SIZE;

    /**
     * The size of a block.
     */
    public final int SQRTSIZE;

    /**
     * Generates a new grid with dimensions of 9 by 9, initializes all boxes.
     */
    public Grid() {
        this.SIZE = 9;
        this.SQRTSIZE = ((int) Math.sqrt(SIZE));
        this.board = new Box[SIZE][SIZE];

        // Initialize all boxes.
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.board[x][y] = new Box(this.SIZE);
            }
        }
    }

    /**
     * Generates a new grid that is a clone of the given one.
     * This is not the same as just creating a new Grid variable and assigning it to the old one, since that would be a reference, not a copy.
     *
     * @param other The grid to be copied.
     */
    public Grid(Grid other) {
        this.SIZE = other.SIZE;
        this.SQRTSIZE = ((int) Math.sqrt(other.SIZE));
        this.board = new Box[other.SIZE][other.SIZE];

        // Initialize all boxes
        for (int x = 0; x < other.SIZE; x++) {
            for (int y = 0; y < other.SIZE; y++) {
                this.board[x][y] = new Box(other.getBoard()[x][y]);
            }
        }
    }

    /**
     * Get the grid's board.
     *
     * @return The grid's board.
     */
    public Box[][] getBoard() {
        return this.board;
    }

    /**
     * @return Returns the value of the box at the given coordinates
     */
    public int getVal(int xPos, int yPos) {
        return this.board[xPos][yPos].getVal();
    }

    /**
     * Get the notes of a box.
     *
     * @param xPos The x coordinate of the box.
     * @param yPos The y coordinate of the box.
     * @return The notes of the box.
     */
    public Notes getNotes(int xPos, int yPos) {
        return this.board[xPos][yPos].getNotes();
    }


    /**
     * @return Returns true if the box at the given coordinates is empty, false if it has a value.
     */
    public boolean isBoxEmpty(int xPos, int yPos) {
        return (this.getVal(xPos, yPos) == 0);
    }


    /**
     * Adds a value to the chosen box if it was empty.
     *
     * @param val Adds a value to the box of given coordinates, only if the box had no value.
     * @return Returns true if the box has been modified (had no value before), false otherwise.
     */
    public boolean addValue(int xPos, int yPos, int val) {
        Box box = this.board[xPos][yPos];
        if (box.getVal() != 0) {
            return false;
        }
        box.setVal(val);

        // Delete all notes that become invalid in the row
        for (int x = 0; x < this.SIZE; x++) {
            if (x != xPos) // All cells except the one we are adding a value to.
                this.deleteNote(x, yPos, val);
        }

        for (int y = 0; y < this.SIZE; y++) {
            if (y != yPos) // All cells except the one we are adding a value to.
                this.deleteNote(xPos, y, val);
        }

        int blockStartX = (xPos / this.SQRTSIZE) * this.SQRTSIZE;
        int blockEndX = (1 + xPos / this.SQRTSIZE) * this.SQRTSIZE - 1;
        int blockStartY = (yPos / this.SQRTSIZE) * this.SQRTSIZE;
        int blockEndY = (1 + yPos / this.SQRTSIZE) * this.SQRTSIZE - 1;
        for (int x = blockStartX; x < blockEndX; x++) {
            for (int y = blockStartY; y < blockEndY; y++) {
                if (x != xPos && y != yPos)
                    deleteNote(x, y, val);
            }
        }

        return true;
    }


    /**
     * Determines if a rectangle is correct (has no duplicate values).
     *
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
                    } else {
                        presentNumbers[val - 1] = true;
                    }
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
     * Prints nbDash number of dashes
     *
     * @param nbDash the amount of dashes to be printed
     */
    private void printLine(int nbDash) {
        for (int i = 0; i < nbDash - 1; i++) {
            System.out.print("-");
        }
        System.out.println("-");

    }

    /**
     * Prints nbEqual number of equals
     *
     * @param nbEqual the amount of equals to be printed
     */
    private void printLineEqual(int nbEqual) {
        for (int i = 0; i < nbEqual - 1; i++) {
            System.out.print("=");
        }
        System.out.println("=");

    }


    /**
     * Prints out a graphical representation of the grid to standard output.
     */
    public void print() {
        for (int y = 0; y < SIZE; y++) {
            if (y % SQRTSIZE == 0) {
                printLine(25);
            }
            for (int x = 0; x < SIZE; x++) {
                if (x % SQRTSIZE == 0) {
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
     * Prints out a graphical representation of the grid to standard output.
     */
    private void printWithNotes_aux(int y, int debut) {
        int note = debut;
        int x = -1;
        for (int j = 0; j < SIZE * SQRTSIZE; j++) {
            if (j % SIZE == 0) {
                System.out.print("|");
            }
            if (j % SQRTSIZE == 0) {
                System.out.print("| ");
                note = debut;
                x++;
            }
            System.out.print(!this.board[x][y].isNotePresent(note) ? "  " : note + " ");
            note++;
        }
    }

    /**
     * Prints a text representation of the grid with all notes.
     */
    public void printWithNotes() {
        int nbEqual = SIZE * SQRTSIZE * 2 + 23;
        for (int y = 0; y < SIZE; y++) {
            if (y % SQRTSIZE == 0) {
                printLineEqual(nbEqual);
            }
            for (int i = 1; i < SIZE; i = i + 3) {
                printWithNotes_aux(y, i);
                System.out.println("||");
            }
            if ((y + 1) % SQRTSIZE != 0) {
                printLine(nbEqual);
            }
        }
        printLineEqual(nbEqual);
    }

    /**
     * Prints the notes of the chosen box
     *
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     */
    public void afficheNote(int xPos, int yPos) {
        System.out.print("Notes case (" + xPos + ", " + yPos + "): ");
        this.board[xPos][yPos].afficheNote();
    }


    /**
     * Adds a note to the chosen box
     *
     * @param x    The x coordinate of the box to add a note to.
     * @param y    The y coordinate of the box to add a note to.
     * @param note The note to add to the box.
     */
    public void addNote(int x, int y, int note) {
        this.board[x][y].addNote(note);
    }

    /**
     * Deletes a note of the chosen box
     *
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     * @param note The note to remove
     * @return True if the grid has changed (the note was removed), otherwise false (the note was not there).
     */
    public boolean deleteNote(int xPos, int yPos, int note) {
        return this.board[xPos][yPos].deleteNote(note);
    }

    /**
     * Deletes a note of the chosen box
     *
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     */
    public void deleteAllNote(int xPos, int yPos) {
        this.board[xPos][yPos].deleteAllNote();
    }

    /**
     * delete all note in the field witch is equals to  val
     *
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX   X coordinate of the end of the rectangle.
     * @param endY   Y coordinate of the end of the rectangle.
     * @param val    value of the note witch is delete
     */
    private void deleteNotes(int startX, int startY, int endX, int endY, int val) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                this.deleteNote(x, y, val);
            }
        }
    }

    /**
     * Determines if the given note is present in a box
     *
     * @param note The note to check
     * @param x    The x coordinate (column) to look in
     * @param y    The y coordinate (line) to look in
     * @return Whether the
     */
    public boolean isNotePresent(int note, int x, int y) {
        return board[x][y].isNotePresent(note);
    }

    /**
     * Get the number of notes in a box.
     *
     * @param x The x coordinate of the box.
     * @param y The y coordinate of the box.
     * @return The number of notes in the box.
     */
    public int getNbNotes(int x, int y) {
        return this.board[x][y].getNbNote();
    }


    /**
     * Determines the starting X coordinate of a block
     *
     * @param numBlock The number of the block, starting at 1
     * @return The X starting coordinate of the block
     */
    public int blockStartX(int numBlock) {
        int num = numBlock - 1; // True number of the block starting at 0
        int column = num % SQRTSIZE; // Block column
        return column * SQRTSIZE; // Cell column
    }

    /**
     * Determines the ending X coordinate of a block
     *
     * @param numBlock The number of the block, starting at 1
     * @return The X starting coordinate of the block
     */
    public int blockEndX(int numBlock) {
        return blockStartX(numBlock) + SQRTSIZE;
    }

    /**
     * Determines the starting Y coordinate of a block
     *
     * @param numBlock The number of the block, starting at 1
     * @return The Y starting coordinate of the block
     */
    public int blockStartY(int numBlock) {
        int num = numBlock - 1; // True number of the block starting at 0
        int line = num / SQRTSIZE; // Block column
        return line * SQRTSIZE; // Cell column
    }

    /**
     * Determines the ending Y coordinate of a block
     *
     * @param numBlock The number of the block, starting at 1
     * @return The Y starting coordinate of the block
     */
    public int blockEndY(int numBlock) {
        return blockStartY(numBlock) + SQRTSIZE;
    }

    /**
     * Find which block a box is in
     *
     * @param x The x coordinate of the block
     * @param y The y coordinate of the block
     * @return The block that contains the chosen box
     */
    public int findBlock(int x, int y) {
        int res = (y / SQRTSIZE) * SQRTSIZE + (x / SQRTSIZE);
        return res + 1;
    }

    /**
     * Find if a box is in the given block
     *
     * @param x     The x coordinate of the block to check
     * @param y     The y coordinate of the block to check
     * @param block The block to check for
     * @return Whether the boc is in the chosen block
     */
    public boolean isInBlock(int x, int y, int block) {
        return findBlock(x, y) == block; // +1 because we number blocks starting at 1 and not 0
    }
}
