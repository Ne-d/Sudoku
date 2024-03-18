package org.prepro.model;

public class Box {
    /**
     * The size of the grid this box belongs to. Used for the notes.
     */
    private final int SIZE;

    /**
     * The notes this box holds.
     * Each bit of this int represents a note. It is set to 1 if the note is set, 0 if it is unset.
     */
    private final Notes notes;

    /**
     * Creates a new box with default value 0 and all notes set.
     */
    public Box(int size) {
        this.SIZE = size;
        this.notes = new Notes(this.SIZE);
    }

    /**
     * Create a new Box that is a copy of the given one.
     *
     * @param other The Box to copy.
     */
    public Box(Box other) {
        this.notes = new Notes(other.notes);
        this.SIZE = other.SIZE;
    }

    /**
     * Get the value of this box
     *
     * @return The value of this box
     */
    public int getVal() {
        return this.notes.getUniqueNote();
    }

    /**
     * Sets the value of this box
     *
     * @param val The value to set to this box
     */
    public void setVal(int val) {
        for (int i = 1; i <= this.SIZE; i++) {
            if (i != val) {
                deleteNote(i);
            }
        }
        addNote(val);
    }

    /**
     * Removes a note from this box
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
     */
    public boolean deleteAllNote(int[] notes) {
        return this.notes.deleteAllExcept(notes);
    }

    /**
     * Adds a note to this box
     *
     * @param note The note to be added
     */
    public void addNote(int note) {
        this.notes.add(note);
    }

    /**
     * Find if a note is set in this box or not
     *
     * @param note The note to look for
     * @return Whether the note is set or not
     */
    public boolean isNotePresent(int note) {
        return this.notes.isPresent(note);
    }

    /**
     * @return The amount of notes for this box
     */
    public int getNbNote() {
        return this.notes.getNumber();
    }

    /**
     * Prints the content of the notes in this box
     */
    public void afficheNote() {
        this.notes.print();
    }

    /**
     * Gets the notes of this box.
     *
     * @return The notes of this box.
     */
    public Notes getNotes() {
        return this.notes;
    }
}