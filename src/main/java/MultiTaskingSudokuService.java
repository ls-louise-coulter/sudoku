import java.util.concurrent.Callable;

public class MultiTaskingSudokuService implements Callable<Boolean> {

    private static int gridSize = SudokuApplication.gridSize;

    int[][] grid;
    int row;
    int column;
    int possible;

    public MultiTaskingSudokuService(int[][] grid, int row, int column, int possible) {
        this.grid = grid;
        this.row = row;
        this.column = column;
        this.possible = possible;
    }

    @Override
    public Boolean call() throws Exception {
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
