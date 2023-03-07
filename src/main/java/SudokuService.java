import java.util.ArrayList;
import java.util.List;

public class SudokuService {

    private static int gridSize = SudokuApplication.gridSize;

    public boolean solve(int[][] grid, boolean multiTaskingEnabled) throws Exception {
        for(int row = 0; row < gridSize; row++) {
            for(int column = 0; column < gridSize; column++) {
                if(grid[row][column] == 0) {
                    for(int possible = 1; possible <= gridSize; possible++) {

                        if(multiTaskingEnabled) {
                            MultiTaskingSudokuService multiTasker = new MultiTaskingSudokuService(grid, row, column, possible);
                            if (multiTasker.call()) {
                                grid[row][column] = possible;

                                if (solve(grid, true)) {
                                    return true;
                                } else {
                                    grid[row][column] = 0;
                                }
                            }
                        }
                        else {
                            if (performAllChecks(grid, row, column, possible)) {
                                grid[row][column] = possible;
                                if (solve(grid, false)) {
                                    return true;
                                } else {
                                    grid[row][column] = 0;
                                }
                            }
                        }
                    }
                    return false;
                }
            }
        }
        printSolution(grid);
        return true;
    }

    private static void printSolution(int[][] grid) {
        for(int across = 0; across < gridSize; across++) {
            for (int down = 0; down < gridSize; down++) {
                if(down == gridSize-1) {
                    System.out.println(grid[across][down] + " ");
                } else {
                    System.out.print(grid[across][down] + "  ");
                }
            }
        }
    }

    private boolean performAllChecks(int[][] grid, int row, int column, int possible) {
        return !acrossCheck(grid, row, possible)
                && !downCheck(grid, column, possible)
                && !miniGridCheck(grid, row, column, possible);
    }

    private boolean acrossCheck(int[][]grid, int row, int possible){
        for(int n = 0; n < gridSize; n++) {
            if (grid[row][n] == possible) {
                return true;
            }
        }
        return false;
    }

    private boolean downCheck(int[][]grid, int column, int possible) {
        for(int n = 0; n < gridSize; n++) {
            if(grid[n][column] == possible) {
                return true;
            }
        }
        return false;
    }

    private boolean miniGridCheck(int[][]grid, int row, int column, int possible) {
        int miniGridRowStart = row - row % 3;
        int miniGridColumnStart = column - column % 3;

        for(int x = miniGridRowStart; x < miniGridRowStart+3; x++) {
            for (int y = miniGridColumnStart; y < miniGridColumnStart+3; y++) {
                if(grid[x][y] == possible) {
                    return true;
                }
            }
        }
        return false;
    }
}
