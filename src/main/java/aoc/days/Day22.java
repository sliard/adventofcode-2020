package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

public class Day22 extends Day<Long> {

    public static void main(String[] args) {
        Day22 d = new Day22();
        d.init("/day22.txt", "departure");
        d.printResult();
    }

    LinkedList<Integer> player1 = new LinkedList<>();
    LinkedList<Integer> player2 = new LinkedList<>();

    public void init(String... args) {
        // init stuff
        if (args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            int index = 1;
            String line = lines.get(index);
            while(!line.isEmpty()) {
                player1.add(Integer.parseInt(line));
                index++;
                line = lines.get(index);
            }
            index+=2;
            while (index < lines.size()) {
                line = lines.get(index);
                player2.add(Integer.parseInt(line));
                index++;
            }

        } catch (Exception ex) {
            println("Read file error (" + args[0] + ") : " + ex.getMessage());
        }
    }

    public Long part1() {
        long result = 0;

        LinkedList<Integer> player1Copy = new LinkedList<>(player1);
        LinkedList<Integer> player2Copy = new LinkedList<>(player2);

        playGame(player1Copy, player2Copy);
        LinkedList<Integer> winner = player1Copy.isEmpty() ? player2Copy : player1Copy;

        int place = winner.size();
        while(!winner.isEmpty()) {
            result += winner.poll() * place;
            place--;
        }

        return result;
    }


    public void playGame(LinkedList<Integer> game1, LinkedList<Integer> game2) {
        while(!game1.isEmpty() && !game2.isEmpty()) {
            int p1 = game1.poll();
            int p2 = game2.poll();
            if (p1 > p2) {
                game1.add(p1);
                game1.add(p2);
            } else {
                game2.add(p2);
                game2.add(p1);
            }
        }
    }


    public boolean isGame1win(List<Integer> game1, List<Integer> game2) {
        if(game2 == null || game2.isEmpty()) {
            return true;
        }
        if(game1 == null || game1.isEmpty()) {
            return false;
        }

        LinkedList<Integer> game1Copy = new LinkedList<>(game1);
        LinkedList<Integer> game2Copy = new LinkedList<>(game2);
        playGameRec(game1Copy, game2Copy);
        return !game1Copy.isEmpty();
    }

    public void playGameRec(LinkedList<Integer> game1, LinkedList<Integer> game2) {
        List<String> oldGame = new ArrayList<>();

        while(!game1.isEmpty() && !game2.isEmpty()) {

            String hash = game1.hashCode()+"-"+game2.hashCode();
            if(oldGame.contains(hash)) {
                game1.addAll(game2);
                game2.clear();
                return;
            }
            oldGame.add(hash);

            int p1 = game1.poll();
            int p2 = game2.poll();
            if(p1 <= game1.size() && p2 <= game2.size()) {
                boolean p1win = isGame1win(game1.subList(0,p1), game2.subList(0, p2));
                if (p1win) {
                    game1.add(p1);
                    game1.add(p2);
                } else {
                    game2.add(p2);
                    game2.add(p1);
                }
            } else if (p1 > p2) {
                game1.add(p1);
                game1.add(p2);
            } else {
                game2.add(p2);
                game2.add(p1);
            }
        }
    }

    public Long part2() {
        long result = 0;

        LinkedList<Integer> player1Copy = new LinkedList<>(player1);
        LinkedList<Integer> player2Copy = new LinkedList<>(player2);

        playGameRec(player1Copy, player2Copy);
        LinkedList<Integer> winner = player1Copy.isEmpty() ? player2Copy : player1Copy;

        int place = winner.size();
        while(!winner.isEmpty()) {
            result += winner.poll() * place;
            place--;
        }

        return result;
    }

}