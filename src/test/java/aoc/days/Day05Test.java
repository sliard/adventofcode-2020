package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {

    @ParameterizedTest
    @CsvSource({
            "/day05-01.txt, 820",
            "/day05-02.txt, 567"
    })
    void testPart1(String fileName, long result) {
        Day05 day = new Day05();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day05-03.txt, 610"
    })
    void testPart2(String fileName, long result) {
        Day05 day = new Day05();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
