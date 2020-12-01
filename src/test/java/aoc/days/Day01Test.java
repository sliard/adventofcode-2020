package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {

    @ParameterizedTest
    @CsvSource({
            "/day01-01.txt, 514579"
    })
    void testPart1(String fileName, long result) {
        Day01 day = new Day01();
        day.init(fileName);
        assertEquals(result, day.part1());
    }

    @ParameterizedTest
    @CsvSource({
            "/day01-01.txt, 241861950"
    })
    void testPart2(String fileName, long result) {
        Day01 day = new Day01();
        day.init(fileName);
        assertEquals(result, day.part2());
    }

}
