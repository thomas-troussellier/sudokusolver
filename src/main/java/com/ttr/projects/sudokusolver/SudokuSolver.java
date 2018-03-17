package com.ttr.projects.sudokusolver;

import java.util.List;

import com.ttr.projects.sudokusolver.loader.DirectoryLoading;
import com.ttr.projects.sudokusolver.model.Grid;

public class SudokuSolver {
    public static void main(String[] args) {
        // First step load grids
        List<Grid> grids = DirectoryLoading.loadGridsFromDirectory(args[0]);

        grids.stream().forEach(Grid::displayGrid);
    }
}
