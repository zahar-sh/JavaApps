package game.figure;

import javafx.scene.paint.Color;

public class F6 extends StatedFigure {
    public F6(Color color) {
        super(0, 0, 1, 0, 1, 1, 2, 1, color);
    }
    public F6(Color color, byte state) {
        super(0, 0, 1, 0, 1, 1, 2, 1, color, state);
    }

    protected void s0_90(int one) {
        int two = one + one;
        int minus = -one;
        p1.x += two;

        p2.x += one;
        p2.y += one;

        p4.x += minus;
        p4.y += one;
    }
    protected void s90_180(int one) {
        int two = one + one;
        int minus = -one;
        p1.y += two;

        p2.x += minus;
        p2.y += one;

        p4.x += minus;
        p4.y += minus;
    }
    protected void s180_270(int one) {
        int two = one + one;
        int minus = -one;
        p4.x += one;
        p4.y += minus;

        p2.x += minus;
        p2.y += minus;

        p1.x += -two;
    }
    protected void s270_0(int one) {
        int two = one + one;
        int minus = -one;
        p4.x += one;
        p4.y += one;

        p2.x += one;
        p2.y += minus;

        p1.y += -two;
    }
}