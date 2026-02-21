import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Optional;

enum EvalResultType { VALUE, OUT_OF_RANGE, INVALID }

class EvalResult {
    final EvalResultType type;
    final BigInteger value;

    private EvalResult(EvalResultType type, BigInteger value) {
        this.type = type;
        this.value = value;
    }

    static final EvalResult INVALID = new EvalResult(EvalResultType.INVALID, null);

    static EvalResult of(BigInteger v) {
        if (v.compareTo(BigInteger.ONE) < 0 || v.compareTo(BigInteger.valueOf(17)) > 0)
            return new EvalResult(EvalResultType.OUT_OF_RANGE, v);
        return new EvalResult(EvalResultType.VALUE, v);
    }

    boolean isValid() { return type == EvalResultType.VALUE; }
}

public class Feb2026_Subtiles2_13 {

    private static final String OUTPUT_FILE = "output_2.txt";

    // Grid is 13 rows x 13 cols (0-indexed)
    // Formula positions from image:
    // r0,c4:  6c-4b
    // r1,c7:  8-b
    // r2,c1:  (a^b-4)/(6c+1)       r2,c3:  (b+c)/(c-1)       r2,c6:  b^2-b/c       r2,c8:  sqrt(30+a)/c       r2,c10: (a+b)/(c-3a)
    // r3,c4:  (b-3a)/(a-c)          r3,c7:  8a-2b             r3,c9:  b/(a-c)        r3,c11: (b+9)/sqrt(c-a)
    // r4,c1:  18/(ac+1)             r4,c5:  c^b               r4,c10: (3+b^2)/sqrt(3+2c)
    // r5,c3:  b/(a^2-c^2)           r5,c12: sqrt(a+2)/a
    // r6,c2:  a^b-12/a              r6,c4:  2c+c/a            r6,c6:  4a-5b          r6,c8:  c+2a              r6,c10: b/(9a-5c)
    // r7,c0:  (b^3+2c)/(b+2c)       r7,c9:  b/(a-1)
    // r8,c2:  (c-b)/(2a)            r8,c7:  b/(a-c)           r8,c11: (b+c)/(a-c)
    // r9,c1:  log_c(a)              r9,c3:  (c^2-b)/a         r9,c5:  (b-1)^2        r9,c8:  cbrt(43-ac)/a
    // r10,c2: (b-a)/(a-c)           r10,c4: 11-b              r10,c6: (b-2a)/(a-c)   r10,c9: (c+3)/a           r10,c11: 8c-b/c
    // r11,c5: b^2
    // r12,c8: (2^b+1)/(ac)

    static final int[] constraintHits = new int[7];
    static final String[] constraintNames = {
        "c == 1",
        "(c - 3*a) == 0",
        "a == c",
        "(c - a) <= 0",
        "(a*a - c*c) == 0",
        "(9*a - 5*c) == 0",
        "a == 1"
    };

    static final int[] formulaHits = new int[37];
    static final String[] formulaNames = {
        "f_0_4",  "f_1_7",  "f_2_1",  "f_2_3",  "f_2_6",  "f_2_8",  "f_2_10",
        "f_3_4",  "f_3_7",  "f_3_9",  "f_3_11", "f_4_1",  "f_4_5",  "f_4_10",
        "f_5_3",  "f_5_12", "f_6_2",  "f_6_4",  "f_6_6",  "f_6_8",  "f_6_10",
        "f_7_0",  "f_7_9",  "f_8_2",  "f_8_7",  "f_8_11",
        "f_9_1",  "f_9_3",  "f_9_5",  "f_9_8",
        "f_10_2", "f_10_4", "f_10_6", "f_10_9", "f_10_11",
        "f_11_5", "f_12_8"
    };

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        int found = 0;

        for (int a = 1; a <= 200; a++) {
            for (int b = 1; b <= 10; b++) {
                for (int c = 1; c <= 50; c++) {
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

        StringBuilder stats = new StringBuilder();
        stats.append("\n=== Constraint Stats ===\n");
        for (int i = 0; i < constraintNames.length; i++) {
            stats.append(String.format("  %-25s blocked %d times%n", constraintNames[i], constraintHits[i]));
        }
        stats.append("\n=== Formula Stats ===\n");
        for (int i = 0; i < formulaNames.length; i++) {
            stats.append(String.format("  %-10s blocked %d times%n", formulaNames[i], formulaHits[i]));
        }
        System.out.print(stats);
    }

    // ── Exact-arithmetic helpers ──────────────────────────────────────────────

    static Optional<BigInteger> divideExact(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) return Optional.empty();
        BigInteger[] dr = a.divideAndRemainder(b);
        return dr[1].equals(BigInteger.ZERO) ? Optional.of(dr[0]) : Optional.empty();
    }

    static Optional<BigInteger> sqrtExact(BigInteger n) {
        if (n.signum() < 0) return Optional.empty();
        BigInteger[] sr = n.sqrtAndRemainder();
        return sr[1].equals(BigInteger.ZERO) ? Optional.of(sr[0]) : Optional.empty();
    }

    static Optional<BigInteger> cbrtExact(BigInteger n) {
        if (n.equals(BigInteger.ZERO)) return Optional.of(BigInteger.ZERO);
        boolean negative = n.signum() < 0;
        BigInteger absN = n.abs();
        int bits = (absN.bitLength() + 2) / 3;
        BigInteger hi = BigInteger.ONE.shiftLeft(bits + 1);
        BigInteger lo = BigInteger.ONE;
        while (lo.compareTo(hi) <= 0) {
            BigInteger mid = lo.add(hi).shiftRight(1);
            int cmp = mid.pow(3).compareTo(absN);
            if (cmp == 0) return Optional.of(negative ? mid.negate() : mid);
            else if (cmp < 0) lo = mid.add(BigInteger.ONE);
            else hi = mid.subtract(BigInteger.ONE);
        }
        return Optional.empty();
    }

    static boolean inAllowedRange(BigInteger v) {
        return v.compareTo(BigInteger.ONE) >= 0 && v.compareTo(BigInteger.valueOf(17)) <= 0;
    }

    private static EvalResult inRange(BigInteger v) { return EvalResult.of(v); }

    // ── Returns array of formula values in grid order, or null if any is invalid ──

    private static int[] computeFormulas(int a, int b, int c) {
        if (c == 1)                    { constraintHits[0]++; return null; }
        if ((c - 3 * a) == 0)          { constraintHits[1]++; return null; }
        if (a == c)                    { constraintHits[2]++; return null; }
        if ((c - a) <= 0)              { constraintHits[3]++; return null; }
        if ((a * a - c * c) == 0)      { constraintHits[4]++; return null; }
        if ((9 * a - 5 * c) == 0)      { constraintHits[5]++; return null; }
        if (a == 1)                    { constraintHits[6]++; return null; }

        EvalResult[] results = {
            f_0_4(b, c),
            f_1_7(b),
            f_2_1(a, b, c), f_2_3(b, c), f_2_6(b, c), f_2_8(a, c), f_2_10(a, b, c),
            f_3_4(a, b, c), f_3_7(a, b), f_3_9(a, b, c), f_3_11(b, c, a),
            f_4_1(a, c), f_4_5(b, c), f_4_10(b, c),
            f_5_3(a, b, c), f_5_12(a),
            f_6_2(a, b), f_6_4(a, c), f_6_6(a, b), f_6_8(a, c), f_6_10(a, b, c),
            f_7_0(b, c), f_7_9(a, b),
            f_8_2(a, b, c), f_8_7(a, b, c), f_8_11(a, b, c),
            f_9_1(a, c), f_9_3(a, b, c), f_9_5(b), f_9_8(a, c),
            f_10_2(a, b, c), f_10_4(b), f_10_6(a, b, c), f_10_9(a, c), f_10_11(b, c),
            f_11_5(b),
            f_12_8(a, b, c)
        };

        int[] vals = new int[results.length];
        for (int i = 0; i < results.length; i++) {
            if (!results[i].isValid()) { formulaHits[i]++; return null; }
            vals[i] = results[i].value.intValue();
        }
        return vals;
    }

    // ── Formula methods named f_[row]_[col] ──────────────────────────────────

    static EvalResult f_0_4(int b, int c) {
        return inRange(BigInteger.valueOf(6L * c - 4L * b));
    }

    static EvalResult f_1_7(int b) {
        return inRange(BigInteger.valueOf(8L - b));
    }

    static EvalResult f_2_1(int a, int b, int c) {
        BigInteger num = BigInteger.valueOf(a).pow(b).subtract(BigInteger.valueOf(4));
        BigInteger den = BigInteger.valueOf(6L * c + 1);
        return divideExact(num, den).map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_2_3(int b, int c) {
        return divideExact(BigInteger.valueOf(b + c), BigInteger.valueOf(c - 1))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_2_6(int b, int c) {
        BigInteger B = BigInteger.valueOf(b);
        return divideExact(B, BigInteger.valueOf(c))
                .map(q -> inRange(B.multiply(B).subtract(q)))
                .orElse(EvalResult.INVALID);
    }

    static EvalResult f_2_8(int a, int c) {
        return sqrtExact(BigInteger.valueOf(30L + a))
                .flatMap(s -> divideExact(s, BigInteger.valueOf(c)))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_2_10(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(a + b), BigInteger.valueOf(c - 3L * a))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_3_4(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b - 3L * a), BigInteger.valueOf(a - c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_3_7(int a, int b) {
        return inRange(BigInteger.valueOf(8L * a - 2L * b));
    }

    static EvalResult f_3_9(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b), BigInteger.valueOf(a - c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_3_11(int b, int c, int a) {
        return sqrtExact(BigInteger.valueOf(c - a))
                .flatMap(s -> divideExact(BigInteger.valueOf(b + 9L), s))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_4_1(int a, int c) {
        return divideExact(BigInteger.valueOf(18), BigInteger.valueOf((long) a * c + 1))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_4_5(int b, int c) {
        return inRange(BigInteger.valueOf(c).pow(b));
    }

    static EvalResult f_4_10(int b, int c) {
        BigInteger num = BigInteger.valueOf(3L + (long) b * b);
        return sqrtExact(BigInteger.valueOf(3L + 2L * c))
                .flatMap(s -> divideExact(num, s))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_5_3(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b), BigInteger.valueOf((long) a * a - (long) c * c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_5_12(int a) {
        return sqrtExact(BigInteger.valueOf(a + 2L))
                .flatMap(s -> divideExact(s, BigInteger.valueOf(a)))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_6_2(int a, int b) {
        BigInteger A = BigInteger.valueOf(a);
        return divideExact(BigInteger.valueOf(12), A)
                .map(q -> inRange(A.pow(b).subtract(q)))
                .orElse(EvalResult.INVALID);
    }

    static EvalResult f_6_4(int a, int c) {
        BigInteger C = BigInteger.valueOf(c);
        return divideExact(C, BigInteger.valueOf(a))
                .map(q -> inRange(C.multiply(BigInteger.TWO).add(q)))
                .orElse(EvalResult.INVALID);
    }

    static EvalResult f_6_6(int a, int b) {
        return inRange(BigInteger.valueOf(4L * a - 5L * b));
    }

    static EvalResult f_6_8(int a, int c) {
        return inRange(BigInteger.valueOf(c + 2L * a));
    }

    static EvalResult f_6_10(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b), BigInteger.valueOf(9L * a - 5L * c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_7_0(int b, int c) {
        BigInteger B = BigInteger.valueOf(b);
        BigInteger twoC = BigInteger.valueOf(2L * c);
        BigInteger num = B.pow(3).add(twoC);
        BigInteger den = B.add(twoC);
        return divideExact(num, den).map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_7_9(int a, int b) {
        return divideExact(BigInteger.valueOf(b), BigInteger.valueOf(a - 1L))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_8_2(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(c - b), BigInteger.valueOf(2L * a))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_8_7(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b), BigInteger.valueOf(a - c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_8_11(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b + c), BigInteger.valueOf(a - c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_9_1(int a, int c) {
        if (c <= 1) return EvalResult.INVALID;
        BigInteger A = BigInteger.valueOf(a);
        BigInteger C = BigInteger.valueOf(c);
        BigInteger power = C;
        BigInteger exp = BigInteger.ONE;
        while (power.compareTo(A) < 0) {
            power = power.multiply(C);
            exp = exp.add(BigInteger.ONE);
        }
        return power.equals(A) ? inRange(exp) : EvalResult.INVALID;
    }

    static EvalResult f_9_3(int a, int b, int c) {
        return divideExact(BigInteger.valueOf((long) c * c - b), BigInteger.valueOf(a))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_9_5(int b) {
        return inRange(BigInteger.valueOf((long)(b - 1) * (b - 1)));
    }

    static EvalResult f_9_8(int a, int c) {
        BigInteger n = BigInteger.valueOf(43L - (long) a * c);
        return cbrtExact(n)
                .flatMap(r -> divideExact(r, BigInteger.valueOf(a)))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_10_2(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b - a), BigInteger.valueOf(a - c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_10_4(int b) {
        return inRange(BigInteger.valueOf(11L - b));
    }

    static EvalResult f_10_6(int a, int b, int c) {
        return divideExact(BigInteger.valueOf(b - 2L * a), BigInteger.valueOf(a - c))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_10_9(int a, int c) {
        return divideExact(BigInteger.valueOf(c + 3L), BigInteger.valueOf(a))
                .map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    static EvalResult f_10_11(int b, int c) {
        BigInteger C = BigInteger.valueOf(c);
        return divideExact(BigInteger.valueOf(b), C)
                .map(q -> inRange(C.multiply(BigInteger.valueOf(8)).subtract(q)))
                .orElse(EvalResult.INVALID);
    }

    static EvalResult f_11_5(int b) {
        return inRange(BigInteger.valueOf((long) b * b));
    }

    static EvalResult f_12_8(int a, int b, int c) {
        BigInteger num = BigInteger.TWO.pow(b).add(BigInteger.ONE);
        BigInteger den = BigInteger.valueOf((long) a * c);
        return divideExact(num, den).map(Feb2026_Subtiles2_13::inRange).orElse(EvalResult.INVALID);
    }

    private static int[][] buildGrid(int[] v) {
        // v indices match the order in computeFormulas return
        // 0:r0c4, 1:r1c7, 2:r2c1, 3:r2c3, 4:r2c6, 5:r2c8, 6:r2c10,
        // 7:r3c4, 8:r3c7, 9:r3c9, 10:r3c11, 11:r4c1, 12:r4c5, 13:r4c10,
        // 14:r5c3, 15:r5c12, 16:r6c2, 17:r6c4, 18:r6c6, 19:r6c8, 20:r6c10,
        // 21:r7c0, 22:r7c9, 23:r8c2, 24:r8c7, 25:r8c11,
        // 26:r9c1, 27:r9c3, 28:r9c5, 29:r9c8,
        // 30:r10c2, 31:r10c4, 32:r10c6, 33:r10c9, 34:r10c11,
        // 35:r11c5, 36:r12c8
        int[][] g = new int[13][13];
        g[0][4]  = v[0];
        g[1][7]  = v[1];
        g[2][1]  = v[2];  g[2][3]  = v[3];  g[2][6]  = v[4];  g[2][8]  = v[5];  g[2][10] = v[6];
        g[3][4]  = v[7];  g[3][7]  = v[8];  g[3][9]  = v[9];  g[3][11] = v[10];
        g[4][1]  = v[11]; g[4][5]  = v[12]; g[4][10] = v[13];
        g[5][3]  = v[14]; g[5][12] = v[15];
        g[6][2]  = v[16]; g[6][4]  = v[17]; g[6][6]  = v[18]; g[6][8]  = v[19]; g[6][10] = v[20];
        g[7][0]  = v[21]; g[7][9]  = v[22];
        g[8][2]  = v[23]; g[8][7]  = v[24]; g[8][11] = v[25];
        g[9][1]  = v[26]; g[9][3]  = v[27]; g[9][5]  = v[28]; g[9][8]  = v[29];
        g[10][2] = v[30]; g[10][4] = v[31]; g[10][6] = v[32]; g[10][9] = v[33]; g[10][11]= v[34];
        g[11][5] = v[35];
        g[12][8] = v[36];
        return g;
    }
}
