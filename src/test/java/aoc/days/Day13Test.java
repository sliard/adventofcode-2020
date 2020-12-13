package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {

    @ParameterizedTest
    @CsvSource({
            "/day13-01.txt, 295",
    })
    void testPart1(String fileName, long result) {
        Day13 day = new Day13();
        day.init(fileName);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day13-01.txt, 1068781",
            "/day13-02.txt, 3417",
            "/day13-03.txt, 754018",
            "/day13-04.txt, 779210",
            "/day13-05.txt, 1261476",
            "/day13-06.txt, 1202161486",
    })
    void testPart2(String fileName, long result) {
        Day13 day = new Day13();
        day.init(fileName);
        assertEquals(result, day.part2());
    }
}
