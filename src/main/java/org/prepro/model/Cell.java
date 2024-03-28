package org.prepro.model;

/**
 * A class that represents a cell in a grid of sudoku.
 */
public class Cell {
    /**
     * The size of the grid this cell belongs to. Used for the notes.
     */
    private final int SIZE;

    /**
     * The notes this cell holds.
     * Each bit of this int represents a note. It is set to 1 if the note is set, 0 if it is unset.
     */
    private Notes notes;

    /**
     * Creates a new cell with default value 0 and all notes set.
     *
     * @param size The size of the grid this cell is in. This determines the possible notes.
     */
    public Cell(int size) {
        this.SIZE = size;
        this.notes = new Notes(this.SIZE);
    }

    /**
     * Create a new Cell that is a copy of the given one.
     *
     * @param other The Cell to copy.
     */
    public Cell(Cell other) {
        this.notes = new Notes(other.notes);
        this.SIZE = other.SIZE;
    }

    /**
     * Get the value of this cell
     *
     * @return The value of this cell
     */
    public int getVal() {
        return this.notes.getUniqueNote();
    }

    /**
     * Sets the value of this cell
     *
     * @param val The value to set to this cell
     */
    public void setVal(int val) {
        for (int i = 1; i <= this.SIZE; i++) {
            deleteNote(i);
        }

        addNote(val);
    }

    /**
     * Removes a note from this cell
     *
     * @param note The note to be removed
     * @return True if the note has been deleted, false if it was not there.
     */

    public boolean deleteNote(int note) {
        return this.notes.delete(note);
    }

    /**
     * Delete all the notes
     */
    public void deleteAllNote() {
        this.notes.deleteAll();
    }

    /**
     * Delete all notes except the note present in the argument
     *
     * @param notes An array of the notes to keep, all the others will be deleted.
     * @return True if the grid has been modified, otherwise false.
     */
    public boolean deleteAllNote(int[] notes) {
        return this.notes.deleteAllExcept(notes);
    }

    /**
     * Adds a note to this cell
     *
     * @param note The note to be added
     */
    public void addNote(int note) {
        this.notes.add(note);
    }

    /**
     * Find if a note is set in this cell or not
     *
     * @param note The note to look for
     * @return Whether the note is set or not
     */
    public boolean isNotePresent(int note) {
        return this.notes.isPresent(note);
    }

    /**
     * @return The amount of notes for this cell
     */
    public int getNbNote() {
        return this.notes.getNumber();
    }

    /**
     * Prints the content of the notes in this cell
     */
    public void afficheNote() {
        this.notes.print();
    }

    /**
     * Gets the notes of this cell.
     *
     * @return The notes of this cell.
     */
    public Notes getNotes() {
        return this.notes;
    }

    /**
     * Sets the notes of this cell to the given Notes object.
     *
     * @param notes The notes to put into this cell.
     */
    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}