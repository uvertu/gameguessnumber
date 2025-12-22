package com.game.gameguessnumber;

import com.game.gameguessnumber.api.BackendClient;
import com.game.gameguessnumber.api.dto.LeaderboardEntry;
import com.game.gameguessnumber.api.dto.LeaderboardResponse;
import com.game.gameguessnumber.i18n.I18n;
import com.game.gameguessnumber.navigation.SceneNavigator;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LeaderboardController {

    @FXML
    private TableView<LeaderboardEntry> tableView;
    @FXML
    private TableColumn<LeaderboardEntry, Number> rankColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Number> attemptsColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Number> durationColumn;
    @FXML
    private TableColumn<LeaderboardEntry, String> dateColumn;

    @FXML
    private Label statusLabel;

    private final BackendClient backendClient = new BackendClient();
    private final ObservableList<LeaderboardEntry> rows = FXCollections.observableArrayList();

    public void initialize() {
        tableView.setItems(rows);

        rankColumn.setCellValueFactory(cell -> new ReadOnlyIntegerWrapper(cell.getValue().rank()));
        attemptsColumn.setCellValueFactory(cell -> new ReadOnlyIntegerWrapper(cell.getValue().attempts()));
        durationColumn.setCellValueFactory(cell -> new ReadOnlyLongWrapper(cell.getValue().durationMs()));
        dateColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(formatDate(cell.getValue().playedAt())));

        refresh();
    }

    @FXML
    protected void onRefreshClick() {
        refresh();
    }

    private void refresh() {
        statusLabel.setText("");
        backendClient.getLeaderboard(10)
                .thenAccept(resp -> Platform.runLater(() -> render(resp)))
                .exceptionally(ex -> {
                    Platform.runLater(() -> statusLabel.setText(format("error.backendUnavailable")));
                    return null;
                });
    }

    private void render(LeaderboardResponse resp) {
        rows.clear();
        if (resp == null || resp.entries() == null || resp.entries().isEmpty()) {
            statusLabel.setText(format("leaderboard.empty"));
            return;
        }
        rows.addAll(resp.entries());
    }

    @FXML
    protected void onBackClick() {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            SceneNavigator.setScene(stage, "/com/game/gameguessnumber/hello-view.fxml", "title.mainMenu", 400, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatDate(String iso) {
        if (iso == null || iso.isBlank()) {
            return "";
        }
        try {
            OffsetDateTime dt = OffsetDateTime.parse(iso);
            return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception ignored) {
            return iso;
        }
    }

    private static String format(String key, Object... args) {
        ResourceBundle bundle = I18n.getBundle();
        if (!bundle.containsKey(key)) {
            return key;
        }
        return MessageFormat.format(bundle.getString(key), args);
    }
}
