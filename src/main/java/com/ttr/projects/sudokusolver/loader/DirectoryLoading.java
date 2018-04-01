package com.ttr.projects.sudokusolver.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.ttr.projects.sudokusolver.model.Cell;
import com.ttr.projects.sudokusolver.model.Grid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import static com.ttr.projects.sudokusolver.model.SudokuGridConstants.*;

public class DirectoryLoading {
    private static String[] GRID_FILES_EXTENTIONS = {"grid"};

    public static List<Grid> loadGridsFromDirectory(final String directoryPath) {

        final File folder = new File(directoryPath);
        if (!folder.exists()) {
            System.out.println("Directory " + directoryPath + " does not exist");
        }

        return FileUtils.listFiles(folder, GRID_FILES_EXTENTIONS, false).stream().map(DirectoryLoading::convertGridFileToGrid).filter(Objects::nonNull).collect(Collectors.toList
                ());
    }

    private static Grid convertGridFileToGrid(File file) {
        if (!file.exists()) {
            System.err.printf("File %s does not exist", file.getName());
            return null;
        }

        Grid scannedGrid;

        try {
            List<String> lines = Files.lines(file.toPath()).filter(line -> {
                // remove lines starting with //
                boolean comment = StringUtils.startsWith(line, "//");
                // remove blank lines
                boolean blankLine = StringUtils.isBlank(line);

                return !(comment || blankLine);
            }).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(lines)) {
                System.err.printf("File did not contain a grid");
                return null;
            }

            if (lines.size() != GRID_ROW_NUMBER) {
                System.err.printf("Grid does not have %s rows", GRID_ROW_NUMBER);
                return null;
            }

            List<Cell> grid = IntStream.range(0, lines.size()).mapToObj(row -> {
                String[] intAsString = lines.get(row).split(",");
                List<Cell> cells = new ArrayList<>();
                for (int column = 0; column < intAsString.length; column++) {
                    // row and column +1 because zero based indexes
                    cells.add(new Cell(Integer.parseInt(intAsString[column]), new ArrayList<>(CELLS_POSSIBLE_VALUES), row + 1, column + 1));
                }
                return cells;
            }).flatMap(List::stream).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(grid)) {
                System.err.printf("Failed to parse grid, grid was empty");
                return null;
            }

            if (grid.size() != GRID_CELL_NUMBER) {
                System.err.printf("Grid did not have %s cells", GRID_CELL_NUMBER);
                return null;
            }

            scannedGrid = new Grid(file.getName(), grid);
        } catch (IOException ioe) {
            System.err.printf("Failed to parse file %s with error %s", file.getName(), ioe.getMessage());
            return null;
        }

        return scannedGrid;
    }
}
