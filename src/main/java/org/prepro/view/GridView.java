package org.prepro.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.prepro.model.Grid;
import org.prepro.model.Notes;

public class GridView extends GridPane {
    private Grid grid;
    private final CellView[][] cellViews;
    private CellView selectedCellView;

    public GridView() {
        this.setAlignment(Pos.CENTER);

        //this.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0))));
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,
                new CornerRadii(0),
                new BorderWidths(2),
                new Insets(0))));

        this.setHgap(2);
        this.setVgap(2);

        this.grid = testGrid();

        grid.print();
        grid.printWithNotes();


        // Size of the visual grid including separators (2 separators for a 9x9 grid).
        final int TOTAL_SIZE = /*this.SIZE + (SQRTSIZE - 1);*/ grid.SIZE;
        int gridX = 0;
        int gridY = 0;

        cellViews = new CellView[grid.SIZE][grid.SIZE];

        for (int y = 0; y < TOTAL_SIZE; y++) {
            for (int x = 0; x < TOTAL_SIZE; x++) {

                /*
                if(x % (SQRTSIZE + 1) == SQRTSIZE && x != 0) {
                    this.add(new Separator(Orientation.VERTICAL), x, y);
                }

                else if(y % (SQRTSIZE + 1) == SQRTSIZE && y != 0) {
                    this.add(new Separator(Orientation.HORIZONTAL), x , y);
                }
                */


                Notes notes = grid.getNotes(x, y);
                int value = grid.getVal(x, y);

                CellView currentCellView = new CellView(notes, value, grid.SIZE, x, y, this);
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
                NotesView notesView = this.selectedCellView.getNotesView();
                if (notesView.getNotes().isPresent(pressedNumber) && notesView.getNotes().getNumber() > 1) {
                    notesView.deleteNote(pressedNumber);
                } else {
                    notesView.addNote(pressedNumber);
                }
                selectedCellView.update();
            }
        });
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

    public void setSelectedCell(CellView cellView) {
        this.selectedCellView = cellView;
    }
}
