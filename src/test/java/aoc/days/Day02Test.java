package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {

    @ParameterizedTest
    @CsvSource({
            "/day02-01.txt, 2"
    })
    void testPart1(String fileName, long result) {
        Day02 day = new Day02();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day02-01.txt, 1"
    })
    void testPart2(String fileName, long result) {
        Day02 day = new Day02();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
