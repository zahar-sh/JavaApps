package game.figure;

import game.point.Point;
import javafx.scene.paint.Color;

public interface Figure extends Iterable<Point> {
    default int getMinX() {
        int min = Integer.MAX_VALUE;
        for (Point p : this)
            if (p.x < min)
                min = p.x;
        return min;
    }
    default int getMaxX() {
        int max = Integer.MIN_VALUE;
        for (Point p : this)
            if (p.x > max)
                max = p.x;
        return max;
    }
    default int getMinY() {
        int min = Integer.MAX_VALUE;
        for (Point p : this)
            if (p.x < min)
                min = p.x;
        return min;
    }
    default int getMaxY() {
        int min = Integer.MIN_VALUE;
        for (Point p : this)
            if (p.y > min)
                min = p.y;
        return min;
    }

    void moveX(int dx);
    void moveY(int dy);

    void rotateLeft();
    void rotateRight();

    Color getColor();
}