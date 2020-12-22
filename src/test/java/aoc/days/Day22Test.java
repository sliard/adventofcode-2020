package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day22Test {

    @ParameterizedTest
    @CsvSource({
            "/day22-01.txt, 306",
    })
    void testPart1(String fileName, Long result) {
        Day22 day = new Day22();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day22-01.txt, 291",
//            "/day22-02.txt, 369",
    })
    void testPart2(String fileName, Long result) {
        Day22 day = new Day22();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
