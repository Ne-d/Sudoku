package org.prepro.controller;

import javafx.fxml.FXML;
import org.prepro.model.Grid;
import org.prepro.model.solver.Solver;
import org.prepro.view.GridView;

public class MainViewController {
    @FXML
    GridView gridView;

    @FXML
    public void solveAction(){
        Grid grid = gridView.getGrid();
        Solver.solve(grid);
        gridView.actualize();
        System.out.println("solve");
    }

    @FXML
    public void resetAction(){
        gridView.getStartingGrid();
        gridView.actualize();
        System.out.println("reset");
    }
}
