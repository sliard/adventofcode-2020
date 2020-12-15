package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    @ParameterizedTest
    @CsvSource({
            "/day14-01.txt, 165",
    })
    void testPart1(String fileName, long result) {
        Day14 day = new Day14();
        day.init(fileName);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day14-02.txt, 208",
    })
    void testPart2(String fileName, long result) {
        Day14 day = new Day14();
        day.init(fileName);
        assertEquals(result, day.part2());
    }
}
