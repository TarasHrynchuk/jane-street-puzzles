import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Feb2026_Subtiles2_13Test {

    @ParameterizedTest(name = "f_0_4(b={0}, c={1}) -> {2}")
    @CsvSource({
        "1, 2,  8,           VALUE",
        "2, 2,  4,           VALUE",
        "3, 2,  0,           OUT_OF_RANGE",
        "4, 2, -4,           OUT_OF_RANGE",
        "1, 3, 14,           VALUE",
        "2, 3, 10,           VALUE",
        "3, 3,  6,           VALUE",
        "5, 3, -2,           OUT_OF_RANGE",
        "4, 4,  8,           VALUE",
        "5, 4,  4,           VALUE",
        "6, 4,  0,           OUT_OF_RANGE",
        "1, 5, 26,           OUT_OF_RANGE",
        "4, 5, 14,           VALUE",
        "7, 5,  2,           VALUE",
        "10, 5, -10,         OUT_OF_RANGE",
        "2, 10, 52,          OUT_OF_RANGE"
    })
    void testF_0_4(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_0_4(b, c);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    @ParameterizedTest(name = "f_1_7(b={0}) -> {1}")
    @CsvSource({
        "-5,  13, VALUE",
        " 0,   8, VALUE",
        " 1,   7, VALUE",
        " 2,   6, VALUE",
        " 3,   5, VALUE",
        " 4,   4, VALUE",
        " 5,   3, VALUE",
        " 6,   2, VALUE",
        " 7,   1, VALUE",
        " 8,   0, OUT_OF_RANGE",
        " 9,  -1, OUT_OF_RANGE",
        "20, -12, OUT_OF_RANGE",
        "-20, 28, OUT_OF_RANGE"
    })
    void testF_1_7(int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_1_7(b);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }
}