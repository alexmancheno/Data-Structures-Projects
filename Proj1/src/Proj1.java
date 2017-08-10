/**
 * Created by Alex Mancheno on 4/2/17.
 */
import java.io.*;
import java.util.Scanner;

public class Proj1 {

    public static void main (String[] args) {

        try {
            File input = new File(args[0]);
            File output = new File(args[1]);
            Scanner reader = new Scanner(input);
            FileWriter writer = new FileWriter(output);
            LargeNumbers[] array = new LargeNumbers[3];
            String line;

            for (int i = 0; reader.hasNextLine(); i++) {
                line = reader.nextLine();
                if ((i % 3) != 2) {
                    array[i % 3] = new LargeNumbers(line);
                }
                 else {
                    writer.write("pair: " + array[0] + " and " + array[1] + "\n");
                    writer.write("sum: " + LargeNumbers.add(array[0], array[1]) + "\n");
                    writer.write("sum: " + LargeNumbers.multiply(array[0], array[1]) + "\n\n");
                }

            }
            writer.write("sum: " + LargeNumbers.add(array[0], array[1]) + "\n");
            writer.write("sum: " + LargeNumbers.multiply(array[0], array[1]) + "\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("There was an IOException: " + e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println("There was another kind of exception: " + e.getLocalizedMessage());
        }
    }
}
