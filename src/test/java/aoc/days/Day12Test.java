package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    @ParameterizedTest
    @CsvSource({
            "/day12-01.txt, 25",
            "/day12-02.txt, 9",
            "/day12-03.txt, 31",
            "/day12-04.txt, 18"
    })
    void testPart1(String fileName, long result) {
        Day12 day = new Day12();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day12-01.txt, 286",
            "/day12-02.txt, 66",
            "/day12-03.txt, 274",
            "/day12-04.txt, 184",
            "/day12-05.txt, 286",
            "/day12-06.txt, 1825",
    })
    void testPart2(String fileName, long result) {
        Day12 day = new Day12();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
