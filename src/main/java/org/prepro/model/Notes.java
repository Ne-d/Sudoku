package org.prepro.model;

public class Notes {
    private int tab;

    private final int SIZE;

    public Notes(int size) {
        this.tab = 0x1FF; // TODO: Calculate default value based on SIZE.
        this.SIZE = size;
    }

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

    public void toggle(int note) {
        if (isPresent(note)) {
            delete(note);
        } else {
            add(note);
        }
    }
}
