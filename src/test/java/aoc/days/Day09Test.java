package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {

    @ParameterizedTest
    @CsvSource({
            "/day09-01.txt, 5, 127"
    })
    void testPart1(String fileName, String size, long result) {
        Day09 day = new Day09();
        day.init(fileName, size);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day09-01.txt, 5, 62"
    })
    void testPart2(String fileName, String size, long result) {
        Day09 day = new Day09();
        day.init(fileName, size);
        assertEquals(result, day.part2());
    }

}
