package com.example.queenplaceholder;

import com.example.queenplaceholder.common.ImageLoader;
import com.example.queenplaceholder.controller.ControllerLoader;
import com.example.queenplaceholder.controller.MainController;
import com.example.queenplaceholder.exception.LoadException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QueenPlaceholderApplication extends Application {

    private static final ControllerLoader CONTROLLER_LOADER = new ControllerLoader(QueenPlaceholderApplication.class);

    private static final ImageLoader IMAGE_LOADER = new ImageLoader(QueenPlaceholderApplication.class);

    public static ControllerLoader getControllerLoader() {
        return CONTROLLER_LOADER;
    }

    public static ImageLoader getImageLoader() {
        return IMAGE_LOADER;
    }

    public static void main(String[] args) {
        launch();
    }

    private MainController mainController;

    @Override
    public void start(Stage stage) throws LoadException {
        mainController = MainController.load(getControllerLoader());
        Scene scene = new Scene(mainController.getRoot());
        stage.setTitle("Queen placeholder");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        mainController.destroy();
    }
}