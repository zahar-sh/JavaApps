package Game;

import MyPackage.Images;
import MyPackage.MyImage;
import MyPackage.StaticImage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class World {

    private StaticImage[] background = new StaticImage[25];

    private static final int border = Constants.WIDTH / 4;
    public static final int speed = 10;
    public static long x = 0, y = 0;
    private ArrayList<StaticImage> objects = new ArrayList<>();

    public World() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                background[5 * j + i] = new StaticImage(Constants.WIDTH * (i - 2), Constants.HEIGHT * (j - 2),
                        Constants.WIDTH, Constants.HEIGHT,
                        Images.convertToBufferedImage(Images.getImage("img\\fon.png")));
            }
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("worldObjects.txt");
        assert inputStream != null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String string;
            while ((string = in.readLine()) != null) {
                String[] strings = string.split(" ");
                objects.add(new StaticImage(Integer.parseInt(strings[0]) * Constants.DOT_SIZE_X,
                        Integer.parseInt(strings[1]) * Constants.DOT_SIZE_Y,
                        Integer.parseInt(strings[2]) * Constants.DOT_SIZE_X,
                        Integer.parseInt(strings[3]) * Constants.DOT_SIZE_Y,
                        Images.convertToBufferedImage(Images.getImage("img\\" + strings[4]))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int x, int y) {
        World.x -= x;
        World.y -= y;

        for (StaticImage back : background) {
            back.y -= (double) y / speed;
            back.x -= (double) x / speed;
        }

        for (StaticImage staticImage : objects) {
            staticImage.translate(-x, -y);
        }
    }

    public void draw(Graphics g) {
        for (StaticImage back : background) {
            back.draw(g);
        }
        for (StaticImage staticImage : objects) {
            staticImage.draw(g);
        }
    }

    public static boolean intersects(MyImage myImage) {
        int tw = Constants.WIDTH + 2 * border;
        int th = Constants.HEIGHT + 2 * border;
        int rw = (int) myImage.width;
        int rh = (int) myImage.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = (int) (World.x + border);
        int ty = (int) (World.y + border);
        int rx = (int) myImage.x;
        int ry = (int) myImage.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;

        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }
}