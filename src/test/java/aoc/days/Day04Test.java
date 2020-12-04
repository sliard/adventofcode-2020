package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {

    @ParameterizedTest
    @CsvSource({
            "/day04-01.txt, 2"
    })
    void testPart1(String fileName, long result) {
        Day04 day = new Day04();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day04-02.txt, 0",
            "/day04-03.txt, 4"
    })
    void testPart2(String fileName, long result) {
        Day04 day = new Day04();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
