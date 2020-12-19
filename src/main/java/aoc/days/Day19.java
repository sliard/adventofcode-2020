package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends Day<Long> {

    public static void main(String[] args) {
        Day19 d = new Day19();
        d.init("/day19.txt" , "departure");
        d.printResult();
    }

    Map<Integer, Rule> allRules = new HashMap<>();
    List<String> datas = new ArrayList<>();

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            int index = 0;
            String line = lines.get(index);
            while (!line.isEmpty()) {
                String[] v1 = line.split(":");
                Rule r = getOrCreate(v1[0]);

                if(v1[1].trim().startsWith("\"")) {
                    r.val = v1[1].trim().substring(1,v1[1].trim().length()-1);
                } else {
                    String[] vr = v1[1].trim().split("\\|");
                    for(String l : vr[0].split(" ")) {
                        r.left.add(getOrCreate(l));
                    }
                    if(vr.length > 1) {
                        for(String l : vr[1].trim().split(" ")) {
                            r.right.add(getOrCreate(l));
                        }
                    }
                }

                index++;
                line = lines.get(index);
            }
            index++;
            while (index < lines.size()) {
                line = lines.get(index);
                datas.add(line);
                index++;
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Rule getOrCreate(String i) {
        int vi = Integer.parseInt(i.trim());
        return getOrCreate(vi);
    }

    public Rule getOrCreate(int i) {
        Rule r = allRules.getOrDefault(i, new Rule());
        r.index = i;
        allRules.put(i, r);
        return r;
    }

    @Data
    public class Rule {
        public int index;
        public String val;

        public List<Rule> right = new ArrayList<>();
        public List<Rule> left = new ArrayList<>();

        public Map<String, Boolean> alreadyTest = new HashMap<>();
    }

    @Data
    public class RulePosition {
        Rule parent;
        Rule currentRule;
        boolean left;
        public int index;
    }

    public boolean checkRule(String word, Rule r) {
        if(word == null || word.isEmpty()) {
            return false;
        }
        if(r.alreadyTest.containsKey(word)) {
            return r.alreadyTest.get(word);
        }
        boolean result;
        if(r.val != null) {
            result = r.val.equals(word);
        } else {
            result = checkRuleList(word,r.left);
            if(!result) {
                result = checkRuleList(word,r.right);
            }
        }
        r.alreadyTest.put(word, result);
        return result;
    }

    public boolean checkRuleList(String word, List<Rule> lr) {
        if((word == null || word.isEmpty()) && lr.isEmpty()) {
            return true;
        }
        if(word == null || word.isEmpty()) {
            return false;
        }
        if( lr.isEmpty()) {
            return false;
        }

        if(lr.size() == 1) {
            return checkRule(word, lr.get(0));
        }

        Rule first = lr.get(0);
        List<Rule> restRules = lr.subList(1, lr.size());

        for(int i=1; i<word.length(); i++) {
            boolean v1 = checkRule(word.substring(0,i), first);
            boolean v2 = checkRuleList(word.substring(i), restRules);
            if(v1 && v2) {
                return true;
            }
        }

        return false;
    }

    public void cleanRules() {
        for(Rule r : allRules.values()) {
            r.alreadyTest = new HashMap<>();
        }
    }


    public Long part1() {
        long result = 0;

        Rule r0 = allRules.get(0);
        for(String line : datas) {
            cleanRules();
            if(checkRule(line, r0)) {
                result++;
            }
        }

        return result;
    }


    public Long part2() {
        long result = 0;

        Rule r8 = allRules.get(8);
        r8.left.clear();
        r8.left.add(allRules.get(42));
        r8.right.clear();
        r8.right.add(allRules.get(42));
        r8.right.add(allRules.get(8));

        Rule r11 = allRules.get(11);
        r11.left.clear();
        r11.left.add(allRules.get(42));
        r11.left.add(allRules.get(31));
        r11.right.clear();
        r11.right.add(allRules.get(42));
        r11.right.add(allRules.get(11));
        r11.right.add(allRules.get(31));


        Rule r0 = allRules.get(0);
        for(String line : datas) {
            cleanRules();
            if(checkRule(line, r0)) {
                result++;
            }
        }

        return result;
    }



}
