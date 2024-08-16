package game.container;

import game.figure.Figure;
import game.figure.IteratedFigure;
import game.point.Point;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

public class Container implements Iterable<Element> {
    private final int x;
    private final byte[] lineCounter;
    private Element[] points;
    private int size;

    public Container(int x, int y, int initialCap) {
        if (x < 0 || y < 0 || initialCap < 0)
            throw new IllegalArgumentException();
        this.x = x;
        lineCounter = new byte[y];
        points = new Element[initialCap];
    }

    private void inc(int y) {
        if (y >= 0 && y < lineCounter.length)
            lineCounter[y]++;
    }
    private void dec(int y) {
        if (y >= 0 && y < lineCounter.length)
            lineCounter[y]--;
    }

    private void checkElementIndex(int i) {
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException();
    }

    private int binarySearch(Point point) {
        int low = 0;
        int high = size;

        int mid, cmp;
        while (low < high) {
            mid = (low + high - 1) >>> 1;
            cmp = Point.compare(points[mid].point, point);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid;
            else
                return mid;
        }
        return -(low + 1);  // key not found
    }

    public int addFigure(Figure figure) {
        for (Point point : figure)
            add(new Element(point, figure.getColor()));
        int removedLine = 0;
        for (Point point : figure)
            if (checkLine(point))
                removedLine++;
        return removedLine;
    }
    public int addFigure(IteratedFigure f) {
        f.resetCursor();
        while (f.hasNext())
            add(new Element(f.next(), f.getColor()));
        int removedLine = 0;
        f.resetCursor();
        while (f.hasNext())
            if (checkLine(f.next()))
                removedLine++;
        return removedLine;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public void forEach(Consumer<? super Element> action) {
        if (action == null)
            throw new NullPointerException();
        for (int i = 0, to = size; i < to; i++)
            action.accept(points[i]);
    }
    public Iterator<Element> iterator() {
        return new Iterator<Element>() {
            int lastRet = -1, cursor = 0;

            public boolean hasNext() {
                return cursor < size;
            }

            public Element next() {
                checkElementIndex(cursor);
                return points[(lastRet = cursor++)];
            }

            public void remove() {
                if (lastRet == -1)
                    throw new IllegalStateException();
                Container.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            }
        };
    }

    public void add(Element element) {
        if (element == null)
            throw new NullPointerException();
        final int s = size;
        int i = binarySearch(element.point);
        if (i < 0) {
            //insert new element
            i = (-i) - 1;
            while (i < size && Point.compare(points[i].point, element.point) < 0)
                i++;
            if (s >= points.length) {
                int newSize = s + (s >> 1) + 1;
                points = Arrays.copyOf(points, newSize);
            }
            inc(element.point.y);

            size = s + 1;
            int l = size - i;
            if (l > 0)
                System.arraycopy(points, i, points, i + 1, l - 1);
        } //else replace old
        points[i] = element;
    }

    private boolean checkLine(Point point) {
        int y = point.y;
        if (y >= 0 && y < lineCounter.length && lineCounter[y] >= x) {
            removeLine(y);
            return true;
        }
        return false;
    }
    private void removeLine(int line) {
        int insert = 0;
        for (int i = 0, to = size; i < to; i++) {
            Point p = points[i].point;
            if (p.y < line)
                p.y++;
            else if (p.y == line)
                continue;
            points[insert++] = points[i];
        }
        while (size > insert)
            points[--size] = null;
        System.arraycopy(lineCounter, 0, lineCounter, 1, line);
        lineCounter[0] = 0;
    }

    public boolean contains(Point point) {
        return binarySearch(point) > -1;
    }
    public int indexOf(Point point) {
        return binarySearch(point);
    }

    public Element get(int index) {
        checkElementIndex(index);
        return points[index];
    }
    public void remove(int index) {
        checkElementIndex(index);
        dec(points[index].point.y);
        final int newSize = size - 1;
        if (newSize > index)
            System.arraycopy(points, index + 1, points, index, newSize - index);
        points[size = newSize] = null;
    }

    public void clear() {
        Element[] elements = points;
        for (int i = 0, to = size; i < to; i++)
            elements[i] = null;
        size = 0;
        for (int i = 0, to = lineCounter.length; i < to; i++)
            lineCounter[i] = 0;
    }

    public String toString() {
        if (size == 0)
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int max = size - 1;
        for (int i = 0; ; i++) {
            sb.append(points[i].toString());
            if (i == max)
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}