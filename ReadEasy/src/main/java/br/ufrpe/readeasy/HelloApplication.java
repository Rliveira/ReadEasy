package br.ufrpe.readeasy;

import br.ufrpe.readeasy.gui.ScreenManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("clienteCatalogo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setMinWidth(1200);
        stage.setMinHeight(700);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}