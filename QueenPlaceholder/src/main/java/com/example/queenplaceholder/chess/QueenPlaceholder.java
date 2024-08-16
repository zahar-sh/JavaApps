package com.example.queenplaceholder.chess;

import java.util.Arrays;

public class QueenPlaceholder {

    private final int width;

    private final int height;

    private final int[] queenStack;

    private int stackSize;

    public QueenPlaceholder(int width, int height) {
        this.width = width;
        this.height = height;
        this.queenStack = new int[height];
        this.stackSize = 0;

        queenStack[stackSize++] = 0;
    }

    public boolean hasNext() {
        if (stackSize != height) {
            return true;
        }
        for (int i = 0; i < stackSize; i++) {
            int queenX = queenStack[i];
            if (queenX != (width - 1)) {
                return true;
            }
        }
        return false;
    }

    public void next() {
        if (!isFull() && !isLastQueenIntersected()) {
            queenStack[stackSize++] = 0;
        } else {
            while (stackSize > 0) {
                int lastQueenX = getLastQueenX();
                int lastQueenY = getLastQueenY();

                int queenX = lastQueenX + 1;
                if (queenX < width) {
                    queenStack[lastQueenY] = queenX;
                    break;
                } else {
                    stackSize--;
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getQueenStack() {
        return queenStack;
    }

    public boolean isFull() {
        return stackSize == queenStack.length;
    }

    public boolean isLastQueenIntersected() {
        return intersects(getLastQueenX(), getLastQueenY(), queenStack);
    }

    public boolean isFound() {
        return isFull() && !isLastQueenIntersected();
    }

    public int getLastQueenX() {
        return queenStack[getLastQueenY()];
    }

    public int getLastQueenY() {
        return stackSize - 1;
    }

    public static boolean intersects(int qx1, int qy1, int qx2, int qy2) {
        return qx1 == qx2 || qy1 == qy2
                || Math.abs(qx1 - qx2) == Math.abs(qy1 - qy2);
    }

    private static boolean intersects(int lastQueenX, int lastQueenY, int[] queenStack) {
        for (int queenY = 0; queenY < lastQueenY; queenY++) {
            int queenX = queenStack[queenY];
            if (intersects(lastQueenX, lastQueenY, queenX, queenY)) {
                return true;
            }
        }
        return false;
    }
}
