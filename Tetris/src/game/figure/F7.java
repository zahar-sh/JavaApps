package game.figure;

import javafx.scene.paint.Color;

public class F7 extends StatedFigure {
    public F7(Color color) {
        super(1, 0, 0, 1, 1, 1, 2, 1, color);
    }
    public F7(Color color, byte state) {
        super(1, 0, 0, 1, 1, 1, 2, 1, color, state);
    }

    protected void s0_90(int one) {
        int minus = -one;
        p1.x += one;
        p1.y += one;

        p2.x += one;
        p2.y += minus;

        p4.x += minus;
        p4.y += one;
    }
    protected void s90_180(int one) {
        int minus = -one;
        p1.x += minus;
        p1.y += one;

        p2.x += one;
        p2.y += one;

        p4.x += minus;
        p4.y += minus;
    }
    protected void s180_270(int one) {
        int minus = -one;
        p4.x += one;
        p4.y += minus;

        p2.x += minus;
        p2.y += one;

        p1.x += minus;
        p1.y += minus;
    }
    protected void s270_0(int one) {
        int minus = -one;
        p4.x += one;
        p4.y += one;

        p2.x += minus;
        p2.y += minus;

        p1.x += one;
        p1.y += minus;
    }
}