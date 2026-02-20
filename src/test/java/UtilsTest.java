import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    // ── divideExact ───────────────────────────────────────────────────────────

    @ParameterizedTest(name = "divideExact({0}, {1}) -> {2}")
    @CsvSource(nullValues = "N", value = {
        " 6,  2,  3",   // exact positive division
        " 5,  2,  N",   // not exact
        " 6,  0,  N",   // division by zero
        " 0,  5,  0",   // zero dividend
        "-6,  2, -3",   // negative dividend exact
        "-5,  2,  N",   // negative dividend not exact
        " 6, -2, -3",   // negative divisor exact
        " 1,  1,  1",   // identity
        "12,  4,  3",   // larger exact
        " 7,  3,  N",   // not exact remainder
    })
    void testDivideExact(long a, long b, Long expected) {
        Optional<BigInteger> result = Feb2026_Subtiles2_13.divideExact(
                BigInteger.valueOf(a), BigInteger.valueOf(b));
        if (expected == null) {
            assertThat(result).isEmpty();
        } else {
            assertThat(result).hasValue(BigInteger.valueOf(expected));
        }
    }

    // ── sqrtExact ─────────────────────────────────────────────────────────────

    @ParameterizedTest(name = "sqrtExact({0}) -> {1}")
    @CsvSource(nullValues = "N", value = {
        " 0,  0",   // sqrt(0) = 0
        " 1,  1",   // sqrt(1) = 1
        " 4,  2",   // sqrt(4) = 2
        " 9,  3",   // sqrt(9) = 3
        "25,  5",   // sqrt(25) = 5
        " 2,  N",   // not a perfect square
        " 3,  N",   // not a perfect square
        " 8,  N",   // not a perfect square
        "-1,  N",   // negative input
        "-4,  N",   // negative input
    })
    void testSqrtExact(long n, Long expected) {
        Optional<BigInteger> result = Feb2026_Subtiles2_13.sqrtExact(BigInteger.valueOf(n));
        if (expected == null) {
            assertThat(result).isEmpty();
        } else {
            assertThat(result).hasValue(BigInteger.valueOf(expected));
        }
    }

    // ── cbrtExact ─────────────────────────────────────────────────────────────

    @ParameterizedTest(name = "cbrtExact({0}) -> {1}")
    @CsvSource(nullValues = "N", value = {
        "   0,  0",   // cbrt(0) = 0
        "   1,  1",   // cbrt(1) = 1
        "   8,  2",   // cbrt(8) = 2
        "  27,  3",   // cbrt(27) = 3
        " 125,  5",   // cbrt(125) = 5
        "  -1, -1",   // cbrt(-1) = -1
        "  -8, -2",   // cbrt(-8) = -2
        " -27, -3",   // cbrt(-27) = -3
        "   2,  N",   // not a perfect cube
        "   9,  N",   // not a perfect cube
        "  26,  N",   // not a perfect cube (one below 27)
        "  28,  N",   // not a perfect cube (one above 27)
        "  -2,  N",   // negative, not a perfect cube
        "  -7,  N",   // negative, not a perfect cube
    })
    void testCbrtExact(long n, Long expected) {
        Optional<BigInteger> result = Feb2026_Subtiles2_13.cbrtExact(BigInteger.valueOf(n));
        if (expected == null) {
            assertThat(result).isEmpty();
        } else {
            assertThat(result).hasValue(BigInteger.valueOf(expected));
        }
    }
}
