module com.game.gameguessnumber {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.game.gameguessnumber to javafx.fxml;
    exports com.game.gameguessnumber;
}