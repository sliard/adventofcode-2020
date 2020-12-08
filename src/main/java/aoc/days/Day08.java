package aoc.days;

import aoc.Day;
import aoc.handheld.Command;
import aoc.handheld.HandheldCode;
import aoc.handheld.Instruction;

public class Day08 extends Day<Long> {

    public static void main(String[] args) {
        Day08 d = new Day08();
        d.init("/day08.txt");
        d.printResult();
    }


    HandheldCode handheldCode = new HandheldCode();

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            handheldCode.initFromFile(args[0]);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Long part1() {
        handheldCode.resetVisitedData();
        handheldCode.run();
        return handheldCode.accumulator;
    }

    public Long part2() {
        handheldCode.resetVisitedData();

        if(handheldCode.run()) {
            return handheldCode.accumulator;
        }

        for(Instruction j : handheldCode.getAllJump()) {
            j.setCommand(Command.NOP);
            handheldCode.resetVisitedData();
            if(handheldCode.run()) {
                return handheldCode.accumulator;
            }
            j.setCommand(Command.JUMP);
        }

        for(Instruction j : handheldCode.getAllNop()) {
            j.setCommand(Command.JUMP);
            handheldCode.resetVisitedData();
            if(handheldCode.run()) {
                return handheldCode.accumulator;
            }
            j.setCommand(Command.NOP);
        }

        return (long)-1;
    }

}
