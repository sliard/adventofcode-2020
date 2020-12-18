package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18Test {

    @ParameterizedTest
    @CsvSource({
            "/day18-01.txt, 71",
            "/day18-02.txt, 51",
            "/day18-03.txt, 26",
            "/day18-04.txt, 437",
            "/day18-05.txt, 12240",
            "/day18-06.txt, 13632",
            "/day18-07.txt, 26335",
    })
    void testPart1(String fileName, long result) {
        Day18 day = new Day18();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day18-01.txt, 231",
            "/day18-02.txt, 51",
            "/day18-03.txt, 46",
            "/day18-04.txt, 1445",
            "/day18-05.txt, 669060",
            "/day18-06.txt, 23340",
    })
    void testPart2(String fileName, long result) {
        Day18 day = new Day18();
        day.init(fileName, "");
        assertEquals(result, day.part2());
    }
}
