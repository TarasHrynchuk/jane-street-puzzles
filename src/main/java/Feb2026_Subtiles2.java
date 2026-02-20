import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Feb2026_Subtiles2 {

    private static final String OUTPUT_FILE = "output_1.txt";

    public static void main(String[] args) throws IOException {
        int[][] grid = {
            { 1,  2,  3,  4,  5},
            { 6,  7,  8,  9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
        };

        StringBuilder sb = new StringBuilder();
        for (int[] row : grid) {
            for (int val : row) {
                sb.append(String.format("%3d", val));
            }
            sb.append("\n");
        }

        System.out.print(sb);

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            pw.print(sb);
        }
    }
}
