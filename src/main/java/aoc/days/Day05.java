package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Day05 extends Day<Long> {

    public static void main(String[] args) {
        Day05 d = new Day05();
        d.init("/day05.txt");
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

        for(String line : data) {
            int id = getSeatId(line);
            if(id > result) {
                result = id;
            }
        }

        return result;
    }

    public Long part2() {
        long max = 0;
        long min = Long.MAX_VALUE;
        List<Integer> allSeatId = new ArrayList<>();
        for(String line : data) {
            int id = getSeatId(line);
            allSeatId.add(id);
            if(id > max) {
                max = id;
            }
            if(id < min) {
                min = id;
            }
        }
        int nbSeat = data.size();
        long som = (nbSeat+1) * (min+max)/2;
        for(Integer v : allSeatId) {
            som -= v;
        }
        return som;
    }

    public int getSeatId(String seatData) {

        int row = getVal(seatData.substring(0,7), 0, 127);
        int column = getVal(seatData.substring(7), 0, 7);

        return row*8+column;
    }

    public int getVal(String seatData, int min, int max) {

        for(char c : seatData.toCharArray()) {
            if(c == 'F' || c == 'L') {
                max = ((max-min)/2)+min;
            } else {
                min = (int)Math.ceil((double)(max-min)/2)+min;
            }
        }
        return min;
    }

}
