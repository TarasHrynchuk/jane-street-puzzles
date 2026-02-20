import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Feb2026_Subtiles2 {

    private static final String OUTPUT_FILE = "output_1.txt";

    public static void main(String[] args) throws IOException {
        // Grid is 5 rows x 5 cols
        // Formulas (0 means empty cell):
        // Row 0: [0, 3x,    0,  y/x,  0    ]
        // Row 1: [0, 0,     2x+1, 0,  0    ]
        // Row 2: [x^2, 0,   0,  0,    y-1  ]
        // Row 3: [0, x-1,   0,  y,    0    ]
        // Row 4: [y-x, 0,   0,  0,    x^3-y]

        StringBuilder sb = new StringBuilder();
        int found = 0;

        for (int x = 1; x <= 6; x++) {
            for (int y = 1; y <= 6; y++) {
                // Compute all formula values
                int r0c1 = 3 * x;
                int r0c3_num = y, r0c3_den = x; // y/x must be positive integer
                int r1c2 = 2 * x + 1;
                int r2c0 = x * x;
                int r2c4 = y - 1;
                int r3c1 = x - 1;
                int r3c3 = y;
                int r4c0 = y - x;
                int r4c4 = x * x * x - y;

                // Check all must be positive integers (y/x: divisible and > 0)
                if (r0c3_den == 0 || r0c3_num % r0c3_den != 0) continue;
                int r0c3 = r0c3_num / r0c3_den;

                // Constraint #1: all values must be between 1 and 6
                if (r0c1 < 1 || r0c1 > 6 || r0c3 < 1 || r0c3 > 6 ||
                    r1c2 < 1 || r1c2 > 6 || r2c0 < 1 || r2c0 > 6 ||
                    r2c4 < 1 || r2c4 > 6 || r3c1 < 1 || r3c1 > 6 ||
                    r3c3 < 1 || r3c3 > 6 || r4c0 < 1 || r4c0 > 6 ||
                    r4c4 < 1 || r4c4 > 6) continue;

                // Constraint #2: value N may appear at most N times
                int[] vals = {r0c1, r0c3, r1c2, r2c0, r2c4, r3c1, r3c3, r4c0, r4c4};
                int[] counts = new int[7];
                for (int v : vals) counts[v]++;
                boolean valid = true;
                for (int n = 1; n <= 6; n++) {
                    if (counts[n] > n) { valid = false; break; }
                }
                if (!valid) continue;

                found++;
                sb.append(String.format("x=%d, y=%d%n", x, y));

                int[][] grid = {
                    {0,     r0c1, 0,     r0c3,  0   },
                    {0,     0,    r1c2,  0,     0   },
                    {r2c0,  0,    0,     0,     r2c4},
                    {0,     r3c1, 0,     r3c3,  0   },
                    {r4c0,  0,    0,     0,     r4c4}
                };

                for (int[] row : grid) {
                    for (int val : row) {
                        sb.append(String.format("%5d", val));
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
        }

        if (found == 0) sb.append("No valid (x, y) pairs found.\n");

        System.out.print(sb);

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            pw.print(sb);
        }
    }
}
