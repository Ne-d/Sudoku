package org.prepro.model;


import org.prepro.model.RowOrColumn.RowOrColumnType;
import org.prepro.model.solver.RulesElevenTwelve;
import org.prepro.model.solver.RulesFiveToTen;
import org.prepro.model.solver.RulesOneToThree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Grid {
    private final Box[][] board;
    public final int SIZE; // Number of columns and line
    public final int SQRTSIZE; //Number for a block
    private final int DIFFICULTY; // Number of boxes to be given a value when generating a grid.

    /**
     * Generates a new grid with dimensions of 9 by 9, initializes all boxes and fills 17 of them.
     */
    public Grid() {

        this.SIZE = 9;
        this.SQRTSIZE = ((int) Math.sqrt(SIZE));
        this.DIFFICULTY = 17;
        this.board = new Box[SIZE][SIZE];

        // Initialize all boxes.
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.board[x][y] = new Box(this.SIZE);
            }
        }
    }

    public Box[][] getBoard() {
        return this.board;
    }

    /**
     * @return Returns the value of the box at the given coordinates
     */
    public int getVal(int xPos, int yPos) {
        return this.board[xPos][yPos].getVal();
    }

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

        // Delete all notes that become invalid once this new value is added to the grid
        this.deleteNotes(xPos, 0, xPos, this.SIZE - 1, val); //delete note column
        this.deleteNotes(0, yPos, this.SIZE - 1, yPos, val); //delete note line
        this.deleteNotes((xPos / this.SQRTSIZE) * this.SQRTSIZE,
                (yPos / this.SQRTSIZE) * this.SQRTSIZE,
                (1 + xPos / this.SQRTSIZE) * this.SQRTSIZE - 1,
                (1 + yPos / this.SQRTSIZE) * this.SQRTSIZE - 1, val); //delete note square
        this.deleteAllNote(xPos, yPos);

        return true;
    }


    /**
     * Sets the value val to the box of given coordinates.
     *
     * @param xPos The x coordinate of the chosen box.
     * @param yPos The y coordinate of the chosen box.
     * @param val  The value to put in the chosen box.
     */
    public void replaceValue(int xPos, int yPos, int val) {
        this.board[xPos][yPos].setVal(val);
    }


    /**
     * Removes the value of the box at the given coordinates.
     *
     * @return Returns true if the box has been modified (had a value before). Returns false if the box has not been modified (already had no value).
     */
    public boolean removeValue(int xPos, int yPos) {
        if (!isBoxEmpty(xPos, yPos)) {
            this.board[xPos][yPos].setVal(0);
            return true;
        } else {
            return false;
        }
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
     * Fills boxes with a value. The number of boxes filled is equal to DIFFICULTY.
     */
    private void generateNumber() {
        int posX = 0;
        int posY = 0;
        int val;
        boolean foundValid;

        for (int i = 0; i < this.DIFFICULTY; i++) {
            foundValid = false;
            while (!foundValid) { // While a correct value has not yet been found
                // Find random coordinates
                if (foundValid) {
                    replaceValue(posX, posY, 0);
                }
                posX = (int) (Math.random() * (this.SIZE));
                posY = (int) (Math.random() * (this.SIZE));
                //Find random value
                val = (int) (Math.random() * (this.SIZE + 1));

                addValue(posX, posY, val);

                foundValid = validVal(posX, posY);

                this.print();
            }
            System.out.println(i + " " + this.getVal(posX, posY) + "(" + posX + "," + posY + ") ");
        }
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
     * @return true if the value's position are possible else return false
     */
    public boolean validVal(int xPos, int yPos) {
        if (!isRowValid(xPos + 1)) {
            return false;
        }

        if (!isColumnValid(yPos + 1)) {
            return false;
        }

        return isBlockValid(xPos / this.SQRTSIZE, yPos / this.SQRTSIZE);
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
     * Adds a note to the chosen box
     *
     * @param xPos X coordinate of the chosen box
     * @param yPos Y coordinate of the chosen box
     * @param note The note to add
     */
    public void addNote(int xPos, int yPos, int note) {
        this.board[xPos][yPos].addNote(note);
    }

    /**
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX   X coordinate of the end of the rectangle.
     * @param endY   Y coordinate of the end of the rectangle.
     * @return if the grid has been modified
     */
    public boolean isNotePresentOnce(int startX, int startY, int endX, int endY, int[] nbNotesRec) {
        for (int oc = 0; oc < this.SIZE; oc++) { //parcours du tableau des notes du block

            if (nbNotesRec[oc] == 1) { // regarde si une note est présente qu'une seule fois dans le rectangle

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {

                        for (int i = 1; i <= 9; i++) { //cherche la note

                            if (board[x][y].isNotePresent(i) && i == oc + 1) {
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

    public int getNbNotes(int x, int y) {
        return this.board[x][y].getNbNote();
    }

    /**
     * Finds the number of notes in a rectangle.
     *
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX   X coordinate of the end of the rectangle.
     * @param endY   Y coordinate of the end of the rectangle.
     * @return The number of notes found
     */
    private int getNbNote(int startX, int startY, int endX, int endY) {
        int found = 0;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {

                for (int i = 1; i <= 9; i++) { //cherche la note

                    if (board[x][y].isNotePresent(i)) {
                        found++;
                    }
                }
            }
        }
        return found;
    }


    /**
     * Finds all the combinations (k-tuples) of k numbers in a list from 1 to size.
     *
     * @param size          The max value of the numbers.
     * @param len           The amount of numbers to take.
     * @param startPosition The minimum value of the numbers.
     * @param result        The tuple currently being built by the function.
     * @param resultList    The list of tuples to be returned.
     */
    static void combinations_aux(int size, int len, int startPosition, int[] result, List<int[]> resultList) {
        if (len == 0) {
            resultList.add(result.clone());
            return;
        }
        for (int i = startPosition; i <= size + 1 - len; i++) {
            result[result.length - len] = i;
            combinations_aux(size, len - 1, i + 1, result, resultList);
        }
    }

    /**
     * Finds all the combinations (k-tuples) of k numbers in a list from 1 to size.
     *
     * @param size The max value of the numbers.
     * @param k    The amount of numbers to take.
     * @return A list of tuples with k elements from 1 to size.
     */
    static List<int[]> combinations(int size, int k) {
        List<int[]> res = new ArrayList<>();

        combinations_aux(size, k, 1, new int[k], res);

        return res;
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

    /**
     * Finds if the given Row or Column could be in an X-Wing (has the candidate note exactly twice).
     *
     * @param note The note to look for
     * @param lc   Whether we are looking in a Row or a Column
     * @return If there are exactly two candidates in the row or column, return their coordinates. Else return Empty.
     */
    public Optional<int[][]> xWingGetCoordinates(int note, RowOrColumn lc) {
        int nbFound = 0;

        int[][] coords = new int[2][2];

        //For every row or column in the grid, stopping if we found more notes than 2
        if (lc.type == RowOrColumnType.Row) { // If we are looking in a row
            for (int x = 0; x < SIZE; x++) {
                if (isNotePresent(note, x, lc.number)) {
                    if (nbFound < 2) {
                        nbFound++;
                        coords[nbFound - 1] = new int[]{x, lc.number};
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } else { // If we are looking in a column
            for (int y = 0; y < SIZE; y++) {
                if (isNotePresent(note, lc.number, y)) {
                    if (nbFound < 2) {
                        nbFound++;
                        coords[nbFound - 1] = new int[]{lc.number, y};
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }

        // Return the list of coordinates in the k-tuple
        return Optional.of(coords);
    }

    /**
     * Finds if the rows or columns suitable for an X-Wing have their candidates aligned.
     *
     * @param lce    Whether we are looking in rows or columns.
     * @param first  The first row or column's list of coordinates.
     * @param second The second row or column's list of coordinates.
     * @return True if the candidates are aligned, false otherwise.
     */
    public boolean xWingAreRowsOrColumnsAligned(RowOrColumnType lce, int[][] first, int[][] second) {
        if (lce == RowOrColumnType.Row) { // If we are looking in a Row
            boolean sameXSoFar = true;
            boolean differentYSoFar = true;
            for (int i = 0; i < 2 && sameXSoFar && differentYSoFar; i++) {
                // Check if the X coordinate of the candidates are aligned.
                sameXSoFar = (first[i][0] == second[i][0]);
                // Check if the Y coordinates of the candidates are different (not on the same row)
                differentYSoFar = (first[i][1] != second[i][1]);
            }

            return sameXSoFar && differentYSoFar;
        } else { // If we are looking in a Column
            boolean sameYSoFar = true;
            boolean differentXSoFar = true;
            for (int i = 0; i < 2 && sameYSoFar && differentXSoFar; i++) {
                // Check if the X coordinate of the candidates are aligned.
                sameYSoFar = (first[i][1] == second[i][1]);
                // Check if the X coordinates of the candidates are different (not on the same row)
                differentXSoFar = (first[i][0] != second[i][0]);
            }

            return sameYSoFar && differentXSoFar;
        }
    }


    /**
     * Checks if the given rows or columns contain an X-Wing.
     *
     * @param note The note to look for.
     * @param lc1  The first row or column to check.
     * @param lc2  The second row or column to check.
     * @return The coordinates of the X-Wing's candidates (the corners of the X-Wing).
     */

    public Optional<int[][]> checkXWing(int note, RowOrColumn lc1, RowOrColumn lc2) {
        // Find a row or column that could be in an X-Wing.
        Optional<int[][]> first = xWingGetCoordinates(note, lc1);

        // If we haven't found a suitable row or column, return early.
        if (first.isEmpty()) {
            return Optional.empty();
        }

        // Find another suitable row or column.
        Optional<int[][]> second = xWingGetCoordinates(note, lc2);

        // If we haven't found a second suitable row or column, return early.
        if (second.isEmpty()) {
            return Optional.empty();
        } else {
            // Check that the candidates in both rows or columns are properly aligned for an X-Wing.
            boolean candidatesAligned = xWingAreRowsOrColumnsAligned(lc1.type, first.get(), second.get());
            if (!candidatesAligned) {
                return Optional.empty();
            }

            // Make a list of all the coordinates of the X-Wing to return.
            int[][] coordinates = new int[2][2];
            coordinates[0] = first.get()[0];
            coordinates[1] = second.get()[1];

            System.out.println("xWingGetCoordinates - note : " + note + ", coordinates : " + Arrays.deepToString(coordinates));

            return Optional.of(coordinates);
        }
    }

    /**
     * Solve X-wing
     *
     * @param note The note to look for in the tuple
     * @param lce  Whether we are looking in rows or columns
     * @return Whether the grid has been changed by solving the X-Wing.
     */
    public boolean solveXWing(int note, RowOrColumnType lce, int[][] coordinates) {
        boolean hasChanged = false;

        int x1 = coordinates[0][0];
        int x2 = coordinates[1][0];

        int y1 = coordinates[0][1];
        int y2 = coordinates[1][1];

        if (lce == RowOrColumnType.Row) { // If we are working in a row
            // Delete all candidates in the columns, except the ones in the X-Wing.
            for (int i = 0; i < this.SIZE; i++) {
                if (i != y1 && i != y2) {
                    // Using the binary OR operator to make hasChanged true if there is any change, but never return to false.
                    hasChanged |= deleteNote(x1, i, note);
                    hasChanged |= deleteNote(x2, i, note);
                }
            }
        } else { // If we are working in a column
            // Delete all candidates in the row, except the ones in the X-Wing.
            for (int i = 0; i < this.SIZE; i++) {
                if (i != x1 && i != x2) {
                    hasChanged |= deleteNote(i, y1, note);
                    hasChanged |= deleteNote(i, y2, note);
                }
            }
        }

        return hasChanged;
    }

    /**
     * Try to solve an X-Wing
     *
     * @return True if the grid has changed (an X-Wing has been found and solved), otherwise false.
     */
    public boolean ruleThirteen() {
        boolean hasChanged = false;

        for (int note = 1; note <= this.SIZE && !hasChanged; note++) {
            List<int[]> combinations = combinations(9, 2);

            // Try to solve an X-Wing in any row.
            for (int combIndex = 0; combIndex < combinations.size() && !hasChanged; combIndex++) {
                RowOrColumn row1 = new RowOrColumn(RowOrColumnType.Row, combinations.get(combIndex)[0] - 1);
                RowOrColumn row2 = new RowOrColumn(RowOrColumnType.Row, combinations.get(combIndex)[1] - 1);

                Optional<int[][]> coords = checkXWing(note, row1, row2);

                if (coords.isPresent()) {
                    System.out.println("Found X-Wing in rows for note " + note + " at coords " + Arrays.deepToString(coords.get()));
                    hasChanged = solveXWing(note, RowOrColumnType.Row, coords.get());

                    if (hasChanged) {
                        break;
                    }
                }
            }

            // Try to solve an X-Wing in any column
            for (int combIndex = 0; combIndex < combinations.size() && !hasChanged; combIndex++) {
                RowOrColumn column1 = new RowOrColumn(RowOrColumnType.Column, combinations.get(combIndex)[0] - 1);
                RowOrColumn column2 = new RowOrColumn(RowOrColumnType.Column, combinations.get(combIndex)[1] - 1);

                Optional<int[][]> coords = checkXWing(note, column1, column2);

                if (coords.isPresent()) {
                    System.out.println("Found X-Wing in columns for note " + note + " at coords " + Arrays.deepToString(coords.get()));
                    hasChanged = solveXWing(note, RowOrColumnType.Column, coords.get());

                    if (hasChanged) {
                        break;
                    }
                }
            }
        }

        System.out.println("hasChanged = " + (hasChanged ? "true" : "false"));
        System.out.println();
        return hasChanged;
    }

    public void allRules() {
        boolean oneToThree;
        boolean fiveToTen;
        boolean elevenTwelve;

        do {
            do {
                do {
                    oneToThree = RulesOneToThree.solve(this);
                } while (oneToThree);

                fiveToTen = RulesFiveToTen.solve(this);
            } while (fiveToTen);

            elevenTwelve = RulesElevenTwelve.solve(this);
        } while (elevenTwelve);
    }
}
