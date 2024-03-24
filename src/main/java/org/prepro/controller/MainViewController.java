package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.prepro.model.solver.Solver;
import org.prepro.view.CellView;
import org.prepro.view.GridView;
import org.prepro.view.NotesView;

import java.io.IOException;

import static java.lang.System.exit;

public class MainViewController {
    private Stage stage;

    @FXML
    GridView gridView;
    @FXML
    Label statut;
    @FXML
    Label mode;
    /**
     * if true that means notes are selected else value are selected
     */
    private boolean notesOrValue = false;

    public void setupStageEventHandlers(Stage stage) {
        this.stage = stage;

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int pressedNumber = -1;
            switch (event.getCode()) {
                case NUMPAD0, DIGIT0 -> pressedNumber = 0;
                case NUMPAD1, DIGIT1 -> pressedNumber = 1;
                case NUMPAD2, DIGIT2 -> pressedNumber = 2;
                case NUMPAD3, DIGIT3 -> pressedNumber = 3;
                case NUMPAD4, DIGIT4 -> pressedNumber = 4;
                case NUMPAD5, DIGIT5 -> pressedNumber = 5;
                case NUMPAD6, DIGIT6 -> pressedNumber = 6;
                case NUMPAD7, DIGIT7 -> pressedNumber = 7;
                case NUMPAD8, DIGIT8 -> pressedNumber = 8;
                case NUMPAD9, DIGIT9 -> pressedNumber = 9;
                case V -> {this.notesOrValue = false;
                    this.updateMode();}
                case N -> {this.notesOrValue = true;
                    this.updateMode();}
                default -> {
                }
            }

            if (pressedNumber > 0 && this.notesOrValue) {
                CellView selectedCellView = this.gridView.getCellView(this.gridView.getSelectedColumn(),this.gridView.getSelectedRow());
                NotesView notesView = selectedCellView.getNotesView();

                if (notesView.getNotes().isPresent(pressedNumber) && notesView.getNotes().getNumber() > 1) {
                    notesView.deleteNote(pressedNumber);
                    gridView.getGrid().deleteNote(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow(), pressedNumber);
                } else {
                    notesView.addNote(pressedNumber);
                    gridView.getGrid().addNote(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow(), pressedNumber);
                }

                selectedCellView.update();
            }
            if (pressedNumber > 0 && !this.notesOrValue) {
                CellView selectedCellView = this.gridView.getCellView(this.gridView.getSelectedColumn(),this.gridView.getSelectedRow());
                NotesView notesView = selectedCellView.getNotesView();

                notesView.deleteAllNote();
                notesView.addNote(pressedNumber);
                gridView.getGrid().addNote(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow(), pressedNumber);

                selectedCellView.update();
            }
        });
        
        this.addKeyboardNavigation();
        this.gridView.setSelectedCell(0,0);
        this.updateValidity();
        this.updateMode();
    }

    @FXML
    public void solveAction() {
        System.out.println("Solving the grid...");
        Solver.solve(this.gridView.getGrid());
        this.gridView.update();
        System.out.println("Grid solved.");
        this.updateValidity();
        this.updateMode();
    }

    @FXML
    public void resetAction() {
        gridView.resetToStartingGrid();
        System.out.println("Grid reset");
        this.gridView.setSelectedCell(0,0);
        this.updateValidity();
        this.updateMode();
    }

    @FXML
    public void openGrid() throws IOException {
        FileChooser fileChooser = new FileChooser();
        this.gridView.loadGrid(gridView.loadGridFromFile(fileChooser.showOpenDialog(this.stage).getPath()));
        this.updateValidity();
        this.updateMode();
        this.gridView.setSelectedCell(0,0);
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

    @FXML
    public void updateMode() {
        if(this.notesOrValue){
            this.mode.setText("Selected Mode : Note");
        }
        else {
            this.mode.setText("Selected Mode : Value");
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

