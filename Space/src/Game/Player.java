package Game;

import MyPackage.Images;
import MyPackage.RotatedImage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Корабль игрока
 * RotatedImage - может вращаться
 */

public class Player extends RotatedImage implements Ship {

    /**
     * Оружие
     */

    private Arm arm;

    /**
     * Вражеский корабль на который происходит самонаведение ракет
     */
    private Enemy enemy;

    /**
     * Хилки
     */

    private int heal;

    public int i = 0;

    public Player(int x, int y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
        arm = new Arm(this, 20,
                Constants.DOT_SIZE_X / 4, Constants.DOT_SIZE_Y, 3, 20,
                Images.convertToBufferedImage(Images.getImage("img\\rocket2.png")));
        newGame();
    }

    public void newGame() {
        heal = 400;
    }

    /**
     * Прорисовка
     *
     * @param g Graphics
     */

    @Override
    public void draw(Graphics g) {
        arm.draw(g);
        if (isCrash())
            return;
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.RED);
        g2D.setFont(Constants.FONT);
        g2D.drawString(String.valueOf(heal), (int) x, (int) y);
        g2D.setColor(Color.WHITE);
        g2D.drawString(String.valueOf(i), Constants.DOT_SIZE_X * 4, Constants.DOT_SIZE_Y * 4);
        if (enemy != null) {
            if (!enemy.isCrash()) {
                g2D.setColor(Color.CYAN);
                g2D.setStroke(Constants.BASIC_STROKE);
                int[] a = {(int) enemy.x, (int) (enemy.x + enemy.width),
                        (int) (enemy.x + enemy.width), (int) enemy.x, (int) enemy.x};
                int[] b = {(int) enemy.y, (int) enemy.y, (int) (enemy.y + enemy.height),
                        (int) (enemy.y + enemy.height), (int) enemy.y};
                g2D.drawPolyline(a, b, a.length);
            }
        }
        super.draw(g);
    }

    /**
     * Обновление оружия
     * Если если захваценный корабль, происжодит самонаведение ракет
     *
     * @param enemies список вражеских кораблей которые нужно проверить на попадание
     */

    @Override
    public void update(Ship[] enemies) {
        if (isCrash()) return;
        if (enemy != null) {
            if (enemy.isCrash()) {
                enemy = null;
            } else {
                arm.homing(enemy.getX(), enemy.getY());
            }
        }
        i += arm.update(enemies);
    }

    @Override
    public void shot(int x, int y) {
        if (isCrash()) return;
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

    /**
     * Захват цели
     *
     * @param enemy вражеский корабль
     */

    public void homing(Enemy enemy) {
        this.enemy = enemy;
    }

    public void ultra() {
        arm.ultra();
    }
}