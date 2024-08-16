package com.example.sudoku.core;

import java.util.Arrays;
import java.util.BitSet;

public class Sudoku {

    private static final byte EMPTY = Byte.MAX_VALUE;

    private final int boardDimension;

    private final byte[] board;

    private final BitSet rows;

    private final BitSet columns;

    private final BitSet squares;

    private final int squareDimension;

    public Sudoku(int boardDimension, int squareDimension) {
        if (boardDimension < 0) {
            throw new IllegalArgumentException("Invalid boardDimension: " + boardDimension);
        }
        if (squareDimension * squareDimension != boardDimension) {
            throw new IllegalArgumentException("Invalid squareDimension: " + squareDimension);
        }
        this.boardDimension = boardDimension;
        this.squareDimension = squareDimension;
        int matrixLength = boardDimension * boardDimension;
        byte[] board = new byte[matrixLength];
        Arrays.fill(board, EMPTY);
        this.board = board;
        this.rows = new BitSet(matrixLength);
        this.columns = new BitSet(matrixLength);
        this.squares = new BitSet(matrixLength);
    }

    public Sudoku() {
        this(9, 3);
    }

    private int boardIndex(int x, int y) {
        return y * boardDimension + x;
    }

    private int rowIndex(int y, int offset) {
        return y * boardDimension + offset;
    }

    private int columnIndex(int x, int offset) {
        return x * boardDimension + offset;
    }

    private int squareIndex(int x, int y, int offset) {
        int squareIndex = (y / squareDimension) * squareDimension + (x / squareDimension);
        return squareIndex * boardDimension + offset;
    }

    private void checkIndex(int x, int y) {
        if (!(x >= 0 && x < boardDimension)) {
            throw new IndexOutOfBoundsException("Invalid x: " + x);
        }
        if (!(y >= 0 && y < boardDimension)) {
            throw new IndexOutOfBoundsException("Invalid y: " + y);
        }
    }

    private void checkNumber(int number) {
        if (!(number >= 1 && number <= boardDimension)) {
            throw new IllegalArgumentException("Invalid number: " + number);
        }
    }

    public int boardDimension() {
        return boardDimension;
    }

    public boolean contains(int x, int y) {
        return get(x, y) != null;
    }

    public Integer get(int x, int y) {
        checkIndex(x, y);
        int boardIndex = boardIndex(x, y);
        byte offset = board[boardIndex];
        if (offset == EMPTY) {
            return null;
        }
        return offset + 1;
    }

    public boolean set(int x, int y, int number) {
        checkIndex(x, y);
        checkNumber(number);
        int offset = number - 1;
        int boardIndex = boardIndex(x, y);
        if (board[boardIndex] == offset) {
            return false;
        }
        int rowIndex = rowIndex(y, offset);
        if (rows.get(rowIndex)) {
            return false;
        }
        int columnIndex = columnIndex(x, offset);
        if (columns.get(columnIndex)) {
            return false;
        }
        int squareIndex = squareIndex(x, y, offset);
        if (squares.get(squareIndex)) {
            return false;
        }
        board[boardIndex] = (byte) offset;
        rows.set(rowIndex);
        columns.set(columnIndex);
        squares.set(squareIndex);
        return true;
    }

    public void clear(int x, int y) {
        checkIndex(x, y);
        int boardIndex = boardIndex(x, y);
        byte offset = board[boardIndex];
        if (offset == EMPTY) {
            return;
        }
        int rowIndex = rowIndex(y, offset);
        int columnIndex = columnIndex(x, offset);
        int squareIndex = squareIndex(x, y, offset);
        board[boardIndex] = EMPTY;
        rows.clear(rowIndex);
        columns.clear(columnIndex);
        squares.clear(squareIndex);
    }

    public void clear() {
        Arrays.fill(board, EMPTY);
        rows.clear();
        columns.clear();
        squares.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sudoku sudoku = (Sudoku) o;
        return Arrays.equals(board, sudoku.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int y = 0; y < boardDimension; y++) {
            sb.append('[');
            for (int x = 0; x < boardDimension; x++) {
                Integer number = get(x, y);
                String string = number == null ? "-" : number.toString();
                sb.append(string).append(',');
            }
            sb.setLength(sb.length() - 1);
            sb.append(']').append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }
}
