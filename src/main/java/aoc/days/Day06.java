package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06 extends Day<Long> {

    public static void main(String[] args) {
        Day06 d = new Day06();
        d.init("/day06.txt");
        d.printResult();
    }

    List<String> data;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            data = ReadTxtFile.readFileAsStringList(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;

        Set<Character> groupAnswer = new HashSet<>();
        for(String line : data) {
            if(line.length() == 0) {
                result += groupAnswer.size();
                groupAnswer = new HashSet<>();
                continue;
            }
            for(char c : line.toCharArray()) {
                groupAnswer.add(c);
            }
        }
        result += groupAnswer.size();
        return result;
    }

    public Long part2() {
        long result = 0;

        String currentFull = "";
        boolean first = true;
        for(String line : data) {
            if(line.length() == 0) {

                result += currentFull.length();
                first = true;
                continue;
            }

            if(first) {
                currentFull = line;
                first = false;
            } else {
                StringBuilder common = new StringBuilder();
                for(char c : line.toCharArray()) {
                    if(currentFull.contains(""+c)) {
                        common.append(c);
                    }
                }
                currentFull = common.toString();
            }
        }
        result += currentFull.length();
        return result;
    }

}
