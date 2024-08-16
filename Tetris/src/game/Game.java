package game;

import game.container.Container;
import game.figure.IteratedFigure;
import game.point.Point;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.swing.Timer;
import java.util.ArrayDeque;
import java.util.Queue;

public class Game {
    enum SpeedState {
        s0(5, 250),
        s1(10, 200),
        s2(20, 175),
        s3(30, 150),
        s4(50, 125),
        s5(70, 100),
        s6(100, 75);

        public final int line;
        public final int delay;

        SpeedState(int line, int delay) {
            this.line = line;
            this.delay = delay;
        }
    }

    private final Container container;
    private final Canvas canvas;

    private SpeedState state = SpeedState.s0;
    private final Timer timer = new Timer(state.delay, a -> down());

    private final Queue<IteratedFigure> nextFigures = new ArrayDeque<>();
    private IteratedFigure figure;

    private boolean inGame = false;
    private int lines, score;

    private static final int x = 10;
    private static final int y = 20;
    private final int w, h;

    public Game(Canvas canvas, int dotWidth, int dotHeight) {
        this.canvas = canvas;
        this.w = dotWidth;
        this.h = dotHeight;
        container = new Container(x, y, 20);
        clear(canvas.getGraphicsContext2D());
        repaint();
    }

    private boolean outOfRange(IteratedFigure f) {
        f.resetCursor();
        while (f.hasNext()) {
            Point p = f.next();
            if (p.x < 0 || p.x >= x || p.y >= y)
                return true;
        }
        return false;
    }
    private boolean contains(IteratedFigure f) {
        f.resetCursor();
        while (f.hasNext())
            if (container.contains(f.next()))
                return true;
        return false;
    }
    private boolean isOut(IteratedFigure f) {
        return outOfRange(f) || contains(f);
    }

    public void left() {
        figure.moveX(-1);
        if (isOut(figure))
            figure.moveX(1);
        else
            repaint();
    }
    public void right() {
        figure.moveX(1);
        if (isOut(figure))
            figure.moveX(-1);
        else
            repaint();
    }
    public void rotate() {
        figure.rotateRight();
        if (isOut(figure))
            figure.rotateLeft();
        else
            repaint();
    }
    public synchronized void down() {
        figure.moveY(1);
        if (isOut(figure)) {
            figure.moveY(-1);
            scoreUp(container.addFigure(figure));
            figure = nextFigure();
            repaint();
            if (contains(figure))
                gameOver();
        } else {
            repaint();
        }
    }

    private void scoreUp(int line) {
        if (line == 0)
            return;
        int pow;
        if (line == 1) pow = 10;
        else if (line == 2) pow = 20;
        else if (line == 3) pow = 30;
        else pow = 50;
        score += line * x * pow;
        lines += line;

        if (lines > state.line) {
            int next = state.ordinal() + 1;
            SpeedState[] states = SpeedState.values();
            if (next < states.length) {
                state = states[next];
                timer.setDelay(state.delay);
            }
        }
    }
    private IteratedFigure nextFigure() {
        nextFigures.offer(createFigure());

        IteratedFigure next = nextFigures.remove();
        setInCenter(next);
        return next;
    }
    private void setInCenter(IteratedFigure figure) {
        int min = figure.getMinX();
        int l = figure.getMaxX() - min;
        int center = (x - l) >> 1;
        figure.moveX(center - min);
        figure.moveY(-figure.getMaxY());
    }

    private IteratedFigure createFigure() {
        return FigureCreator.createFigure(lines);
    }

    private void repaint() {
        Platform.runLater(() -> {
            GraphicsContext g = canvas.getGraphicsContext2D();
            clear(g);
            container.forEach(e -> {
                g.setFill(e.color);
                drawPoint(g, e.point);
            });
            drawFigure(g, figure);

            g.setFill(Color.WHITE);
            g.setStroke(Color.WHITE);

            int x0 = x * w;
            int dy = 0;
            g.translate(x0, 0);

            g.strokeLine(0, 0, 0, canvas.getHeight());

            x0 += w;
            dy += h;
            g.translate(w, h);

            Runtime r = Runtime.getRuntime();
            g.fillText("Lines: " + lines + "\nScore: " + score +
                    "\nMemory: " + (r.totalMemory() - r.freeMemory()), 0,  15);
            dy += h << 1;
            g.translate(0, h << 1);
            for (IteratedFigure f : nextFigures) {
                drawFigure(g, f);
                int val = (f.getMaxY() + 2) * h;
                g.translate(0, val);
                dy += val;
            }
            g.translate(-x0, -dy);
        });
    }
    private void clear(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawPoint(GraphicsContext context, Point p) {
        context.fillRect(p.x * w, p.y * h, w - 2, h - 2);
    }
    private void drawFigure(GraphicsContext context, IteratedFigure f) {
        if (context == null || f == null)
            return;
        context.setFill(f.getColor());
        f.resetCursor();
        while (f.hasNext())
            drawPoint(context, f.next());
    }

    public void start() {
        inGame = true;
        lines = 0;
        score = 0;
        container.clear();
        nextFigures.clear();
        for (int i = 0; i < 4; i++)
            nextFigures.offer(createFigure());
        figure = nextFigure();
        state = SpeedState.s0;
        timer.setDelay(state.delay);
        System.gc();
        resume();
    }

    public boolean isRunning() {
        return timer.isRunning();
    }
    public void resume() {
        timer.start();
    }
    public void pause() {
        timer.stop();
    }

    private void gameOver() {
        inGame = false;
        pause();
    }
    public boolean isInGame() {
        return inGame;
    }
}