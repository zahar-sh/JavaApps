module com.example.queenplaceholder {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.queenplaceholder;
    opens com.example.queenplaceholder to javafx.fxml;

    exports com.example.queenplaceholder.common;
    opens com.example.queenplaceholder.common to javafx.fxml;

    exports com.example.queenplaceholder.controller;
    opens com.example.queenplaceholder.controller to javafx.fxml;

    exports com.example.queenplaceholder.exception;
    opens com.example.queenplaceholder.exception to javafx.fxml;
}