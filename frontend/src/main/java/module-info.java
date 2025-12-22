module com.game.gameguessnumber {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;

    // Jackson (automatic modules)
    requires com.fasterxml.jackson.databind;

    opens com.game.gameguessnumber to javafx.fxml;
    opens com.game.gameguessnumber.api.dto to com.fasterxml.jackson.databind;
    exports com.game.gameguessnumber;
}
