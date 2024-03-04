package org.prepro.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainView extends AnchorPane {
    public MainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        loader.setRoot(this);
        loader.load();
    }
}
