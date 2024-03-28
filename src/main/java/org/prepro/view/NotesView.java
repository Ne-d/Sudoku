package org.prepro.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.prepro.model.Notes;

import static java.lang.Math.sqrt;

/**
 * The visual representation of the notes of a cell in a sudoku grid.
 */
public class NotesView extends GridPane {
    /**
     * The size of the grid, which is also the amount of possible notes.
     */
    private final int SIZE;

    /**
     * An array of labels, each representing one note of the cell.
     */
    private final Label[] labels;

    /**
     * The notes of the cell.
     */
    private Notes notes;

    /**
     * Create a new NotesView.
     *
     * @param notes The notes to put in the NotesView.
     * @param size  The size of the grid, which is also the amount of possible notes.
     */
    public NotesView(Notes notes, int size) {
        this.setAlignment(Pos.CENTER);

        this.SIZE = size;
        final int SQRTSIZE = (int) sqrt(size);

        this.notes = notes;

        labels = new Label[this.SIZE];

        // For every possible note in the cell.
        for (int i = 0; i < this.SIZE; i++) {
            // Create a new label to represent the note.
            labels[i] = new Label("");
            labels[i].setPrefHeight(15);
            labels[i].setPrefWidth(15);
            labels[i].setAlignment(Pos.CENTER);

            // If the note is present, set teh corresponding label's text to the value of the note.
            if (this.notes.isPresent(i + 1)) {
                labels[i].setText(String.valueOf(i + 1));
            }

            // Add the label to the grid.
            this.add(labels[i], i % SQRTSIZE, i / SQRTSIZE);
        }
    }

    /**
     * Delete a note from the cell.
     *
     * @param note The note to delete.
     */
    public void deleteNote(int note) {
        this.notes.delete(note);
        this.labels[note - 1].setText("");
    }

    /**
     * Delete all notes from the cell.
     */
    public void deleteAllNote() {
        this.notes.deleteAll();
        for (Label label : this.labels) {
            label.setText("");
        }
    }

    /**
     * Add a note to the cell.
     *
     * @param note The note to add.
     */
    public void addNote(int note) {
        this.notes.add(note);
        this.labels[note - 1].setText(String.valueOf(note));
    }

    /**
     * Update the visual representation of the cell to match the notes.
     */
    public void update() {
        for (int i = 0; i < this.SIZE; i++) {
            if (this.notes.isPresent(i + 1)) {
                labels[i].setText(String.valueOf(i + 1));
            } else {
                labels[i].setText(" ");
            }
        }
    }

    /**
     * Get the notes of this cell.
     *
     * @return The notes of this cell.
     */
    public Notes getNotes() {
        return this.notes;
    }

    /**
     * Sets the notes of this cell.
     *
     * @param notes The notes to put in this cell.
     */
    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}
