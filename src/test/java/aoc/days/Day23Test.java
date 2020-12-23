package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day23Test {

    @ParameterizedTest
    @CsvSource({
            "/day23-01.txt, 67384529",
    })
    void testPart1(String fileName, Long result) {
        Day23 day = new Day23();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day23-01.txt, 149245887792",
    })
    void testPart2(String fileName, Long result) {
        Day23 day = new Day23();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
