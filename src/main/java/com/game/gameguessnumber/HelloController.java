package com.game.gameguessnumber;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onStartGameButtonClick() {
        try {
            // Загружаем игровое окно
            Parent gameRoot = FXMLLoader.load(getClass().getResource("game-view.fxml"));
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 400, 400));
            stage.setTitle("Угадай число - Игра");
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Ошибка загрузки игры!");
        }
    }
}