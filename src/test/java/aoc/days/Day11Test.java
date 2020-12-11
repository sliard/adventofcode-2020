package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @ParameterizedTest
    @CsvSource({
            "/day11-01.txt, 37"
    })
    void testPart1(String fileName, long result) {
        Day11 day = new Day11();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day11-01.txt, 26"
    })
    void testPart2(String fileName, long result) {
        Day11 day = new Day11();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
