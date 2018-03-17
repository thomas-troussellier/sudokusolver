package com.ttr.projects.sudokusolver.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.ttr.projects.sudokusolver.model.Cell;
import com.ttr.projects.sudokusolver.model.Grid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;

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
            System.out.println("File " + file.getName() + " does not exist");
            return null;
        }

        Grid scannedGrid;

        try {
            List<Cell[]> cellLines = Files.lines(file.toPath()).map(line -> {
                String[] intAsString = line.split(",");
                Cell[] cells = new Cell[intAsString.length];
                for (int i = 0; i < intAsString.length; i++) {
                    cells[i] = new Cell(Integer.parseInt(intAsString[i]), IntStream.range(1, intAsString.length).toArray());
                }

                return cells;
            }).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(cellLines)) {
                return null;
            }

            Cell[][] grid = new Cell[cellLines.size()][];
            grid = cellLines.toArray(grid);

            scannedGrid = new Grid(file.getName(), grid);
        } catch (IOException ioe) {
            System.out.println("Failed to parse file " + file.getName());
            return null;
        }

        return scannedGrid;
    }
}
