package aoc.handheld;

import aoc.utils.ReadTxtFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandheldCode {

    public List<Instruction> allInstruction;
    public long accumulator = 0;

    public void initFromFile(String fileName) throws IOException {
        List<String> allLines = ReadTxtFile.readFileAsStringList(fileName);
        allInstruction = new ArrayList<>();
        for(String line : allLines) {
            String[] d = line.split(" ");
            Instruction i = new Instruction(Command.getFromName(d[0].trim()), Integer.parseInt(d[1].trim()));
            allInstruction.add(i);
        }
    }

    public List<Instruction> getAllJump() {
        return allInstruction.stream().filter(i -> i.getCommand().equals(Command.JUMP)).collect(Collectors.toList());
    }

    public List<Instruction> getAllNop() {
        return allInstruction.stream().filter(i -> i.getCommand().equals(Command.NOP)).collect(Collectors.toList());
    }

    public boolean run() {
        accumulator = 0;
        int index = 0;
        Instruction currentInstruction = allInstruction.get(0);
        boolean endRun = false;

        while (!endRun && !currentInstruction.isVisited()) {
            currentInstruction.setVisited(true);

            switch (currentInstruction.getCommand()) {
                case ACCUMULATOR:
                    accumulator += currentInstruction.getVal();
                    index++;
                    break;
                case JUMP:
                    index += currentInstruction.getVal();
                    break;
                case NOP:
                    index++;
                    break;
                default:
                    throw new IllegalArgumentException("bad command : " + currentInstruction.getCommand());
            }
            if (index == allInstruction.size()) {
                endRun = true;
            } else {
                currentInstruction = allInstruction.get(index);
            }
        }
        return endRun;
    }


    public void resetVisitedData() {
        for(Instruction i : allInstruction) {
            i.setVisited(false);
        }
    }

}
