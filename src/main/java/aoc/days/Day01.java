package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;

import java.util.List;

public class Day01 extends Day<Long> {

    public static void main(String[] args) {
        Day01 d = new Day01();
        d.init("/day01.txt");
        d.printResult();
    }

    List<Integer> data;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            data = ReadTxtFile.readFileAsIntegerList(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        int val = 0;
        for (int i=0; i<data.size(); i++) {
            for(int j=i+1; j<data.size(); j++) {
                val = data.get(i) + data.get(j);
                if (val == 2020) {
                    return ((long)data.get(i))*((long)data.get(j));
                }
            }
        }
        return (long) -1;
    }

    public Long part2() {
        int val = 0;
        for (int i=0; i<data.size(); i++) {
            for(int j=i+1; j<data.size(); j++) {
                for(int k=j+1; k<data.size(); k++) {
                    val = data.get(i) + data.get(j) + data.get(k);
                    if (val == 2020) {
                        return ((long)data.get(i))*((long)data.get(j))*((long)data.get(k));
                    }
                }
            }
        }
        return (long) -1;
    }

}
