import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Proj3 {
    static int ROW = 10;
    static int COL = 10;

    public static void main (String[] args) {
        int[][] maze = new int[ROW][COL];

        try {
            FileReader input = new FileReader("input.txt");
            BufferedReader reader = new BufferedReader(input);
            String line;
            int row = 0, mazeNumber = 1;

            while (true) {
                line = reader.readLine();
                if (line != null && line.length() > 0) {
                    StringTokenizer st = new StringTokenizer(line, " ");
                    for (int col = 0; col < 10; col++)
                        maze[row][col] = Integer.parseInt(st.nextToken());
                    row++;
                } else {
                    row = 0;    // Reset row number after reading in entire maze.
                    System.out.println(String.format("Attempting to find solution to maze number %d:", mazeNumber));
                    if (!findWay(maze, 0, 0, 0, -1)) {
                        System.out.println(String.format("No solution found to maze number %d.", mazeNumber));
                    } else {
                        System.out.println(String.format("Solution to maze number %d found: ", mazeNumber));
                        printMaze(maze);
                    }

                    maze = new int[ROW][COL];   // Recreate new maze after attempting to find solution.
                    mazeNumber++;
                    System.out.println();   // To space out the output to every attempted maze.
                }

                if (line == null) break;
            }
        } catch (Exception e) {
            printError(e);     // Wrote static helper method below to help debug issues.
        }
    }

    // The order for searching the path is: east, south, west, and finally north.
    public static boolean findWay(int maze[][], int r, int c, int prevR, int prevC) {

        if (r == ROW - 1 && c == COL - 1) {
            maze[r][c] = 2; // Mark path before returning that a solution was found.
            return true;
        }

        if (r < 0 || c < 0 || r == ROW || c == COL) {
            return false;   // When out of bounds.
        } else if (maze[r][c] == 1 || maze[r][c] == 2) {
            return false;   // Hit either a wall or already-marked position.
        } else if (amountOfOptions(maze, r, c) == 0) {
            /* If the amount of options at current position is 0, then that means the current
            * position is a dead and backtracking must happen.
            */
            System.out.println(String.format("Start backtracking from [%d, %d]", r, c));
            System.out.println(String.format("Backtracking from [%d, %d] to [%d, %d]", r, c, prevR, prevC));
            return false;
        }

        maze[r][c] = 2; // Mark location before attempting to go to next location.

        boolean found;

        // Try to go east
        found = findWay(maze, r, c + 1, r, c);
        if (found) return true;

        // Try to go south
        found = findWay(maze, r + 1, c, r ,c);
        if (found) return true;

        // Try to go west
        found = findWay(maze, r, c - 1, r, c);
        if (found) return true;

        //Try to go north
        found = findWay(maze, r - 1, c, r, c);
        if (found) return true;

        // Unmark location and begin backtracking if current path showed no promise.
        System.out.println(String.format("Backtracking from [%d, %d] to [%d, %d]", r, c, prevR, prevC));;
        maze[r][c] = 0;
        return false;
    }

    /* Method to help detect whether current position is dead-end or not (so that I know when to
    *  print the "beginning to backtrack" message.
    */
    public static int amountOfOptions(int maze[][], int r, int c) {
        int options = 0;

        // Check right
        if (c < COL - 1 && c >= 0 && r >= 0 && r < ROW)
            if (maze[r][c + 1] != 1 && maze[r][c + 1] != 2)
                options++;

        // Check down
        if (r + 1 < ROW && r >= 0 && c >= 0 && c < COL)
            if (maze[r + 1][c] != 1 && maze[r + 1][c] != 2)
                options++;

        // Check left
        if (c - 1 > 0 && c < COL && r >= 0 && r < ROW)
            if (maze[r][c - 1] != 1 && maze[r][c - 1] != 2)
                options++;


        // Check up
        if (r - 1 >= 0 && r < ROW && c >= 0 && c < COL)
            if (maze[r - 1][c] != 1 && maze[r - 1][c] != 2)
                options++;

        return options;
    }

    public static void printMaze(int[][] maze) {
        for (int r = 0; r < 10; r++) {
            for(int c = 0; c < 10; c++) {
                System.out.print(maze[r][c] + " ");
            }
            System.out.println();
        }
    }

    // For debugging purposes.
    public static void printError(Exception e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        printWriter.flush();
        String stackTrace = writer.toString();
        System.out.println(stackTrace);
    }
}
