package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {

    @ParameterizedTest
    @CsvSource({
            "/day06-01.txt, 11"
    })
    void testPart1(String fileName, long result) {
        Day06 day = new Day06();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day06-01.txt, 6"
    })
    void testPart2(String fileName, long result) {
        Day06 day = new Day06();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
