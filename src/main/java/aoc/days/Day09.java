package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;

import java.util.List;

public class Day09 extends Day<Long> {

    public static void main(String[] args) {
        Day09 d = new Day09();
        d.init("/day09.txt", "25");
        d.printResult();
    }

    List<Long> data;
    int preambleSize;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            data = ReadTxtFile.readFileAsLongList(args[0]);
            preambleSize = Integer.parseInt(args[1]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;

        for(int i = preambleSize; i < data.size(); i++) {
            if(isInvalid(data.get(i), i - preambleSize, i)) {
                return data.get(i);
            }
        }

        return result;
    }

    public boolean isInvalid(long val, int start, int end) {
        for(int i=start; i<end; i++) {
            long val1 = data.get(i);
            for(int j=i+1; j<end; j++) {
                long sum = data.get(j) + val1;
                if(sum == val) {
                    return false;
                }
            }
        }
        return true;
    }

    public Long part2() {
        long invalid = 0;
        for(int i = preambleSize; i < data.size() && invalid == 0; i++) {
            if(isInvalid(data.get(i), i - preambleSize, i)) {
                invalid = data.get(i);
            }
        }

        int start = 0;
        int end = 1;
        long currentSum = data.get(start) + data.get(end);

        while(currentSum != invalid) {
            if(currentSum > invalid) {
                if(start+1 == end) {
                    end++;
                    currentSum += data.get(end);
                } else {
                    currentSum -= data.get(start);
                    start++;
                }
            } else {
                end++;
                currentSum += data.get(end);
            }
        }

        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for(int i=start; i<=end; i++) {
            if(min > data.get(i)) {
                min = data.get(i);
            }
            if(max < data.get(i)) {
                max = data.get(i);
            }
        }

        return min + max;
    }

}
