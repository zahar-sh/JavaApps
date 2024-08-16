package sample;

import game.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class Controller {
    @FXML
    private BorderPane pane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        Game game = new Game(canvas, 30, 30);
        pane.setOnKeyPressed(keyEvent -> {
            if (game.isInGame()) {
                switch (keyEvent.getCode()) {
                    case A:
                    case LEFT:
                        game.left();
                        break;
                    case D:
                    case RIGHT:
                        game.right();
                        break;
                    case W:
                    case UP:
                        game.rotate();
                        break;
                    case S:
                    case DOWN:
                        game.down();
                        break;
                    case SPACE:
                        if (game.isRunning())
                            game.pause();
                        else
                            game.resume();
                        break;
                }
            } else {
                game.start();
            }
        });
    }
}
