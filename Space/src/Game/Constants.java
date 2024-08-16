package Game;

import java.awt.*;

public class Constants {

    /**
     * Определение размера экрана
     */

    public static final Dimension DISPLAY_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Количество условных точек по X
     */

    public static final int X = 32;

    /**
     * Количество условных точек по Y
     */

    public static final int Y = 18;

    /**
     * Интервал обновления игры
     */

    public static final int DELAY = 10;

    /**
     * Ширина экрана
     */

    public static final int WIDTH = DISPLAY_SIZE.width;

    /**
     * Высота экрана
     */

    public static final int HEIGHT = DISPLAY_SIZE.height;

    /**
     * Ширина условной точки
     */

    public static final int DOT_SIZE_X = WIDTH / X;

    /**
     * Высота условной точки
     */

    public static final int DOT_SIZE_Y = HEIGHT / Y;

    /**
     * Ширина корабля
     */

    public static final int SHIP_SIZE_X = DOT_SIZE_X * 7 / 5;

    /**
     * Высота корабля
     */

    public static final int SHIP_SIZE_Y = DOT_SIZE_Y * 7 / 5;

    /**
     * Скорость прохождения одной условной точки по X с учетом интервала обновления игры
     */

    public static final int SPEED_X = DOT_SIZE_X * DELAY / 1000;

    /**
     * Скорость прохождения одной условной точки по Y с учетом интервала обновления игры
     */

    public static final int SPEED_Y = DOT_SIZE_Y * DELAY / 1000;

    /**
     * Шрифт
     */

    public static final Font FONT = new Font("Arial", Font.BOLD, DOT_SIZE_Y / 2);

    /**
     * Ширина линии
     */

    public static final BasicStroke BASIC_STROKE = new BasicStroke(3F);
}