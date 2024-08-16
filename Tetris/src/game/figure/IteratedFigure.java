package game.figure;

import game.point.Point;

import java.util.Iterator;

public interface IteratedFigure extends Figure, Iterator<Point> {
    default int getMinX() {
        int min = Integer.MAX_VALUE;
        resetCursor();
        while (hasNext()) {
            Point p = next();
            if (p.x < min)
                min = p.x;
        }
        return min;
    }
    default int getMaxX() {
        int max = Integer.MIN_VALUE;
        resetCursor();
        while (hasNext()) {
            Point p = next();
            if (p.x > max)
                max = p.x;
        }
        return max;
    }
    default int getMinY() {
        int min = Integer.MAX_VALUE;
        resetCursor();
        while (hasNext()) {
            Point p = next();
            if (p.x < min)
                min = p.x;
        }
        return min;
    }
    default int getMaxY() {
        int min = Integer.MIN_VALUE;
        resetCursor();
        while (hasNext()) {
            Point p = next();
            if (p.y > min)
                min = p.y;
        }
        return min;
    }

    void resetCursor();
}