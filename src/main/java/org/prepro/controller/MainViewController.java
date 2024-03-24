package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.prepro.model.solver.Solver;
import org.prepro.view.GridView;

import java.io.IOException;

import static java.lang.System.exit;

public class MainViewController {
    private Stage stage;

    @FXML
    GridView gridView;
    @FXML
    Label statut;

    public void setupStageEventHandlers(Stage stage) {
        this.stage = stage;
        gridView.setupStageEventHandlers(stage);
        this.addKeyboardNavigation();
        this.gridView.setSelectedCell(0,0);
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
    public void openGrid() throws IOException {
        FileChooser fileChooser = new FileChooser();
        this.gridView.loadGrid(gridView.loadGridFromFile(fileChooser.showOpenDialog(this.stage).getPath()));
        this.updateValidity();
    }

    @FXML
    public void saveGrid() throws IOException {
        FileChooser fileChooser = new FileChooser();
        this.gridView.saveGridToFile(fileChooser.showSaveDialog(this.stage).getPath());
    }

    public void updateValidity() {
        if (this.gridView.getGrid().isValid()) {
            statut.setText("The selected grid is valid.");
            statut.setTextFill(Color.GREEN);
        } else {
            statut.setText("The selected grid is not valid.");
            statut.setTextFill(Color.RED);
        }
    }

    /**
     * Allow to navigate with the zqsd keys in the grid
     */
    public void addKeyboardNavigation(){
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                switch (event.getCode()) {
                    case Z -> {if (this.gridView.getSelectedRow() > 0){
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow() - 1);
                    }}
                    case S -> {if (this.gridView.getSelectedRow() < this.gridView.getGrid().SIZE - 1){
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow() + 1);
                    }}
                    case Q -> {if (this.gridView.getSelectedColumn() > 0){
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn() - 1, this.gridView.getSelectedRow());
                    }}
                    case D -> {if (this.gridView.getSelectedColumn() < this.gridView.getGrid().SIZE - 1){
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn() + 1, this.gridView.getSelectedRow());
                    }}
                }
        });
    }

    @FXML
    public void quit(){
        exit(0);
    }
}

