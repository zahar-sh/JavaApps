package game.container;

import game.point.Point;
import javafx.scene.paint.Color;

public class Element {
    public Point point;
    public Color color;

    public Element(Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public String toString() {
        return point.toString();
    }
}