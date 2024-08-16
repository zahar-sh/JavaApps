package com.example.queenplaceholder.controller;

import com.example.queenplaceholder.exception.LoadException;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;

public class ControllerLoader {

    private final Class<?> cls;

    public ControllerLoader(Class<?> cls) {
        this.cls = cls;
    }

    public <T extends Controller> T load(String path) throws LoadException {
        InputStream resource = cls.getResourceAsStream(path);
        if (resource == null) {
            throw new LoadException("resource not found");
        }
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.load(resource);
        } catch (IOException e) {
            throw new LoadException(e);
        }
        return loader.getController();
    }
}
