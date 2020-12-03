package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Test {

    @ParameterizedTest
    @CsvSource({
            "/day03-01.txt, 7"
    })
    void testPart1(String fileName, long result) {
        Day03 day = new Day03();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day03-01.txt, 336"
    })
    void testPart2(String fileName, long result) {
        Day03 day = new Day03();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
