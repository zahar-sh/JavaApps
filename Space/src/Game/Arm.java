package Game;

import MyPackage.Images;
import MyPackage.MyImage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Arm {

    /**
     * Ссылка на корабль который стреляет
     */

    protected Ship ship;

    /**
     * Массив пуль
     */

    protected Bullet[] bullets;

    /**
     * Индекс не рабочей пули
     */

    protected int indexDisabledBullet;

    /**
     * Не рабочая пуля не определена
     */

    protected static final int NONE = -1;

    /**
     * @param ship         ссылка на корабль который управляет оружием
     * @param capacity     максимальное количество летящих пуль
     * @param bulletWidth  ширина пули
     * @param bulletHeight высота пули
     * @param speed        скорость
     * @param damage       урон
     * @param image        изображение
     * @throws IllegalArgumentException если длинна массива не положительная
     * @throws NullPointerException     если управляющий корабль null
     */

    public Arm(Ship ship, int capacity,
               int bulletWidth, int bulletHeight,
               int speed, int damage, BufferedImage image) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal capacity " + capacity);
        bullets = new Bullet[capacity];
        if (ship == null)
            throw new NullPointerException("Ship can't be null");
        this.ship = ship;
        indexDisabledBullet = NONE;
        for (int i = 0; i < bullets.length; i++) {
            Bullet bullet = new Bullet(bulletWidth, bulletHeight, speed, damage, Images.convertToBufferedImage(image));
            bullet.isWorks = false;
            bullets[i] = bullet;
        }
    }

    /**
     * Определение позиции выстрела.
     * Настройка пули, угол и движение.
     *
     * @param x координата выстрела по X
     * @param y координата выстрела по Y
     */

    public void shot(int x, int y) {
        setIndexDeletedBullet();
        if (indexDisabledBullet != NONE) {
            Bullet bullet = bullets[indexDisabledBullet];
            Point p = ship.getCenter();
            bullet.setLocation(p.x, p.y);
            bullet.shot(x, y);
            bullet.isWorks = true;
            indexDisabledBullet = NONE;
        }
    }

    /**
     * Метод поиска нерабочей пули
     */

    private void setIndexDeletedBullet() {
        for (int i = 0; i < bullets.length; i++) {
            if (!bullets[i].isWorks) {
                indexDisabledBullet = i;
                return;
            }
        }
        indexDisabledBullet = NONE;
    }

    /**
     * Прорисовка движущихся пуль
     *
     * @param g Graphics
     */

    public void draw(Graphics g) {
        for (Bullet bullet : bullets) {
            if (bullet.isWorks)
                bullet.draw(g);
        }
    }

    /**
     * Проверка на попадание, движение и проверка на выслет за границы экрана
     *
     * @param ships массив кораблей которые нужно проверить на попадание
     */

    public int update(Ship[] ships) {
        int i = 0;
        for (Bullet bullet : bullets) {
            if (bullet.isWorks) {
                for (Ship ship : ships) {
                    MyImage myImage = (MyImage) ship;
                    if (myImage.intersects(bullet) && !ship.isCrash()) {
                        ship.hit(bullet.damage);
                        bullet.isWorks = false;
                        if (ship.isCrash()) i++;
                    }
                }
                bullet.update();
            }
            if (!(bullet.x >= 0 && bullet.x <= Constants.WIDTH &&
                    bullet.y >= 0 && bullet.y <= Constants.HEIGHT)) {
                bullet.isWorks = false;
            }
        }
        return i;
    }

    /**
     * Перенаправление движения пули
     *
     * @param x координата выстрела по X
     * @param y координата выстрела по Y
     */

    public void homing(int x, int y) {
        for (Bullet bullet : bullets) {
            if (bullet.isWorks)
                bullet.shot(x, y);
        }
    }

    public void ultra() {
        for (int i = 0; i < bullets.length; i++) {
            shot((int) (Math.random() * 1000), (int) (Math.random() * 1000));
        }
    }
}