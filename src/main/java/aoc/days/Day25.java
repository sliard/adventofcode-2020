package aoc.days;

import aoc.Day;
import aoc.common.Direction;
import aoc.utils.ReadTxtFile;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

public class Day25 extends Day<Long> {

    public static void main(String[] args) {
        Day25 d = new Day25();
        d.init("/day25.txt");
        d.printResult();
    }

    long cardPublicKey;
    long doorPublicKey;

    public void init(String... args) {
        // init stuff
        if (args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            cardPublicKey = Long.parseLong(lines.get(0));
            doorPublicKey = Long.parseLong(lines.get(1));

        } catch (Exception ex) {
            println("Read file error (" + args[0] + ") : " + ex.getMessage());
        }
    }

    public long transform(long a, int nbLoop) {
        long result = 1;
        for(int i=0;i<nbLoop;i++) {
            result = result * a;
            result = result % 20201227;
        }
        return result;
    }

    public int findNbLoop(long a, long target) {
        long value = 1;
        int nbLoop = 0;
        while(value != target) {
            value = value * a;
            value = value % 20201227;
            nbLoop++;
        }
        return nbLoop;
    }

    public Long part1() {
        int nbLoopCard = findNbLoop(7,cardPublicKey);
        return transform(doorPublicKey, nbLoopCard);
    }

    public Long part2() {

        return (long)0;
    }

}