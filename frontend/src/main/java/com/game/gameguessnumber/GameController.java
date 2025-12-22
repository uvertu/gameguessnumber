package com.game.gameguessnumber;

import com.game.gameguessnumber.api.BackendClient;
import com.game.gameguessnumber.api.dto.GuessResponse;
import com.game.gameguessnumber.api.dto.GuessStatus;
import com.game.gameguessnumber.i18n.I18n;
import com.game.gameguessnumber.navigation.SceneNavigator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.UUID;

public class GameController {
    @FXML
    private Label headerLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private Label attemptsLabel;
    @FXML
    private TextField guessField;
    @FXML
    private ListView<String> historyListView;

    private final BackendClient backendClient = new BackendClient();

    private UUID gameId;
    private int minNumber;
    private int maxNumber;

    private ObservableList<String> attemptHistory;

    public void initialize() {
        attemptHistory = FXCollections.observableArrayList();
        historyListView.setItems(attemptHistory);
        startNewGame();
    }

    public int getAttemptsForTest() {
        // Keeps compatibility with the existing tests, now based on the size of history.
        return attemptHistory.size();
    }

    private void startNewGame() {
        setLoadingState(true);
        attemptHistory.clear();

        backendClient.startGame(I18n.getLocale())
                .thenAccept(resp -> Platform.runLater(() -> {
                    gameId = resp.gameId();
                    minNumber = resp.min();
                    maxNumber = resp.max();

                    applyLocalizedTexts();

                    messageLabel.setText(resp.message());
                    attemptsLabel.setText(format("game.attempts", 0));

                    guessField.setText("");
                    guessField.setStyle("");
                    guessField.setDisable(false);
                    guessField.requestFocus();

                    setLoadingState(false);
                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        messageLabel.setText(format("error.backendUnavailable"));
                        attemptsLabel.setText("");
                        guessField.setDisable(true);
                        setLoadingState(false);
                    });
                    return null;
                });
    }

    private void applyLocalizedTexts() {
        ResourceBundle bundle = I18n.getBundle();
        headerLabel.setText(MessageFormat.format(bundle.getString("game.header"), minNumber, maxNumber));
        guessField.setPromptText(MessageFormat.format(bundle.getString("game.prompt"), minNumber, maxNumber));
    }

    private void setLoadingState(boolean isLoading) {
        guessField.setDisable(isLoading);
    }

    @FXML
    protected void onCheckButtonClick() {
        String inputText = guessField.getText();

        int userGuess;
        try {
            userGuess = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            messageLabel.setText(format("error.invalidNumber"));
            guessField.setStyle("-fx-border-color: #ff4444;");
            return;
        }

        setLoadingState(true);

        backendClient.makeGuess(gameId, userGuess, I18n.getLocale())
                .thenAccept(resp -> Platform.runLater(() -> {
                    renderGuessResult(userGuess, resp);
                    guessField.setText("");
                    guessField.requestFocus();
                    setLoadingState(false);
                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        messageLabel.setText(format("error.backendUnavailable"));
                        setLoadingState(false);
                    });
                    return null;
                });
    }

    private void renderGuessResult(int userGuess, GuessResponse resp) {
        messageLabel.setText(resp.message());
        attemptsLabel.setText(format("game.attempts", resp.attempts()));

        addToHistory(resp.attempts(), userGuess, resp);

        if (resp.status() == GuessStatus.CORRECT) {
            guessField.setStyle("-fx-border-color: #44ff44; -fx-background-color: #e8f5e8;");
            guessField.setDisable(true);
        } else if (resp.status() == GuessStatus.OUT_OF_RANGE) {
            guessField.setStyle("-fx-border-color: #ff4444;");
        } else {
            guessField.setStyle("");
        }
    }

    private void addToHistory(int attempt, int guess, GuessResponse resp) {
        String entry = "#" + attempt + ": " + guess + " — " + toCompactStatus(resp);
        attemptHistory.add(0, entry);
    }

    private static String toCompactStatus(GuessResponse resp) {
        return switch (resp.status()) {
            case TOO_LOW -> "⬆️";
            case TOO_HIGH -> "⬇️";
            case OUT_OF_RANGE -> "⚠️";
            case CORRECT -> "🎉";
            case GAME_NOT_FOUND, GAME_ALREADY_FINISHED -> "⛔";
        } + " " + resp.message();
    }

    @FXML
    protected void onNewGameClick() {
        startNewGame();
    }

    @FXML
    protected void onBackToMenuClick() {
        try {
            Stage stage = (Stage) messageLabel.getScene().getWindow();
            SceneNavigator.setScene(stage, "/com/game/gameguessnumber/hello-view.fxml", "title.mainMenu", 400, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String format(String key, Object... args) {
        ResourceBundle bundle = I18n.getBundle();
        if (!bundle.containsKey(key)) {
            // Fallback for keys not present in the frontend bundle.
            return key;
        }
        return MessageFormat.format(bundle.getString(key), args);
    }
}
