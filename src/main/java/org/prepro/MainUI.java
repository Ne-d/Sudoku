package org.prepro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.prepro.controller.MainViewController;


public class MainUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        BorderPane borderPane = new BorderPane();
        loader.setRoot(borderPane);
        loader.load();
        ((MainViewController) loader.getController()).setupStageEventHandlers(stage);

        Scene scene = new Scene(borderPane, 1280, 720);

        stage.setScene(scene);
        stage.setTitle("Project Sudoku");
        stage.show();
    }
}
