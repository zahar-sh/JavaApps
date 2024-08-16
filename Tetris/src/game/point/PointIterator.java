package game.point;

import java.util.Iterator;

public abstract class PointIterator implements Iterator<Point> {
    protected Point cursor;

    public PointIterator(Point cursor) {
        this.cursor = cursor;
    }

    public boolean hasNext() {
        return cursor != null;
    }
    public Point next() {
        Point ret = cursor;
        cursor = nextOf(cursor);
        return ret;
    }

    protected abstract Point nextOf(Point cursor);
}