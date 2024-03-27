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
import org.prepro.ExecutionTime;
import org.prepro.model.Grid;
import org.prepro.model.solver.Backtracking;
import org.prepro.model.solver.Solver;
import org.prepro.view.CellView;
import org.prepro.view.GridView;
import org.prepro.view.NotesView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.exit;

public class MainViewController {
    /**
     * The main stage of the application, used to add keyboard event handlers.
     */
    private Stage stage;

    /**
     * The visual representation of the grid.
     */
    @FXML
    private GridView gridView;

    /**
     * The label indicating the validity of the grid.
     */
    @FXML
    private Label validityLabel;

    /**
     * The label indicating the current editing mode (note or value).
     */
    @FXML
    private Label modeLabel;

    /**
     * The label indicating the execution time of the last solve operation.
     */
    @FXML
    private Label executionTimeLabel;

    /**
     * if true that means notes are selected else value are selected
     */
    private boolean notesOrValue = false;

    /**
     * Adds to the main window's stage the event handlers that need to be set there (keyboard events).
     *
     * @param stage The stage to add the event handlers to.
     */
    public void setupStageEventHandlers(Stage stage) {
        this.stage = stage;

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int pressedNumber = -1;

            // Get the key events.
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

    /**
     * Solves the current grid using heuristics, then backtracking.
     *
     * @throws NoSuchMethodException     If the method name given to measureNanosecond is incorrect.
     * @throws InvocationTargetException If the method given to measureNanosecond cannot be invoked.
     * @throws IllegalAccessException    If the method given to measureNanosecond is inaccessible.
     */
    @FXML
    public void solveAction() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Solving the grid using heuristics, then backtracking...");

        long nanoTime = ExecutionTime.measureNanosecond(
                null,
                Solver.class.getMethod("solve", Grid.class),
                this.gridView.getGrid()
        );
        System.out.println("Grid solved.");

        this.setExecutionTime(nanoTime);
        this.gridView.update();
        this.updateValidity();
        this.updateMode();
    }

    /**
     * Solves the current grid using only backtracking.
     *
     * @throws NoSuchMethodException     If the method name given to measureNanosecond is incorrect.
     * @throws InvocationTargetException If the method given to measureNanosecond cannot be invoked.
     * @throws IllegalAccessException    If the method given to measureNanosecond is inaccessible.
     */
    @FXML
    public void backtrackingAction() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Solving the grid, only using backtracking.");

        long nanoTime = ExecutionTime.measureNanosecond(
                null,
                Backtracking.class.getMethod("solve", Grid.class),
                this.gridView.getGrid()
        );

        System.out.println("Grid solved using only backtracking.");

        this.setExecutionTime(nanoTime);
        this.gridView.update();
        this.updateValidity();
        this.updateMode();
    }

    /**
     * Resets the grid to its default state (the last grid that was loaded).
     */
    @FXML
    public void resetAction() {
        gridView.resetToStartingGrid();
        System.out.println("Grid reset");

        this.gridView.setSelectedCell(0, 0);
        this.updateValidity();
        this.updateMode();
    }

    /**
     * Open a grid from a file, chosen through a File Chooser.
     */
    @FXML
    public void openAction() {
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

    /**
     * Sets the current grid to a new empty grid.
     */
    @FXML
    public void newAction() {
        this.gridView.setGrid(new Grid());
    }

    /**
     * Saves the current grid to a file, chosen through a File Chooser.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void saveGrid() throws IOException {
        FileChooser fileChooser = new FileChooser();
        this.gridView.saveGridToFile(fileChooser.showSaveDialog(this.stage).getPath());
    }

    /**
     * Updates the label which indicates the validity of the grid.
     */
    public void updateValidity() {
        if (this.gridView.getGrid().isValid()) {
            validityLabel.setText("The selected grid is valid.");
            validityLabel.setTextFill(Color.GREEN);
        } else {
            validityLabel.setText("The selected grid is not valid.");
            validityLabel.setTextFill(Color.RED);
        }
    }

    /**
     * Update the label which indicates the editing mode (note or value).
     */
    @FXML
    public void updateMode() {
        if (this.notesOrValue) {
            this.modeLabel.setText("Selected Mode : Note");
        } else {
            this.modeLabel.setText("Selected Mode : Value");
        }
    }

    /**
     * Sets the text of the label that indicates the execution time for solving operations.
     *
     * @param nanoTime The time in nanoseconds that the execution took.
     */
    public void setExecutionTime(long nanoTime) {
        long milliTime = nanoTime / 1_000_000;

        String nanoTimeFormatted = String.format("%,d", nanoTime);
        String milliTimeFormatted = String.format("%,d", milliTime);

        this.executionTimeLabel.setText("Execution time: " + milliTimeFormatted + " ms (" + nanoTimeFormatted + " ns).");
    }

    /**
     * Sets up keyboard navigation using z, q, s and d to select cells on the grid.
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

    /**
     * Shows a dialog window containing the program's keyboards shortcuts.
     */
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

    /**
     * Shows a dialog window that gives information about the project.
     */
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

    /**
     * Exits the application.
     */
    @FXML
    public void quit() {
        exit(0);
    }
}
