package com.game.gameguessnumber;

import com.game.gameguessnumber.i18n.I18n;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"), I18n.getBundle());
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle(I18n.getBundle().getString("title.mainMenu"));
        stage.setScene(scene);
        stage.show();
    }
}