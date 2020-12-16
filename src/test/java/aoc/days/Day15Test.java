package aoc.days;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    @ParameterizedTest
    @CsvSource({
            "/day15-01.txt, 1",
            "/day15-02.txt, 10",
            "/day15-03.txt, 27",
            "/day15-04.txt, 78",
            "/day15-05.txt, 438",
            "/day15-06.txt, 1836",
            "/day15-07.txt, 436",
    })
    void testPart1(String fileName, long result) {
        Day15 day = new Day15();
        day.init(fileName);
        assertEquals(result, day.part1());
    }


    @ParameterizedTest
    @CsvSource({
            "/day15-01.txt, 2578",
//            "/day15-02.txt, 3544142",
//            "/day15-03.txt, 261214",
//            "/day15-04.txt, 6895259",
//            "/day15-05.txt, 18",
//            "/day15-06.txt, 362",
//            "/day15-07.txt, 175594",
    })
    void testPart2(String fileName, long result) {
        Day15 day = new Day15();
        day.init(fileName);
        assertEquals(result, day.part2());
    }
}
