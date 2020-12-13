package aoc.days;

import aoc.Day;
import aoc.common.Direction;
import aoc.common.Rotation;
import aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 extends Day<Long> {

    public static void main(String[] args) {
        Day13 d = new Day13();
        d.init("/day13.txt");
        d.printResult();
    }

    int earliestTimestamp;
    List<Integer> allData;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            allData = new ArrayList<>();
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            earliestTimestamp = Integer.parseInt(allLines.get(0));
            String[] allBus = allLines.get(1).split(",");
            for(String bus : allBus) {
                if(bus.equals("x")) {
                    allData.add(-1);
                } else {
                    allData.add(Integer.parseInt(bus));
                }
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        int waitTime = Integer.MAX_VALUE;
        int selectedBus = 0;

        for(Integer bus : allData) {
            if(bus > 0 && (bus - earliestTimestamp%bus) < waitTime) {
                waitTime = bus - earliestTimestamp%bus;
                selectedBus = bus;
            }
        }

        return (long)selectedBus * waitTime;
    }

    public Long part2() {

        Map<Integer, Integer> constraints = new HashMap<>();
        for(int i=0; i<allData.size(); i++) {
            int val = allData.get(i);
            if(val > 0) {
                constraints.put(val, (val-(i%val))%val);
            }
        }

        long result = 0;
        long step = 1;

        for (Integer key : constraints.keySet()) {
            int bus = key;
            int cons = constraints.get(key);
            while(result % bus != cons) {
                result += step;
            }
            step *= bus;
        }

        return result;
    }

}
