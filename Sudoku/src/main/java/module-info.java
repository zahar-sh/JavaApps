module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.sudoku;
    exports com.example.sudoku.controller;
    exports com.example.sudoku.exception;

    opens com.example.sudoku to javafx.fxml;
    opens com.example.sudoku.controller to javafx.fxml;
    opens com.example.sudoku.exception to javafx.fxml;
}