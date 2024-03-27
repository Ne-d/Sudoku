package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.prepro.model.Grid;
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

    @FXML
    Label executionTime;

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
                case V -> {
                    this.notesOrValue = false;
                    this.updateMode();
                }
                case N -> {
                    this.notesOrValue = true;
                    this.updateMode();
                }
                default -> {
                }
            }

            if (pressedNumber > 0 && this.notesOrValue) { // If we are in note editing mode.
                CellView selectedCellView = this.gridView.getCellView(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow());
                NotesView notesView = selectedCellView.getNotesView();

                // If the desired note is present and there is more than one note in the cell.
                if (notesView.getNotes().isPresent(pressedNumber) && notesView.getNotes().getNumber() > 1) {
                    // Delete note from the model.
                    notesView.deleteNote(pressedNumber);

                    // Delete note from the view.
                    gridView.getGrid().deleteNote(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow(), pressedNumber);

                    this.gridView.update();
                    updateValidity();
                } else {
                    // Add note to the model.
                    notesView.addNote(pressedNumber);

                    // Add note to the view.
                    gridView.getGrid().addNote(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow(), pressedNumber);

                    this.gridView.update();
                    updateValidity();
                }
            }
            if (pressedNumber > 0 && !this.notesOrValue) { // If we are in value editing mode.
                CellView selectedCellView = this.gridView.getCellView(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow());
                NotesView notesView = selectedCellView.getNotesView();

                gridView.getGrid().addValue(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow(), pressedNumber);

                this.gridView.update();
                updateValidity();
            }
        });

        this.addKeyboardNavigation();
        this.gridView.setSelectedCell(0, 0);
        this.updateValidity();
        this.updateMode();
    }

    @FXML
    public void solveAction() {
        long startTime = System.currentTimeMillis();
        System.out.println("Solving the grid...");
        Solver.solve(this.gridView.getGrid());
        long endTime = System.currentTimeMillis();
        long longTime = endTime - startTime;
        this.executionTime.setText("Execution time : " + Long.toString(longTime) + " ms");
        this.gridView.update();
        System.out.println("Grid solved.");
        this.updateValidity();
        this.updateMode();
    }

    @FXML
    public void resetAction() {
        gridView.resetToStartingGrid();
        System.out.println("Grid reset");
        this.gridView.setSelectedCell(0, 0);
        this.executionTime.setText("Execution time : 0 ms");
        this.updateValidity();
        this.updateMode();
    }

    @FXML
    public void openGrid() {
        try {
            FileChooser fileChooser = new FileChooser();
            this.gridView.loadGrid(gridView.loadGridFromFile(fileChooser.showOpenDialog(this.stage).getPath()));
            this.updateValidity();
            this.updateMode();
            this.gridView.setSelectedCell(0, 0);
        } catch (Exception exception) {
            System.err.println("Error on openGrid :" + exception.getMessage());
        }
    }

    @FXML
    public void createNewGrid() {
        this.gridView.setGrid(new Grid());
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
        if (this.notesOrValue) {
            this.mode.setText("Selected Mode : Note");
        } else {
            this.mode.setText("Selected Mode : Value");
        }
    }

    /**
     * Allow to navigate with the zqsd keys in the grid
     */
    public void addKeyboardNavigation() {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case Z -> {
                    if (this.gridView.getSelectedRow() > 0) {
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow() - 1);
                    }
                }
                case S -> {
                    if (this.gridView.getSelectedRow() < this.gridView.getGrid().SIZE - 1) {
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn(), this.gridView.getSelectedRow() + 1);
                    }
                }
                case Q -> {
                    if (this.gridView.getSelectedColumn() > 0) {
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn() - 1, this.gridView.getSelectedRow());
                    }
                }
                case D -> {
                    if (this.gridView.getSelectedColumn() < this.gridView.getGrid().SIZE - 1) {
                        this.gridView.setSelectedCell(this.gridView.getSelectedColumn() + 1, this.gridView.getSelectedRow());
                    }
                }
            }
        });
    }

    @FXML
    public void help() {
        Stage helpDialog = new Stage();
        BorderPane helpPane = new BorderPane();
        Scene helpScene = new Scene(helpPane);
        helpDialog.setScene(helpScene);
        Label helpMessage = new Label("""
                Keybindings :
                - Z = UP
                - Q = LEFT
                - S = DOWN
                - D = RIGHT
                - N = Switch to Note mode
                - V = Switch to Value Mode
                """);
        helpMessage.setPadding(new Insets(20));
        helpPane.setCenter(helpMessage);
        helpDialog.initOwner(stage);
        helpDialog.initModality(Modality.APPLICATION_MODAL);
        helpDialog.showAndWait();
    }

    @FXML
    public void about() {
        Stage aboutDialog = new Stage();
        BorderPane aboutPane = new BorderPane();
        Scene aboutScene = new Scene(aboutPane);
        aboutDialog.setScene(aboutScene);
        Label aboutMessage = new Label("""
                Created by Maitrehenry Nathanael, Chevalier Jean-Michel, Denis Baptiste & Vessella Emilie
                For educational purposes
                For University of Poitiers
                """);
        aboutMessage.setPadding(new Insets(20));
        aboutPane.setCenter(aboutMessage);
        aboutDialog.initOwner(stage);
        aboutDialog.initModality(Modality.APPLICATION_MODAL);
        aboutDialog.showAndWait();
    }

    @FXML
    public void quit() {
        exit(0);
    }
}

