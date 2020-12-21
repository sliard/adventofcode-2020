package aoc.days;

import aoc.Day;
import aoc.utils.ReadTxtFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

public class Day21 extends Day<String> {

    public static void main(String[] args) {
        Day21 d = new Day21();
        d.init("/day21.txt", "departure");
        d.printResult();
    }

    List<Food> allFood = new ArrayList<>();
    Map<String, Ingredient> allIngredient = new HashMap<>();
    Map<String, Allergen> allAllergen = new HashMap<>();

    public void init(String... args) {
        // init stuff
        if (args == null || args.length == 0) {
            println("No args");
            return;
        }
        try {
            List<String> lines = ReadTxtFile.readFileAsStringList(args[0]);
            int index = 0;
            for(String line : lines) {
                String[] part = line.split(" \\(contains ");
                Food f = new Food();
                f.name = ""+index++;
                for(String p : part[0].split(" ")) {
                    f.ingredients.add(getOrCreateIngredient(p));
                }
                for(String p : part[1].substring(0,part[1].length()-1).split(",")) {
                    f.allergens.add(getOrCreateAllergen(p.trim(),f));
                }
                allFood.add(f);
            }

        } catch (Exception ex) {
            println("Read file error (" + args[0] + ") : " + ex.getMessage());
        }
    }

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @ToString(onlyExplicitlyIncluded = true)
    public class Food {
        @ToString.Include
        @EqualsAndHashCode.Include
        public String name;

        List<Ingredient> ingredients = new ArrayList<>();
        List<Allergen> allergens = new ArrayList<>();
    }

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @ToString(onlyExplicitlyIncluded = true)
    public class Ingredient {
        @ToString.Include
        @EqualsAndHashCode.Include
        public String name;

        @ToString.Include
        public int nbUse;

        @ToString.Include
        public Allergen allergen;
    }

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @ToString(onlyExplicitlyIncluded = true)
    public class Allergen {
        @ToString.Include
        @EqualsAndHashCode.Include
        public String name;

        List<Food> inFood = new ArrayList<>();

        public Ingredient ingredient;
    }

    public Ingredient getOrCreateIngredient(String name) {
        Ingredient i = allIngredient.getOrDefault(name, new Ingredient());
        i.name = name;
        i.nbUse++;
        allIngredient.put(name, i);
        return i;
    }

    public Allergen getOrCreateAllergen(String name, Food f) {
        Allergen a = allAllergen.getOrDefault(name, new Allergen());
        a.name = name;
        a.inFood.add(f);
        allAllergen.put(name, a);
        return a;
    }

    public String part1() {
        long result = 0;

        List<Allergen> restAllergen = new ArrayList<>(allAllergen.values());
        while (!restAllergen.isEmpty()) {
            List<Allergen> toRemove = new ArrayList<>();
            for(Allergen a : restAllergen) {
                List<Ingredient> possible = new ArrayList<>(a.inFood.get(0).ingredients);
                for(int i=0; i<a.inFood.size(); i++) {
                    List<Ingredient> rest = new ArrayList<>();
                    for(Ingredient ingredient : a.inFood.get(i).ingredients) {
                        if(ingredient.allergen == null && possible.contains(ingredient)) {
                            rest.add(ingredient);
                        }
                    }
                    possible = rest;
                }
                if(possible.size() == 1) {
                    possible.get(0).allergen = a;
                    a.ingredient = possible.get(0);
                    toRemove.add(a);
                }
            }
            restAllergen.removeAll(toRemove);
        }
        for(Ingredient i : allIngredient.values()) {
            if(i.allergen == null) {
                result += i.nbUse;
            }
        }

        return ""+result;
    }


    public String part2() {

        List<Allergen> allergens = new ArrayList<>(allAllergen.values());

        allergens.sort(Comparator.comparing(o -> o.name));

        List<String> allIngredientName = new ArrayList<>();
        for(Allergen a : allergens) {
            allIngredientName.add(a.ingredient.name);
        }

        return  String.join(",", allIngredientName);
    }

}