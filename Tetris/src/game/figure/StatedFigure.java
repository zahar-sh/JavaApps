package game.figure;

import javafx.scene.paint.Color;

public abstract class StatedFigure extends AbstractFigure {
    public static final byte s0 = 0;
    public static final byte s90 = 1;
    public static final byte s180 = 2;
    public static final byte s270 = 3;
    private byte state;

    public StatedFigure(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color color) {
        super(x1, y1, x2, y2, x3, y3, x4, y4, color);
        this.state = s0;
    }
    public StatedFigure(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color color, byte initialState) {
        super(x1, y1, x2, y2, x3, y3, x4, y4, color);
        if (initialState < 0 || initialState > 3)
            throw new IllegalArgumentException();
        this.state = initialState;
    }

    public final byte getState() {
        return state;
    }

    public final void rotateLeft() {
        switch (state) {
            case s0:
                s270_0(-1);
                state = s270;
                break;
            case s270:
                s180_270(-1);
                state = s180;
                break;
            case s180:
                s90_180(-1);
                state = s90;
                break;
            case s90:
                s0_90(-1);
                state = s0;
                break;
        }
    }
    public final void rotateRight() {
        switch (state) {
            case s0:
                s0_90(1);
                state = s90;
                break;
            case s90:
                s90_180(1);
                state = s180;
                break;
            case s180:
                s180_270(1);
                state = s270;
                break;
            case s270:
                s270_0(1);
                state = s0;
                break;
        }
    }

    protected abstract void s0_90(int one);
    protected abstract void s90_180(int one);
    protected abstract void s180_270(int one);
    protected abstract void s270_0(int one);
}