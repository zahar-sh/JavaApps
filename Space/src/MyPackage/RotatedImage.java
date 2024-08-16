package MyPackage;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RotatedImage extends MyImage {

    protected final Rectangle realBounds = new Rectangle();
    protected double radians;

    public RotatedImage(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    public RotatedImage(int width, int height, BufferedImage image) {
        this(0, 0, width, height, image);
    }

    public RotatedImage(BufferedImage image) {
        this(0, 0, image.getWidth(), image.getHeight(), image);
    }

    public void setRadians(double radians) {
        this.radians = radians;
    }

    public double getRadians() {
        return radians;
    }

    public Rectangle getRealBounds() {
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        realBounds.width = (int) (cos * width + sin * height);
        realBounds.height = (int) (sin * width + cos * height);

        realBounds.x = (int) (x - (realBounds.width - width) / 2);
        realBounds.y = (int) (y - (realBounds.height - height) / 2);
        return realBounds;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.translate(x, y);
        g2D.rotate(radians, width / 2.0, height / 2.0);

        g2D.drawImage(image, 0, 0, (int) width, (int) height, null);

        g2D.rotate(-radians, width / 2.0, height / 2.0);
        g2D.translate(-x, -y);
    }

    @Override
    public boolean contains(double x, double y) {
        Rectangle r = getRealBounds();
        x -= r.x;
        y -= r.y;
        return (x >= 0 && x <= r.width) &&
                (y >= 0 && y <= r.height);
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        bits ^= Double.doubleToLongBits(y);
        bits = bits * 31 + Double.doubleToLongBits(width);
        bits = bits * 31 + Double.doubleToLongBits(height);
        bits ^= Double.doubleToLongBits(radians);
        bits = bits * 31 + image.hashCode();
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof RotatedImage) {
            RotatedImage bullet = (RotatedImage) obj;
            return (x == bullet.x) && (y == bullet.y) &&
                    (width == bullet.width) && (height == bullet.height) &&
                    (radians == bullet.radians) && (image.equals(bullet.image));
        }
        return false;
    }
}