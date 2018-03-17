package com.ttr.projects.sudokusolver.model;

public class Grid {
    private String name;
    private Cell[][] grid;

    public Grid(String name, Cell[][] grid) {
        this.name = name;
        this.grid = grid;
    }

    public void displayGrid() {
        System.out.println("Grid " + name);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }
}
