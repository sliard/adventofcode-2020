package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24Test {

    @ParameterizedTest
    @CsvSource({
            "/day24-01.txt, 1",
            "/day24-02.txt, 1",
            "/day24-03.txt, 10",
    })
    void testPart1(String fileName, Long result) {
        Day24 day = new Day24();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day24-03.txt, 2208",
    })
    void testPart2(String fileName, Long result) {
        Day24 day = new Day24();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
