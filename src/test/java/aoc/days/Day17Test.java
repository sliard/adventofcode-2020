package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    @ParameterizedTest
    @CsvSource({
            "/day17-01.txt, 112",
    })
    void testPart1(String fileName, long result) {
        Day17 day = new Day17();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day17-01.txt, 848",
    })
    void testPart2(String fileName, long result) {
        Day17 day = new Day17();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
