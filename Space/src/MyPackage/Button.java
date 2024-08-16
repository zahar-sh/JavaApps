package MyPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Button extends StaticImage {
    public Color color;
    public Font font;
    public String string;
    private BufferedImage pressedImage;
    private boolean pressed;
    public boolean released;

    public Button(int x, int y, int width, int height, BufferedImage image,
                  Color color, Font font, String string) {
        super(x, y, width, height, image);
        this.color = (color == null ? Color.BLACK : color);
        this.font = (font == null ? new Font("Arial", Font.BOLD, height / 2) : font);
        this.string = (string == null ? "" : string);
        pressedImage = this.image;
    }

    public Button(int width, int height, BufferedImage image,
                  Color color, Font font, String string) {
        this(0, 0, width, height, image, color, font, string);
    }

    public Button(BufferedImage image, Color color, Font font, String string) {
        this(0, 0, 0, 0, image, color, font, string);
    }

    public void setPressedImage(BufferedImage image) {
        if (image == null) throw new NullPointerException();
        else pressedImage = image;
    }

    public void mousePressed(MouseEvent e) {
        pressed = contains(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        if (pressed) {
            released = contains(e.getX(), e.getY());
            pressed = false;
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (pressed) {
            pressed = contains(e.getX(), e.getY());
        }
    }

    @Override
    public void draw(Graphics g) {
        if (pressed) {
            g.drawImage(pressedImage, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            super.draw(g);
        }
        g.setColor(color);
        g.setFont(font);
        Rectangle2D rect = g.getFontMetrics().getStringBounds(string, g);
        Point p = getCenter();
        g.drawString(string, (int) (p.x - rect.getWidth() / 2), (int) (p.y + rect.getHeight() / 4));
    }
}