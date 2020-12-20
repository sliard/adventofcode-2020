package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day20Test {

    @ParameterizedTest
    @CsvSource({
            "/day20-01.txt, 20899048083289",
    })
    void testPart1(String fileName, long result) {
        Day20 day = new Day20();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day20-01.txt, 273",
    })
    void testPart2(String fileName, long result) {
        Day20 day = new Day20();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
