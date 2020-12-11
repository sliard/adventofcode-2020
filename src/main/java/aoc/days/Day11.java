package aoc.days;

import aoc.Day;
import aoc.common.Direction;
import aoc.utils.ReadTxtFile;

import java.util.List;

public class Day11 extends Day<Long> {

    public static void main(String[] args) {
        Day11 d = new Day11();
        d.init("/day11.txt");
        d.printResult();
    }

    char[][] allData;
    int width;
    int height;

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> allLines = ReadTxtFile.readFileAsStringList(args[0]);
            width = allLines.get(0).length();
            height = allLines.size();
            allData = new char[width][height];
            for(int y=0; y<allLines.size(); y++) {
                for(int x=0; x<allLines.get(y).length(); x++) {
                    allData[x][y] = allLines.get(y).charAt(x);
                }
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }


    public int life() {
        int nbSeat = 0;
        char[][] newData = new char[width][height];
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                int nbAdjacent = countAdjacent(x,y);
                if(allData[x][y] == 'L' && (nbAdjacent == 0)) {
                    newData[x][y] = '#';
                } else if (allData[x][y] == '#' && (nbAdjacent >= 4)) {
                    newData[x][y] = 'L';
                } else {
                    newData[x][y] = allData[x][y];
                }
            }
        }
        for(int x=0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                allData[x][y] = newData[x][y];
                if(allData[x][y] == '#') {
                    nbSeat++;
                }
            }
        }
        return nbSeat;
    }

    public int countAdjacent(int x, int y) {
        int result = 0;

        for(int nx=x-1; nx<=x+1; nx++) {
            for (int ny = y-1; ny <= y+1 ; ny++) {
                if(!(nx==x && ny==y)) {
                    result += (nx>=0 && nx<width && ny>=0 && ny<height && allData[nx][ny] == '#') ? 1 : 0;
                }
            }
        }
        return result;
    }


    public int life2() {
        int nbSeat = 0;
        char[][] newData = new char[width][height];
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                int nbAdjacent = countAdjacentV2(x,y);
                if(allData[x][y] == 'L' && (nbAdjacent == 0)) {
                    newData[x][y] = '#';
                } else if (allData[x][y] == '#' && (nbAdjacent >= 5)) {
                    newData[x][y] = 'L';
                } else {
                    newData[x][y] = allData[x][y];
                }
            }
        }
        for(int x=0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                allData[x][y] = newData[x][y];
                if(allData[x][y] == '#') {
                    nbSeat++;
                }
            }
        }
        return nbSeat;
    }

    public int countAdjacentV2(int x, int y) {
        int result = 0;

        for(Direction d : Direction.values()) {
            int nx = x + d.deltaX;
            int ny = y + d.deltaY;
            boolean found = false;
            while(!found && nx>=0 && nx<width && ny>=0 && ny<height && ( allData[nx][ny] != 'L')) {
                found = allData[nx][ny] == '#';
                nx = nx + d.deltaX;
                ny = ny + d.deltaY;
            }
            result += found ? 1 : 0;
        }
        return result;
    }


    public Long part1() {
        long result = -1;

        char[][] saveData = new char[width][height];
        for(int x=0; x<width; x++) {
            System.arraycopy(allData[x], 0, saveData[x], 0, height);
        }

        long nbSeat = life();
        while(nbSeat != result) {
            result = nbSeat;
            nbSeat = life();
        }

        for(int x=0; x<width; x++) {
            System.arraycopy(saveData[x], 0, allData[x], 0, height);
        }

        return result;
    }


    public Long part2() {
        long result = -1;
        long nbSeat = life2();
        while(nbSeat != result) {
            result = nbSeat;
            nbSeat = life2();
        }
        return result;
    }

    public void printData() {
        for(int y=0; y<height; y++) {
            StringBuilder sb = new StringBuilder();
            for(int x=0; x<width; x++) {
                sb.append(allData[x][y]);
            }
            System.out.println(sb.toString());
        }
        System.out.println();
    }

}
