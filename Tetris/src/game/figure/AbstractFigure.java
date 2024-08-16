package game.figure;

import game.point.Point;
import game.point.PointIterator;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public abstract class AbstractFigure extends PointIterator implements IteratedFigure {
    protected final Point p1, p2, p3, p4;
    private final Color color;

    public AbstractFigure(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color color) {
        this(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3), new Point(x4, y4), color);
    }
    public AbstractFigure(Point p1, Point p2, Point p3, Point p4, Color color) {
        super(p1);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.color = color;
    }

    public int getMinX() {
        int min = p1.x;
        if (p2.x < min)
            min = p2.x;
        if (p3.x < min)
            min = p3.x;
        if (p4.x < min)
            min = p4.x;
        return min;
    }
    public int getMaxX() {
        int max = p1.x;
        if (p2.x > max)
            max = p2.x;
        if (p3.x > max)
            max = p3.x;
        if (p4.x > max)
            max = p4.x;
        return max;
    }
    public int getMinY() {
        int min = p1.y;
        if (p2.y < min)
            min = p2.y;
        if (p3.y < min)
            min = p3.y;
        if (p4.y < min)
            min = p4.y;
        return min;
    }
    public int getMaxY() {
        int max = p1.y;
        if (p2.y > max)
            max = p2.y;
        if (p3.y > max)
            max = p3.y;
        if (p4.y > max)
            max = p4.y;
        return max;
    }

    public void moveX(int dx) {
        this.p1.x += dx;
        this.p2.x += dx;
        this.p3.x += dx;
        this.p4.x += dx;
    }
    public void moveY(int dy) {
        this.p1.y += dy;
        this.p2.y += dy;
        this.p3.y += dy;
        this.p4.y += dy;
    }

    /*public Spliterator<Point> spliterator() {
        return new Spliterator<>() {
            static final int ch = Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL;
            int size = 4;
            final Iterator<Point> iterator = iterator();
            @Override
            public boolean tryAdvance(Consumer<? super Point> action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    size--;
                    return true;
                }
                return false;
            }

            @Override
            public Spliterator<Point> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return size;
            }

            @Override
            public int characteristics() {
                return ch;
            }
        };
    }*/
    public void forEach(Consumer<? super Point> action) {
        if (action == null)
            throw new NullPointerException();
        action.accept(p1);
        action.accept(p2);
        action.accept(p3);
        action.accept(p4);
    }
    public Iterator<Point> iterator() {
        return new PointIterator(p1) {
            protected Point nextOf(Point cursor) {
                return AbstractFigure.this.nextOf(cursor);
            }
        };
    }
    public void resetCursor() {
        cursor = p1;
    }

    protected Point nextOf(Point point) {
        if (point == null)
            throw new NoSuchElementException();
        else if (point == p1)
            point = p2;
        else if (point == p2)
            point = p3;
        else if (point == p3)
            point = p4;
        else
            point = null;
        return point;
    }
    public Color getColor() {
        return color;
    }
}