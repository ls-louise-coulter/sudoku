import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SudokuApplication {

    public static int gridSize = 9;

    public static void main(String[] args) throws FileNotFoundException {

        String line;
        SudokuService service = new SudokuService();

        if(args.length < 2) {
            System.out.println("Invalid input: not enough arguments provided");
        }

        try {
            boolean useMultiThreading = false;
            if(args[0].equals("1")) {
                useMultiThreading = true;
            }
            String fileName = args[1];

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            line = bufferReader.readLine();

            int[][] unsolvedGrid = new int[gridSize][gridSize];
            int y = 0;

            while(line!= null) {
                int[] rowArray = parseStringIntoArray(line);
                unsolvedGrid[y] = rowArray;
                y++;
                line = bufferReader.readLine();
            }
            bufferReader.close();
            fileReader.close();

            System.out.println();
            service.solve(unsolvedGrid, useMultiThreading);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static int[] parseStringIntoArray(String line) {
        String[] rowAsStringArray = line.split(",", -1);
        int[] row = new int[gridSize];

        for(int i = 0; i < gridSize; i++) {
            if(rowAsStringArray[i].equals("x")) {
                row[i] = 0;
            }
            else {
                row[i] = Integer.parseInt(rowAsStringArray[i]);
            }
        }
        return row;
    }

}
