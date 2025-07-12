public class state {
    public static void set_state (char grid, int x, int y, char state) {
        grid [gameEngine.cell_to_index(x, y)] == state;
    }

    public static char get_state (char grid, int x, int y) {
        return grid[gameEngine.cell_to_index(x, y)];
    }

    public static void set_grid (char grid, char state) {
        for (int y = 0; y < Constants.GRID_ROWS; y++) {
            for (int x = 0; x < Constants.GRID_COLS; x++) {
                set_state(grid, x, y, state);
            }
        }
    }

    public static void new_state_grid (char old, char new) {
        for (int y = 0; y < Constants.GRID_ROWS; y++) {
            for (int x = 0; x < Constants.GRID_COLS; x++) {
                int alive = gameEngine.living_neighbours (old, x, y);
                int new_state = Constants.DEAD;
                if (get_state(old, x, y) == Constants.ALIVE) {
                    if (alive == 2 || alive == 3) {
                        new_state = Constants.ALIVE;
                    }
                } else {
                    if (alive == 3) {
                        new_state = Constants.ALIVE;
                    }
                }
            }
        } 
    }
}
