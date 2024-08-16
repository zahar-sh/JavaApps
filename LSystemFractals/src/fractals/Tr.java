package fractals;

import javafx.scene.canvas.GraphicsContext;

public class Tr implements Fr {
    private Tr() {
    }

    public String getStart() {
        return "F-G-G";
    }
    public double getDefaultAngle() {
        return 120;
    }
    public double getDefaultStep() {
        return 10;
    }

    public String apply(char key) {
        return switch (key) {
            case 'F' -> "F-G+F+G-F";
            case 'G' -> "GG";
            case '-' -> "-";
            case '+' -> "+";
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public void draw(CharSequence in, GraphicsContext out, double step, double angle) {
        for (int i = 0, l = in.length(); i < l; i++) {
            switch (in.charAt(i)) {
                case 'F', 'G' -> {
                    out.strokeLine(0, 0, step, 0);
                    out.translate(step, 0);
                }
                case '-' -> out.rotate(-angle);
                case '+' -> out.rotate(angle);
                default -> throw new IllegalStateException("Unexpected value: " + in.charAt(i));
            }
        }
    }

    public static Tr getInstance() {
        return InstanceHolder.instance;
    }
    private static class InstanceHolder {
        static final Tr instance = new Tr();
    }
}