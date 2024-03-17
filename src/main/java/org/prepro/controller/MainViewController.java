package org.prepro.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.prepro.model.Grid;
import org.prepro.model.GridExemple;
import org.prepro.model.solver.Solver;
import org.prepro.view.GridView;

public class MainViewController {
    @FXML
    GridView gridView;
    @FXML
    ComboBox<GridExemple> open;
    @FXML
    public void initialize(){
        ObservableList<GridExemple> gridlist = FXCollections.observableArrayList(GridExemple.values());
        open.setItems(gridlist);
    }

    @FXML
    public void solveAction() {
        Grid grid = gridView.getGrid();
        Solver.solve(grid);
        gridView.actualize();
        System.out.println("solve");
    }

    @FXML
    public void resetAction() {
        gridView.getStartingGrid();
        gridView.actualize();
        System.out.println("reset");
    }

    @FXML
    public void openAction() {
        GridExemple gridChoisi = open.getValue();
        gridView.selectGridExemple(open.getValue());
        gridView.getStartingGrid();
        gridView.actualize();
        System.out.println("Vous avez choisi : " + gridChoisi);
    }
}
