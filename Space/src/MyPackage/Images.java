package MyPackage;

import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URL;

public class Images {

    public static BufferedImage rotateImage(Image image, double angle) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        return rotate(image, width, height, angle);
    }

    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();
        return rotate(image, width, height, angle);
    }

    private static BufferedImage rotate(Image image, int width, int height, double angle) {
        double radian = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radian));
        double cos = Math.abs(Math.cos(radian));

        int nWidth = (int) (width * cos + height * sin);
        int nHeight = (int) (height * cos + width * sin);

        BufferedImage result = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = result.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.translate((nWidth - width) / 2, (nHeight - height) / 2);
        graphics.rotate(radian, width / 2.0, height / 2.0);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return result;
    }

    public static BufferedImage convertToBufferedImage(Image image) {
        BufferedImage result = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = result.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return result;
    }

    public static Image getImage(String name) {
        URL url = Images.class.getClassLoader().getResource(name);
        if (url == null) throw new NullPointerException("Wrong filename " + name);
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon.getImage();
    }
}
