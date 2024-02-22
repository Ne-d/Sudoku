package org.prepro.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.prepro.view.GridView;

public class MainViewController {
    @FXML
    BorderPane root;

    public void initialize() {
        this.root.setCenter(new GridView());
    }
}
