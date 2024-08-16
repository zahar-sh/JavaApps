package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Controller {

    @FXML
    private Pane pausePane;

    @FXML
    private ImageView imageView;

    @FXML
    private VBox gamePane;

    @FXML
    private Label label;

    @FXML
    private Canvas canvas;

    @FXML
    private VBox menuPane;

    @FXML
    private Pane infoPane;

    @FXML
    void initialize() {
        timeline.setCycleCount(Animation.INDEFINITE);
        imageView.setImage(new Image("background.png"));

        menuPane.setOnKeyPressed(this::menuPaneKeyPressed);
        gamePane.setOnKeyPressed(this::gamePaneKeyPressed);
        pausePane.setOnKeyPressed(this::pausePanePressed);
        infoPane.setOnKeyPressed(this::infoPanePressed);

        selectPane(menuPane);
    }

    @FXML
    void exitAction() {
        Main.primaryStage.close();
    }

    @FXML
    void infoSelect() {
        selectPane(infoPane);
    }

    @FXML
    void newGameAction() {
        selectPane(gamePane);
        newGame();
    }

    private void menuPaneKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            selectPane(gamePane);
            newGame();
        }
    }
    private void gamePaneKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.ESCAPE) {
            selectPane(pausePane);
            timeline.stop();
        } else {
            if (inGame) {
                if (code == KeyCode.UP) {
                    if (head.direction != Direction.DOWN)
                        head.direction = Direction.UP;
                } else if (code == KeyCode.DOWN) {
                    if (head.direction != Direction.UP)
                        head.direction = Direction.DOWN;
                } else if (code == KeyCode.LEFT) {
                    if (head.direction != Direction.RIGHT)
                        head.direction = Direction.LEFT;
                } else if (code == KeyCode.RIGHT) {
                    if (head.direction != Direction.LEFT)
                        head.direction = Direction.RIGHT;
                }
            } else {
                newGame();
            }
        }
    }
    private void pausePanePressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            selectPane(menuPane);
        } else if (keyEvent.getCode() == KeyCode.ENTER) {
            selectPane(gamePane);
            timeline.play();
        }
    }
    private void infoPanePressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            selectPane(menuPane);
        }
    }

    private Pane pane = new Pane();
    private void selectPane(Pane newPane) {
        pane.setDisable(true);
        pane.setVisible(false);

        newPane.setDisable(false);
        newPane.setVisible(true);

        pane = newPane;
    }

    private final int DOT_SIZE = 30;
    private final int x0 = 20;
    private final int y0 = 20;

    private boolean inGame = false;

    private final Image background = new Image("field.png");

    private final Image appleImg = new Image("apple.png");
    private final Point apple = new Point();

    private final Image headImg = new Image("head.png");
    private final Element head = new Element();

    private final Image dotImage = new Image("dot.png");
    private final Element[] snake = new Element[x0 * y0 + 1];
    private int size = 0;

    private final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> update()));

    private void update() {
        move();
        checkApple();
        repaint();
        checkGame();
    }

    private void newGame() {
        head.x = x0 - 1;
        head.y = 0;
        head.direction = Direction.LEFT;

        inGame = true;
        size = 0;
        for (int i = 0; i < 3; i++)
            grow();
        timeline.play();
        setScore();
    }
    private void grow() {
        Element previous = size == 0 ? head : snake[size - 1];
        Element element = new Element();
        copy(element, previous);
        snake[size++] = element;
    }
    private void setScore() {
        label.setText("Your score: " + (size + 1));
    }

    private void copy(Element dest, Element src) {
        dest.x = src.x;
        dest.y = src.y;
        dest.direction = src.direction;
    }
    private boolean equal(Point first, Point second) {
        return first.x == second.x && first.y == second.y;
    }

    private void checkGame() {
        inGame = inSize(head) && checkCollisions();
        if (!inGame)
            timeline.stop();
    }
    private boolean inSize(Point point) {
        return point.x >= 0 && point.x < x0 &&
                point.y >= 0 && point.y < y0;
    }
    private boolean checkCollisions() {
        for (int i = 0; i < size; i++)
            if (equal(head, snake[i]))
                return false;
        return true;
    }

    private void move() {
        for (int i = size - 1; i > 0; i--) {
            copy(snake[i], snake[i - 1]);
        }
        copy(snake[0], head);
        switch (head.direction) {
            case UP:
                head.y--;
                break;
            case DOWN:
                head.y++;
                break;
            case LEFT:
                head.x--;
                break;
            case RIGHT:
                head.x++;
                break;
        }
    }

    private void checkApple() {
        if (equal(head, apple)) {
            grow();
            setScore();
            createApple();
        }
    }
    private void createApple() {
        apple.x = (int)(Math.random() * x0);
        apple.y = (int)(Math.random() * y0);
    }

    private void repaint() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        drawSnake(context);
        drawHead(context);
        context.drawImage(appleImg, apple.x * DOT_SIZE, apple.y * DOT_SIZE, DOT_SIZE, DOT_SIZE);
    }
    private void drawSnake(GraphicsContext context) {
        double angle = 0;
        for (int i = 0; i < size; i++) {
            switch (snake[i].direction) {
                case DOWN:
                case UP:
                    angle = 90;
                    break;
                case LEFT:
                case RIGHT:
                    angle = 0;
                    break;
            }
            draw(context, dotImage, snake[i].x * DOT_SIZE, snake[i].y * DOT_SIZE, DOT_SIZE, DOT_SIZE,  angle);
        }
    }
    private void drawHead(GraphicsContext context) {
        double angle = 0;
        switch (head.direction) {
            case DOWN:
                angle = 0;
                break;
            case UP:
                angle = 180;
                break;
            case LEFT:
                angle = 90;
                break;
            case RIGHT:
                angle = 270;
                break;
        }
        draw(context, headImg, head.x * DOT_SIZE, head.y * DOT_SIZE, DOT_SIZE, DOT_SIZE, angle);
    }
    private void draw(GraphicsContext graphicsContext, Image image, int x, int y, int width, int height, double angle) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;
        double centerX = x + halfWidth;
        double centerY = y + halfHeight;
        graphicsContext.translate(centerX, centerY);
        graphicsContext.rotate(angle);
        graphicsContext.drawImage(image, -halfWidth, -halfHeight, width, height);
        graphicsContext.rotate(-angle);
        graphicsContext.translate(-centerX, -centerY);
    }
}
