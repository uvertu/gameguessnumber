package com.game.gameguessnumber;

import com.game.gameguessnumber.i18n.I18n;
import com.game.gameguessnumber.navigation.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ChoiceBox<String> languageChoice;

    @FXML
    public void initialize() {
        languageChoice.setItems(FXCollections.observableArrayList("Русский", "English"));

        // Initialize selection according to current locale
        if (I18n.getLocale().getLanguage().equalsIgnoreCase("en")) {
            languageChoice.getSelectionModel().select("English");
        } else {
            languageChoice.getSelectionModel().select("Русский");
        }

        languageChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            Locale newLocale = "English".equals(newValue)
                    ? Locale.forLanguageTag("en")
                    : Locale.forLanguageTag("ru");

            if (!newLocale.getLanguage().equalsIgnoreCase(I18n.getLocale().getLanguage())) {
                I18n.setLocale(newLocale);
                reloadCurrentScene();
            }
        });
    }

    @FXML
    protected void onStartGameButtonClick() {
        try {
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            SceneNavigator.setScene(stage, "/com/game/gameguessnumber/game-view.fxml", "title.game", 420, 450);
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Ошибка загрузки игры!");
        }
    }

    @FXML
    protected void onLeaderboardButtonClick() {
        try {
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            SceneNavigator.setScene(stage, "/com/game/gameguessnumber/leaderboard-view.fxml", "title.leaderboard", 520, 420);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadCurrentScene() {
        try {
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            SceneNavigator.setScene(stage, "/com/game/gameguessnumber/hello-view.fxml", "title.mainMenu", 400, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
