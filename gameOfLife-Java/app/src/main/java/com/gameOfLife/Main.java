package com.gameoflife;

public class Main {
    public static void main(String[] args) {
        char[] oldGrid = new char[Constants.GRID_CELLS];
        char[] newGrid = new char[Constants.GRID_CELLS];
        
        // Initialize grid with dead cells
        State.setGrid(oldGrid, Constants.DEAD);
        
        // Set initial pattern (glider)
        State.setState(oldGrid, 10, 10, Constants.ALIVE);
        State.setState(oldGrid, 9, 10, Constants.ALIVE);
        State.setState(oldGrid, 11, 10, Constants.ALIVE);
        State.setState(oldGrid, 11, 9, Constants.ALIVE);
        State.setState(oldGrid, 10, 8, Constants.ALIVE);
        
        // Set another pattern
        State.setState(oldGrid, 30, 30, Constants.ALIVE);
        State.setState(oldGrid, 29, 30, Constants.ALIVE);
        State.setState(oldGrid, 31, 30, Constants.ALIVE);
        State.setState(oldGrid, 31, 29, Constants.ALIVE);
        State.setState(oldGrid, 30, 28, Constants.ALIVE);
        
        // Game loop
        while (true) {
            State.newStateGrid(oldGrid, newGrid);
            GameEngine.printGrid(newGrid);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
            
            State.newStateGrid(newGrid, oldGrid);
            GameEngine.printGrid(oldGrid);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
