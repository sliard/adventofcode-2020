package aoc.days;

import aoc.Day;
import aoc.common.Direction;
import aoc.utils.ReadTxtFile;
import lombok.*;

import java.util.*;

public class Day24 extends Day<Long> {

    public static void main(String[] args) {
        Day24 d = new Day24();
        d.init("/day24.txt");
        d.printResult();
    }

    List<List<Direction>> datas = new ArrayList<>();

    public void init(String... args) {
        // init stuff
        if (args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);

            for(String line : lines) {
                List<Direction> allDirections = new ArrayList<>();

                for(int i=0; i<line.length();i++) {
                    char c = line.charAt(i);

                    if(c == 'e') {
                        allDirections.add(Direction.EAST);
                    } else if (c == 'w') {
                        allDirections.add(Direction.WEST);
                    } else {
                        String d = line.substring(i,i+2);
                        switch (d) {
                            case "se" :
                                allDirections.add(Direction.SOUTH_EAST);
                                break;
                            case "sw" :
                                allDirections.add(Direction.SOUTH);
                                break;
                            case "ne" :
                                allDirections.add(Direction.NORTH);
                                break;
                            case "nw" :
                                allDirections.add(Direction.NORTH_WEST);
                                break;
                        }
                        i++;
                    }
                }
                datas.add(allDirections);
            }

        } catch (Exception ex) {
            println("Read file error (" + args[0] + ") : " + ex.getMessage());
        }
    }

    @Data
    @RequiredArgsConstructor
    public class Point {
        @NonNull
        int x;
        @NonNull
        int y;
    }

    List<Direction> adjacent = List.of(Direction.EAST, Direction.WEST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.NORTH, Direction.NORTH_WEST);

    public Long part1() {

        Map<Point, Integer> allDestination = new HashMap<>();

        for(List<Direction> directions : datas) {
            Point start = new Point(0,0);
            for(Direction d : directions) {
                start.x += d.deltaX;
                start.y += d.deltaY;
            }
            if(allDestination.containsKey(start)) {
                allDestination.remove(start);
            } else {
                allDestination.put(start, 0);
            }
        }

        return (long)allDestination.size();
    }

    public Long part2() {

        Set<Point> allDestination = new HashSet<>();

        for(List<Direction> directions : datas) {
            Point start = new Point(0,0);
            for(Direction d : directions) {
                start.x += d.deltaX;
                start.y += d.deltaY;
            }
            if(allDestination.contains(start)) {
                allDestination.remove(start);
            } else {
                allDestination.add(start);
            }
        }

        for(int i=0; i<100;i++) {
            allDestination = game(allDestination);
        }
        return (long)allDestination.size();
    }

    public Set<Point> game(Set<Point> allBlack) {
        Set<Point> result = new HashSet<>();

        for(Point bp : allBlack) {
            int nbAdjacent = nbBlackAdjacent(allBlack, bp);
            if (nbAdjacent == 1 || nbAdjacent == 2) {
                result.add(bp);
            }

            for(Direction d : adjacent) {
                Point np = new Point(bp.x+d.deltaX, bp.y+d.deltaY);
                if(!allBlack.contains(np)) {
                    // if white
                    nbAdjacent = nbBlackAdjacent(allBlack, np);
                    if (nbAdjacent == 2) {
                        result.add(np);
                    }
                }
            }
        }
        return result;
    }

    public int nbBlackAdjacent(Set<Point> allBlack, Point p) {
        int result = 0;

        for(Direction d : adjacent) {
            Point np = new Point(p.x+d.deltaX, p.y+d.deltaY);
            result += allBlack.contains(np) ? 1 : 0;
        }
        return result;
    }

}