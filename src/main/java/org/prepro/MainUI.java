package org.prepro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.prepro.view.MainView;

public class MainUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainView pane = new MainView();
        Scene scene = new Scene(pane, 1280, 720);

        stage.setScene(scene);
        stage.show();
    }
}
