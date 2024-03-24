package org.prepro.view;

import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.prepro.model.Grid;
import org.prepro.model.GridExemple;
import org.prepro.model.Notes;

import java.io.*;

public class GridView extends GridPane {
    private Grid grid;
    private Grid startingGrid;
    private CellView[][] cellViews;
    private int selectedColumn = 0;
    private int selectedRow = 0;
    /**
     * if true that means notes are selected else value are selected
     */
    private boolean notesOrValue = false;

    public GridView() {
        this.setAlignment(Pos.CENTER);

        this.setHgap(2);
        this.setVgap(2);

        loadGrid(testGrid());
    }

    public void loadGrid(Grid grid) {
        this.grid = grid;

        // We use the copy constructor to make a new grid that won't be a reference to the old one.
        this.startingGrid = new Grid(grid);
        this.cellViews = new CellView[grid.SIZE][grid.SIZE];
        int cellSize = 60;

        for (int y = 0; y < this.grid.SIZE; y++) {
            for (int x = 0; x < this.grid.SIZE; x++) {
                this.getChildren().remove(cellViews[x][y]);
                Notes notes = this.grid.getNotes(x, y);
                int value = this.grid.getVal(x, y);

                CellView currentCellView = new CellView(notes, value, this.grid.SIZE, x, y, this, cellSize);
                this.add(currentCellView, x, y);
                cellViews[x][y] = currentCellView;
            }
        }

        // Ajouter les bordures des boîtes (3x3)
        for (int i = 0; i < grid.SQRTSIZE; i++) {
            for (int j = 0; j < grid.SQRTSIZE; j++) {
                int size = (int) (this.grid.SQRTSIZE * cellSize + (grid.SQRTSIZE-1)*this.getHgap());
                Rectangle cellBorder = new Rectangle(size, size);
                cellBorder.setFill(null);
                cellBorder.setStroke(Color.BLACK);
                this.add(cellBorder, j * grid.SQRTSIZE, i * grid.SQRTSIZE,
                        grid.SQRTSIZE, grid.SQRTSIZE);
            }
        }
    }

    public void setupStageEventHandlers(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int pressedNumber = -1;
            switch (event.getCode()) {
                case NUMPAD0, DIGIT0 -> pressedNumber = 0;
                case NUMPAD1, DIGIT1 -> pressedNumber = 1;
                case NUMPAD2, DIGIT2 -> pressedNumber = 2;
                case NUMPAD3, DIGIT3 -> pressedNumber = 3;
                case NUMPAD4, DIGIT4 -> pressedNumber = 4;
                case NUMPAD5, DIGIT5 -> pressedNumber = 5;
                case NUMPAD6, DIGIT6 -> pressedNumber = 6;
                case NUMPAD7, DIGIT7 -> pressedNumber = 7;
                case NUMPAD8, DIGIT8 -> pressedNumber = 8;
                case NUMPAD9, DIGIT9 -> pressedNumber = 9;
                case V -> this.notesOrValue = false;
                case N -> this.notesOrValue = true;
                default -> {
                }
            }

            if (pressedNumber > 0 && this.notesOrValue) {
                CellView selectedCellView = this.cellViews[this.selectedColumn][this.selectedRow];
                NotesView notesView = selectedCellView.getNotesView();

                if (notesView.getNotes().isPresent(pressedNumber) && notesView.getNotes().getNumber() > 1) {
                    notesView.deleteNote(pressedNumber);
                    this.grid.deleteNote(this.selectedColumn, this.selectedRow, pressedNumber);
                } else {
                    notesView.addNote(pressedNumber);
                    this.grid.addNote(this.selectedColumn, this.selectedRow, pressedNumber);
                }

                selectedCellView.update();
            }
            if (pressedNumber > 0 && !this.notesOrValue) {
                CellView selectedCellView = this.cellViews[this.selectedColumn][this.selectedRow];
                NotesView notesView = selectedCellView.getNotesView();

                notesView.deleteAllNote();
                notesView.addNote(pressedNumber);
                this.grid.addNote(this.selectedColumn, this.selectedRow, pressedNumber);

                selectedCellView.update();
            }
        });
    }

    public void update() {
        for (int y = 0; y < this.grid.SIZE; y++) {
            for (int x = 0; x < this.grid.SIZE; x++) {
                Notes notes = this.grid.getNotes(x, y);
                int value = this.grid.getVal(x, y);

                cellViews[x][y].setNotes(notes);
                cellViews[x][y].setValue(value);
            }
        }
    }

    /**
     * Load a grid from a file.
     *
     * @param filePath The path to the file to read.
     * @return The grid that has been loaded.
     * @throws IOException If the file cannot be read.
     */
    public Grid loadGridFromFile(String filePath) throws IOException {
        Grid grid = new Grid();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        int valueRead;
        int row = 0;
        int column = 0;
        while ((valueRead = reader.read()) != -1) {
            if (valueRead == '\n') {
                row++;
                column = 0;
                continue;
            }

            if (valueRead != ' ')
                grid.addValue(column, row, Character.getNumericValue((char) valueRead));

            column++;
        }

        reader.close();

        return grid;
    }

    public void saveGridToFile(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        for (int y = 0; y < this.grid.SIZE; y++) {
            for (int x = 0; x < this.grid.SIZE; x++) {
                int val = grid.getVal(x, y);

                if (val != 0)
                    writer.write(Integer.toString(val));
                else
                    writer.write(' ');
            }
            writer.write('\n');
        }

        writer.close();
    }

    public Grid testGrid() {
        Grid grid1 = GridExemple.grid1.getGrid();

        return grid1;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void resetToStartingGrid() {
        this.loadGrid(startingGrid);
        //this.update();
    }

    public int getSelectedColumn(){
        return selectedColumn;
    }

    public int getSelectedRow(){
        return selectedRow;
    }

    public void setSelectedCell(int column, int row) {
        this.cellViews[this.selectedColumn][this.selectedRow].setStyle("-fx-background-color:lightgray");
        this.selectedColumn = column;
        this.selectedRow = row;
        this.cellViews[column][row].setStyle("-fx-background-color:#FFCCFF");
    }
}
