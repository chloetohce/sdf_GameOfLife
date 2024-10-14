import java.io.*;
import java.util.*;

public class GameOfLife {
    /*
     * Handles user input. 
     * Contains a board class that handles the game.
     */
    private Board board;
    private int width;
    private int height;

    private GameOfLife(int width, int height, int x, int y, List<List<String>> data) {
        this.board = Board.generateBoard(width, height, x, y, data);
        this.width = width;
        this.height = height;
    }

    private GameOfLife(int width, int height, Board board) {
        this.board = board;
        this.width = width;
        this.height = height;
    }

    private static GameOfLife of(File f) throws IOException {
        // Gets arguments from file
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        int width = 0;
        int height= 0;
        int x = 0;
        int y = 0;
        List<List<String>> data = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("#")) {
                continue;
            }
            
            if (line.startsWith("GRID")) {
                String[] grid = line.split(" ");
                width = Integer.parseInt(grid[1]);
                height = Integer.parseInt(grid[2]);
            } else if (line.startsWith("START")) {
                String[] start = line.split(" ");
                x = Integer.parseInt(start[1]);
                y = Integer.parseInt(start[2]);
            } else if (line.startsWith("DATA")) {
                int i = 0;
                while ((line = br.readLine()) != null) {
                    Arrays.asList(line);
                    data.add(Arrays.asList(line.split("")));
                }
            }
        }
        br.close();
        return new GameOfLife(width, height, x, y, data);
    }

    public void play(int n) {
        
        if (n != 0) {
            this.board.printBoard();
            System.out.println();
            
            GameOfLife nextGen = new GameOfLife(width, height, board.nextGen());
            nextGen.play(n - 1);
        } else {
            System.out.println("======== END OF GAME ========");
        }        
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 0) {
            System.out.println("Please enter file.");
            System.exit(-1);
        }

        String fileName = args[0];
        File f = new File(fileName);

        GameOfLife gol = GameOfLife.of(f);
        gol.play(5);
        
    }
}