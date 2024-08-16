package com.example.sudoku;

import com.example.sudoku.controller.ControllerLoader;
import com.example.sudoku.controller.MainController;
import com.example.sudoku.exception.LoadingException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuApplication extends Application {
    private static final ControllerLoader CONTROLLER_LOADER = new ControllerLoader(SudokuApplication.class);

    public static ControllerLoader getControllerLoader() {
        return CONTROLLER_LOADER;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws LoadingException {
        MainController controller = MainController.load(getControllerLoader());
        Scene scene = new Scene(controller.getRoot());
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }
}