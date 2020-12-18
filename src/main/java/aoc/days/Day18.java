package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day18 extends Day<Long> {

    public static void main(String[] args) {
        Day18 d = new Day18();
        d.init("/day18.txt" , "departure");
        d.printResult();
    }

    List<String> lines;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            lines = ReadTxtFile.readFileAsStringList(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;

        for(String line : lines) {
            String cl = computeOneLine(line, false);
            while (cl.contains("(")) {
                cl = computeOneLine(cl, false);
            }
            List<Element> myElem = getFromString(cl);
            long v = computeStringNoPrio(myElem);
            result += v;
        }

        // 341513 is too low
        // 3753343388092 is too low
        // 16332191652452

        return result;
    }

    public String computeOneLine(String line, boolean prio) {

        int[] indexData = new int[line.length()];

        Map<Integer, List<Integer>> changeDeepIndex = new HashMap<>();

        int deep = 0;
        int maxDeep = 0;
        for(int i=0; i<line.length(); i++) {
            char c = line.charAt(i);
            if(c == '(') {
                deep++;
                List<Integer> allStep = changeDeepIndex.getOrDefault(deep, new ArrayList<>());
                allStep.add(i);
                changeDeepIndex.put(deep, allStep);
            } else if(c == ')') {
                List<Integer> allStep = changeDeepIndex.getOrDefault(deep, new ArrayList<>());
                allStep.add(i);
                changeDeepIndex.put(deep, allStep);
                deep--;
            }
            if(deep > maxDeep) {
                maxDeep = deep;
            }
            indexData[i] = deep;
        }

        String currentLine = line;
        int delta = 0;
        if(maxDeep > 0) {
            List<Integer> allStep = changeDeepIndex.get(maxDeep);
            for(int i=0; i<allStep.size(); i+=2) {
                int start = allStep.get(i) + delta;
                int end = allStep.get(i+1) + 1 + delta;
                String deepString = "";
                try {
                    deepString = currentLine.substring(start, end);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<Element> myElem = getFromString(deepString);
                long v = prio ? computeStringPrio(myElem) : computeStringNoPrio(myElem);
                String startLine = currentLine.substring(0,start) + v;
                delta += startLine.length() - end;
                currentLine = startLine + currentLine.substring(end);
            }
        }

        return currentLine;
    }

    public List<Element> getFromString(String data) {
        List<Element> result = new ArrayList<>();

        if (data.startsWith("(")) {
            data = data.substring(1);
        }
        if (data.endsWith(")")) {
            data = data.substring(0, data.length()-1);
        }
        String[] elem = data.split(" ");

        for(String el : elem) {
            Element e = new Element();
            if(el.equals("*") || el.equals("+")) {
                e.operator = el.charAt(0);
            } else {
                e.value = Integer.parseInt(el);
            }
            result.add(e);
        }
        return result;
    }


    public long computeStringNoPrio(List<Element> operation) {

        if(operation.size() == 0) {
            return 0;
        }

        if(operation.size() == 1) {
            return operation.get(0).value;
        }

        long result = operation.get(0).value;

        // manage
        for(int i=1; i<operation.size(); i++) {
            Element e = operation.get(i);
            if(e.operator == '*') {
                result *= operation.get(i+1).value;
                i++;
            } else if(e.operator == '+') {
                result += operation.get(i+1).value;
                i++;
            }
        }

        return result;
    }

    public long computeStringPrio(List<Element> operation) {

        if(operation.size() == 0) {
            return 0;
        }

        if(operation.size() == 1) {
            return operation.get(0).value;
        }

        List<Element> temp = new ArrayList<>();
        int tempIndex = 0;
        // manage
        for(int i=0; i<operation.size(); i++) {
            Element e = operation.get(i);
            if(e.operator == '+') {
                Element ne = new Element();
                ne.value = temp.get(tempIndex-1).value + operation.get(i+1).value;
                temp.set(tempIndex-1, ne);
                i++;
            } else {
                temp.add(e);
                tempIndex++;
            }
        }

        if(temp.size() == 1) {
            return temp.get(0).value;
        }
        long result = 1;
        for(int i=0; i<temp.size(); i+=2) {
            Element e = temp.get(i);
            result *= e.value;
        }
        return result;
    }

    @Data
    public class Element {
        long value;
        char operator;
    }

    public Long part2() {
        long result = 0;

        for(String line : lines) {
            String cl = computeOneLine(line, true);
            while (cl.contains("(")) {
                cl = computeOneLine(cl, true);
            }
            List<Element> myElem = getFromString(cl);
            long v = computeStringPrio(myElem);
            result += v;
        }

        // 4262718141345 is too low

        return result;

    }

}
