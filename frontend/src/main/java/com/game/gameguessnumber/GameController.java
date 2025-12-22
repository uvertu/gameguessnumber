package com.game.gameguessnumber;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Random;

public class GameController {
    @FXML
    private Label messageLabel;
    @FXML
    private Label attemptsLabel;
    @FXML
    private TextField guessField;
    @FXML
    private ListView<String> historyListView;

    private int targetNumber;
    private int attempts;
    private Random random;
    private ObservableList<String> attemptHistory;

    public void initialize() {
        random = new Random();
        attemptHistory = FXCollections.observableArrayList();
        historyListView.setItems(attemptHistory);
        startNewGame();
    }

    public int getAttemptsForTest() {
        return attempts;
    }

    private void startNewGame() {
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
        attemptHistory.clear();
        updateAttemptsLabel();
        messageLabel.setText("Я загадал число от 1 до 100. Попробуйте угадать!");
        guessField.setText("");
        guessField.setStyle("");
        guessField.setDisable(false);
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Попытки: " + attempts);
    }

    private void addToHistory(int guess, String result) {
        String historyEntry = "Попытка " + attempts + ": " + guess + " - " + result;
        attemptHistory.add(0, historyEntry); // Добавляем в начало списка
    }

    @FXML
    protected void onCheckButtonClick() {
        String inputText = guessField.getText();
        
        try {
            int userGuess = Integer.parseInt(inputText);
            attempts++;
            updateAttemptsLabel();
            
            if (userGuess < 1 || userGuess > 100) {
                messageLabel.setText("Пожалуйста, введите число от 1 до 100!");
                guessField.setStyle("-fx-border-color: #ff4444;");
                return;
            }
            
            if (userGuess == targetNumber) {
                String victoryText = "🎉 Поздравляем! Вы угадали число " + targetNumber + " за " + attempts + " попыток!";
                messageLabel.setText(victoryText);
                guessField.setStyle("-fx-border-color: #44ff44; -fx-background-color: #e8f5e8;");
                addToHistory(userGuess, "УГАДАЛИ! 🎉");
                guessField.setDisable(true);
            } else if (userGuess < targetNumber) {
                messageLabel.setText("Мое число БОЛЬШЕ чем " + userGuess);
                guessField.setStyle("");
                addToHistory(userGuess, "Больше ⬆️");
            } else {
                messageLabel.setText("Мое число МЕНЬШЕ чем " + userGuess);
                guessField.setStyle("");
                addToHistory(userGuess, "Меньше ⬇️");
            }
            
            guessField.setText("");
            guessField.requestFocus();
            
        } catch (NumberFormatException e) {
            messageLabel.setText("Пожалуйста, введите корректное число!");
            guessField.setStyle("-fx-border-color: #ff4444;");
        }
    }

    @FXML
    protected void onNewGameClick() {
        startNewGame();
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
