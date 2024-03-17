package org.prepro.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.prepro.model.Grid;
import org.prepro.model.GridExemple;
import org.prepro.model.Notes;

public class GridView extends GridPane {
    private GridExemple gridStart;
    private Grid grid;
    public Grid getGrid() {
        return this.grid;
    }

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

        selectGridExemple();
        this.grid = this.getStartingGrid();

        this.actualize();
    }

    /**
     * actualize the gridView
     */
    public void actualize(){
//        this.grid.print();
//        this.grid.printWithNotes();

        // Size of the visual grid including separators (2 separators for a 9x9 grid).
        final int TOTAL_SIZE = /*this.SIZE + (SQRTSIZE - 1);*/ this.grid.SIZE;

        for(int y = 0; y < TOTAL_SIZE; y++) {
            for(int x = 0; x < TOTAL_SIZE; x++) {

                /*
                if(x % (SQRTSIZE + 1) == SQRTSIZE && x != 0) {
                    this.add(new Separator(Orientation.VERTICAL), x, y);
                }

                else if(y % (SQRTSIZE + 1) == SQRTSIZE && y != 0) {
                    this.add(new Separator(Orientation.HORIZONTAL), x , y);
                }
                */

                Notes notes = this.grid.getNotes(x, y);
                int value = this.grid.getVal(x, y);

                this.add(new CellView(notes, value, this.grid.SIZE), x, y);
            }
        }
    }

    /**
     * roll back the grid to the starting grid
     * @return the starting grid before all change
     */
    public Grid getStartingGrid(){
        Grid start = gridStart.getGrid();
        if (this.grid != null){
            this.grid = start;
        }
        return start;
    }
    public void selectGridExemple( GridExemple grid){
        this.gridStart = grid;
    }
    public void selectGridExemple(){
        this.gridStart = GridExemple.grid1;
    }
}
