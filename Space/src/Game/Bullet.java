package Game;

import MyPackage.RotatedImage;

import java.awt.image.BufferedImage;

/**
 * Пуля
 * RotatedImage - может вращаться
 */

public class Bullet extends RotatedImage {

    /**
     * Смещение по x и y
     */

    private double dx, dy;

    /**
     * Скорость и урон
     */
    public int speed, damage;

    /**
     * Существует ли пуля
     */

    public boolean isWorks;

    /**
     * Создание пули
     * @param width ширина пули
     * @param height высота пули
     * @param speed скорость движения
     * @param damage урон
     * @param image изображение
     */
    public Bullet(int width, int height, int speed, int damage, BufferedImage image) {
        super(width, height, image);
        this.speed = speed;
        this.damage = damage;
    }

    /**
     * Выстел, определение угла наклона и скорости движение
     * @param x координата по X
     * @param y координата по Y
     */

    public void shot(double x, double y) {
        x -= this.x;
        y -= this.y;

        double distance = (Math.sqrt(x * x + y * y));

        radians = x > 0 ? -Math.acos(y / distance) : Math.acos(y / distance);
        dx = speed * x / distance;
        dy = speed * y / distance;
    }

    /**
     * Движение пули
     */

    public void update() {
        x += dx;
        y += dy;
    }

    @Override
    public int hashCode() {
        long bits = super.hashCode() + (isWorks ? 17 : 0);
        bits = bits * 31 + speed;
        bits = bits * 31 + damage;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Bullet) {
            Bullet bullet = (Bullet) obj;
            return (x == bullet.x) && (y == bullet.y) &&
                    (width == bullet.width) && (height == bullet.height) &&
                    (speed == bullet.speed) && (damage == bullet.damage) &&
                    (radians == bullet.radians) && (image.equals(bullet.image));
        }
        return false;
    }
}