package org.prepro.view;

import javafx.scene.layout.GridPane;

public class GridView extends GridPane {
    public GridView() {
        this.setHgap(20);
        this.setVgap(20);

        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                this.add(new CellView(x + ", " + y), x, y);
            }
        }
    }
}
