package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.prepro.view.GridView;

public class MainViewController {
    @FXML
    GridView grid;

    public void setupStageEventHandlers(Stage stage) {
        grid.setupStageEventHandlers(stage);
    }
}
