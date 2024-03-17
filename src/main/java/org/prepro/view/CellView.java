package org.prepro.view;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.prepro.model.Notes;

public class CellView extends StackPane {
    private final int SIZE;
    private final int COLUMN;
    private final int ROW;

    private ValueView valueView;
    private NotesView notesView;
    private int value;
    private Notes notes;

    public CellView(Notes notes, int value, int size, int column, int row, GridView gridView) {
        this.SIZE = size;
        this.COLUMN = column;
        this.ROW = row;
        this.notes = notes;

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))));

        setupNotes(this.notes);
        setupValue(value);

        this.getChildren().addAll(valueView, notesView);
        this.update();

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> gridView.setSelectedCell(this.COLUMN, this.ROW));
    }

    private void setupNotes(Notes notes) {
        this.notesView = new NotesView(notes, this.SIZE);
    }

    private void setupValue(int value) {
        this.value = value;
        this.valueView = new ValueView(value);
    }

    private void showValue() {
        this.valueView.setVisible(true);
        this.notesView.setVisible(false);
    }

    private void showNotes() {
        this.valueView.setVisible(false);
        this.notesView.setVisible(true);
    }

    public void update() {
        if (notes.getNumber() == 1) {
            this.value = this.notes.getUniqueNote();
            valueView.setValue(this.value);
            showValue();
        }

        if (notes.getNumber() == 0) {
            valueView.setValue(this.value);
            showValue();
        }

        if (notes.getNumber() > 1) {
            showNotes();
        }
    }

    public void setValue(int value) {
        this.value = value;
        update();
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        this.getNotesView().setNotes(notes);
        update();
    }

    public NotesView getNotesView() {
        return this.notesView;
    }

    public ValueView getValueView() {
        return this.valueView;
    }
}
