package org.prepro;

public class Box{
    private int val;            // The absence of a value (empty box) is represented by a zero.
    private boolean[] notes;    // The number of a note is determined by the index in the array. Its presence is the boolean.

    public Box(){
        this.val = 0;
        this.notes = new boolean [] { true,true,true,true,true,true,true,true,true };
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void delete_note(int note){
        this.notes[note] = false;
    }

    public void addNote(int note){
        this.notes[note] = true;
    }

}