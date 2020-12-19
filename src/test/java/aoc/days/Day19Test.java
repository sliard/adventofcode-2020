package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19Test {

    @ParameterizedTest
    @CsvSource({
            "/day19-01.txt, 2",
            "/day19-02.txt, 3",
    })
    void testPart1(String fileName, long result) {
        Day19 day = new Day19();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day19-02.txt, 12",
    })
    void testPart2(String fileName, long result) {
        Day19 day = new Day19();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
