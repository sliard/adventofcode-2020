package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {

    @ParameterizedTest
    @CsvSource({
            "/day16-01.txt, 71",
    })
    void testPart1(String fileName, long result) {
        Day16 day = new Day16();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day16-02.txt, 1716",
    })
    void testPart2(String fileName, long result) {
        Day16 day = new Day16();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
