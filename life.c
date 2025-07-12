#include <stdio.h>
#include <unistd.h>

#define GRID_ROWS 50
#define GRID_COLS 50
#define GRID_CELS (GRID_ROWS * GRID_COLS)
#define ALIVE '*'
#define DEAD '.'

/* Changes the x,y grid position in the linear array, implementing
 * wrapping, so negative and positive values can be used. */
int cell_to_index(int x, int y) {
    if (x < 0) {
        x = (-x) % GRID_COLS;
        x = GRID_COLS - x;
    }
    if (y < 0) {
        y = (-y) % GRID_ROWS;
        y = GRID_ROWS - y;
    }
    if (x >= GRID_COLS) x = x % GRID_COLS;
    if (y >= GRID_ROWS) y = y % GRID_ROWS;

    return y*GRID_COLS+x;
}

/* the functions sets the state of the cell at cords x, y */
void set_state (char* grid, int x, int y, char state) {
    grid[cell_to_index(x, y)] = state;
}

/* the function returns the state fo the cell at cords x, y */
char get_state (char* grid, int x, int y) {
    return grid[cell_to_index(x, y)];
}

/* the function prints the state of the entire grid */
void print_grid (char* grid) {
    printf("\x1b[3J\x1b[H\x1b[2J"); // clears the screen so game is full screen
    for (int y = 0; y < GRID_ROWS; y++) {
        for (int x = 0; x < GRID_COLS; x++) {
            printf ("%c",get_state(grid, x, y));
        }
        printf ("\n");
    }
}

/* this function sets the state of the entire grid at the begining of the game */
void set_grid (char* grid, char state) {
    for (int y = 0; y < GRID_ROWS; y++) {
        for (int x = 0; x < GRID_COLS; x++) {
            set_state(grid, x, y, state);       
        }
    }
}

/* counts the number of living neighbours around our cell */
int living_neighbours (char* grid, int x, int y) {
    int alive = 0;
    for (int yo = -1; yo <= 1; yo++) {
        for (int xo = -1; xo <= 1; xo++) {
            if (xo == 0 && yo == 0) continue; // so it does not count him self al a neighbour
            if (get_state(grid, x+xo, y+yo) == ALIVE) {
                alive ++;
            }
        }
    }
    return alive;
}

void new_state_grid (char *old, char *new) {
    for (int y = 0; y < GRID_ROWS; y++) {
        for (int x = 0; x < GRID_COLS; x++) {
            int alive = living_neighbours(old, x, y);
            int new_state = DEAD;
            if (get_state(old, x, y) == ALIVE) {
                if (alive == 2 || alive == 3) {
                    new_state = ALIVE;
                }
            } else {
                if (alive == 3) {
                    new_state = ALIVE;
                }
            }
            set_state(new, x, y, new_state);
        }
    }
}

int main (void) {

    char old_grid[GRID_CELS];
    char new_grid[GRID_CELS];

    set_grid(old_grid, DEAD);
    set_state(old_grid, 10, 10, ALIVE);
    set_state(old_grid, 9, 10, ALIVE);
    set_state(old_grid, 11, 10, ALIVE);
    set_state(old_grid, 11, 9, ALIVE);
    set_state(old_grid, 10, 8, ALIVE);

    set_state(old_grid, 30, 30, ALIVE);
    set_state(old_grid, 29, 30, ALIVE);
    set_state(old_grid, 31, 30, ALIVE);
    set_state(old_grid, 31, 29, ALIVE);
    set_state(old_grid, 30, 28, ALIVE);

    
    while (1) {
        new_state_grid(old_grid, new_grid);
        print_grid(new_grid);
        usleep(100000);
        new_state_grid(new_grid, old_grid);
        print_grid(old_grid);
        usleep(100000);
    }
    return 0;
}
