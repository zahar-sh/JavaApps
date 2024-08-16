package com.example.queenplaceholder.controller;

import com.example.queenplaceholder.QueenPlaceholderApplication;
import com.example.queenplaceholder.chess.QueenPlaceholder;
import com.example.queenplaceholder.common.ImageLoader;
import com.example.queenplaceholder.exception.LoadException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainController implements Controller {

    private static final String FXML_PATH = "main-view.fxml";

    public static MainController load(ControllerLoader loader) throws LoadException {
        return loader.load(FXML_PATH);
    }

    @FXML
    private VBox root;

    @FXML
    private Canvas canvas;

    @FXML
    private Button nextButton;

    @FXML
    private ToggleButton findButton;

    private Image queenImage;

    private int cw;

    private int ch;

    private QueenPlaceholder placeholder;

    private ScheduledExecutorService service;

    private Future<?> future;

    private Lock futureLock;

    private AtomicBoolean found;

    @FXML
    private void initialize() throws LoadException {
        ImageLoader imageLoader = QueenPlaceholderApplication.getImageLoader();
        queenImage = imageLoader.load("queen.png");
        placeholder = new QueenPlaceholder(8, 8);
        cw = (int) (canvas.getWidth() / placeholder.getWidth());
        ch = (int) (canvas.getHeight() / placeholder.getHeight());
        service = Executors.newSingleThreadScheduledExecutor();
        futureLock = new ReentrantLock();
        found = new AtomicBoolean();
        nextButton.setOnAction(this::onNextButtonClick);
        findButton.setOnAction(this::onFindButtonClick);

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(Color.BLACK);
        context.setLineWidth(3.0);
        drawChessBoard(context);
    }

    private void onNextButtonClick(ActionEvent event) {
        if (placeholder.hasNext()) {
            placeholder.next();
            onPlaceholderChanged();
        }
    }

    private void onFindButtonClick(ActionEvent event) {
        futureLock.lock();
        try {
            if (future != null) {
                future.cancel(false);
                try {
                    future.get();
                } catch (CancellationException ignored) {
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    handleException(e);
                    return;
                }
            }
            if (findButton.isSelected()) {
                var action = new Runnable() {

                    private Future<?> future;

                    @Override
                    public void run() {
                        if ((future != null && !future.isCancelled()) && !Thread.currentThread().isInterrupted()) {
                            if (placeholder.hasNext()) {
                                placeholder.next();
                                Platform.runLater(MainController.this::onPlaceholderChanged);
                                if (placeholder.isFound()) {
                                    stop();
                                }
                            } else {
                                stop();
                            }
                        }
                    }

                    private void stop() {
                        if (future != null) {
                            future.cancel(false);
                        }
                        Platform.runLater(() -> findButton.setSelected(false));
                    }
                };
                future = action.future = service.scheduleAtFixedRate(action, 0, 500, TimeUnit.MILLISECONDS);
            }
        } finally {
            futureLock.unlock();
        }
    }

    private void handleException(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("An error has occurred");
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }

    private void onPlaceholderChanged() {
        if (placeholder.isLastQueenIntersected()) {
            int lastQueenX = placeholder.getLastQueenX();
            int lastQueenY = placeholder.getLastQueenY();
            int[] placedQueens = placeholder.getQueenStack();
            int sizeBeforeLast = lastQueenY;

            GraphicsContext context = canvas.getGraphicsContext2D();
            drawChessBoard(context);
            drawPlacedQueens(context, placedQueens, sizeBeforeLast);
            drawSelectedQueen(context, lastQueenX, lastQueenY, Color.YELLOW);
        } else {
            if (!placeholder.isFull()) {
                int lastQueenX = placeholder.getLastQueenX();
                int lastQueenY = placeholder.getLastQueenY();
                int[] placedQueens = placeholder.getQueenStack();
                int sizeBeforeLast = lastQueenY;

                GraphicsContext context = canvas.getGraphicsContext2D();
                drawChessBoard(context);
                drawPlacedQueens(context, placedQueens, sizeBeforeLast);
                drawSelectedQueen(context, lastQueenX, lastQueenY, Color.RED);
            } else {
                int[] placedQueens = placeholder.getQueenStack();

                GraphicsContext context = canvas.getGraphicsContext2D();
                drawChessBoard(context);
                drawPlacedQueens(context, placedQueens, placedQueens.length);
            }
        }
    }

    private void drawChessBoard(GraphicsContext context) {
        for (int y = 0; y < placeholder.getHeight(); y++) {
            for (int x = 0; x < placeholder.getWidth(); x++) {
                context.setFill(((x + y) & 1) == 0 ? Color.CHOCOLATE : Color.TAN);
                context.fillRect(x * cw, y * ch, cw, ch);
            }
        }
        context.strokeRect(0, 0, placeholder.getWidth() * cw, placeholder.getHeight() * ch);
    }

    private void drawQueen(GraphicsContext context, int queenX, int queenY) {
        context.drawImage(queenImage, queenX * cw, queenY * ch, cw, ch);
    }

    private void drawSelectedQueen(GraphicsContext context, int lastQueenX, int lastQueenY, Color color) {
        context.setFill(color);
        context.fillOval(lastQueenX * cw, lastQueenY * ch, cw, ch);
        drawQueen(context, lastQueenX, lastQueenY);
    }

    private void drawPlacedQueens(GraphicsContext context, int[] placedQueens, int size) {
        for (int queenY = 0; queenY < size; queenY++) {
            int queenX = placedQueens[queenY];
            drawQueen(context, queenX, queenY);
        }
    }

    @Override
    public Pane getRoot() {
        return root;
    }

    public void destroy() {
        service.shutdown();
        try {
            service.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}