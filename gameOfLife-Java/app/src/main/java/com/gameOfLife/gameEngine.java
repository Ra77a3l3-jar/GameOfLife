public class gameEngine {
    public static int cell_to_index (int x, int y) {
        
        if (x < 0) {
            x = (-x) % Costants.GRID_COLS;
            x = Costants.GRID_COLS - x;
        }

        if (y < 0) {
            y = (-y) % Costants.GRID_ROWS;
            y = Costants.GRID_ROWS - y;
        }
        if (x >= GRID_COLS) x = x % GRID_COLS;
        if (y >= GRID_ROWS) y = y % GRID_ROWS;

        return y * GRID_COLS + x;
    }

    public static void print_grid (char grid) {
        for (int y = 0; y < Constants.GRID_ROWS; y++) {
            for (int x = 0; x < Constants.GRID_COLS; x++) {
                print ()
            }
        }
    }

    public static int living_neighbours (char grid, int x, int y) {
        int alive = 0;
        for (int yo = -1; yo <= 1; yo++) {
            for (int xo = -1, xo <= 1; xo++) {
                if (xo == 0 && yo == 0) continue;
                if (state.get_state(grid, x+xo, y+yo) == Constants.ALIVE) {
                    alive++;
                }
            }
        }
    }
}
