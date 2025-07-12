package com.gameoflife;

public class State {
    
    public static void setState(char[] grid, int x, int y, char state) {
        grid[GameEngine.cellToIndex(x, y)] = state;
    }

    public static char getState(char[] grid, int x, int y) {
        return grid[GameEngine.cellToIndex(x, y)];
    }

    public static void setGrid(char[] grid, char state) {
        for (int y = 0; y < Constants.GRID_ROWS; y++) {
            for (int x = 0; x < Constants.GRID_COLS; x++) {
                setState(grid, x, y, state);
            }
        }
    }

    public static void newStateGrid(char[] old, char[] newGrid) {
        for (int y = 0; y < Constants.GRID_ROWS; y++) {
            for (int x = 0; x < Constants.GRID_COLS; x++) {
                int alive = GameEngine.livingNeighbours(old, x, y);
                char newState = Constants.DEAD;
                
                if (getState(old, x, y) == Constants.ALIVE) {
                    if (alive == 2 || alive == 3) {
                        newState = Constants.ALIVE;
                    }
                } else {
                    if (alive == 3) {
                        newState = Constants.ALIVE;
                    }
                }
                setState(newGrid, x, y, newState);
            }
        }
    }
}
