
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Proj2 {

    final static int row = 9;
    final static int col = 61;
    final static int offset = col / 2;

    public static void main(String[] args) {

        BinaryTree t = new BinaryTree();
        String[][] plottedTree = new String[row][col];
        try {
            FileReader input = new FileReader(args[0]); // Read in first parameter from the command line ('input.txt')
            File output = new File(args[1]);            // Read in second parameter from command line ('output.txt')
            BufferedReader reader = new BufferedReader(input); // Put read in file into BufferedReader
            String line;
            while ((line = reader.readLine()) != null) { // Read in line before evaluating expression.
                System.out.println("Sequence: " + line);

                t = buildBinaryTree(t, line);            // Build the binary tree after reading in sequence.

                plotArray(t, plottedTree, offset, 4, 0); // Take the newly built binary tree and plot to array.

                printArray(plottedTree, output);                 // Print the plotted array tree.

                plottedTree = new String[row][col]; // Clear array for the next line of input.
                t.makeEmpty();                      // Clear the binary tree for the next line of input.
                line = reader.readLine();           // Read in the empty line.
            }
        } catch (Exception e) {
            printError(e);     // Wrote static helper method below to help debug issues.
        }
    }

    // To build the binary tree from the sequence given by the input line.
    public static BinaryTree buildBinaryTree(BinaryTree t, String line) {
        StringTokenizer st = new StringTokenizer(line, " ");
        while (st.hasMoreTokens()) {
            try {
                t = BinaryTree.insert(t, Integer.parseInt(st.nextToken()));
            } catch (Exception e) {
                printError(e);
            }
        }
        return t;
    }

    /*Function to plot binary tree into 2D; the pattern I found while drawing it out on a board is that the amount
    * of spaces between a node, edge, and a root is some power of two (save for the last row).
    */
    public static String[][] plotArray(BinaryTree t, String[][] arr, int offset, int power, int height) {
        if (t.getRootObj() == null) return arr; // Stop recursion when there are no more nodes to plot.
        else {
            arr[height][offset] = t.getRootObj().toString(); // Else, plot string representation of object onto array.
            {
                if (t.getLeft().getRootObj() != null) { // If there is a left non-empty subtree.
                    arr[height + 1][(offset - (int) (Math.pow(2, power - 1)))] = "/"; // Plot slash.
                    plotArray(t.getLeft(), arr, (int) (offset - Math.pow(2, power)), power - 1, height + 2); // Go to left subtree.
                }
                if (t.getRight().getRootObj() != null) { // If there is a right non-empty subtree.
                    arr[height + 1][offset + (int) (Math.pow(2, power - 1))] = "\\"; // Plot slash.
                    plotArray(t.getRight(), arr, (int) (offset + Math.pow(2, power)), power - 1, height + 2); // Go to right subtree.
                }
            }
        }
        return arr;
    }

    /* Function to print the array; it includes my attempt to take into account that
    * some of the nodes take up two spaces (when it holds a two digit number): it
    * appends a space to the line only if a two digit number was not just appended.
    */
    public static void printArray(String[][] array, File output) {
        try{
            FileWriter fw = new FileWriter(output, true); // Append to file
            fw.write("--------------------------------------------------------------------------------"
            + "\n");
            for (int i = 0; i < array.length; i++) {
                StringBuilder sb = new StringBuilder();
                boolean appendSpace = true;
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] != null) {
                        sb.append(array[i][j]);
                        if (array[i][j].length() == 2) appendSpace = false;
                    }
                    if (appendSpace) sb.append(" ");
                    appendSpace = true;
                }
                System.out.println(sb.toString());
                fw.write(sb.toString() + "\n");
            }
            fw.close(); // File must be closed to save changes.
        } catch (Exception e) {
            printError(e);
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
