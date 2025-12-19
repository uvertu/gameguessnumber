module com.game.gameguessnumber {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.game.gameguessnumber to javafx.fxml;
    exports com.game.gameguessnumber;
}