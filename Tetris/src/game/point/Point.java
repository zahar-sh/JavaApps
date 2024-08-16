package game.point;

public class Point {
    public byte x, y;

    public Point() {
    }
    public Point(int x, int y) {
        this.x = (byte) x;
        this.y = (byte) y;
    }

    public static int compare(Point o1, Point o2) {
        int cmp = o1.x - o2.x;
        return (cmp == 0) ? (o1.y - o2.y) : cmp;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }
    public int hashCode() {
        return x ^ y;
    }
    public String toString() {
        return "x=" + x + ", y=" + y;
    }
}