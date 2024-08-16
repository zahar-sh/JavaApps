package Game;

import java.awt.Point;
import java.awt.Graphics;

/**
 * interface корабля
 * Определение общих свойство объектов
 */

public interface Ship {

    /**
     * Они должны быть прорисованы
     *
     * @param g Graphics
     */

    void draw(Graphics g);

    /**
     * Движение, обновление, проверка на попадание
     *
     * @param ships список корабдей которые нужно проверить на попадание
     */

    void update(Ship[] ships);

    /**
     * Выстел
     *
     * @param x координата по X
     * @param y координата по Y
     */

    void shot(int x, int y);

    /**
     * Находится ли точка в пределах корабля
     *
     * @param x координата по X
     * @param y координата по Y
     * @return результат
     */

    boolean contains(double x, double y);

    /**
     * @return координата по X
     */

    int getX();

    /**
     * @return координата по Y
     */

    int getY();

    /**
     * @return координата по X и Y центральной точки корабля
     */

    Point getCenter();

    /**
     * @param damage нанесение урона
     */

    void hit(int damage);

    /**
     * @return работает ли корабль
     */

    boolean isCrash();

    /**
     * @param bound диапозон в double
     * @return случайное целое число
     */

    default int randomInt(double bound) {
        return (int) (Math.random() * bound);
    }
}