package org.prepro.view;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.prepro.model.Notes;

/**
 * A pane that represents a cell in the visual representation of a sudoku grid.
 */
public class CellView extends StackPane {
    /**
     * The size of the grid.
     */
    private final int SIZE;

    /**
     * The column this cell is in.
     */
    private final int COLUMN;

    /**
     * The row this cell is in.
     */
    private final int ROW;

    /**
     * The ValueView for this cell.
     */
    private ValueView valueView;

    /**
     * The NotesView for this cell.
     */
    private NotesView notesView;

    /**
     * The value this cell holds.
     */
    private int value;

    /**
     * The notes this cell holds.
     */
    private Notes notes;

    /**
     * Creates a new CellView.
     *
     * @param notes    The notes this CellView will hold.
     * @param value    The value this CellView will hold.
     * @param size     The size of the grid this CellView is in.
     * @param column   The column this CellView is in.
     * @param row      The row this CellView is in.
     * @param gridView The GridView this CellView is a part of.
     * @param cellSize The displayed size of a cell in pixels.
     */
    public CellView(Notes notes, int value, int size, int column, int row, GridView gridView, int cellSize) {
        this.SIZE = size;
        this.COLUMN = column;
        this.ROW = row;
        this.notes = notes;

        this.setMinSize(cellSize, cellSize);
        this.setMaxSize(cellSize, cellSize);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), new Insets(0))));

        setupNotes(this.notes);
        setupValue(value);

        this.getChildren().addAll(valueView, notesView);
        this.update();

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> gridView.setSelectedCell(this.COLUMN, this.ROW));
    }

    /**
     * Sets up the notes of the NotesView.
     *
     * @param notes The notes to put in the NotesView.
     */
    private void setupNotes(Notes notes) {
        this.notesView = new NotesView(notes, this.SIZE);
    }

    /**
     * Set up the value of the ValueView.
     *
     * @param value The value to put in the ValueView.
     */
    private void setupValue(int value) {
        this.value = value;
        this.valueView = new ValueView(value);
    }

    /**
     * Shows the value and hides the notes
     */
    private void showValue() {
        this.valueView.setVisible(true);
        this.notesView.setVisible(false);
    }

    /**
     * Shows the notes and hides the value
     */
    private void showNotes() {
        this.valueView.setVisible(false);
        this.notesView.setVisible(true);
    }

    /**
     * Update the view of a cell
     */
    public void update() {
        // If there is only one note (a value has been found)
        if (notes.getNumber() == 1) {
            this.value = this.notes.getUniqueNote();
            valueView.setValue(this.value);
            showValue();
        }

        // If there is no note, this should never happen.
        if (notes.getNumber() == 0) {
            System.out.println("CellView.update ERROR: The cell has no notes.");
            valueView.setValue(this.value);
            showValue();
        }

        // If there are several notes
        if (notes.getNumber() > 1) {
            this.notesView.setNotes(notes);
            this.notesView.update();
            showNotes();
        }
    }

    /**
     * Updates this cell's value.
     *
     * @param value The new value for this cell.
     */
    public void setValue(int value) {
        this.value = value;
        update();
    }

    /**
     * Updates this cell's notes.
     *
     * @param notes The new notes for this cell.
     */
    public void setNotes(Notes notes) {
        this.notes = notes;
        this.getNotesView().setNotes(notes);
        update();
    }

    /**
     * Gets the NotesView this cell holds.
     *
     * @return Gets the NotesView this cell holds.
     */
    public NotesView getNotesView() {
        return this.notesView;
    }

    /**
     * Gets the ValueView this cell holds.
     *
     * @return The ValueView this cell holds.
     */
    public ValueView getValueView() {
        return this.valueView;
    }
}
