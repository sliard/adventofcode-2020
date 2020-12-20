package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day20 extends Day<Long> {

    public static void main(String[] args) {
        Day20 d = new Day20();
        d.init("/day20.txt", "departure");
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
            for (int index = 0; index < lines.size(); index++) {
                String line = lines.get(index);
                String[] w = line.split(" ");
                Tile t = new Tile();
                t.name = Integer.parseInt(w[1].substring(0, w[1].length() - 1));
                t.data = new char[10][10];
                for (int y = 0; y < height; y++) {
                    index++;
                    line = lines.get(index);
                    for (int x = 0; x < width; x++) {
                        t.data[x][y] = line.charAt(x);
                    }
                }
                t.computeAllSideValue();
                allTiles.add(t);
                index++;
            }

        } catch (Exception ex) {
            println("Read file error (" + args[0] + ") : " + ex.getMessage());
        }
    }

    int width = 10;
    int height = 10;
    List<Tile> allTiles = new ArrayList<>();

    @Data
    @ToString
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public class Tile {
        @EqualsAndHashCode.Include
        public int name;

        @ToString.Exclude
        char[][] data;

        @ToString.Exclude
        List<String> allSideValue = new ArrayList<>();

        boolean used;

        int sideFree;

        boolean flip;
        int rotate;

        @ToString.Exclude
        List<Tile> linkTo = new ArrayList<>();

        public void computeAllSideValue() {
            StringBuilder top = new StringBuilder();
            StringBuilder bottom = new StringBuilder();
            StringBuilder left = new StringBuilder();
            StringBuilder right = new StringBuilder();
            for (int a = 0; a < width; a++) {
                top.append(data[a][0]);
                bottom.append(data[a][height - 1]);
                left.append(data[0][a]);
                right.append(data[width - 1][a]);
            }
            allSideValue.add(top.toString());
            allSideValue.add(right.toString());
            allSideValue.add(bottom.reverse().toString());
            allSideValue.add(left.reverse().toString());
            allSideValue.add(top.reverse().toString());
            allSideValue.add(right.reverse().toString());
            allSideValue.add(bottom.reverse().toString());
            allSideValue.add(left.reverse().toString());
        }

        public void moveData() {
            if(rotate == 0 && !flip) {
                return;
            }

            if(flip) {
                flipMatrix(data);
            }

            for(int i=0; i<rotate; i++) {
                rotateMatrix(data);
            }

        }
    }


    public void flipMatrix(char[][] matrix) {
        for(int y=0; y < matrix.length; y++) {
            for(int x=0; x < (matrix[y].length / 2); x++) {
                char tmp = matrix[matrix[y].length-1-x][y];
                matrix[matrix[y].length-1-x][y] = matrix[x][y];
                matrix[x][y] = tmp;
            }
        }
    }

    public void rotateMatrix(char[][] matrix) {
        int row = matrix.length;
        //first find the transpose of the matrix.
        for (int i = 0; i < row; i++) {
            for (int j = i; j < row; j++) {
                char temp = matrix[j][i];
                matrix[j][i] = matrix[i][j];
                matrix[i][j] = temp;
            }
        }
        //reverse each row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row / 2; j++) {
                char temp = matrix[j][i];
                matrix[j][i] = matrix[row - 1 - j][i];
                matrix[row - 1 - j][i] = temp;
            }
        }
    }

    public Long part1() {
        long result = 1;

        for (Tile currentTile : allTiles) {

            boolean[] sideFree = new boolean[4];
            for (int i = 0; i < 4; i++) {
                sideFree[i] = true;
            }

            for (Tile testTile : allTiles) {
                if (!testTile.equals(currentTile)) {
                    for (int i = 0; i < 4; i++) {
                        String sideToTest = currentTile.allSideValue.get(i);
                        sideFree[i] &= !testTile.allSideValue.contains(sideToTest);
                    }
                }
            }

            int nbFree = 0;
            for (int i = 0; i < 4; i++) {
                if (sideFree[i]) {
                    nbFree++;
                }
            }
            currentTile.sideFree = nbFree;
            if (nbFree == 2) {
                result *= currentTile.name;
            }
        }

        return result;
    }


    public Long part2() {
        List<Tile> angle = new ArrayList<>();
        boolean[] sideFree = new boolean[4];
        for (Tile currentTile : allTiles) {
            for (int i = 0; i < 4; i++) {
                sideFree[i] = true;
            }
            for (Tile testTile : allTiles) {
                if (!testTile.equals(currentTile)) {
                    for (int i = 0; i < 4; i++) {
                        String sideToTest = currentTile.allSideValue.get(i);
                        if (testTile.allSideValue.contains(sideToTest)) {
                            currentTile.linkTo.add(testTile);
                        }
                        sideFree[i] &= !testTile.allSideValue.contains(sideToTest);
                    }
                }
            }
            int nbFree = 0;
            for (int i = 0; i < 4; i++) {
                if (sideFree[i]) {
                    nbFree++;
                }
            }
            currentTile.sideFree = nbFree;
            if (nbFree == 2) {
                angle.add(currentTile);
            }
        }

        int tileWidth = (int) Math.sqrt(allTiles.size());
        int tileHeight = tileWidth;

        Tile[][] global = new Tile[tileHeight][tileWidth];
        global[0][0] = rotateFirst(angle.get(0));

        for (int y = 0; y < tileHeight; y++) {
            for (int x = 0; x < tileHeight; x++) {
                if (x == 0 && y == 0) {

                    continue;
                }
                if (x == 0) {
                    // Before is up
                    Tile top = global[y - 1][0];
                    global[y][x] = getNextToDown(top);
                    global[y][x].used = true;
                } else {
                    // Before is left
                    Tile left = global[y][x-1];
                    global[y][x] = getNextToRight(left);
                    global[y][x].used = true;
                }
            }
        }

        char[][] globalData = new char[tileWidth*((width-2))][tileWidth*((width-2))];

        long nbChar = 0;

        for (int y = 0; y < tileHeight; y++) {
            for (int x = 0; x < tileHeight; x++) {
                Tile t = global[y][x];
                t.moveData();

                for(int l=1; l<height-1; l++) {
                    int line = (l-1) + y*(height-2);
                    for(int r=1; r<width-1; r++) {
                        int row = (r-1) + x*(width-2);
                        globalData[row][line] = t.data[r][l];
                        if(t.data[r][l] == '#') {
                            nbChar++;
                        }
                    }
                }
            }
        }

        List<String> mask = new ArrayList<>();
        mask.add("                  # ");
        mask.add("#    ##    ##    ###");
        mask.add(" #  #  #  #  #  #   ");

        int nbmonsterChar = 15;

        int nbMonster = 0;

        for(int i=0; i<4; i++) {
            nbMonster += countMonster(globalData, mask);
            rotateMatrix(globalData);
        }
        flipMatrix(globalData);
        for(int i=0; i<4; i++) {
            nbMonster += countMonster(globalData, mask);
            rotateMatrix(globalData);
        }

        return (nbChar - nbMonster*nbmonsterChar);
    }

    public boolean lineMaskOk(String line, String mask) {
        if(line.length() != mask.length()) {
            return false;
        }

        for(int i=0; i<mask.length(); i++) {
            char cm = mask.charAt(i);
            char cl = line.charAt(i);
            if(cm == '#' && cl != '#') {
                return false;
            }
        }
        return true;
    }

    public String replaceMask(String line, String mask) {
        if(line.length() != mask.length()) {
            return "";
        }

        String result = line;
        for(int i=0; i<mask.length(); i++) {
            char cm = mask.charAt(i);
            char cl = line.charAt(i);
            if(cm == '#' && cl == '#') {
                result = result.substring(0,i) + "O" + result.substring(i+1,mask.length());
            }
        }
        return result;
    }

    public int countMonster(char[][] allDataChar, List<String> monster) {

        int result = 0;
        List<String> allLines = new ArrayList<>();
        for(int y=0; y< allDataChar.length; y++) {
            StringBuffer sb = new StringBuffer();
            for(int x=0; x< allDataChar[y].length; x++) {
                sb.append(allDataChar[x][y]);
            }
            allLines.add(sb.toString());
        }

        int fullLineSize = allLines.get(0).length();
        int monsterSize = monster.get(0).length();

        for(int i=0; i<allLines.size()-3;i++) {
            for(int s=0; s<fullLineSize-monsterSize; s++) {
                boolean foudMonster = true;
                for(int mi=0; mi<monster.size() && foudMonster; mi++) {
                    String subLine = allLines.get(i+mi).substring(s,s+monsterSize);
                    foudMonster &= lineMaskOk(subLine, monster.get(mi));
                }
                if (foudMonster) {
                    result++;
                    s+=monsterSize;
                }
            }

        }
        return result;
    }


    public void printData(char[][] allDataChar) {

        System.out.println("***");
        for(int y=0; y< allDataChar.length; y++) {
            StringBuffer sb = new StringBuffer();
            for(int x=0; x< allDataChar[y].length; x++) {
                sb.append(allDataChar[x][y]);
            }
            System.out.println(sb);
        }
    }

    public Tile getNextToRight(Tile base) {

        String side = "";
        switch (base.rotate) {
            case 0:
                side = base.flip ? base.allSideValue.get(7) : base.allSideValue.get(1);
                break;
            case 1:
                side = base.flip ? base.allSideValue.get(4) : base.allSideValue.get(0);
                break;
            case 2:
                side = base.flip ? base.allSideValue.get(5) : base.allSideValue.get(3);
                break;
            case 3:
                side = base.flip ? base.allSideValue.get(6) : base.allSideValue.get(2);
                break;
        }

        for (Tile t : base.linkTo) {
            if (t.allSideValue.contains(side) && !t.used) {
                // good !
                int i = t.allSideValue.indexOf(side);
                switch (i) {
                    case 0:
                        t.flip = true;
                        t.rotate = 3;
                        break;
                    case 1:
                        t.flip = true;
                        t.rotate = 0;
                        break;
                    case 2:
                        t.flip = true;
                        t.rotate = 1;
                        break;
                    case 3:
                        t.flip = true;
                        t.rotate = 2;
                        break;
                    case 4:
                        t.flip = false;
                        t.rotate = 3;
                        break;
                    case 5:
                        t.flip = false;
                        t.rotate = 2;
                        break;
                    case 6:
                        t.flip = false;
                        t.rotate = 1;
                        break;
                    case 7:
                        t.flip = false;
                        t.rotate = 0;
                        break;
                }
                return t;
            }
        }

        throw new IllegalArgumentException("No result");
    }


    public Tile getNextToDown(Tile base) {

        String side = "";
        switch (base.rotate) {
            case 0:
                side = base.flip ? base.allSideValue.get(6) : base.allSideValue.get(2);
                break;
            case 1:
                side = base.flip ? base.allSideValue.get(7) : base.allSideValue.get(1);
                break;
            case 2:
                side = base.flip ? base.allSideValue.get(4) : base.allSideValue.get(0);
                break;
            case 3:
                side = base.flip ? base.allSideValue.get(5) : base.allSideValue.get(3);
                break;
        }

        for (Tile t : base.linkTo) {
            if (t.allSideValue.contains(side) && !t.used) {
                // good !
                int i = t.allSideValue.indexOf(side);
                switch (i) {
                    case 0:
                        t.flip = true;
                        t.rotate = 0;
                        break;
                    case 1:
                        t.flip = true;
                        t.rotate = 1;
                        break;
                    case 2:
                        t.flip = true;
                        t.rotate = 2;
                        break;
                    case 3:
                        t.flip = true;
                        t.rotate = 3;
                        break;
                    case 4:
                        t.flip = false;
                        t.rotate = 0;
                        break;
                    case 5:
                        t.flip = false;
                        t.rotate = 3;
                        break;
                    case 6:
                        t.flip = false;
                        t.rotate = 2;
                        break;
                    case 7:
                        t.flip = false;
                        t.rotate = 1;
                        break;
                }
                return t;
            }
        }

        throw new IllegalArgumentException("No result");
    }

    public Tile rotateFirst(Tile base) {
        Tile link1 = base.linkTo.get(0);
        Tile link2 = base.linkTo.get(1);

        for(int i=0; i<4; i++) {
            int index = link1.allSideValue.indexOf(base.allSideValue.get(i));
            if(index >= 0) {
                String afterSide = base.allSideValue.get((i+1)%4);
                int indexAfter = link2.allSideValue.indexOf(afterSide);
                String beforeSide = base.allSideValue.get((i+1)%4);
                int indexBefore = link2.allSideValue.indexOf(beforeSide);
                if(indexAfter >= 0) {
                    base.rotate = (i+1)%4;
                } else {
                    base.rotate = i;
                }
                return base;
            }
        }

        throw new IllegalArgumentException("No result");

    }
}