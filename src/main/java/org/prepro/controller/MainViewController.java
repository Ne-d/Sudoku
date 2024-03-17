package org.prepro.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.prepro.model.Grid;
import org.prepro.model.GridExemple;
import org.prepro.model.solver.Solver;
import org.prepro.view.GridView;

public class MainViewController {
    @FXML
    public Label gridValid;
    @FXML
    GridView gridView;
    @FXML
    ComboBox<GridExemple> open;
    @FXML
    public void initialize(){
        ObservableList<GridExemple> gridlist = FXCollections.observableArrayList(GridExemple.values());
        open.setItems(gridlist);
        actualize();
    }

    /**
     * actualize the gridview and the label
     */
    private void actualize(){
        Grid grid = gridView.getGrid();
        gridView.actualize();
        System.out.println(gridValid.getText());
        gridValid.setText("la grille est " + (grid.isValid() ?"valide": "invalide"));
    }
    @FXML
    public void openAction() {
        GridExemple gridChoisi = open.getValue();
        gridView.selectGridExemple(open.getValue());
        gridView.getStartingGrid();
        this.actualize();
        System.out.println("Vous avez choisi : " + gridChoisi);
    }
    @FXML
    public void solveAction() {
        Grid grid = gridView.getGrid();
        Solver.solve(grid);
        this.actualize();
        System.out.println("solve");
    }

    @FXML
    public void resetAction() {
        gridView.getStartingGrid();
        this.actualize();
        System.out.println("reset");
    }
}
