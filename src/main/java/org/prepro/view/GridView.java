package org.prepro.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.prepro.model.Notes;

import static java.lang.Math.sqrt;

public class GridView extends GridPane {
    private final int SIZE = 9;
    private final int SQRTSIZE = (int) sqrt(this.SIZE);

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

        final int TOTAL_SIZE = this.SIZE + (SQRTSIZE - 1);
        for(int x = 0; x < TOTAL_SIZE; x++) {
            for(int y = 0; y < TOTAL_SIZE; y++) {
                if(x % (SQRTSIZE + 1) == SQRTSIZE && x != 0) {
                    this.add(new Separator(Orientation.VERTICAL), x, y);
                }
                else if(y % (SQRTSIZE + 1) == SQRTSIZE && y != 0) {
                    this.add(new Separator(Orientation.HORIZONTAL), x , y);
                }
                else {
                    Notes notes = new Notes();
                    notes.delete(1);
                    notes.delete(2);
                    notes.delete(3);
                    notes.delete(4);
                    notes.delete(5);
                    notes.delete(6);
                    notes.delete(7);
                    notes.delete(9);

                    this.add(new CellView(notes, 5, 9), x, y);
                }
            }
        }
    }
}
