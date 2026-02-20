import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class FormulaResult {
    enum Reason { OK, NOT_INTEGER, OUT_OF_RANGE, NOT_FINITE }

    final double raw;
    final int value;
    final boolean valid;
    final Reason reason;

    private FormulaResult(double raw, int value, boolean valid, Reason reason) {
        this.raw = raw;
        this.value = value;
        this.valid = valid;
        this.reason = reason;
    }

    static FormulaResult of(double raw) {
        if (Double.isNaN(raw) || Double.isInfinite(raw))
            return new FormulaResult(raw, 0, false, Reason.NOT_FINITE);
        int iv = (int) Math.round(raw);
        if (Math.abs(raw - iv) > 1e-9)
            return new FormulaResult(raw, iv, false, Reason.NOT_INTEGER);
        if (iv < 1 || iv > 17)
            return new FormulaResult(raw, iv, false, Reason.OUT_OF_RANGE);
        return new FormulaResult(raw, iv, true, Reason.OK);
    }
}

public class Feb2026_Subtiles2_13 {

    private static final String OUTPUT_FILE = "output_2.txt";

    // Grid is 13 rows x 13 cols (0-indexed)
    // Formula positions from image:
    // r0,c4:  6c-4b
    // r1,c7:  8-b
    // r2,c1:  (a^b-4)/(6c+1)       r2,c3:  (b+c)/(c-1)       r2,c5:  b^2-b/c       r2,c7:  sqrt(30+a)/c       r2,c9:  (a+b)/(c-3a)
    // r3,c3:  (b-3a)/(a-c)          r3,c5:  8a-2b             r3,c7:  b/(a-c)        r3,c9:  (b+9)/sqrt(c-a)
    // r4,c1:  18/(ac+1)             r4,c4:  c^b               r4,c9:  (3+b^2)/sqrt(3+2c)
    // r5,c2:  b/(a^2-c^2)           r5,c12: sqrt(a+2)/a
    // r6,c1:  a^b-12/a              r6,c3:  2c+c/a            r6,c5:  4a-5b          r6,c7:  c+2a              r6,c9:  b/(9a-5c)
    // r7,c0:  (b^3+2c)/(b+2c)       r7,c7:  b/(a-1)
    // r8,c1:  (c-b)/(2a)            r8,c6:  b/(a-c)           r8,c9:  (b+c)/(a-c)
    // r9,c1:  log_c(a)              r9,c2:  (c^2-b)/a         r9,c4:  (b-1)^2        r9,c7:  cbrt(43-ac)/a
    // r10,c1: (b-a)/(a-c)           r10,c3: 11-b              r10,c5: (b-2a)/(a-c)   r10,c7: (c+3)/a           r10,c9: 8c-b/c
    // r11,c4: b^2
    // r12,c6: (2^b+1)/(ac)

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        int found = 0;

        for (int a = 1; a <= 10; a++) {
            for (int b = 1; b <= 5; b++) {
                for (int c = 1; c <= 10; c++) {
                    int[] vals = computeFormulas(a, b, c);
                    if (vals == null) continue;

                    // Constraint: value N may appear at most N times
                    int[] counts = new int[18];
                    for (int v : vals) counts[v]++;
                    boolean valid = true;
                    for (int n = 1; n <= 17; n++) {
                        if (counts[n] > n) { valid = false; break; }
                    }
                    if (!valid) continue;

                    found++;
                    sb.append(String.format("a=%d, b=%d, c=%d%n", a, b, c));
                    int[][] grid = buildGrid(vals);
                    for (int[] row : grid) {
                        for (int val : row) {
                            sb.append(String.format("%5d", val));
                        }
                        sb.append("\n");
                    }
                    sb.append("\n");
                }
            }
        }

        if (found == 0) sb.append("No valid (a, b, c) combinations found.\n");

        System.out.print(sb);
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            pw.print(sb);
        }
    }

    // Returns array of formula values in grid order, or null if a denominator guard fails.
    // Each formula is validated via FormulaResult; returns null if any result is invalid.
    private static int[] computeFormulas(int a, int b, int c) {
        if (c == 1) return null;
        if ((c - 3 * a) == 0) return null;
        if (a == c) return null;
        if ((c - a) <= 0) return null;
        if ((a * a - c * c) == 0) return null;
        if ((9 * a - 5 * c) == 0) return null;
        if (a == 1) return null;

        FormulaResult[] results = {
            f_0_4(b, c),
            f_1_7(b),
            f_2_1(a, b, c), f_2_3(b, c), f_2_5(b, c), f_2_7(a, c), f_2_9(a, b, c),
            f_3_3(a, b, c), f_3_5(a, b), f_3_7(a, b, c), f_3_9(b, c, a),
            f_4_1(a, c), f_4_4(b, c), f_4_9(b, c),
            f_5_2(a, b, c), f_5_12(a),
            f_6_1(a, b), f_6_3(a, c), f_6_5(a, b), f_6_7(a, c), f_6_9(a, b, c),
            f_7_0(b, c), f_7_7(a, b),
            f_8_1(a, b, c), f_8_6(a, b, c), f_8_9(a, b, c),
            f_9_1(a, c), f_9_2(a, b, c), f_9_4(b), f_9_7(a, c),
            f_10_1(a, b, c), f_10_3(b), f_10_5(a, b, c), f_10_7(a, c), f_10_9(b, c),
            f_11_4(b),
            f_12_6(a, b, c)
        };

        int[] vals = new int[results.length];
        for (int i = 0; i < results.length; i++) {
            if (!results[i].valid) return null;
            vals[i] = results[i].value;
        }
        return vals;
    }

    // Formula methods named f_[row]_[col]
    static FormulaResult f_0_4(int b, int c)  { return FormulaResult.of(6.0 * c - 4.0 * b); }
    static FormulaResult f_1_7(int b)         { return FormulaResult.of(8.0 - b); }
    static FormulaResult f_2_1(int a, int b, int c) { return FormulaResult.of((Math.pow(a, b) - 4.0) / (6.0 * c + 1.0)); }
    static FormulaResult f_2_3(int b, int c)  { return FormulaResult.of((b + c) / (double)(c - 1)); }
    static FormulaResult f_2_5(int b, int c)  { return FormulaResult.of(b * b - (double) b / c); }
    static FormulaResult f_2_7(int a, int c)  { return FormulaResult.of(Math.sqrt(30.0 + a) / c); }
    static FormulaResult f_2_9(int a, int b, int c) { return FormulaResult.of((a + b) / (double)(c - 3 * a)); }
    static FormulaResult f_3_3(int a, int b, int c) { return FormulaResult.of((b - 3.0 * a) / (a - c)); }
    static FormulaResult f_3_5(int a, int b)  { return FormulaResult.of(8.0 * a - 2.0 * b); }
    static FormulaResult f_3_7(int a, int b, int c) { return FormulaResult.of((double) b / (a - c)); }
    static FormulaResult f_3_9(int b, int c, int a) { return FormulaResult.of((b + 9.0) / Math.sqrt(c - a)); }
    static FormulaResult f_4_1(int a, int c)  { return FormulaResult.of(18.0 / (a * c + 1.0)); }
    static FormulaResult f_4_4(int b, int c)  { return FormulaResult.of(Math.pow(c, b)); }
    static FormulaResult f_4_9(int b, int c)  { return FormulaResult.of((3.0 + b * b) / Math.sqrt(3.0 + 2.0 * c)); }
    static FormulaResult f_5_2(int a, int b, int c) { return FormulaResult.of((double) b / (a * a - c * c)); }
    static FormulaResult f_5_12(int a)        { return FormulaResult.of(Math.sqrt(a + 2.0) / a); }
    static FormulaResult f_6_1(int a, int b)  { return FormulaResult.of(Math.pow(a, b) - 12.0 / a); }
    static FormulaResult f_6_3(int a, int c)  { return FormulaResult.of(2.0 * c + (double) c / a); }
    static FormulaResult f_6_5(int a, int b)  { return FormulaResult.of(4.0 * a - 5.0 * b); }
    static FormulaResult f_6_7(int a, int c)  { return FormulaResult.of(c + 2.0 * a); }
    static FormulaResult f_6_9(int a, int b, int c) { return FormulaResult.of((double) b / (9 * a - 5 * c)); }
    static FormulaResult f_7_0(int b, int c)  { return FormulaResult.of((Math.pow(b, 3) + 2.0 * c) / (b + 2.0 * c)); }
    static FormulaResult f_7_7(int a, int b)  { return FormulaResult.of((double) b / (a - 1)); }
    static FormulaResult f_8_1(int a, int b, int c) { return FormulaResult.of((c - b) / (2.0 * a)); }
    static FormulaResult f_8_6(int a, int b, int c) { return FormulaResult.of((double) b / (a - c)); }
    static FormulaResult f_8_9(int a, int b, int c) { return FormulaResult.of((b + c) / (double)(a - c)); }
    static FormulaResult f_9_1(int a, int c)  { return FormulaResult.of(Math.log(a) / Math.log(c)); }
    static FormulaResult f_9_2(int a, int b, int c) { return FormulaResult.of((c * c - b) / (double) a); }
    static FormulaResult f_9_4(int b)         { return FormulaResult.of(Math.pow(b - 1, 2)); }
    static FormulaResult f_9_7(int a, int c)  { return FormulaResult.of(Math.cbrt(43.0 - a * c) / a); }
    static FormulaResult f_10_1(int a, int b, int c) { return FormulaResult.of((b - a) / (double)(a - c)); }
    static FormulaResult f_10_3(int b)        { return FormulaResult.of(11.0 - b); }
    static FormulaResult f_10_5(int a, int b, int c) { return FormulaResult.of((b - 2.0 * a) / (a - c)); }
    static FormulaResult f_10_7(int a, int c) { return FormulaResult.of((c + 3.0) / a); }
    static FormulaResult f_10_9(int b, int c) { return FormulaResult.of(8.0 * c - (double) b / c); }
    static FormulaResult f_11_4(int b)        { return FormulaResult.of(b * b); }
    static FormulaResult f_12_6(int a, int b, int c) { return FormulaResult.of((Math.pow(2, b) + 1.0) / (a * c)); }

    private static int[][] buildGrid(int[] v) {
        // v indices match the order in computeFormulas return
        // 0:r0c4, 1:r1c7, 2:r2c1, 3:r2c3, 4:r2c5, 5:r2c7, 6:r2c9,
        // 7:r3c3, 8:r3c5, 9:r3c7, 10:r3c9, 11:r4c1, 12:r4c4, 13:r4c9,
        // 14:r5c2, 15:r5c12, 16:r6c1, 17:r6c3, 18:r6c5, 19:r6c7, 20:r6c9,
        // 21:r7c0, 22:r7c7, 23:r8c1, 24:r8c6, 25:r8c9,
        // 26:r9c1, 27:r9c2, 28:r9c4, 29:r9c7,
        // 30:r10c1, 31:r10c3, 32:r10c5, 33:r10c7, 34:r10c9,
        // 35:r11c4, 36:r12c6
        int[][] g = new int[13][13];
        g[0][4]  = v[0];
        g[1][7]  = v[1];
        g[2][1]  = v[2];  g[2][3]  = v[3];  g[2][5]  = v[4];  g[2][7]  = v[5];  g[2][9]  = v[6];
        g[3][3]  = v[7];  g[3][5]  = v[8];  g[3][7]  = v[9];  g[3][9]  = v[10];
        g[4][1]  = v[11]; g[4][4]  = v[12]; g[4][9]  = v[13];
        g[5][2]  = v[14]; g[5][12] = v[15];
        g[6][1]  = v[16]; g[6][3]  = v[17]; g[6][5]  = v[18]; g[6][7]  = v[19]; g[6][9]  = v[20];
        g[7][0]  = v[21]; g[7][7]  = v[22];
        g[8][1]  = v[23]; g[8][6]  = v[24]; g[8][9]  = v[25];
        g[9][1]  = v[26]; g[9][2]  = v[27]; g[9][4]  = v[28]; g[9][7]  = v[29];
        g[10][1] = v[30]; g[10][3] = v[31]; g[10][5] = v[32]; g[10][7] = v[33]; g[10][9] = v[34];
        g[11][4] = v[35];
        g[12][6] = v[36];
        return g;
    }
}
