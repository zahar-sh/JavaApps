package game.figure;

import javafx.scene.paint.Color;

public class F5 extends StatedFigure {
    public F5(Color color) {
        super(1, 0, 2, 0, 0, 1, 1, 1, color);
    }
    public F5(Color color, byte state) {
        super(1, 0, 2, 0, 0, 1, 1, 1, color, state);
    }

    protected void s0_90(int one) {
        int two = one + one;
        int minus = -one;
        p1.x += one;
        p1.y += one;

        p2.y += two;

        p3.x += one;
        p3.y += minus;
    }
    protected void s90_180(int one) {
        int two = one + one;
        int minus = -one;
        p1.x += minus;
        p1.y += one;

        p2.x += -two;

        p3.x += one;
        p3.y += one;
    }
    protected void s180_270(int one) {
        int two = one + one;
        int minus = -one;
        p2.y += -two;

        p1.x += minus;
        p1.y += minus;

        p3.x += minus;
        p3.y += one;
    }
    protected void s270_0(int one) {
        int two = one + one;
        int minus = -one;
        p2.x += two;

        p1.x += one;
        p1.y += minus;

        p3.x += minus;
        p3.y += minus;
    }
}