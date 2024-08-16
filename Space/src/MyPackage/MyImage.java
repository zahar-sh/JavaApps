package MyPackage;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MyImage {
    private final Point point = new Point();
    public double x, y, width, height;
    public BufferedImage image;

    public MyImage(double x, double y, double width, double height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public double distance(int x, int y) {
        Point p = getCenter();
        x -= p.x;
        y -= p.y;
        return Math.sqrt(x * x + y * y);
    }

    public Point getCenter() {
        point.x = (int) (x + width / 2);
        point.y = (int) (y + height / 2);
        return point;
    }

    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersects(MyImage myImage) {
        double tw = this.width;
        double th = this.height;
        double rw = myImage.width;
        double rh = myImage.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        double tx = this.x;
        double ty = this.y;
        double rx = myImage.x;
        double ry = myImage.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;

        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public abstract void draw(Graphics g);

    public abstract boolean contains(double x, double y);
}