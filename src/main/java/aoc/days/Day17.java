package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 extends Day<Long> {

    public static void main(String[] args) {
        Day17 d = new Day17();
        d.init("/day17.txt" , "departure");
        d.printResult();
    }

    public void init(String ...args) {
        // init stuff
        if(args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            Set<Point> points = new HashSet<>();
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            minWidth = 0;
            minHeight = 0;
            maxHeight = lines.size();
            for(int y=0; y < lines.size(); y++) {
                String line = lines.get(y);
                maxWidth = line.length();
                for(int x=0; x < line.length(); x++) {
                    if(line.charAt(x) == '#') {
                        points.add(new Point(x,y));
                    }
                }
                layers.put(0,points);
            }

            layersOfLayers.put(0, layers);
        } catch (Exception ex) {
            println("Read file error ("+args[0]+") : "+ex.getMessage());
        }
    }

    public Map<Integer, Set<Point>> layers = new HashMap<>();
    int minWidth;
    int maxWidth;
    int minHeight;
    int maxHeight;

    public Map<Integer,Map<Integer, Set<Point>>> layersOfLayers = new HashMap<>();

    @Data
    @RequiredArgsConstructor
    public class Point {
        @NonNull
        int x;
        @NonNull
        int y;
    }


    public int life() {
        int nbCube = 0;
        Map<Integer, Set<Point>> newData = new HashMap<>();
        int min = layers.keySet().stream().sorted().min(Integer::compareTo).orElseThrow(NoSuchElementException::new);
        int max = layers.keySet().stream().sorted().max(Integer::compareTo).orElseThrow(NoSuchElementException::new);

        minWidth--;
        maxWidth++;
        minHeight--;
        maxHeight++;

        for(int layer=min-1; layer <=max+1; layer++) {

            int layerNbCube = 0;
            Set<Point> newLayer = new HashSet<>();
            for(int x=minWidth; x<maxWidth; x++) {
                for(int y=minHeight; y<maxHeight; y++) {
                    int nbAdjacent = countAdjacent(x,y,layer);
                    boolean currentIsActive = isActive(x,y,layer);
                    if(currentIsActive && (nbAdjacent == 2 || nbAdjacent == 3)) {
                        newLayer.add(new Point(x,y));
                        nbCube++;
                        layerNbCube++;
                    } else if (!currentIsActive && (nbAdjacent == 3)) {
                        newLayer.add(new Point(x,y));
                        nbCube++;
                        layerNbCube++;
                    }
                }
            }

            if(layer == min-1 || layer == max+1) {
                if(layerNbCube > 0) {
                    newData.put(layer, newLayer);
                }
            } else {
                newData.put(layer, newLayer);
            }
        }

        layers = newData;

        return nbCube;
    }

    public boolean isActive(int x, int y, int z) {
        if(!layers.containsKey(z)) {
            return false;
        }
        return layers.get(z).contains(new Point(x,y));
    }

    public int countAdjacent(int x, int y, int z) {
        int result = 0;

        for(int nz=z-1; nz <= z+1; nz++) {
            if(!layers.containsKey(nz)) {
                continue;
            }
            Set<Point> currentLayer = layers.get(nz);
            for(int nx=x-1; nx<=x+1; nx++) {
                for (int ny = y-1; ny <= y+1 ; ny++) {
                    if(!(nx==x && ny==y && nz==z)) {
                        result += currentLayer.contains(new Point(nx,ny)) ? 1 : 0;
                    }
                }
            }
        }
        return result;
    }


    public boolean isActive(int x, int y, int z, int w) {
        if(!layersOfLayers.containsKey(w)) {
            return false;
        }
        Map<Integer, Set<Point>> currentLayerofLayer = layersOfLayers.get(w);
        if(!currentLayerofLayer.containsKey(z)) {
            return false;
        }
        return currentLayerofLayer.get(z).contains(new Point(x,y));
    }

    public int countAdjacent(int x, int y, int z, int w) {
        int result = 0;

        for (int nw=w-1; nw <= w+1; nw++) {
            if(!layersOfLayers.containsKey(nw)) {
                continue;
            }
            Map<Integer, Set<Point>> currentLayerofLayer = layersOfLayers.get(nw);
            for(int nz=z-1; nz <= z+1; nz++) {
                if(!currentLayerofLayer.containsKey(nz)) {
                    continue;
                }
                Set<Point> currentLayer = currentLayerofLayer.get(nz);
                for(int nx=x-1; nx<=x+1; nx++) {
                    for (int ny = y-1; ny <= y+1 ; ny++) {
                        if(!(nx==x && ny==y && nz==z && nw == w)) {
                            result += currentLayer.contains(new Point(nx,ny)) ? 1 : 0;
                        }
                    }
                }
            }
        }

        return result;
    }


    public int lifeV2() {
        int nbCube = 0;

        minWidth--;
        maxWidth++;
        minHeight--;
        maxHeight++;

        Map<Integer,Map<Integer, Set<Point>>> newLayerOfLayer = new HashMap<>();
        int min = layersOfLayers.keySet().stream().sorted().min(Integer::compareTo).orElseThrow(NoSuchElementException::new);
        int max = layersOfLayers.keySet().stream().sorted().max(Integer::compareTo).orElseThrow(NoSuchElementException::new);

        int minLayer = layersOfLayers.get(0).keySet().stream().sorted().min(Integer::compareTo).orElseThrow(NoSuchElementException::new);
        int maxLayer = layersOfLayers.get(0).keySet().stream().sorted().max(Integer::compareTo).orElseThrow(NoSuchElementException::new);

        for(int w=min-1; w <= max+1; w++) {
            Map<Integer, Set<Point>> newData = new HashMap<>();

            for(int layer=minLayer-1; layer <=maxLayer+1; layer++) {
                Set<Point> newLayer = new HashSet<>();
                for(int x=minWidth; x<maxWidth; x++) {
                    for(int y=minHeight; y<maxHeight; y++) {
                        int nbAdjacent = countAdjacent(x,y,layer,w);
                        boolean currentIsActive = isActive(x,y,layer,w);
                        if(currentIsActive && (nbAdjacent == 2 || nbAdjacent == 3)) {
                            newLayer.add(new Point(x,y));
                            nbCube++;
                        } else if (!currentIsActive && (nbAdjacent == 3)) {
                            newLayer.add(new Point(x,y));
                            nbCube++;
                        }
                    }
                }
                newData.put(layer, newLayer);
            }

            newLayerOfLayer.put(w, newData);
        }

        layersOfLayers = newLayerOfLayer;

        return nbCube;
    }

    public void printData(int i) {
        System.out.println("** After "+(i+1)+" cycle");
        for(Integer w : layersOfLayers.keySet().stream().sorted().collect(Collectors.toList())) {
            System.out.println("********");
            System.out.println("w="+w);
            printData(layersOfLayers.get(w));
        }
    }


    public void printData(Map<Integer, Set<Point>> allLayers) {
        int min = allLayers.keySet().stream().sorted().min(Integer::compareTo).orElseThrow(NoSuchElementException::new);
        int max = allLayers.keySet().stream().sorted().max(Integer::compareTo).orElseThrow(NoSuchElementException::new);

        System.out.println("********************");
        for(int z=min; z <=max; z++) {
            System.out.println("z="+z);
            Set<Point> layer = allLayers.get(z);
            for(int y=minHeight; y<maxHeight; y++) {
                StringBuilder sb = new StringBuilder();
                for(int x=minWidth; x<maxWidth; x++) {
                    if(layer.contains(new Point(x,y))) {
                        sb.append('#');
                    } else {
                        sb.append('.');
                    }
                }
                System.out.println(sb.toString());
            }

        }

        System.out.println();
    }

    public Long part1() {

        long result = 0;

        for(int i=0; i<6; i++) {
            result = life();
        }

        return result;
    }

    public Long part2() {
        long result = 0;

        for(int i=0; i<6; i++) {
            result = lifeV2();
        }

        return result;
    }

}
