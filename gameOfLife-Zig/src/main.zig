const std = @import("std");
const print = std.debug.print;

const GRID_COLS = 40;
const GRID_ROWS = 40;
const GRID_CELS = (GRID_COLS * GRID_ROWS);
const ALIVE = '*';
const DEAD = '.';

pub fn cell_to_index(x: usize, y: usize) usize {
    if (x < 0) {
        x = @mod(x, GRID_COLS);
        if (x < 0) x += GRID_COLS;
    }

    if (y < 0) {
        y = @mod(y, GRID_ROWS);
        if (y < 0) y += GRID_ROWS;
    }

    if (x >= GRID_COLS) x = @mod(x, GRID_COLS);
    if (y >= GRID_ROWS) y = @mod(y, GRID_ROWS);

    return y * GRID_COLS + x;
}

pub fn set_state(grid: []u8, x: usize, y: usize, state: u8) void {
    grid[cell_to_index(x, y)] = state;
}

pub fn get_state(grid: []u8, x: usize, y: usize) u8 {
    return grid[cell_to_index(x, y)];
}

pub fn print_grid(grid: []u8) void {
    var y: usize = 0;

    print("\x1b[2J\x1b[H", .{});
    while (y < GRID_ROWS) {
        var x: usize = 0;
        while (x < GRID_COLS) {
            print("{c}", .{get_state(grid, x, y)});
            x = x + 1;
        }
        print("\n", .{});
        y = y + 1;
    }
}

pub fn set_grid(grid: []u8, state: u8) void {
    var y: usize = 0;

    while (y < GRID_ROWS) {
        var x: usize = 0;
        while (x < GRID_COLS) {
            set_state(grid, x, y, state);
            x = x + 1;
        }
        y = y + 1;
    }
}

pub fn living_neighbours(grid: []u8, x: usize, y: usize) i8 {
    var alive: u8 = 0;
    var yo: usize = -1;

    while (yo <= 1) {
        var xo: usize = -1;
        while (xo <= 1) {
            if (xo == 0 and yo == 0) continue;
            if (get_state(grid, x + xo, y + yo) == ALIVE) {
                alive += 1;
            }
            xo = xo + 1;
        }
        yo = yo + 1;
    }
    return alive;
}

pub fn new_state_grid(old: []u8, new: []u8) void {
    var y: usize = 0;

    while (y < GRID_ROWS) {
        var x: usize = 0;
        while (x < GRID_COLS) {
            const alive: u8 = living_neighbours(old, x, y);
            var new_state: u8 = DEAD;
            if (get_state(old, x, y) == ALIVE) {
                if (alive == 2 or alive == 3) {
                    new_state = ALIVE;
                }
            } else {
                if (alive == 3) {
                    new_state = ALIVE;
                }
            }
            set_state(new, x, y, new_state);
            x = x + 1;
        }
        y = y + 1;
    }
}

pub fn main() !void {
    var old_grid: [GRID_CELS]u8 = undefined;
    var new_grid: [GRID_CELS]u8 = undefined;

    set_grid(&old_grid, DEAD);
    set_state(&old_grid, 10, 10, ALIVE);
    set_state(&old_grid, 9, 10, ALIVE);
    set_state(&old_grid, 11, 10, ALIVE);
    set_state(&old_grid, 11, 9, ALIVE);
    set_state(&old_grid, 10, 8, ALIVE);

    set_state(&old_grid, 30, 10, ALIVE);
    set_state(&old_grid, 31, 10, ALIVE);
    set_state(&old_grid, 32, 10, ALIVE);

    var counter: i8 = 0;

    while (true) {
        new_state_grid(&old_grid, &new_grid);
        print_grid(&new_grid);
        try std.time.sleep(100_000_000);
        new_state_grid(&new_grid, &old_grid);
        print_grid(&old_grid);
        try std.time.sleep(100_000_000);
        print("Generation: {}\n", .{counter});
        counter += 1;
    }
}
