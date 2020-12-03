package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;

import java.util.List;

public class Day03 extends Day<Long> {

    public static void main(String[] args) {
        Day03 d = new Day03();
        d.init("/day03.txt");
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
        result = countTree(3,1);
        return result;
    }

    public Long part2() {
        long result = 1;

        result *= countTree(1,1);
        result *= countTree(3,1);
        result *= countTree(5,1);
        result *= countTree(7,1);
        result *= countTree(1,2);

        return result;
    }

    // not for all down value, but 1 and 2 are ok :)
    private long countTree(int right, int down) {
        long result = 0;
        int index=0;
        for(int l=0; l<data.size(); l++) {
            if(l%down == 0) {
                result += (data.get(l).charAt((index)%data.get(l).length()) == '#') ? 1 : 0;
                index = index+right;
            }
        }
        return result;
    }

}
