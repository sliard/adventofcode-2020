package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Test {

    @ParameterizedTest
    @CsvSource({
            "/day21-01.txt, 5",
    })
    void testPart1(String fileName, String result) {
        Day21 day = new Day21();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day21-01.txt, 'mxmxvkd,sqjhc,fvjkl'",
    })
    void testPart2(String fileName, String result) {
        Day21 day = new Day21();
        day.init(fileName, "");
        day.part1();
        assertEquals(result, day.part2());
    }
}
