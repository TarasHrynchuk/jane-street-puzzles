import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Feb2026_Subtiles2_13Test {

    @ParameterizedTest(name = "f_0_4(b={0}, c={1}) -> {2}")
    @CsvSource({
        "1, 2,  8,           OK",
        "2, 2,  4,           OK",
        "3, 2,  0,           OUT_OF_RANGE",
        "4, 2, -4,           OUT_OF_RANGE",
        "1, 3, 14,           OK",
        "2, 3, 10,           OK",
        "3, 3,  6,           OK",
        "5, 3, -2,           OUT_OF_RANGE",
        "4, 4,  8,           OK",
        "5, 4,  4,           OK",
        "6, 4,  0,           OUT_OF_RANGE",
        "1, 5, 26,           OUT_OF_RANGE",
        "4, 5, 14,           OK",
        "7, 5,  2,           OK",
        "10, 5, -10,         OUT_OF_RANGE",
        "2, 10, 52,          OUT_OF_RANGE"
    })
    void testF_0_4(int b, int c, int expectedValue, FormulaResult.Reason expectedReason) {
        FormulaResult result = Feb2026_Subtiles2_13.f_0_4(b, c);

        assertThat(result.value).isEqualTo(expectedValue);
        assertThat(result.reason).isEqualTo(expectedReason);
    }

    @ParameterizedTest(name = "f_1_7(b={0}) -> {1}")
    @CsvSource({
        "-5,  13, OK",
        " 0,   8, OK",
        " 1,   7, OK",
        " 2,   6, OK",
        " 3,   5, OK",
        " 4,   4, OK",
        " 5,   3, OK",
        " 6,   2, OK",
        " 7,   1, OK",
        " 8,   0, OUT_OF_RANGE",
        " 9,  -1, OUT_OF_RANGE",
        "20, -12, OUT_OF_RANGE",
        "-20, 28, OUT_OF_RANGE"
    })
    void testF_1_7(int b, int expectedValue, FormulaResult.Reason expectedReason) {
        FormulaResult result = Feb2026_Subtiles2_13.f_1_7(b);

        assertThat(result.value).isEqualTo(expectedValue);
        assertThat(result.reason).isEqualTo(expectedReason);
    }
}


