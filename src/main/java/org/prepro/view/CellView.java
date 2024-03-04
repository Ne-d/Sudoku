package org.prepro.view;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.prepro.model.Notes;

public class CellView extends StackPane {
    private final int SIZE;
    private ValueCellView valueView;
    private NotesCellView notesView;

    private final Notes notes;

    public CellView(Notes notes, int value, int size) {
        this.SIZE = size;
        this.notes = notes;

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))));

        setupNotes(this.notes);
        setupValue(value);

        this.getChildren().addAll(valueView, notesView);
        this.update();
    }

    private void setupNotes(Notes notes) {
        this.notesView = new NotesCellView(notes, this.SIZE);
    }

    private void setupValue(int value) {
        this.valueView = new ValueCellView(value);
    }

    private void showValue() {
        System.out.println("Showing value.");
        this.valueView.setVisible(true);
        this.notesView.setVisible(false);
    }

    private void showNotes() {
        System.out.println("Showing notes.");
        this.valueView.setVisible(false);
        this.notesView.setVisible(true);
    }

    public void update() {
        System.out.println("Number of notes: " + notes.getNumber());
        if(notes.getNumber() == 1) {
            try {
                int newValue = this.notes.getUniqueNote();
                valueView.setValue(newValue);
            }
            catch (Notes.MultipleNotesException e) {
                e.printStackTrace();
            }
            showValue();
        }
        else {
            showNotes();
        }
    }
    
}
