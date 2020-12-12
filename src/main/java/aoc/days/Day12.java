package aoc.days;

import aoc.Day;
import aoc.common.Direction;
import aoc.common.Rotation;
import aoc.utils.ReadTxtFile;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Day12 extends Day<Long> {

    public static void main(String[] args) {
        Day12 d = new Day12();
        d.init("/day12.txt");
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
                allData.add(initElement(line));
            }
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    @Data
    public class Element {

        public Direction d;

        public Rotation r;

        public int value;

        public String data;
    }

    public Element initElement(String line) {
        Element result = new Element();
        String firstCar = line.substring(0,1);
        result.d = Direction.fromVal(firstCar);
        result.r = Rotation.fromName(firstCar);
        result.value = Integer.parseInt(line.substring(1));
        result.data = line;
        return result;
    }

    @Data
    @AllArgsConstructor
    public class Point {
        int x;
        int y;
        Direction face;

        public long getManhattanDistance(Point p) {
            return Math.abs(p.x - x) + Math.abs(p.y - y);
        }
    }

    public Point allMoveFrom(Point p) {
        Point currentPoint = new Point(p.x, p.y, p.face);

        for(Element e : allData) {
            if(e.d != null) {
                currentPoint.x += e.d.deltaX * e.value;
                currentPoint.y += e.d.deltaY * e.value;
            } else if (e.r == Rotation.FORWARD){
                currentPoint.x += currentPoint.face.deltaX * e.value;
                currentPoint.y += currentPoint.face.deltaY * e.value;
            } else {
                currentPoint.face = e.r.newDirection(currentPoint.face, e.value);
            }
        }
        return currentPoint;
    }


    public Long part1() {
        Point p = new Point(0, 0, Direction.EAST);

        Point a = allMoveFrom(p);

        return a.getManhattanDistance(p);
    }


    public Point allMoveWithWayPoint(Point b, Point wayPoint) {
        Point currentPoint = new Point(b.x, b.y, b.face);

        for(Element e : allData) {
            if(e.d != null) {
                wayPoint.x += e.d.deltaX * e.value;
                wayPoint.y += e.d.deltaY * e.value;
            } else if (e.r == Rotation.FORWARD){
                currentPoint.x += wayPoint.x * e.value;
                currentPoint.y += wayPoint.y * e.value;
            } else {
                wayPoint = rotate(e.r, e.value, wayPoint);
            }
        }
        return currentPoint;
    }

    public Point rotate(Rotation r, int angle, Point p) {
        Point result = new Point(p.x,p.y, p.face);
        switch (r) {
            case LEFT:
                while (angle >= 90) {
                    result = new Point(result.y, -result.x, result.face);
                    angle -= 90;
                }
                break;
            case RIGHT:
                while (angle >= 90) {
                    result = new Point(-result.y, result.x, result.face);
                    angle -= 90;
                }
                break;
        }
        return result;
    }

    public Long part2() {
        Point boat = new Point(0, 0, Direction.EAST);
        Point wp = new Point(10, -1, Direction.EAST);

        Point a = allMoveWithWayPoint(boat, wp);

        return a.getManhattanDistance(boat);
    }


}
