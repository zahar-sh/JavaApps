package Game;

import MyPackage.Images;
import MyPackage.RotatedImage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Вражеский корабль
 * RotatedImage - может вращаться
 */

public class Enemy extends RotatedImage implements Ship {

    /**
     * Отступы по краям экрана
     */

    private static final int left = 4 * Constants.DOT_SIZE_X;
    private static final int right = 4 * Constants.DOT_SIZE_X;
    private static final int top = 4 * Constants.DOT_SIZE_Y;
    private static final int bottom = 4 * Constants.DOT_SIZE_Y;

    /**
     * Оружие
     */

    private Arm arm;

    /**
     * Скорость по X, Y и хилки корабля
     */

    private int speedX = 1, speedY = 1;

    public int heal;

    /**
     * Создание корабля и оружия
     * @param width ширина корабля
     * @param height высота корабля
     * @param image изображение
     */

    public Enemy(int width, int height, BufferedImage image) {
        super(width, height, image);
        arm = new Arm(this, 7,
                Constants.DOT_SIZE_X / 4, Constants.DOT_SIZE_Y, 3, 10,
                Images.convertToBufferedImage(Images.getImage("img\\rocket.png")));
    }

    /**
     * создание корабля
     * определение координат появления
     */

    public void createShip() {
        heal = 150;
        setLocation(left + randomInt(Constants.WIDTH - left - right - width),
                top + randomInt(Constants.HEIGHT - top - bottom - height));
    }

    /**
     * Прорисовка корабля
     * рисуем оружие чтобы если корабль сломался пули все равно летели
     * рисуем хилки
     */

    @Override
    public void draw(Graphics g) {
        arm.draw(g);
        if (isCrash()) return;
        super.draw(g);
        g.setColor(Color.RED);
        g.setFont(Constants.FONT);
        g.drawString(String.valueOf(heal), (int) x, (int) y);
    }

    /**
     * Движение корабля в заданном диапазоне + рандомная смена движения
     * обновление оружия чтобы если корабль сломался пули все равно летели.
     * Определения угла поворота в сторону выстрела.
     * Стреляем по игрокам
     */

    @Override
    public void update(Ship[] players) {
        arm.update(players);
        if (isCrash()) {
            if (randomInt(30000.0 / Constants.DELAY) == 0)
                createShip();
            return;
        }
        for (Ship ship : players) {
            Player player = (Player) ship;
            if (x <= left || x + width + right > Constants.WIDTH ||
                    randomInt(20000.0 / Constants.DELAY) == 0) {
                speedX = -speedX;
            }
            if (y <= top || y + height + bottom > Constants.HEIGHT ||
                    randomInt(20000.0 / Constants.DELAY) == 0) {
                speedY = -speedY;
            }
            if (randomInt(5000.0 / Constants.DELAY) == 0) {
                if (randomInt(50.0 / Constants.DELAY) == 0) {
                    arm.ultra();
                } else {
                    Point p = player.getCenter();
                    //делаем разброс
                    shot(p.x + randomInt(1000.0 / Constants.DELAY),
                            p.y + randomInt(1000.0 / Constants.DELAY));
                }
            }
            translate(speedX, speedY);

            double a = player.getX() - this.x;
            double b = player.getY() - this.y;
            double distance = (Math.sqrt(a * a + b * b));
            radians = a > 0 ? -Math.acos(b / distance) : Math.acos(b / distance);
        }
    }

    @Override
    public void shot(int x, int y) {
        arm.shot(x, y);
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public void hit(int damage) {
        heal -= damage;
    }

    @Override
    public boolean isCrash() {
        return heal <= 0;
    }
}