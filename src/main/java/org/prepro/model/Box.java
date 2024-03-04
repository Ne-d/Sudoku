package org.prepro.model;


public class Box {
    private final int SIZE;
    private int val; // The absence of a value (empty box) is represented by a zero.
    private int notes; // Each bit of this int represents a note. It is set to 1 if the note is set, 0 if it is unset.

    /**
     * Creates a new box with default value 0 and all notes set.
     */
    public Box(int size) {
        this.val = 0;
        this.notes = 0x1FF;
        this.SIZE = size;
    }

    /**
     * Get the value of this box
     * @return The value of this box
     */
    public int getVal() {
        return val;
    }

    /**
     * Sets the value of this box
     * @param val The value to set to this box
     */
    public void setVal(int val) {
        this.val = val;
    }

    /**
     * Removes a note from this box
     * @param note The note to be removed
     * @return True if the note has been deleted
     */

    public boolean deleteNote(int note){
        boolean alreadyPresent = isNotePresent(note);
        this.notes = this.notes & ~(int)Math.pow(2, note - 1);
        return alreadyPresent;
    }
    /**
     * delete all note
     */
    public void deleteAllNote(){
        this.notes = 0;
    }
    /**
     * delete all note execpt the note present in notes
     * @param notes which aren't delete
     */
    public boolean deleteAllNote(int []notes){
        boolean modif = false;
        for(int i = 1; i < this.SIZE; i++){

            boolean delete = true;
            for (int note : notes) {
                if (note == i) {
                    delete = false;
                    break;
                }
            }
            if(delete){if(deleteNote(i)){modif = true;}}
        }
        return modif;
    }

    /**
     * Adds a note to this box
     * @param note The note to be added
     */
    public void addNote(int note){
        this.notes = this.notes | (int)Math.pow(2, note - 1);
    }

    /**
     * Find if a note is set in this box or not
     * @param note The note to look for
     * @return Whether the note is set or not
     */
    public boolean isNotePresent(int note) {
        return ((this.notes >> note - 1) & 1) == 1;
    }

    /**
     * @return The amount of notes for this box
     */
    public int getNbNote() {
        int sum = 0;
        for (int i = 0; i < this.SIZE; i++) {
            if (((this.notes >> i) & 1) == 1) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Prints the content of the notes in this box
     */
    public void afficheNote() {
        for (int i = 0; i < this.SIZE; i++) {
            if (((this.notes >> i) & 1) == 1) {
                System.out.print(i + 1 + " ");
            }
        }
        System.out.println();
    }
}