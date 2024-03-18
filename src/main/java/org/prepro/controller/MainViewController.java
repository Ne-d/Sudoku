package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.prepro.model.solver.Solver;
import org.prepro.view.GridView;

import java.io.IOException;

public class MainViewController {

    @FXML
    GridView gridView;
    @FXML
    Label statut;

    public void setupStageEventHandlers(Stage stage) {
        gridView.setupStageEventHandlers(stage);
        updateValidity();
    }

    @FXML
    public void solveAction() {
        System.out.println("Solving the grid...");
        Solver.solve(this.gridView.getGrid());
        this.gridView.update();
        System.out.println("Grid solved.");
        updateValidity();
    }

    @FXML
    public void resetAction() {
        gridView.resetToStartingGrid();
        System.out.println("Grid reset");
    }

    @FXML
    public void testOpenGrid() throws IOException {
        this.gridView.loadGrid(gridView.loadGridFromFile("C:\\Users\\FunRizZ\\Desktop\\test\\gridtest2.txt"));
    }
    public void updateValidity(){
        if(this.gridView.getGrid().isValid()){
            statut.setText("The selected grid is valid.");
            statut.setTextFill(Color.GREEN);
        }
        else {
            statut.setText("The selected grid is not valid.");
            statut.setTextFill(Color.RED);
        }
    }
}

