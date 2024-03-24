package org.prepro.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.prepro.model.Notes;

import static java.lang.Math.sqrt;

public class NotesView extends GridPane {
    private final int SIZE;
    private final int SQRTSIZE;
    private final Label[] labels;
    private Notes notes;

    public NotesView(Notes inNotes, int size) {
        this.setAlignment(Pos.CENTER);

        this.SIZE = size;
        this.SQRTSIZE = (int) sqrt(size);

        this.notes = inNotes;

        labels = new Label[this.SIZE];

        for (int i = 0; i < this.SIZE; i++) {
            labels[i] = new Label("");
            labels[i].setPrefHeight(15);
            labels[i].setPrefWidth(15);
            labels[i].setAlignment(Pos.CENTER);

            if (this.notes.isPresent(i + 1)) {
                labels[i].setText(String.valueOf(i + 1));
            }

            this.add(labels[i], i % this.SQRTSIZE, i / this.SQRTSIZE);
        }
    }

    public void deleteNote(int note) {
        this.notes.delete(note);
        this.labels[note - 1].setText("");
    }

    public void deleteAllNote() {
        this.notes.deleteAll();
        for (Label label : this.labels) {
            label.setText("");
        }
    }

    public void addNote(int note) {
        this.notes.add(note);
        this.labels[note - 1].setText(String.valueOf(note));
    }

    public void update() {
        for (int i = 0; i < this.SIZE; i++) {
            if (this.notes.isPresent(i + 1)) {
                labels[i].setText(String.valueOf(i + 1));
            } else {
                labels[i].setText(" ");
            }
        }
    }

    public Notes getNotes() {
        return this.notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}
