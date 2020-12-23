package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

public class Day23 extends Day<Long> {

    public static void main(String[] args) {
        Day23 d = new Day23();
        d.init("/day23.txt");
        d.printResult();
    }


    public void init(String... args) {
        // init stuff
        if (args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            for(char c : lines.get(0).toCharArray()) {
                data.add(Integer.parseInt(""+c));
            }

        } catch (Exception ex) {
            println("Read file error (" + args[0] + ") : " + ex.getMessage());
        }
    }

    LinkedList<Integer> data = new LinkedList<>();

    public Long part1() {
        Element head = null;
        Element current = null;

        Map<Long, Element> reference = new HashMap<>();

        for(int i : data) {
            if(current == null) {
                current = new Element(i);
                head = current;
            } else {
                current.next = new Element(i);
                current = current.next;
            }
            reference.put((long)i,current);
            current.next = head;
        }

        current = head;
        for(int i=0; i<100;i++) {
            current = gameElement(current, reference, 9);
        }

        StringBuffer sb = new StringBuffer();
        Element e1 = reference.get((long)1);
        Element start = e1.next;
        for(int i=0; i<8; i++) {
            sb.append(start.value);
            start = start.next;
        }

        return Long.parseLong(sb.toString());
    }

    public Long part2() {
        Element head = null;
        Element current = null;

        Map<Long, Element> reference = new HashMap<>();

        for(int i : data) {
            if(current == null) {
                current = new Element(i);
                head = current;
            } else {
                current.next = new Element(i);
                current = current.next;
            }
            reference.put((long)i,current);
            current.next = head;
        }
        for (int i = data.size() + 1; i <= 1000000; i++) {
            current.next = new Element(i);
            current = current.next;
            reference.put((long)i, current);
        }
        current.next = head;

        current = head;
        for(int i=0; i<10000000;i++) {
            current = gameElement(current, reference, 1000000);
        }

        StringBuffer sb = new StringBuffer();
        Element e1 = reference.get((long)1);
        Element start = e1.next;
        for(int i=0; i<8; i++) {
            sb.append(start.value);
            start = start.next;
        }

        return reference.get(1L).next.value * reference.get(1L).next.next.value;
    }

    public class Element {
        public long value;
        public Element next;
        public Element prev;

        public Element(long v) {
            value = v;
        }
    }

    public Element gameElement(Element head, Map<Long, Element> ref, int maxValue) {
        Element current = head;
        Element first = current.next;
        Element last = current.next.next.next;
        // remove 3 elements
        current.next = last.next;
        long target = current.value - 1;
        while (target < 1 || target == first.value ||
                target == first.next.value || target == last.value) {
            target = (target < 1)? maxValue : target - 1;
        }
        Element targetNode = ref.get(target);
        last.next = targetNode.next;
        targetNode.next = first;
        return current.next;
    }

}