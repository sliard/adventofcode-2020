package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day<Long> {

    public static void main(String[] args) {
        Day02 d = new Day02();
        d.init("/day02.txt");
        d.printResult();
    }

    List<Rule> data;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allData = ReadTxtFile.readFileAsStringList(args[0]);

            data = new ArrayList<>();
            for(String line : allData) {
                Rule rule = new Rule();
                // 1-3 b: cdefg
                String[] firstCut = line.split(": ");
                rule.setPassword(firstCut[1].trim());
                rule.setC(firstCut[0].charAt(firstCut[0].length()-1));
                String[] limit = firstCut[0].split("-");
                rule.setMin(Integer.parseInt(limit[0].trim()));
                rule.setMax(Integer.parseInt(limit[1].substring(0,limit[1].length()-1).trim()));
                data.add(rule);
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;
        long countCar;

        for(Rule rule : data) {
            countCar = rule.password.chars().filter(ch -> ch == rule.c).count();
            result += (countCar >= rule.min && countCar <= rule.max) ? 1 : 0;
        }

        return result;
    }

    public Long part2() {
        long result = 0;

        boolean firstIsEquals;
        boolean secondIsEquals;
        for(Rule rule : data) {
            if(rule.password.length() >= rule.min && rule.password.length() >= rule.max) {
                firstIsEquals = rule.password.charAt(rule.min-1) == rule.c;
                secondIsEquals = rule.password.charAt(rule.max-1) == rule.c;
                result += (firstIsEquals ^ secondIsEquals) ? 1 : 0;
            }
        }
        return result;
    }

    @Data
    public class Rule {
        public int min;
        public int max;
        public char c;
        public String password;
    }
}
