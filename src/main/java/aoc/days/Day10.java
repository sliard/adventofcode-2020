package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 extends Day<Long> {

    public static void main(String[] args) {
        Day10 d = new Day10();
        d.init("/day10.txt");
        d.printResult();
    }

    List<Long> data;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            data = ReadTxtFile.readFileAsLongList(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        data.sort(Long::compareTo);

        long nb1 = 1;
        long nb3 = 1;
        for(int i=1; i<data.size(); i++) {
            long diff = data.get(i) - data.get(i-1);
            if(diff == 3) {
                nb3++;
            } else if (diff == 1) {
                nb1++;
            }
        }
        return nb1*nb3;
    }


    public Long part2() {
        data.add((long)0);
        data.sort(Long::compareTo);
        return dp(0);
    }

    Map<Integer, Long> dpResult = new HashMap<>();
    public long dp(int index) {
        if(index == data.size()-1) {
            return 1;
        }
        if(dpResult.containsKey(index)) {
            return dpResult.get(index);
        }
        long result = 0;
        long lastValue = data.get(index);
        for(int j=index+1; j<data.size(); j++) {
            if((data.get(j) - lastValue) <= 3) {
                result += dp(j);
            }
        }
        dpResult.put(index, result);
        return result;
    }
}
