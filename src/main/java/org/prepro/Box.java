package org.prepro;

public class Box{
    private int val;            // 0 est quand il n'y pas de valeur 
    private boolean[] notes;    //les notes vont de 1 à 9 avec le bool qui marque leurs présences  

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


}