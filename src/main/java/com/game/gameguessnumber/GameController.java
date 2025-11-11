package com.game.gameguessnumber;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.io.IOException;

public class GameController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField guessField;

    @FXML
    protected void onCheckButtonClick() {
        messageLabel.setText(" ");
    }

    @FXML
    protected void onBackToMenuClick() {
        try {
            Parent menuRoot = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(new Scene(menuRoot, 400, 300));
            stage.setTitle("Угадай число - Главное меню");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}