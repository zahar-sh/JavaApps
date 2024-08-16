package fractals;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.util.ArrayDeque;

public class Tree2 implements Fr {
    private Tree2() {}

    public String getStart() {
        return "X";
    }
    public double getDefaultAngle() {
        return 30;
    }
    public double getDefaultStep() {
        return 30;
    }

    public String apply(char key) {
        return switch (key) {
            case 'X' -> "F[@[-X]+X]";
            case 'F' -> "F";
            case '@' -> "@";
            case '[' -> "[";
            case ']' -> "]";
            case '-' -> "-";
            case '+' -> "+";
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public void draw(CharSequence in, GraphicsContext out, double step, double angle) {
        draw(in, out, step, angle, 2.0, 0.2, 4, Color.DARKGREEN);
    }
    public void draw(CharSequence in, GraphicsContext out, double step, double angle,
                     double line, double dl, double ds, Color curColor) {
        out.rotate(-90);
        out.setStroke(curColor);
        out.setLineWidth(line);
        ArrayDeque<Node> stack = new ArrayDeque<>();
        for (int i = 0, l = in.length(); i < l; i++) {
            switch (in.charAt(i)) {
                case 'F', 'X' -> {
                    out.strokeLine(0, 0, step, 0);
                    out.translate(step, 0);
                }
                case '@' -> {
                    out.setLineWidth(out.getLineWidth() - dl);
                    out.setStroke(curColor = curColor.brighter());
                    step -= ds;
                }
                case '[' -> stack.push(new Node(out.getTransform(), step, out.getLineWidth(), curColor));
                case ']' -> {
                    Node n = stack.pop();
                    step = n.step;
                    out.setTransform(n.affine);
                    out.setLineWidth(n.line);
                    out.setStroke(n.color);
                }
                case '-' -> out.rotate(-angle);
                case '+' -> out.rotate(angle);
                default -> throw new IllegalStateException("Unexpected value: " + in.charAt(i));
            }
        }
    }
    public static Tree2 getInstance() {
        return InstanceHolder.instance;
    }
    private static class Node {
        final Affine affine;
        final double step;
        final double line;
        final Color color;

        Node(Affine transform, double step, double line, Color stroke) {
            this.affine = transform;
            this.step = step;
            this.line = line;
            this.color = stroke;
        }
    }
    private static class InstanceHolder {
        static final Tree2 instance = new Tree2();
    }
}