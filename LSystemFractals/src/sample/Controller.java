package sample;

import fractals.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        Fr[] frs = {
                Dragon.getInstance(),
                Koha.getInstance(),
                Tr.getInstance(),
                Tree.getInstance(),
                Tree2.getInstance()
        };
        Thread thread = new Thread(() -> {
            for (Fr fr : frs) {
                repaint(fr, fr.getStart());
                fr.map(s -> repaint(fr, s), 5);
            }
        });
        thread.start();
    }

    private void repaint(Fr fr, CharSequence in) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(1, 0, 0, 1, 0, 1);
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setStroke(Color.WHITE);
        g.translate(canvas.getWidth() / 4, canvas.getHeight() / 2);
        fr.draw(in, g);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}