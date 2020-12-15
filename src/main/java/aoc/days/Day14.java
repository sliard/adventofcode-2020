package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day<Long> {

    public static void main(String[] args) {
        Day14 d = new Day14();
        d.init("/day14.txt");
        d.printResult();
    }

    List<Element> allData;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            allData = new ArrayList<>();
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            for(String line : allLines) {
                Element e = new Element();
                String[] e1 = line.split(" = ");
                if (line.startsWith("mask")) {
                    e.mask = e1[1];
                } else {
                    e.address = Integer.parseInt(e1[0].substring(4, e1[0].length()-1));
                    e.value = Integer.parseInt(e1[1]);
                    e.binaryValue = computeBinaryvalue(e.value, 36);
                    e.binaryAddress = computeBinaryvalue(e.address, 36);
                }
                allData.add(e);
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public String computeBinaryvalue(long v, int l) {
        String binaryValue = Long.toBinaryString(v);
        StringBuilder zero = new StringBuilder();
        for(int i=0; i<l-binaryValue.length(); i++) {
            zero.append("0");
        }
        return zero + binaryValue;
    }

    public long applyMask(String mask, String v) {
        long r = 1;
        long result = 0;
        for(int i = mask.length()-1; i>=0; i--) {
            if (mask.charAt(i) == '1') {
                result += r;
            } else if (mask.charAt(i) == 'X' && v.charAt(i) == '1') {
                result += r;
            }
            r *= 2;
        }
        return result;
    }

    public String applyMaskV2(String mask, String v) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<mask.length(); i++) {
            if (mask.charAt(i) == '1') {
                result.append("1");
            } else if (mask.charAt(i) == 'X') {
                result.append("X");
            } else  {
                result.append(v.charAt(i));
            }
        }
        return result.toString();
    }


    @Data
    public class Element {

        public String mask = null;

        public int address;

        public long value;

        public String binaryValue;

        public String binaryAddress;
    }

    public Long part1() {

        Map<Integer, Long> memory = new HashMap<>();

        Element currentMask = null;
        for(Element e : allData) {
            if (e.mask != null) {
                currentMask = e;
            } else {
                memory.put(e.address, applyMask(currentMask.mask, e.binaryValue));
            }
        }

        long result = 0;
        for(Long v : memory.values()) {
            result += v;
        }

        return result;
    }

    public Long part2() {
        Map<Long, Long> memory = new HashMap<>();

        Element currentMask = null;
        for(Element e : allData) {
            if (e.mask != null) {
                currentMask = e;
            } else {
                String a = applyMaskV2(currentMask.mask, e.binaryAddress);
                List<Long> allAddress = getAllValue(a);
                for(long v : allAddress) {
                    memory.put(v, e.value);
                }
            }
        }

        long result = 0;
        for(Long v : memory.values()) {
            result += v;
        }

        return result;

    }

    public List<Long> getAllValue(String v) {

        List<Long> result = new ArrayList<>();

        int nbVar = 0;
        StringBuilder base = new StringBuilder();
        List<Long> factor = new ArrayList<>();
        for(int i=0; i<v.length(); i++) {
            char c = v.charAt(i);
            if(c == 'X') {
                factor.add((long)Math.pow(2,v.length()-i-1));
                nbVar++;
                base.append("0");
            } else {
                base.append(c);
            }
        }
        long baseValue = Long.parseLong(base.toString(), 2);

        if(nbVar == 0) {
            result.add(baseValue);
            return result;
        }

        long maxCombi = (long)Math.pow(2,factor.size());
        for(int i=0; i<maxCombi; i++) {
            long val = 0;
            String bi = Integer.toBinaryString(i);
            int lastIndex = bi.length()-1;
            for(int j=0; j<factor.size();j++) {
                if(lastIndex >= 0 && bi.charAt(lastIndex) == '1') {
                    val += factor.get(j);
                }
                lastIndex--;
            }
            result.add(baseValue + val);
        }
        return result;
    }

}
