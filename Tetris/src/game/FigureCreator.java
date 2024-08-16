package game;

import game.figure.*;
import javafx.scene.paint.Color;

public abstract class FigureCreator {
    public static final FigureCreator[] FACTORY = {
            new FigureCreator(Color.YELLOW, Color.VIOLET, Color.RED) {
                public IteratedFigure ofColor(Color color) {
                    return new F1(color);
                }
            },
            new FigureCreator(Color.CYAN, Color.BLUE, Color.GREEN) {
                public IteratedFigure ofColor(Color color) {
                    return new F2(color);
                }
            },
            new FigureCreator(Color.BLUE, Color.GREEN, Color.CYAN) {
                public IteratedFigure ofColor(Color color) {
                    return new F3(color);
                }
            },
            new FigureCreator(Color.ORANGE, Color.RED, Color.VIOLET) {
                public IteratedFigure ofColor(Color color) {
                    return new F4(color);
                }
            },
            new FigureCreator(Color.GREEN, Color.CYAN, Color.BLUE) {
                public IteratedFigure ofColor(Color color) {
                    return new F5(color);
                }
            },
            new FigureCreator(Color.RED, Color.ORANGE, Color.YELLOW) {
                public IteratedFigure ofColor(Color color) {
                    return new F6(color);
                }
            },
            new FigureCreator(Color.VIOLET, Color.YELLOW, Color.ORANGE) {
                public IteratedFigure ofColor(Color color) {
                    return new F7(color);
                }
            },
    };
    public static IteratedFigure createFigure(int lines) {
        int random = (int) (Math.random() * 7);
        int colorType;
        if (lines < 10) colorType = 0;
        else if (lines < 20) colorType = 1;/*
        else if (lines < 40) colorType = 2;
        else if (lines < 60) colorType = 3;
        else if (lines < 100) colorType = 4;*/
        else colorType = 2;
        return FACTORY[random].ofIndex(colorType);
    }

    private final Color[] colors;
    public FigureCreator(Color... colors) {
        if (colors == null)
            throw new NullPointerException();
        this.colors = colors;
    }

    public int size() {
        return colors.length;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= colors.length)
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + colors.length);
    }
    private Color get(int index) {
        checkIndex(index);
        return colors[index];
    }
    public IteratedFigure ofIndex(int index) {
        return ofColor(get(index));
    }
    public abstract IteratedFigure ofColor(Color value);
}