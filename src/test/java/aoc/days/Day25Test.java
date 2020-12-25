package aoc.days;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day25Test {

    @ParameterizedTest
    @CsvSource({
            "/day25-01.txt, 14897079",
    })
    void testPart1(String fileName, Long result) {
        Day25 day = new Day25();
        day.init(fileName, "");
        assertEquals(result, day.part1());
    }

    @Test
    void testTransforme() {
        Day25 day = new Day25();
        assertEquals(day.transform(7, 8),5764801);
        assertEquals(day.transform(7, 11),17807724);
    }

}
