package com.example.queenplaceholder.common;

import com.example.queenplaceholder.exception.LoadException;
import javafx.scene.image.Image;

import java.io.InputStream;

public class ImageLoader {

    private final Class<?> cls;

    public ImageLoader(Class<?> cls) {
        this.cls = cls;
    }

    public Image load(String path) throws LoadException {
        InputStream resource = cls.getResourceAsStream(path);
        if (resource == null) {
            throw new LoadException("resource not found");
        }
        return new Image(resource);
    }
}
