package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 extends Day<Long> {

    public static void main(String[] args) {
        Day16 d = new Day16();
        d.init("/day16.txt" , "departure");
        d.printResult();
    }

    List<DoubleRange> allRange;
    Ticket myTicket;
    List<Ticket> allTicket;
    String start;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            start = args[1];
            allRange = new ArrayList<>();
            allTicket = new ArrayList<>();
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            int index = 0;
            String currentLine = lines.get(index);
            while(!currentLine.isEmpty()) {
                String[] c1 = currentLine.split(": ");
                String[] c2 = c1[1].split(" or ");

                String[] range1 = c2[0].split("-");
                String[] range2 = c2[1].split("-");

                DoubleRange db = new DoubleRange();
                db.name = c1[0];
                db.index = index;
                db.min1 = Integer.parseInt(range1[0]);
                db.max1 = Integer.parseInt(range1[1]);
                db.min2 = Integer.parseInt(range2[0]);
                db.max2 = Integer.parseInt(range2[1]);
                allRange.add(db);
                index++;
                currentLine = lines.get(index);
            }

            index+=2;
            currentLine = lines.get(index);
            String[] myValue = currentLine.split(",");
            myTicket = new Ticket();
            for(String v : myValue) {
                myTicket.values.add(Integer.parseInt(v));
            }

            for(int i=index+3; i< lines.size(); i++) {
                currentLine = lines.get(i);
                myValue = currentLine.split(",");
                Ticket t = new Ticket();
                for(String v : myValue) {
                    t.values.add(Integer.parseInt(v));
                }
                allTicket.add(t);
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    @Data
    public class DoubleRange {
        int index;
        String name;

        int min1;
        int max1;

        int min2;
        int max2;

        int position;
    }

    @Data
    public class Ticket {
        List<Integer> values = new ArrayList<>();
    }

    public Long part1() {
        long result = 0;

        for(Ticket t : allTicket) {

            for(int i=0; i<t.values.size(); i++) {
                int v = t.values.get(i);
                boolean valid = false;
                for(int j=0; j< allRange.size() && !valid; j++) {
                    DoubleRange r = allRange.get(j);
                    if(((v>=r.min1 && v<=r.max1) || (v>=r.min2 && v<=r.max2))) {
                        valid = true;
                    }
                }
                if(!valid) {
                    result += v;
                }
            }

        }

        return result;
    }

    public Long part2() {

        List<Ticket> goodTicket = new ArrayList<>();

        for(Ticket t : allTicket) {

            boolean allValid = true;
            for(int i=0; i<t.values.size(); i++) {
                int v = t.values.get(i);
                boolean valid = false;
                for(int j=0; j< allRange.size() && !valid; j++) {
                    DoubleRange r = allRange.get(j);
                    if(((v>=r.min1 && v<=r.max1) || (v>=r.min2 && v<=r.max2))) {
                        valid = true;
                    }
                }
                allValid &= valid;
            }
            if(allValid) {
                goodTicket.add(t);
            }
        }

        Map<Integer, List<DoubleRange>> allPossible = new HashMap<>();
        for(int i=0; i<myTicket.values.size(); i++) {
            List<DoubleRange> copyRange = new ArrayList<>();
            for(DoubleRange dr : allRange) {
                copyRange.add(dr);
            }
            allPossible.put(i,copyRange);
        }


        for(Ticket t : goodTicket) {

            for(int i=0; i<t.values.size(); i++) {
                int v = t.values.get(i);
                for(int j=0; j< allRange.size(); j++) {
                    DoubleRange r = allRange.get(j);
                    if(!((v>=r.min1 && v<=r.max1) || (v>=r.min2 && v<=r.max2))) {
                        allPossible.get(i).remove(r);
                    }
                }
            }
        }

        while (!allPossible.isEmpty()) {

            List<DoubleRange> allSolo = new ArrayList<>();
            List<Integer> soloIndex = new ArrayList<>();
            for(int i : allPossible.keySet()) {
                List<DoubleRange> allRangePossible = allPossible.get(i);
                if(allRangePossible.size() == 1) {
                    DoubleRange dbSolo = allRangePossible.get(0);
                    dbSolo.position = i;
                    allSolo.add(dbSolo);
                    soloIndex.add(i);
                }
            }

            for(int i : allPossible.keySet()) {
                List<DoubleRange> allRangePossible = allPossible.get(i);
                for(DoubleRange solo : allSolo) {
                    allRangePossible.remove(solo);
                }
            }

            for(int i : soloIndex) {
                allPossible.remove(i);
            }

        }
        long result = 1;

        for(DoubleRange db : allRange) {
            if(db.name.startsWith(start)) {
                result *= myTicket.values.get(db.position);
            }
        }

        return result;
    }

}
