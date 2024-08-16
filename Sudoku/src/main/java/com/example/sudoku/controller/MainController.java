package com.example.sudoku.controller;

import com.example.sudoku.core.Sudoku;
import com.example.sudoku.core.SudokuSolver;
import com.example.sudoku.exception.LoadingException;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainController implements Controller {

    private static final String FXML_PATH = "main-view.fxml";

    public static MainController load(ControllerLoader loader) throws LoadingException {
        return loader.load(FXML_PATH);
    }


    @FXML
    private VBox root;

    @FXML
    private Canvas sudokuCanvas;

    @FXML
    private Canvas numberCanvas;

    @FXML
    private Button solveButton;

    private Sudoku sudoku;

    private SudokuSolver sudokuSolver;

    private int cellWidth;

    private int cellHeight;

    private int number;

    @FXML
    private void initialize() {
        this.number = 1;
        this.sudoku = createSudoku();
        this.sudokuSolver = new SudokuSolver();
        this.cellWidth = (int) (sudokuCanvas.getWidth() / sudoku.boardDimension());
        this.cellHeight = (int) (sudokuCanvas.getHeight() / sudoku.boardDimension());
        GraphicsContext sudokuCanvasContext = sudokuCanvas.getGraphicsContext2D();
        sudokuCanvasContext.setFont(Font.font("Cascadia Mono, 'Courier New', monospace", FontWeight.BOLD, (double) cellHeight / 2));
        sudokuCanvasContext.setLineWidth(4);
        sudokuCanvasContext.setStroke(Color.GOLD);
        GraphicsContext numberCanvasContext = numberCanvas.getGraphicsContext2D();
        numberCanvasContext.setFont(Font.font("Cascadia Mono, 'Courier New', monospace", FontWeight.BOLD, (double) cellHeight / 3));
        numberCanvasContext.setFill(Color.RED);
        root.setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY > 0) {
                number++;
            } else {
                number--;
            }
            number = Math.max(0, Math.min(number, sudoku.boardDimension()));
            numberRepaint();
        });
        sudokuCanvas.setOnMouseClicked(mouseEvent -> {
            int x = (int) (mouseEvent.getX() / cellWidth);
            int y = (int) (mouseEvent.getY() / cellHeight);
            setNumber(x, y, number);
        });
        solveButton.setOnAction(actionEvent -> solve());
        sudokuRepaint();
        numberRepaint();
    }

    @Override
    public Pane getRoot() {
        return root;
    }

    private Sudoku createSudoku() {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        Sudoku sudoku = new Sudoku();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                char cell = board[y][x];
                if (cell != '.') {
                    int number = cell - '0';
                    sudoku.set(x, y, number);
                }
            }
        }
        return sudoku;
    }

    private void sudokuRepaint() {
        GraphicsContext context = sudokuCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, sudokuCanvas.getWidth(), sudokuCanvas.getHeight());
        for (int y = 0; y <= sudoku.boardDimension(); y++) {
            int y0 = y * cellHeight;
            context.strokeLine(0, y0, sudokuCanvas.getWidth(), y0);
        }
        for (int x = 0; x <= sudoku.boardDimension(); x++) {
            int x0 = x * cellWidth;
            context.strokeLine(x0, 0, x0, sudokuCanvas.getHeight());
        }
        for (int y = 0; y < sudoku.boardDimension(); y++) {
            for (int x = 0; x < sudoku.boardDimension(); x++) {
                Integer number = sudoku.get(x, y);
                String text = number == null ? "-" : Integer.toString(number);
                int x0 = (int) (x * cellWidth + (cellWidth / 3));
                int y0 = (int) (y * cellHeight + (cellHeight / 1.5));
                context.fillText(text, x0, y0);
            }
        }
    }

    private void numberRepaint() {
        GraphicsContext context = numberCanvas.getGraphicsContext2D();
        context.clearRect(0, 0, numberCanvas.getWidth(), numberCanvas.getHeight());
        String text = Integer.toString(number);
        int x0 = cellWidth / 4;
        int y0 = cellHeight / 4;
        context.fillText(text, x0, y0);
    }

    private void setNumber(int x, int y, int number) {
        if (number == 0) {
            sudoku.clear(x, y);
            sudokuRepaint();
        } else {
            if (sudoku.set(x, y, number)) {
                sudokuRepaint();
            }
        }
    }

    private void solve() {
        sudokuSolver.solve(sudoku);
        sudokuRepaint();
    }
}