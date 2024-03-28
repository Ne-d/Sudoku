package org.prepro.model;

/**
 * A class that represents the notes of a cell in a sudoku grid.
 */
public class Notes {
    /**
     * An integer that acts as an array of booleans, each representing a note in the cell.
     */
    private int tab;

    /**
     * The size of the grid these notes are a part of. This determines the amount of notes.
     */
    private final int SIZE;

    /**
     * Creates a new Notes object.
     *
     * @param size The amount of notes (equal to the size of the grid).
     */
    public Notes(int size) {
        this.tab = 0x1FF; // TODO: Calculate default value based on SIZE.
        this.SIZE = size;
    }

    /**
     * Creates a new Notes object, which is a copy of the one given as an argument.
     *
     * @param other The Notes object to copy.
     */
    public Notes(Notes other) {
        this.tab = other.tab;
        this.SIZE = other.SIZE;
    }

    /**
     * Adds a note
     *
     * @param note The note to be added
     */
    public void add(int note) {
        this.tab = this.tab | (int) Math.pow(2, note - 1);
    }

    /**
     * Find if a note is set or not
     *
     * @param note The note to look for
     * @return Whether the note is set or not
     */
    public boolean isPresent(int note) {
        return ((this.tab >> note - 1) & 1) == 1;
    }

    /**
     * @return The amount of notes
     */
    public int getNumber() {
        int sum = 0;
        for (int i = 0; i < this.SIZE; i++) {
            if (((this.tab >> i) & 1) == 1) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Prints the content of the notes
     */
    public void print() {
        for (int i = 0; i < this.SIZE; i++) {
            if (((this.tab >> i) & 1) == 1) {
                System.out.print(i + 1 + " ");
            }
        }
        System.out.println();
    }

    /**
     * Removes a note
     *
     * @param note The note to be removed
     * @return True if the note has been deleted
     */

    public boolean delete(int note) {
        boolean alreadyPresent = isPresent(note);
        this.tab = this.tab & ~(int) Math.pow(2, note - 1);
        return alreadyPresent;
    }

    /**
     * Delete all the notes
     */
    public void deleteAll() {
        this.tab = 0;
    }

    /**
     * Delete all notes except the note present in the argument
     *
     * @param notes An array of the notes to keep, all the others will be deleted.
     * @return True if the grid has been modified, otherwise false.
     */
    public boolean deleteAllExcept(int[] notes) {
        boolean modif = false;

        for (int i = 1; i < this.SIZE; i++) {
            boolean delete = true;

            for (int note : notes) {
                if (note == i) {
                    delete = false;
                    break;
                }
            }
            if (delete) {
                if (delete(i)) {
                    modif = true;
                }
            }
        }
        return modif;
    }

    /**
     * Get the only present note, if there is only one.
     *
     * @return The only present note if there is only one, otherwise zero.
     */
    public int getUniqueNote() {
        if (this.getNumber() == 1) {
            for (int i = 1; i <= SIZE; i++) {
                if (isPresent(i)) {
                    return i;
                }
            }
        }

        return 0;
    }
}
