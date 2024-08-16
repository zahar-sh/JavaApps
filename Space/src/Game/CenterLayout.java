package Game;

import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;

/**
 * Менеджер расположения сомпонентов по центру экрана
 */

public class CenterLayout implements LayoutManager {

    /**
     * Расстояние по Y между компонентами
     */

    private int spaceY;

    /**
     * Отступ сверху
     */

    private int top;

    /**
     * Ширина всех компонентов
     */

    private int width;

    /**
     * Высота всех компонентов
     */

    private int height;

    /**
     * Высота и ширина
     */

    private Dimension size = new Dimension();

    /**
     *
     * @param spaceY  расстояние по Y между компонентами
     * @param top  отступ сверху
     * @param width ширина всех компонентов
     * @param height высота всех компонентов
     * @param size размер окна
     */

    public CenterLayout(int spaceY, int top,
                        int width, int height, Dimension size) {
        this.top = top;
        this.spaceY = spaceY;
        this.width = width;
        this.height = height;
        this.size.width = size.width;
        this.size.height = size.height;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(size);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(size);
    }

    @Override
    public void layoutContainer(Container parent) {
        Component[] list = parent.getComponents();
        int current = top;
        for (Component component : list) {
            Dimension d = component.getMinimumSize();
            int w = width == 0 ? d.width : width;
            int h = height == 0 ? d.height : height;
            component.setBounds((size.width - w) / 2, current, w, h);
            current += h + spaceY;
        }
    }
}