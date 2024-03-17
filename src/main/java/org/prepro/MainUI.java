package org.prepro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.prepro.controller.MainViewController;

public class MainUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        AnchorPane anchorPane = new AnchorPane();
        loader.setRoot(anchorPane);
        loader.load();
        ((MainViewController) loader.getController()).setupStageEventHandlers(stage);

        Scene scene = new Scene(anchorPane, 1280, 720);

        stage.setScene(scene);
        stage.setTitle("Sudoku 2: Electric Boogaloo (TM)");
        stage.show();
    }
}
