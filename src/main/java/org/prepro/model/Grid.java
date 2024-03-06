package org.prepro.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.prepro.model.RowOrColumn.RowOrColumnEnum;

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
     * @param val Adds a value to the box of given coordinates, only if the box had no value.
     * @return Returns true if the box has been modified (had no value before), false otherwise.
     */
    public boolean addValue(int xPos, int yPos, int val) {
    	Box box = this.board[xPos][yPos];
        if(box.getVal() != 0) {return false;}
        box.setVal(val);

        // Delete all notes that become invalid once this new value is added to the grid
        this.deleteNotes(xPos, 0, xPos, this.SIZE-1, val); //delete note column
        this.deleteNotes(0, yPos, this.SIZE-1, yPos, val); //delete note line
        this.deleteNotes((xPos/this.SQRTSIZE)*this.SQRTSIZE,
                         (yPos/this.SQRTSIZE)*this.SQRTSIZE,
                         (1 + xPos/this.SQRTSIZE)*this.SQRTSIZE -1,
                         (1 + yPos/this.SQRTSIZE)*this.SQRTSIZE - 1, val); //delete note square
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
     * Prints nbEqual number of equals
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
     * Prints out a graphical representation of the grid to standard output.
     */
    private void printWithNotes_aux(int y, int debut) {
        int note=debut;
        int x = -1;
        for(int j = 0; j < SIZE*SQRTSIZE; j++){
            if(j % SIZE == 0){System.out.print("|");}
            if(j % SQRTSIZE == 0){System.out.print("| ");note = debut;x++;}
            System.out.print(!this.board[x][y].isNotePresent(note) ? "  " : note + " ");
            note++;
        }
    }
    public void printWithNotes(){
        int nbEqual = SIZE*SQRTSIZE*2+23;
        for(int y = 0; y < SIZE; y++){
            if (y % SQRTSIZE == 0){printLineEqual(nbEqual);}
            for(int i = 1; i<SIZE;i = i+3){
                printWithNotes_aux(y, i);
                System.out.println("||");
            }
            if ((y+1) % SQRTSIZE != 0){printLine(nbEqual);}
        }
        printLineEqual(nbEqual);
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
    public boolean deleteNote(int xPos, int yPos, int note) {
        return this.board[xPos][yPos].deleteNote(note);
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
    private void deleteNotes(int startX, int startY, int endX, int endY, int val) {
	    for (int x = startX; x <= endX; x++) {
	        for (int y = startY; y <= endY; y++) {
	        	this.deleteNote(x, y, val);
                if(board[x][y].getNbNote() == 1) {
                    //board[x][y].setVal(board[x][y].getNotes().getUniqueNote());
                }
	        }
	    }
    }

    /**
     * Determines if the given note is present in a box
     * @param note The note to check
     * @param x The x coordinate (column) to look in
     * @param y The y coordinate (line) to look in
     * @return Whether the
     */
    public boolean isNotePresent(int note, int x, int y) {
        return board[x][y].isNotePresent(note);
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
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @param note the note will looking for
     * @return the number of note found
     */
    private int getNbNote(int startX, int startY, int endX, int endY, int note) {
        int found = 0;
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                
                for (int i = 1; i <= 9; i++) { //cherche la note

                    if (board[x][y].isNotePresent(i)) {
                        found ++;
                    }
                    
                }
            }
            
        }
        return found;
    }
    
    
    
    static void combinations_aux(int size, int len, int startPosition, int[] result, List<int[]> resultList) {
        if(len == 0) {
            resultList.add(result.clone());
            return;
        }
        for(int i = startPosition; i <= size + 1 - len; i++) {
            result[result.length - len] = i;
            combinations_aux(size, len - 1, i + 1, result, resultList);
        }
    }
    
    static List<int[]> combinations(int size, int k) {
        List<int[]> res = new ArrayList<>();
        
        combinations_aux(size, k, 1, new int[k], res);
        
        return res;
    }

    /**
     * @param pK_uplet positions of the k_uplet found
     * @param notes of the k_uplet
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @return if the grid has been changed
     */
    public boolean k_uplet_delNotes(int[] pK_uplet, int[] notes, int startX, int startY, int endX) {
        boolean gridModif = false;
        int k = pK_uplet.length;
        int largeur = endX - startX +1;

        int x, y;
        for (int i = 0; i < this.SIZE; i++){
            x = i % largeur + startX;
            y = i / largeur + startY;

            boolean delete = true;
            for (int j = 0; j < k; j++){
                if(pK_uplet[j] == i){
                   delete = false;
                   if(board[x][y].deleteAllNote(notes)){gridModif = true;}
                }
            }
            if(delete){
                for (int j = 0; j < k; j++){
                    if(board[x][y].deleteNote(notes[j])){gridModif = true;}
                }
            }
        }
        return gridModif;
    }

    public boolean verifIsPresent(int[] tab, int val){
        for(int i = 0; i< tab.length; i++){
            if(tab[i] == val){return true;}
        }
        {return false;}
    }

    /**
     * @param k size of the k uplet
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @return if a k_uplet as found
     */
    public boolean k_upletsTest(int k, int startX, int startY, int endX, int endY) {

        List<int[]> tupples = combinations(this.SIZE, k);
        for(int i = 0; i < tupples.size(); i++) {
            boolean[][] tab = new boolean[k][this.SIZE];
            boolean hidden = true; 
            for(int j = 0; j < k; j++) { //Tous les membres du k-uplet
                int numcase = 0;

                for (int y = startY; y <= endY; y++) { // Pour chaque case du rectangle choisi
                    for(int x = startX; x <= endX; x++) {
                        tab[j][numcase] = board[x][y].isNotePresent(tupples.get(i)[j]);
                        if(tab[j][numcase] && board[x][y].getNbNote() == k && hidden){hidden = false;}
                        else{hidden = true;}
                        numcase++;
                    }
                }
            }

            int nbfound = 0; // notes sur les memes
            int[] pos = new int[k];
            int ajouter = 0;
            int largeur = endX - startX +1;
            List<int[]> comb = combinations(k,2);

            for (int t = 0; t < this.SIZE; t++ ) { // Pour chaque case de la zone
                for(int w = 0; w < comb.size(); w++) { // Pour chaque combinaison entre les colonnes de tab
                    
                    if(tab[comb.get(w)[0] - 1][t] && tab[comb.get(w)[1] - 1][t]) { // Compare les valeurs pour chaque combinaison de colonnes
                        if(hidden || board[(t % largeur) + startX][(t / largeur) + startY].getNbNote() == k){
                            if(!verifIsPresent(pos, t) && ajouter != k){pos[ajouter] = t; ajouter++;}
                            nbfound++;
                        }
                    }
                }
            }
            if(nbfound == k*comb.size()) {
                return k_uplet_delNotes(pos, tupples.get(i), startX, startY, endX);
            }
        }
        return false;
    }
    
    /**
     * @param startX X coordinate of the beginning of the rectangle.
     * @param startY Y coordinate of the beginning of the rectangle.
     * @param endX X coordinate of the end of the rectangle.
     * @param endY Y coordinate of the end of the rectangle.
     * @param note the note will looking for
     * @return if the grid has been modified
     */
    private boolean ruleElevenTwelve(int startX, int startY, int endX, int endY, int note){
        int nbFound = getNbNote(startX+1, startY, endX, endY, note);
        if (nbFound > 3){return false;}

        boolean gridModified = false;
        for(int i = startX; i < endX; i++){ 
            this.deleteNotes(i, 0, i, this.SIZE, note); //delete all note in the column
            this.deleteNotes(0, i, this.SIZE, i, note); //delete all note in the row
        }
        return gridModified;
    }

    /**
     * Determines the starting X coordinate of a block
     * @param numBlock The number of the block, starting at 1
     * @return The X starting coordinate of the block
     */
    int blockStartX(int numBlock) {
        int num = numBlock - 1; // True number of the block starting at 0
        int column = num % SQRTSIZE; // Block column
        return column * SQRTSIZE; // Cell column
    }

    /**
     * Determines the ending X coordinate of a block
     * @param numBlock The number of the block, starting at 1
     * @return The X starting coordinate of the block
     */
    int blockEndX(int numBlock) {
        return blockStartX(numBlock) + SQRTSIZE;
    }

    /**
     * Determines the starting Y coordinate of a block
     * @param numBlock The number of the block, starting at 1
     * @return The Y starting coordinate of the block
     */
    int blockStartY(int numBlock) {
        int num = numBlock - 1; // True number of the block starting at 0
        int line = num / SQRTSIZE; // Block column
        return line * SQRTSIZE; // Cell column
    }

    /**
     * Determines the ending Y coordinate of a block
     * @param numBlock The number of the block, starting at 1
     * @return The Y starting coordinate of the block
     */
    int blockEndY(int numBlock) {
        return blockStartY(numBlock) + SQRTSIZE;
    }

    /**
     * Find if there is a pointing k-tuple in the block and give the line or column it is on, if it exists.
     * @param k The amount of members in the k-uplet
     * @param note The note to look for in k-uplets
     * @param block The block to search in
     * @return If there is a pointing k-tuple, return whether it is on a line, or a column, and the number of that line or column. Else, return Optional.empty()
     */
    // TODO: MAKE THE FUNCTION TO DELETE THE NOTES GIVEN BY THE "PAIRE POINTANTE".
    // Supprimer les notes sur la ligne ou colonnes si pas dans le block testé
    public Optional<List<int[]>> findPointingKTuple(int k, int note, RowOrColumn lc, int block) {
        int nbFound = 0;
        List<int[]> coords = new ArrayList<>();

        // For every box in the block, stopping if we found more notes than k.
        for(int x = blockStartX(block); x < blockEndX(block) && nbFound <= k; x++) {
            for (int y = blockStartY(block); y < blockEndY(block) && nbFound <= k; y++) {
                if(isNotePresent(note, x, y)) {
                    nbFound++;
                    coords.add(new int[]{x, y});
                }
            }
        }

        // If there are not exactly k boxes found, then we don't have a pointing k-uplet.
        if(coords.size() != k) { return Optional.empty(); }

        // For each coordinate in the list, check if they are on the same line or column
        if(lc.e == RowOrColumnEnum.Row) {
            for (int[] c : coords) {
                if(c[1] != lc.number) {
                    return Optional.empty();
                }
            }
        }
        else {
            for (int[] c : coords) {
                if(c[0] != lc.number) {
                    return Optional.empty();
                }
            }
        }

        return Optional.of(coords);
    }

    public boolean solvePointingKTuple(int k, int note, RowOrColumn lc, int block) {
        Optional<List<int[]>> coordsOptional = findPointingKTuple(k, note, lc, block);

        if(coordsOptional.isEmpty()){
            return false;
        }

        boolean hasChanged = false;

        if(lc.e == RowOrColumnEnum.Row) {
            for(int x = 0; x < this.SIZE; x++) {
                if(!isInBlock(x, lc.number, block)) {
                    if(deleteNote(x, lc.number, note))
                    {
                        hasChanged = true;
                    }
                }
            }
        }

        else {
            for(int y = 0; y < this.SIZE; y++) {
                if(!isInBlock(lc.number, y, block)) {
                    if(deleteNote(lc.number, y, note)) {
                        hasChanged = true;
                    }
                }
            }
        }

        return hasChanged;
    }

    /**
     * Find which block a box is in
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
     * @param x The x coordinate of the block to check
     * @param y The y coordinate of the block to check
     * @param block The block to check for
     * @return Whether the boc is in the chosen block
     */
    public boolean isInBlock(int x, int y, int block) {
        return findBlock(x, y) == block; // +1 because we number blocks starting at 1 and not 0
    }

    public Optional<List<int[]>> findBoxReduction(int k, int note, RowOrColumn lc, int block) {
        int nbFound = 0;
        List<int[]> coords = new ArrayList<>();

        // For every box in the row or column, stopping if we found more notes than k.
        if(lc.e == RowOrColumnEnum.Row) { // If we are looking in a row
            for (int x = 0; x < SIZE && nbFound <= k; x++) {
                if (isNotePresent(note, x, lc.number)) {
                    nbFound++;
                    coords.add(new int[]{x, lc.number});
                }
            }
        }
        else { // If we are looking in a column
            for (int y = 0; y < SIZE && nbFound <= k; y++) {
                if (isNotePresent(note, lc.number, y)) {
                    nbFound++;
                    coords.add(new int[]{lc.number, y});
                }
            }
        }

        // If there are not exactly k coordinates found, we can't use box-k reduction
        if(coords.size() != k) { return Optional.empty(); }

        if(lc.e == RowOrColumnEnum.Row) { // If we are looking in a row
            for(int i = 0; i < coords.size(); i++) {
                if (!isInBlock(coords.get(i)[0], lc.number, block)) { // If the coordinate is not in the block
                    return Optional.empty();
                }
            }
        }
        else { // If we are looking in a column
            for(int i = 0; i < coords.size(); i++) {
                if (!isInBlock(lc.number, coords.get(i)[1], block)) {  // If the coordinate is not in the block
                    return Optional.empty();
                }
            }
        }

        // Return the list of coordinates in the k-tuple
        return Optional.of(coords);
    }

    /**
     * Solve BoxReduction
     * @param k The k amount of members in the k-uplet
     * @param note The note to look for in thetuple
     * @param lc The Row or Column to search in
     * @param block The block to search in
     * @return The row that contains the Xwing
     */
    public boolean solveBoxReduction(int k, int note, RowOrColumn lc, int block) {
        Optional<List<int[]>> coordsOptional = findBoxReduction(k, note, lc, block);

        if(coordsOptional.isEmpty()) {
            return false;
        }

        boolean hasChanged = false;

        if(lc.e == RowOrColumnEnum.Row) {
            for(int x = blockStartX(block); x < blockEndX(block); x++) {
                for (int y = blockStartY(block); y < blockEndY(block); y++) {
                    if(y != lc.number) {
                        if(deleteNote(x, y, note)) {
                            hasChanged = true;
                        }
                    }
                }
            }
        }
        else {
            for(int x = blockStartX(block); x < blockEndX(block); x++) {
                for (int y = blockStartY(block); y < blockEndY(block); y++) {
                    if(x != lc.number) {
                        if(deleteNote(x, y, note)) {
                            hasChanged = true;
                        }
                    }
                }
            }
        }

        return hasChanged;
    }


    /**
     * Find Xwing in row
     * @param note The note to look for in tuplets
     * @param lc The row to look for in tuplets
     * @return The row that contains the Xwing
     */

    public Optional<List<int[]>> findXwing(int note, RowOrColumn lc){
        int nbFound = 0;

        List<int[]> coords = new ArrayList<>();

        //For every row or column in the grid, stopping if we found more notes than 2
        if(lc.e == RowOrColumnEnum.Row) { // If we are looking in a row
            for (int x = 0; x < SIZE && nbFound <= 2; x++) {
                if (isNotePresent(note, x, lc.number)) {
                    nbFound++;
                    coords.add(new int[]{x, lc.number});
                }
            }
        }
        else { // If we are looking in a column
            for (int y = 0; y < SIZE && nbFound <= 2; y++) {
                if (isNotePresent(note, lc.number, y)) {
                    nbFound++;
                    coords.add(new int[]{lc.number, y});
                }
            }
        }

        // If there are not exactly k coordinates found, we can't use X-wing
        if(coords.size() != 2) { return Optional.empty(); }

        // Return the list of coordinates in the k-tuple
        return Optional.of(coords);
    }

    /**
     * Solve X-wing
     * @param note The note to look for in the tuple
     * @param lc1 The Row or Column to search in
     * @param lc2 The Row or Column to search in
     * @return The row that contains the Xwing
     */
    /*
    public boolean solveXwing(int k, int note, RowOrColumn lc1, RowOrColumn lc2, int block) {
        Optional<List<int[]>> coordsOptional1 = findXwing(note, lc1);
        Optional<List<int[]>> coordsOptional2 = findXwing(note, lc2);

        if(coordsOptional1.isEmpty() || coordsOptional1.isEmpty()) {
            return false;
        }

        boolean hasChanged = false;

        if(lc1.e == RowOrColumnEnum.Row && lc2.e == RowOrColumnEnum.Row) {
            for(int x = blockStartX(block); x < blockEndX(block); x++) {
                for (int y = blockStartY(block); y < blockEndY(block); y++) {
                    if(y != lc.number) {
                        if(deleteNote(x, y, note)) {
                            hasChanged = true;
                        }
                    }
                }
            }
        }
        else {
            for(int x = blockStartX(block); x < blockEndX(block); x++) {
                for (int y = blockStartY(block); y < blockEndY(block); y++) {
                    if(x != lc.number) {
                        if(deleteNote(x, y, note)) {
                            hasChanged = true;
                        }
                    }
                }
            }
        }

        return hasChanged;
    }*/





    /**
     * Solve the grid using the first three rules
     */
    public boolean rulesOneTwoThree() {
        boolean hasChanged = false;

        for (int x = 0; x < this.SIZE; x++) {
            for (int y = 0; y < this.SIZE; y++) {
                boolean continueColumn;
                boolean continueRow;
                boolean continueBlock;
                do {
                    continueColumn = this.simplerule(x, 0, x, 8);
                    continueRow = this.simplerule(0, y, 8, y);
                    continueBlock = this.simplerule((x / 3) * 3, (y / 3) * 3, (1 + x / 3) * 3 - 1, (1 + y / 3) * 3 - 1);
                    // TODO: On exécute la règle pour les blocs à chaque case donc 9 fois pour chaque bloc, faut changer ça je suppose

                    if(continueColumn || continueRow || continueBlock) {
                        hasChanged = true;
                    }
                } while (continueColumn && continueRow && continueBlock);
            }
        }

        return hasChanged;
    }

    public boolean rulesFiveToTen() {
        boolean hasChanged = false;

        for(int k = 2; k <= 3; k++) {
            for (int x = 0; x < this.SIZE; x++) {
                for (int y = 0; y < this.SIZE; y++) {
                    boolean continueColumn;
                    boolean continueRow;
                    boolean continueBlock;

                    do {
                        continueColumn = k_upletsTest(k, x, 0, x, this.SIZE - 1);
                        continueRow = k_upletsTest(k, 0, y, this.SIZE - 1, y);
                        continueBlock = k_upletsTest(k,
                                (x / 3) * 3,
                                (y / 3) * 3,
                                (1 + x / 3) * 3 - 1,
                                (1 + y / 3) * 3 - 1);

                        if(continueRow || continueColumn || continueBlock) {
                            hasChanged = true;
                        }
                    } while(continueColumn && continueRow && continueBlock);
                }
            }
        }

        return hasChanged;
    }

    public boolean rulesElevenTwelve(){
        boolean hasChanged = false;

        for(int k = 2; k <= 3; k++) {
            for (int notes = 1; notes <= this.SIZE; notes++) {
                for (int block = 1; block <= this.SIZE; block++) {
                    for (int nblc = 0; nblc < this.SIZE; nblc++) {
                        boolean continuePointingPairRow;
                        boolean continuePointingPairColumn;
                        boolean continueBoxReductionRow;
                        boolean continueBoxReductionColumn;

                        do {
                            // Solve pointing pair on a row
                            RowOrColumn rowOrColumn = new RowOrColumn(RowOrColumnEnum.Row, nblc);
                            continuePointingPairRow = solvePointingKTuple(k, notes, rowOrColumn, block);

                            // Solve pointing pair on a column
                            rowOrColumn = new RowOrColumn(RowOrColumnEnum.Column, nblc);
                            continuePointingPairColumn = solvePointingKTuple(k, notes, rowOrColumn, block);

                            // Solve box reduction on a row
                            rowOrColumn = new RowOrColumn(RowOrColumnEnum.Row, nblc);
                            continueBoxReductionRow = solveBoxReduction(k, notes, rowOrColumn, block);

                            // Solve box reduction on a column
                            rowOrColumn = new RowOrColumn(RowOrColumnEnum.Column, nblc);
                            continueBoxReductionColumn = solveBoxReduction(k, notes, rowOrColumn, block);

                            if(continuePointingPairRow || continuePointingPairColumn ||
                                    continueBoxReductionRow || continueBoxReductionColumn) {
                                hasChanged = true;
                            }
                        } while(continuePointingPairRow && continuePointingPairColumn &&
                                continueBoxReductionRow && continueBoxReductionColumn);
                    }
                }
            }
        }

        return hasChanged;
    }

    public void allRules(){
        boolean oneToThree;
        boolean fiveToTen;
        boolean elevenTwelve;

        do {
            do {
                do {
                    oneToThree = rulesOneTwoThree();
                } while(oneToThree);

                fiveToTen = rulesFiveToTen();
            } while(fiveToTen);

            elevenTwelve = rulesElevenTwelve();
        } while(elevenTwelve);
    }
}
