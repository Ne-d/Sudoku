package org.prepro.view;

import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.prepro.model.Grid;
import org.prepro.model.Notes;

import java.io.*;

public class GridView extends GridPane {
    private Grid grid;
    private Grid startingGrid;
    private CellView[][] cellViews;
    private int selectedColumn;
    private int selectedRow;

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

        for (int y = 0; y < this.grid.SIZE; y++) {
            for (int x = 0; x < this.grid.SIZE; x++) {
                this.getChildren().remove(cellViews[x][y]);
                Notes notes = this.grid.getNotes(x, y);
                int value = this.grid.getVal(x, y);

                CellView currentCellView = new CellView(notes, value, this.grid.SIZE, x, y, this);
                this.add(currentCellView, x, y);
                cellViews[x][y] = currentCellView;
            }
        }
    }

    public void setupStageEventHandlers(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int pressedNumber = switch (event.getCode()) {
                case NUMPAD0 -> 0;
                case NUMPAD1 -> 1;
                case NUMPAD2 -> 2;
                case NUMPAD3 -> 3;
                case NUMPAD4 -> 4;
                case NUMPAD5 -> 5;
                case NUMPAD6 -> 6;
                case NUMPAD7 -> 7;
                case NUMPAD8 -> 8;
                case NUMPAD9 -> 9;
                default -> -1;
            };

            if (pressedNumber > 0) {
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
        Grid grid1 = new Grid();
        grid1.addValue(0, 0, 7); //line 1
        grid1.addValue(1, 0, 2);
        grid1.addValue(2, 0, 6);
        grid1.addValue(4, 0, 1);
        grid1.addValue(5, 0, 8);
        grid1.addValue(6, 0, 3);
        grid1.addValue(7, 0, 4);

        grid1.addValue(1, 1, 9); // line 2
        grid1.addValue(4, 1, 5);
        grid1.addValue(5, 1, 2);

        grid1.addValue(0, 2, 5); //line 3
        grid1.addValue(2, 2, 4);
        grid1.addValue(4, 2, 3);
        grid1.addValue(5, 2, 6);
        grid1.addValue(6, 2, 9);
        grid1.addValue(7, 2, 8);
        grid1.addValue(8, 2, 2);

        grid1.addValue(0, 3, 6); // line 4
        grid1.addValue(3, 3, 3);
        grid1.addValue(7, 3, 2);
        grid1.addValue(8, 3, 1);

        grid1.addValue(1, 4, 7); // line 5
        grid1.addValue(5, 4, 4);

        grid1.addValue(3, 5, 6); // line 6
        grid1.addValue(5, 5, 9);
        grid1.addValue(6, 5, 4);
        grid1.addValue(7, 5, 5);

        grid1.addValue(1, 6, 3); // line 7
        grid1.addValue(2, 6, 7);
        grid1.addValue(7, 6, 9);
        grid1.addValue(8, 6, 4);

        grid1.addValue(0, 7, 4); // line 8
        grid1.addValue(2, 7, 1);
        grid1.addValue(5, 7, 3);

        grid1.addValue(3, 8, 2); // line 9
        grid1.addValue(6, 8, 1);

        return grid1;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void resetToStartingGrid() {
        this.loadGrid(startingGrid);
        //this.update();
    }

    public void setSelectedCell(int column, int row) {
        this.cellViews[this.selectedColumn][this.selectedRow].setStyle("-fx-background-color:lightgray");
        this.selectedColumn = column;
        this.selectedRow = row;
        this.cellViews[column][row].setStyle("-fx-background-color:#FFCCFF");
    }
}
