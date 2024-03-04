package org.prepro.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.prepro.model.Notes;

import static java.lang.Math.sqrt;

public class NotesCellView extends GridPane {
    private final int SIZE;
    private final int SQRTSIZE;
    private final Label[] labels;

    private final Notes notes;

    public NotesCellView(Notes inNotes, int size) {
        this.setAlignment(Pos.CENTER);

        this.SIZE = size;
        this.SQRTSIZE = (int) sqrt(size);

        this.notes = inNotes;

        labels = new Label[this.SIZE];

        for(int i = 0; i < this.SIZE; i++) {
            labels[i] = new Label("");
            labels[i].setPrefHeight(15);
            labels[i].setPrefWidth(15);
            labels[i].setAlignment(Pos.CENTER);


            if(this.notes.isPresent(i + 1)) {
                labels[i].setText(String.valueOf(i + 1));
            }

            this.add(labels[i], i % this.SQRTSIZE, i / this.SQRTSIZE);
        }
    }

    public void deleteNote(int note) {
        this.notes.delete(note);
        this.labels[note - 1].setText("");
    }

    public void addNote(int note) {
        this.notes.add(note);
        this.labels[note - 1].setText(String.valueOf(note + 1));
    }
}
