package fractals;

import javafx.scene.canvas.GraphicsContext;

import java.util.function.Consumer;

public interface Fr extends Fun {
    String getStart();
    double getDefaultAngle();
    double getDefaultStep();
    void draw(CharSequence in, GraphicsContext g, double step, double angle);
    default void draw(CharSequence in, GraphicsContext g) {
        draw(in, g, getDefaultStep(), getDefaultAngle());
    }
    default void map(Consumer<String> out, int gen) {
        map(getStart(), this, out, gen);
    }

    static void map(CharSequence in, Fun fun, StringBuilder out) {
        for (int i = 0, l = in.length(); i < l; i++)
            out.append(fun.apply(in.charAt(i)));
    }
    static void map(CharSequence in, Fun fun, Consumer<String> out, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; ; i++) {
            map(in, fun, sb);
            if (i == count) break;
            out.accept((String) (in = sb.toString()));
            sb.setLength(0);
        }
    }
    static void map(CharSequence in, Fun fun, StringBuilder out, int gen) {
        int l = out.length();
        for (int i = 0; ; i++) {
            out.setLength(l);
            map(in, fun, out);
            if (i == gen) break;
            in = out.substring(l);
        }
    }
}