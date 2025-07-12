package com.gameoflife;

public class GameEngine {
    
    public static int cellToIndex(int x, int y) {
        if (x < 0) {
            x = (-x) % Constants.GRID_COLS;
            x = Constants.GRID_COLS - x;
        }
        if (y < 0) {
            y = (-y) % Constants.GRID_ROWS;
            y = Constants.GRID_ROWS - y;
        }
        if (x >= Constants.GRID_COLS) x = x % Constants.GRID_COLS;
        if (y >= Constants.GRID_ROWS) y = y % Constants.GRID_ROWS;

        return y * Constants.GRID_COLS + x;
    }

    public static void printGrid(char[] grid) {
        System.out.print("\033[2J\033[H"); // Clear screen
        for (int y = 0; y < Constants.GRID_ROWS; y++) {
            for (int x = 0; x < Constants.GRID_COLS; x++) {
                System.out.print(grid[cellToIndex(x, y)]);
            }
            System.out.println();
        }
    }

    public static int livingNeighbours(char[] grid, int x, int y) {
        int alive = 0;
        for (int yo = -1; yo <= 1; yo++) {
            for (int xo = -1; xo <= 1; xo++) {
                if (xo == 0 && yo == 0) continue;
                if (State.getState(grid, x + xo, y + yo) == Constants.ALIVE) {
                    alive++;
                }
            }
        }
        return alive;
    }
}
