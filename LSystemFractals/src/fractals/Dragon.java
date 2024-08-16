package fractals;

import javafx.scene.canvas.GraphicsContext;

public class Dragon implements Fr {
    private Dragon() {
    }

    public String getStart() {
        return "FX";
    }
    public double getDefaultAngle() {
        return 90;
    }
    public double getDefaultStep() {
        return 10;
    }

    public String apply(char key) {
        return switch (key) {
            case 'X' -> "X+YF+";
            case 'Y' -> "-FX-Y";
            case 'F' -> "F";
            case '-' -> "-";
            case '+' -> "+";
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public void draw(CharSequence in, GraphicsContext out, double step, double angle) {
        for (int i = 0, l = in.length(); i < l; i++) {
            switch (in.charAt(i)) {
                case 'X', 'Y', 'F' -> {
                    out.strokeLine(0, 0, step, 0);
                    out.translate(step, 0);
                }
                case '-' -> out.rotate(-angle);
                case '+' -> out.rotate(angle);
                default -> throw new IllegalStateException("Unexpected value: " + in.charAt(i));
            }
        }
    }

    public static Dragon getInstance() {
        return InstanceHolder.instance;
    }
    private static class InstanceHolder {
        static final Dragon instance = new Dragon();
    }
}