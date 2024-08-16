package fractals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import java.util.ArrayDeque;

public class Tree implements Fr {
    private Tree() {
    }

    public String getStart() {
        return "X";
    }
    public double getDefaultAngle() {
        return 22.5;
    }
    public double getDefaultStep() {
        return 7;
    }

    public String apply(char key) {
        return switch (key) {
            case 'X' -> "F[+X]F[-X]+X";
            case 'F' -> "FF";
            case '[' -> "[";
            case ']' -> "]";
            case '-' -> "-";
            case '+' -> "+";
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public void draw(CharSequence in, GraphicsContext out, double step, double angle) {
        out.rotate(-90);
        ArrayDeque<Affine> stack = new ArrayDeque<>();
        for (int i = 0, l = in.length(); i < l; i++) {
            switch (in.charAt(i)) {
                case 'F', 'X' -> {
                    out.strokeLine(0, 0, step, 0);
                    out.translate(step, 0);
                }
                case '[' -> stack.push(out.getTransform());
                case ']' -> out.setTransform(stack.pop());
                case '-' -> out.rotate(-angle);
                case '+' -> out.rotate(angle);
                default -> throw new IllegalStateException("Unexpected value: " + in.charAt(i));
            }
        }
    }

    public static Tree getInstance() {
        return InstanceHolder.instance;
    }
    private static class InstanceHolder {
        static final Tree instance = new Tree();
    }
}