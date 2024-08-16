package fractals;

import javafx.scene.canvas.GraphicsContext;

public class Koha implements Fr {
    private Koha() {
    }

    public String getStart() {
        return "F++F++F";
    }
    public double getDefaultAngle() {
        return 60;
    }
    public double getDefaultStep() {
        return 7;
    }

    public String apply(char key) {
        return switch (key) {
            case 'F' -> "F-F++F-F";
            case '-' -> "-";
            case '+' -> "+";
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public void draw(CharSequence in, GraphicsContext out, double step, double angle) {
        for (int i = 0, l = in.length(); i < l; i++) {
            switch (in.charAt(i)) {
                case 'F' -> {
                    out.strokeLine(0, 0, step, 0);
                    out.translate(step, 0);
                }
                case '-' -> out.rotate(-angle);
                case '+' -> out.rotate(angle);
                default -> throw new IllegalStateException("Unexpected value: " + in.charAt(i));
            }
        }
    }

    public static Koha getInstance() {
        return InstanceHolder.instance;
    }
    private static class InstanceHolder {
        static final Koha instance = new Koha();
    }
}