package game.figure;

import javafx.scene.paint.Color;

public class F2 extends StatedFigure {
    public F2(Color color) {
        super(0, 0, 1, 0, 2, 0, 3, 0, color);
    }
    public F2(Color color, byte state) {
        super(0, 0, 1, 0, 2, 0, 3, 0, color, state);
    }

    protected void s0_90(int one) {
        int two = one + one;
        int minus = -one;
        p1.x += two;
        p1.y += minus;

        p2.x += one;

        p3.y += one;

        p4.x += minus;
        p4.y += two;
    }
    protected void s90_180(int one) {
        int two = one + one;
        int minus = -one;
        p1.x += one;
        p1.y += two;

        p2.y += one;

        p3.x += minus;

        p4.x += -two;
        p4.y += minus;
    }
    protected void s180_270(int one) {
        int minusTwo = -(one + one);
        int minusOne = -one;
        p4.x += one;
        p4.y += minusTwo;

        p3.y += minusOne;

        p2.x += minusOne;

        p1.x += minusTwo;
        p1.y += one;
    }
    protected void s270_0(int one) {
        int two = one + one;
        int minus = -one;
        p4.x += two;
        p4.y += one;

        p3.x += one;

        p2.y += minus;

        p1.x += minus;
        p1.y += -two;
    }
}