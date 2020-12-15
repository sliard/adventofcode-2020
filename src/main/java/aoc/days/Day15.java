package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 extends Day<Long> {

    public static void main(String[] args) {
        Day15 d = new Day15();
        d.init("/day15.txt");
        d.printResult();
    }

    List<Integer> allData;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            allData = new ArrayList<>();
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            String[] elements = lines.get(0).split(",");
            for(String e : elements) {
                allData.add(Integer.parseInt(e));
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {

        Map<Integer, Integer> lastPosition1 = new HashMap<>();
        Map<Integer, Integer> lastPosition2 = new HashMap<>();
        int index = 1;
        int lastValue = 0;
        for(int e: allData) {
            lastPosition1.put(e,index);
            index++;
            lastValue = e;
        }

        while (index <= 2020) {

            Integer lastValuePosition1 = lastPosition1.get(lastValue);
            Integer lastValuePosition2 = lastPosition2.get(lastValue);
            if(lastValuePosition1 == null && lastValuePosition2 == null) {
                lastValue = 0;
            } else if(lastValuePosition2 == null) {
                lastValue = 0;
            } else {
                lastValue = lastValuePosition2 - lastValuePosition1;
            }

            lastValuePosition1 = lastPosition1.get(lastValue);
            lastValuePosition2 = lastPosition2.get(lastValue);

            if(lastValuePosition1 == null && lastValuePosition2 == null) {
                lastPosition1.put(lastValue, index);
            } else if(lastValuePosition2 == null) {
                lastPosition2.put(lastValue, index);
            } else {
                lastPosition1.put(lastValue, lastValuePosition2);
                lastPosition2.put(lastValue, index);
            }
            index++;
        }

        return (long)lastValue;
    }

    public Long part2() {

        Map<Integer, Integer> lastPosition1 = new HashMap<>();
        Map<Integer, Integer> lastPosition2 = new HashMap<>();
        int index = 1;
        int lastValue = 0;
        for(int e: allData) {
            lastPosition1.put(e,index);
            index++;
            lastValue = e;
        }

        while (index <= 30000000) {

            Integer lastValuePosition1 = lastPosition1.get(lastValue);
            Integer lastValuePosition2 = lastPosition2.get(lastValue);
            if(lastValuePosition1 == null && lastValuePosition2 == null) {
                lastValue = 0;
            } else if(lastValuePosition2 == null) {
                lastValue = 0;
            } else {
                lastValue = lastValuePosition2 - lastValuePosition1;
            }

            lastValuePosition1 = lastPosition1.get(lastValue);
            lastValuePosition2 = lastPosition2.get(lastValue);

            if(lastValuePosition1 == null && lastValuePosition2 == null) {
                lastPosition1.put(lastValue, index);
            } else if(lastValuePosition2 == null) {
                lastPosition2.put(lastValue, index);
            } else {
                lastPosition1.put(lastValue, lastValuePosition2);
                lastPosition2.put(lastValue, index);
            }
            index++;
        }

        return (long)lastValue;    }

}
