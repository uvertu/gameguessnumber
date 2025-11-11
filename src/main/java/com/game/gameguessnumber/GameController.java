package com.game.gameguessnumber;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class GameController {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField guessField;

    private int targetNumber;
    private int attempts;
    private Random random;

    public void initialize() {
        random = new Random();
        startNewGame();
    }

    private void startNewGame() {
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
        messageLabel.setText("Я загадал число от 1 до 100. Попробуйте угадать!");
        guessField.setText("");
        guessField.setStyle(""); // Сбрасываем стиль
    }

    @FXML
    protected void onCheckButtonClick() {
        String inputText = guessField.getText();
        
        try {
            int userGuess = Integer.parseInt(inputText);
            attempts++;
            
            if (userGuess < 1 || userGuess > 100) {
                messageLabel.setText("Пожалуйста, введите число от 1 до 100!");
                guessField.setStyle("-fx-border-color: #ff4444;");
                return;
            }
            
            if (userGuess == targetNumber) {
                messageLabel.setText("Поздравляем! Вы угадали число " + targetNumber + " за " + attempts + " попыток!");
                guessField.setStyle("-fx-border-color: #44ff44;");
            } else if (userGuess < targetNumber) {
                messageLabel.setText("Мое число БОЛЬШЕ чем " + userGuess + ". Попытка: " + attempts);
                guessField.setStyle("");
            } else {
                messageLabel.setText("Мое число МЕНЬШЕ чем " + userGuess + ". Попытка: " + attempts);
                guessField.setStyle("");
            }
            
        } catch (NumberFormatException e) {
            messageLabel.setText("Пожалуйста, введите корректное число!");
            guessField.setStyle("-fx-border-color: #ff4444;");
        }
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
