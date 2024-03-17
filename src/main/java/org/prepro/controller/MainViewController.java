package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.prepro.model.solver.Solver;
import org.prepro.view.GridView;

public class MainViewController {

    @FXML
    GridView gridView;

    public void setupStageEventHandlers(Stage stage) {
        gridView.setupStageEventHandlers(stage);
    }

    @FXML
    public void solveAction() {
        System.out.println("Solving...");
        Solver.solve(this.gridView.getGrid());
        this.gridView.update();
        System.out.println("Grid solved.");
    }

    @FXML
    public void resetAction() {
        gridView.resetToStartingGrid();
        System.out.println("Grid reset");
    }
}

