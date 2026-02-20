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

    // f_2_1 = (a^b - 4) / (6c + 1)
    @ParameterizedTest(name = "f_2_1(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3, 4, 1, 11, VALUE",
        "2, 3, 0,  4, VALUE",
        "3, 2, 0,  5, VALUE",
        "4, 2, 0, 12, VALUE",
        "2, 2, 0,  0, OUT_OF_RANGE",
        "10, 2, 0, 96, OUT_OF_RANGE",
        "2, 3, 2,  0, INVALID",
        "3, 2, 2,  0, INVALID"
    })
    void testF_2_1(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_2_1(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_2_3 = (b + c) / (c - 1)
    @ParameterizedTest(name = "f_2_3(b={0}, c={1}) -> {3}")
    @CsvSource({
        " 5,  2,  7, VALUE",
        " 2,  2,  4, VALUE",
        "10,  2, 12, VALUE",
        "16,  2, 18, OUT_OF_RANGE",
        " 2,  3,  0, INVALID",
        " 1,  1,  0, INVALID"
    })
    void testF_2_3(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_2_3(b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_2_6 = b^2 - b/c
    @ParameterizedTest(name = "f_2_6(b={0}, c={1}) -> {3}")
    @CsvSource({
        "4, 2, 14, VALUE",
        "2, 2,  3, VALUE",
        "2, 1,  2, VALUE",
        "4, 4, 15, VALUE",
        "1, 1,  0, OUT_OF_RANGE",
        "6, 3, 34, OUT_OF_RANGE",
        "3, 2,  0, INVALID"
    })
    void testF_2_6(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_2_6(b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_2_8 = sqrt(30 + a) / c
    @ParameterizedTest(name = "f_2_8(a={0}, c={1}) -> {3}")
    @CsvSource({
        " 6, 6, 1, VALUE",
        " 6, 3, 2, VALUE",
        " 6, 2, 3, VALUE",
        " 6, 1, 6, VALUE",
        "34, 1, 8, VALUE",
        " 6, 4, -1, INVALID",
        " 1, 1, -1, INVALID"
    })
    void testF_2_8(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_2_8(a, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_2_10 = (a + b) / (c - 3a)
    @ParameterizedTest(name = "f_2_10(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "1,  6, 10, 1, VALUE",
        "2, 10,  9, 4, VALUE",
        "1,  2,  4, 3, VALUE",
        "1, 16,  4, 17, VALUE",
        "1, 17,  4, 18, OUT_OF_RANGE",
        "1,  3,  3, -1, INVALID",
        "1,  5, 10, -1, INVALID"
    })
    void testF_2_10(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_2_10(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_3_4 = (b - 3a) / (a - c)
    @ParameterizedTest(name = "f_3_4(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "2,  7, 1, 1, VALUE",
        "2,  9, 1, 3, VALUE",
        "3, 15, 1, 3, VALUE",
        "3, 17, 1, 4, VALUE",
        "3, 12, 1, -1, INVALID",
        "2,  2, 2, -1, INVALID"
    })
    void testF_3_4(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_3_4(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_3_7 = 8a - 2b
    @ParameterizedTest(name = "f_3_7(a={0}, b={1}) -> {2}")
    @CsvSource({
        "1, 1,  6, VALUE",
        "2, 4,  8, VALUE",
        "2, 3, 10, VALUE",
        "1, 3,  2, VALUE",
        "1, 4,  0, OUT_OF_RANGE",
        "3, 2, 20, OUT_OF_RANGE"
    })
    void testF_3_7(int a, int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_3_7(a, b);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_3_9 = b / (a - c)
    @ParameterizedTest(name = "f_3_9(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3,  4, 1, 2, VALUE",
        "3,  6, 1, 3, VALUE",
        "5, 10, 3, 5, VALUE",
        "3, 36, 1, 18, OUT_OF_RANGE",
        "3,  5, 1, -1, INVALID",
        "2,  3, 2, -1, INVALID"
    })
    void testF_3_9(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_3_9(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_3_11 = (b + 9) / sqrt(c - a)  [signature: f_3_11(b, c, a)]
    @ParameterizedTest(name = "f_3_11(b={0}, c={1}, a={2}) -> {4}")
    @CsvSource({
        " 7,  5, 1,  8, VALUE",
        " 3, 10, 1,  4, VALUE",
        " 0, 10, 1,  3, VALUE",
        " 8,  4, 3, 17, VALUE",
        " 9,  4, 3, 18, OUT_OF_RANGE",
        " 2,  5, 1,  0, INVALID",
        " 2,  2, 3,  0, INVALID"
    })
    void testF_3_11(int b, int c, int a, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_3_11(b, c, a);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_4_1 = 18 / (ac + 1)
    @ParameterizedTest(name = "f_4_1(a={0}, c={1}) -> {3}")
    @CsvSource({
        " 1,  1, 9, VALUE",
        " 1,  2, 6, VALUE",
        " 2,  1, 6, VALUE",
        " 1,  8, 2, VALUE",
        " 2,  4, 2, VALUE",
        " 1, 17, 1, VALUE",
        "17,  1, 1, VALUE",
        " 2,  2, -1, INVALID"
    })
    void testF_4_1(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_4_1(a, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_4_5 = c^b
    @ParameterizedTest(name = "f_4_5(b={0}, c={1}) -> {2}")
    @CsvSource({
        "1,  5,  5, VALUE",
        "2,  3,  9, VALUE",
        "3,  2,  8, VALUE",
        "4,  2, 16, VALUE",
        "1,  1,  1, VALUE",
        "1, 17, 17, VALUE",
        "5,  2, 32, OUT_OF_RANGE",
        "2,  5, 25, OUT_OF_RANGE",
        "1, 18, 18, OUT_OF_RANGE"
    })
    void testF_4_5(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_4_5(b, c);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_4_10 = (3 + b^2) / sqrt(3 + 2c)
    @ParameterizedTest(name = "f_4_10(b={0}, c={1}) -> {3}")
    @CsvSource({
        "3,  3,  4, VALUE",
        "6,  3, 13, VALUE",
        "0,  3,  1, VALUE",
        "9,  3, 28, OUT_OF_RANGE",
        "1,  3,  0, INVALID",
        "1,  1,  0, INVALID"
    })
    void testF_4_10(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_4_10(b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_5_3 = b / (a^2 - c^2)
    @ParameterizedTest(name = "f_5_3(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3,  5, 2, 1, VALUE",
        "3, 10, 2, 2, VALUE",
        "2,  3, 1, 1, VALUE",
        "5, 16, 3, 1, VALUE",
        "3,  7, 2, -1, INVALID",
        "2,  5, 2, -1, INVALID"
    })
    void testF_5_3(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_5_3(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_5_12 = sqrt(a + 2) / a
    @ParameterizedTest(name = "f_5_12(a={0}) -> {2}")
    @CsvSource({
        " 2, 1, VALUE",
        " 7, -1, INVALID",
        "14, -1, INVALID",
        " 1, -1, INVALID",
        " 3, -1, INVALID"
    })
    void testF_5_12(int a, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_5_12(a);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_6_2 = a^b - 12/a
    @ParameterizedTest(name = "f_6_2(a={0}, b={1}) -> {3}")
    @CsvSource({
        " 2, 4, 10, VALUE",
        " 2, 3,  2, VALUE",
        " 3, 2,  5, VALUE",
        " 4, 1,  1, VALUE",
        "12, 1, 11, VALUE",
        " 2, 2, -2, OUT_OF_RANGE",
        " 2, 5, 26, OUT_OF_RANGE",
        " 5, 1,  0, INVALID"
    })
    void testF_6_2(int a, int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_6_2(a, b);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_6_4 = 2c + c/a
    @ParameterizedTest(name = "f_6_4(a={0}, c={1}) -> {3}")
    @CsvSource({
        "2, 4, 10, VALUE",
        "3, 6, 14, VALUE",
        "2, 6, 15, VALUE",
        "1, 5, 15, VALUE",
        "4, 4,  9, VALUE",
        "2, 8, 20, OUT_OF_RANGE",
        "3, 4,  0, INVALID"
    })
    void testF_6_4(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_6_4(a, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_6_6 = 4a - 5b
    @ParameterizedTest(name = "f_6_6(a={0}, b={1}) -> {2}")
    @CsvSource({
        "5, 3,  5, VALUE",
        "2, 1,  3, VALUE",
        "4, 3,  1, VALUE",
        "4, 1, 11, VALUE",
        "5, 2, 10, VALUE",
        "1, 1, -1, OUT_OF_RANGE",
        "5, 4,  0, OUT_OF_RANGE"
    })
    void testF_6_6(int a, int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_6_6(a, b);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_6_8 = c + 2a
    @ParameterizedTest(name = "f_6_8(a={0}, c={1}) -> {2}")
    @CsvSource({
        "1,  1,  3, VALUE",
        "3,  5, 11, VALUE",
        "8,  1, 17, VALUE",
        "7,  2, 16, VALUE",
        "9,  1, 19, OUT_OF_RANGE",
        "8,  2, 18, OUT_OF_RANGE"
    })
    void testF_6_8(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_6_8(a, c);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_6_10 = b / (9a - 5c)
    @ParameterizedTest(name = "f_6_10(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "1,  4, 1, 1, VALUE",
        "2,  6, 3, 2, VALUE",
        "1,  8, 1, 2, VALUE",
        "2,  3, 3, 1, VALUE",
        "1,  3, 1, -1, INVALID",
        "5, 10, 9, -1, INVALID"
    })
    void testF_6_10(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_6_10(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_7_0 = (b^3 + 2c) / (b + 2c)
    @ParameterizedTest(name = "f_7_0(b={0}, c={1}) -> {3}")
    @CsvSource({
        "1,  1, 1, VALUE",
        "1,  5, 1, VALUE",
        "2,  2, 2, VALUE",
        "4,  4, 6, VALUE",
        "2,  1, -1, INVALID",
        "3,  2, -1, INVALID"
    })
    void testF_7_0(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_7_0(b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_7_9 = b / (a - 1)
    @ParameterizedTest(name = "f_7_9(a={0}, b={1}) -> {3}")
    @CsvSource({
        "2,  5,  5, VALUE",
        "3,  4,  2, VALUE",
        "4,  9,  3, VALUE",
        "2, 17, 17, VALUE",
        "2, 18, 18, OUT_OF_RANGE",
        "3,  5,  0, INVALID",
        "1,  5,  0, INVALID"
    })
    void testF_7_9(int a, int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_7_9(a, b);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_8_2 = (c - b) / (2a)
    @ParameterizedTest(name = "f_8_2(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "2,  4, 12, 2, VALUE",
        "1,  3,  7, 2, VALUE",
        "2,  3,  7, 1, VALUE",
        "3,  1,  7, 1, VALUE",
        "1,  1, 35, 17, VALUE",
        "1,  1, 36, -1, INVALID",
        "1,  3,  4, -1, INVALID"
    })
    void testF_8_2(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_8_2(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_8_7 = b / (a - c)
    @ParameterizedTest(name = "f_8_7(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3,  4,  1,  2, VALUE",
        "5, 12,  3,  6, VALUE",
        "5, 36,  3, 18, OUT_OF_RANGE",
        "3,  5,  1,  0, INVALID",
        "3,  3,  3,  0, INVALID"
    })
    void testF_8_7(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_8_7(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_8_11 = (b + c) / (a - c)
    @ParameterizedTest(name = "f_8_11(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3,  4, 2,  6, VALUE",
        "6,  2, 2,  1, VALUE",
        "4,  2, 2,  2, VALUE",
        "3, 16, 2, 18, OUT_OF_RANGE",
        "5,  3, 2,  0, INVALID",
        "3,  3, 3,  0, INVALID"
    })
    void testF_8_11(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_8_11(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_9_1 = log_c(a)
    @ParameterizedTest(name = "f_9_1(a={0}, c={1}) -> {3}")
    @CsvSource({
        " 4, 2, 2, VALUE",
        " 8, 2, 3, VALUE",
        "16, 2, 4, VALUE",
        " 9, 3, 2, VALUE",
        "27, 3, 3, VALUE",
        " 5, 2, -1, INVALID",
        " 1, 2, -1, INVALID",
        " 2, 1, -1, INVALID"
    })
    void testF_9_1(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_9_1(a, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_9_3 = (c^2 - b) / a
    @ParameterizedTest(name = "f_9_3(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "2, 5, 3, 2, VALUE",
        "3, 3, 3, 2, VALUE",
        "5, 4, 3, 1, VALUE",
        "4, 1, 5, 6, VALUE",
        "1, 5, 3, 4, VALUE",
        "3, 9, 3, 0, OUT_OF_RANGE",
        "3, 8, 3, -1, INVALID"
    })
    void testF_9_3(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_9_3(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_9_5 = (b - 1)^2
    @ParameterizedTest(name = "f_9_5(b={0}) -> {1}")
    @CsvSource({
        " 2,  1, VALUE",
        " 3,  4, VALUE",
        " 4,  9, VALUE",
        " 5, 16, VALUE",
        " 0,  1, VALUE",
        " 1,  0, OUT_OF_RANGE",
        " 6, 25, OUT_OF_RANGE"
    })
    void testF_9_5(int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_9_5(b);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_9_8 = cbrt(43 - ac) / a
    @ParameterizedTest(name = "f_9_8(a={0}, c={1}) -> {3}")
    @CsvSource({
        "1, 16,  3, VALUE",
        "1, 35,  2, VALUE",
        "1, 42,  1, VALUE",
        "1, 43,  0, OUT_OF_RANGE",
        "1,  1,  0, INVALID",
        "2, 21,  0, INVALID"
    })
    void testF_9_8(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_9_8(a, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_10_2 = (b - a) / (a - c)
    @ParameterizedTest(name = "f_10_2(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3,  5, 1, 1, VALUE",
        "3,  7, 1, 2, VALUE",
        "5,  9, 1, 1, VALUE",
        "3,  5, 2, 2, VALUE",
        "3,  3, 1, 0, OUT_OF_RANGE",
        "5,  7, 1, -1, INVALID",
        "2,  2, 2, -1, INVALID"
    })
    void testF_10_2(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_10_2(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_10_4 = 11 - b
    @ParameterizedTest(name = "f_10_4(b={0}) -> {1}")
    @CsvSource({
        " 1, 10, VALUE",
        " 2,  9, VALUE",
        "10,  1, VALUE",
        " 0, 11, VALUE",
        "-5, 16, VALUE",
        "11,  0, OUT_OF_RANGE",
        "12, -1, OUT_OF_RANGE"
    })
    void testF_10_4(int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_10_4(b);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_10_6 = (b - 2a) / (a - c)
    @ParameterizedTest(name = "f_10_6(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "3,  8, 1, 1, VALUE",
        "2,  5, 1, 1, VALUE",
        "3, 10, 1, 2, VALUE",
        "4, 10, 2, 1, VALUE",
        "3,  6, 1, 0, OUT_OF_RANGE",
        "3, 11, 1, -1, INVALID",
        "3,  3, 3, -1, INVALID"
    })
    void testF_10_6(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_10_6(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_10_9 = (c + 3) / a
    @ParameterizedTest(name = "f_10_9(a={0}, c={1}) -> {3}")
    @CsvSource({
        "2,  1, 2, VALUE",
        "1,  5, 8, VALUE",
        "4,  1, 1, VALUE",
        "2,  3, 3, VALUE",
        "3,  0, 1, VALUE",
        "1, 17, 20, OUT_OF_RANGE",
        "3,  1, -1, INVALID"
    })
    void testF_10_9(int a, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_10_9(a, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_10_11 = 8c - b/c
    @ParameterizedTest(name = "f_10_11(b={0}, c={1}) -> {3}")
    @CsvSource({
        "4, 2, 14, VALUE",
        "6, 2, 13, VALUE",
        "8, 2, 12, VALUE",
        "2, 2, 15, VALUE",
        "2, 1,  6, VALUE",
        "6, 3, 22, OUT_OF_RANGE",
        "3, 2,  0, INVALID"
    })
    void testF_10_11(int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_10_11(b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }

    // f_11_5 = b^2
    @ParameterizedTest(name = "f_11_5(b={0}) -> {1}")
    @CsvSource({
        " 1,  1, VALUE",
        " 2,  4, VALUE",
        " 3,  9, VALUE",
        " 4, 16, VALUE",
        "-1,  1, VALUE",
        "-4, 16, VALUE",
        " 0,  0, OUT_OF_RANGE",
        " 5, 25, OUT_OF_RANGE",
        "-5, 25, OUT_OF_RANGE"
    })
    void testF_11_5(int b, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_11_5(b);

        assertThat(result.value.intValue()).isEqualTo(expectedValue);
        assertThat(result.type).isEqualTo(expectedType);
    }

    // f_12_8 = (2^b + 1) / (ac)
    @ParameterizedTest(name = "f_12_8(a={0}, b={1}, c={2}) -> {4}")
    @CsvSource({
        "1, 2, 5,  1, VALUE",
        "1, 3, 1,  9, VALUE",
        "9, 3, 1,  1, VALUE",
        "1, 4, 1, 17, VALUE",
        "1, 1, 3,  1, VALUE",
        "3, 1, 1,  1, VALUE",
        "1, 5, 1, 33, OUT_OF_RANGE",
        "2, 3, 1,  0, INVALID"
    })
    void testF_12_8(int a, int b, int c, int expectedValue, EvalResultType expectedType) {
        EvalResult result = Feb2026_Subtiles2_13.f_12_8(a, b, c);

        assertThat(result.type).isEqualTo(expectedType);
        if (expectedType != EvalResultType.INVALID) {
            assertThat(result.value.intValue()).isEqualTo(expectedValue);
        }
    }
}