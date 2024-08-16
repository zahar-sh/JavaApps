package MyPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StaticImage extends MyImage {

    public StaticImage(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    public StaticImage(int width, int height, BufferedImage image) {
        this(0, 0, width, height, image);
    }

    public StaticImage(BufferedImage image) {
        this(0, 0, image.getWidth(), image.getHeight(), image);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }

    @Override
    public boolean contains(double x, double y) {
        x -= this.x;
        y -= this.y;
        return (x >= 0 && x <= width) &&
                (y >= 0 && y <= height);
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        bits ^= Double.doubleToLongBits(y);
        bits = bits * 31 + Double.doubleToLongBits(width);
        bits = bits * 31 + Double.doubleToLongBits(height);
        bits = bits * 31 + image.hashCode();
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof StaticImage) {
            StaticImage si = (StaticImage) obj;
            return (x == si.x) && (y == si.y) &&
                    (width == si.width) && (height == si.height) &&
                    (image.equals(si.image));
        }
        return false;
    }
}