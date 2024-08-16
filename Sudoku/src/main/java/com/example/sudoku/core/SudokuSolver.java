package com.example.sudoku.core;

public class SudokuSolver {
    public void solve(Sudoku sudoku) {
        solve(sudoku, 0, 0);
    }

    private boolean solve(Sudoku sudoku, int x, int y) {
        if (x == sudoku.boardDimension()) {
            x = 0;
            y++;
            if (y == sudoku.boardDimension()) {
                return true;
            }
        }
        if (sudoku.contains(x, y)) {
            return solve(sudoku, x + 1, y);
        }
        for (int number = 1; number <= sudoku.boardDimension(); number++) {
            if (sudoku.set(x, y, number)) {
                if (solve(sudoku, x + 1, y)) {
                    return true;
                }
                sudoku.clear(x, y);
            }
        }
        return false;
    }
}
