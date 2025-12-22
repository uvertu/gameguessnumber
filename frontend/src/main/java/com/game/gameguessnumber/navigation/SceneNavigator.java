package com.game.gameguessnumber.navigation;

import com.game.gameguessnumber.i18n.I18n;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneNavigator {

    private SceneNavigator() {
    }

    public static void setScene(Stage stage, String fxml, String titleKey, double width, double height) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxml), I18n.getBundle());
        Parent root = loader.load();
        stage.setScene(new Scene(root, width, height));
        stage.setTitle(I18n.getBundle().getString(titleKey));
    }
}
