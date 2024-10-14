import java.util.*;

public class Board {
    private final int width;
    private final int height;
    private String[][] board;
    private static final int[] X_COORD_CHANGE = {0, 1, 1, 1, 0, -1, -1, -1};
    private static final int[] Y_COORD_CHANGE = {1, 1, 0, -1, -1, -1, 0, 1};

    private Board(int width, int height, String[][] board) {
        this.width = width;
        this.height = height;
        this.board = board;
    }

    public static Board generateBoard(int width, int height, int x, int y, List<List<String>> data) {
        String[][] board = new String[height][width];
        for (int i = 0; i < data.size(); i++) {
            //iterates through the rows of data
            List<String> currRow = data.get(i);
            for (int j = 0; j < currRow.size(); j++) {
                //iterates through columns, will encounter either space or *
                board[i + y][j + x] = currRow.get(j);
            }
        }

        //Replace all spaces and empty strings with underscore to indicate no life
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null || board[i][j].equals(" ")) {
                    board[i][j] = "_";
                }
            }
        }
        return new Board(width, height, board);
    }

    private int getNeighbours(int x, int y) {
        int num = 0;
        for (int i = 0; i < X_COORD_CHANGE.length; i++) {
            int posX = x + X_COORD_CHANGE[i];
            int posY = y + Y_COORD_CHANGE[i];

            if ((posX < 0) || (posY < 0) || (posX >= width) || (posY >= height)) {
                continue;
            } else if (board[posY][posX].equals("*")) {
                num++;
            }
            
        }
        return num;
    }

    private String checkLife(String curr, int neighbours) {
        if (curr.equals("_") && (neighbours == 3)) {
            return "*";
        } else if ((neighbours >= 2) && (neighbours <= 3) && (curr.equals("*"))) {
            return "*";
        }
        return "_";
    }

    public Board nextGen() {
        String[][] newBoard = new String[height][width];
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                int neighbours = getNeighbours(j, i);
                String nextRound = checkLife(board[i][j], neighbours);
                newBoard[i][j] = nextRound;

            }
        }
        return new Board(width, height, newBoard);
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();
        for (String[] row : this.board) {
            for (String pt : row) {
                sb.append(pt + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
