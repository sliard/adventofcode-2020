package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @ParameterizedTest
    @CsvSource({
            "/day10-01.txt, 220",
            "/day10-02.txt, 35"
    })
    void testPart1(String fileName, long result) {
        Day10 day = new Day10();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day10-01.txt, 19208",
            "/day10-02.txt, 8"
    })
    void testPart2(String fileName, long result) {
        Day10 day = new Day10();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
