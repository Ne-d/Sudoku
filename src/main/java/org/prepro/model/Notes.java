package org.prepro.model;

public class Notes {
    private int tab;

    private final int SIZE = 9;

    public Notes() {
        this.tab = 0x1FF;
    }

    /**
     * Adds a note
     * @param note The note to be added
     */
    public void add(int note){
        this.tab = this.tab | (int)Math.pow(2, note - 1);
    }

    /**
     * Find if a note is set or not
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
    public void afficheNote() {
        for (int i = 0; i < this.SIZE; i++) {
            if (((this.tab >> i) & 1) == 1) {
                System.out.print(i + 1 + " ");
            }
        }
        System.out.println();
    }

    /**
     * Removes a note
     * @param note The note to be removed
     * @return True if the note has been deleted
     */

    public boolean delete(int note){
        boolean alreadyPresent = isPresent(note);
        this.tab = this.tab & ~(int)Math.pow(2, note - 1);
        return alreadyPresent;
    }

    public int getUniqueNote() throws MultipleNotesException {
        if (this.getNumber() == 1) {
            for (int i = 0; i < SIZE; i++) {
                if (isPresent(i)) {
                    return i;
                }
            }
        }

        throw new MultipleNotesException();
    }

    public static class MultipleNotesException extends Exception {}
}
