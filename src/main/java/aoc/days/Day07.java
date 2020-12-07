package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.*;

import java.util.*;

public class Day07 extends Day<Long> {

    public static void main(String[] args) {
        Day07 d = new Day07();
        d.init("/day07.txt");
        d.printResult();
    }

    Map<String, Bag> allBag;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            allBag = new HashMap<>();
            // light red bags contain 1 bright white bag, 2 muted yellow bags.
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);

            for(String line : lines) {
                String[] fstep = line.split(" contain ");

                int lastSpace = fstep[0].trim().lastIndexOf(" ");
                String bagName = fstep[0].trim().substring(0, lastSpace);
                Bag currentBag = allBag.get(bagName);
                if(currentBag == null) {
                    currentBag = new Bag(bagName);
                    allBag.put(bagName, currentBag);
                }
                if(fstep[1].equals("no other bags.")) {
                    continue;
                }
                String[] insideStep = fstep[1].substring(0, fstep[1].length()-1).split(",");
                for(String inside : insideStep) {
                    // 2 muted yellow bags
                    int firstSpace = inside.trim().indexOf(" ");
                    int nbBag = Integer.parseInt(inside.trim().substring(0,firstSpace).trim());

                    lastSpace = inside.trim().substring(firstSpace).trim().lastIndexOf(" ");
                    String iBagName = inside.trim().substring(firstSpace).trim().substring(0, lastSpace);

                    Bag insideBag = allBag.get(iBagName);
                    if(insideBag == null) {
                        insideBag = new Bag(iBagName);
                        allBag.put(iBagName, insideBag);
                    }
                    currentBag.contain.put(insideBag, nbBag);
                    insideBag.inside.put(currentBag, nbBag);
                }
            }

        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;

        Bag shinyGold = allBag.get("shiny gold");

        Set<String> visited = new HashSet<>();
        visited.add("shiny gold");
        Set<Bag> currentBagSet = new HashSet<>(shinyGold.getInside().keySet());

        while (!currentBagSet.isEmpty()) {
            Bag cBag = currentBagSet.iterator().next();
            currentBagSet.remove(cBag);
            result++;
            for(Bag iBag : cBag.inside.keySet()) {
                if(!visited.contains(iBag.name)) {
                    currentBagSet.add(iBag);
                    visited.add(iBag.name);
                }
            }
        }

        return result;
    }

    public Long part2() {
        Bag shinyGold = allBag.get("shiny gold");
        return (long)computeNbContain(shinyGold);
    }


    public int computeNbContain(Bag currentBag) {
        if (currentBag.mustContain != -1) {
            return currentBag.mustContain;
        }
        int val = 0;
        for(Bag iBag : currentBag.contain.keySet()) {
            int nb = computeNbContain(iBag) + 1;
            int f = currentBag.contain.get(iBag);
            val += nb * f;
        }
        currentBag.mustContain = val;
        return currentBag.mustContain;
    }

    @Data
    @RequiredArgsConstructor
    @EqualsAndHashCode(exclude={"contain", "inside", "mustContain"})
    @ToString(exclude={"contain", "inside", "mustContain"})
    public class Bag {
        @NonNull
        public String name;
        public int mustContain = -1;
        public Map<Bag, Integer> contain = new HashMap<>();
        public Map<Bag, Integer> inside = new HashMap<>();

    }
}
