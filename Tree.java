import java.io.*;
import java.util.*;

public class Tree implements Serializable {

    public enum TreeSpecies {MAPLE, BIRCH, FIR}

    private TreeSpecies species;
    private int yearPlanted;
    private double height;
    private double growthRate;

    public Tree(TreeSpecies species, int yearPlanted, double height, double growthRate) {
        this.species = species;
        this.yearPlanted = yearPlanted;
        this.height = height;
        this.growthRate = growthRate;
    }

    public void growForOneYear() {
        height += height * (growthRate / 100.0);
    }

    public String toString() {
        return String.format("%s %d %.2f' %.1f%%", species, yearPlanted, height, growthRate);
    }
    public double getHeight() {
        return height;
    }


    // Method to generate a random tree
    public static Tree generateRandomTree() {
        Random random = new Random();
        TreeSpecies[] speciesValues = TreeSpecies.values();
        TreeSpecies randomSpecies = speciesValues[random.nextInt(speciesValues.length)];
        int randomYear = random.nextInt(25) + 2000; // Random year between 2000 and 2024
        double randomHeight = random.nextDouble() * 10 + 10; // Random height between 10 and 20
        double randomGrowthRate = random.nextDouble() * 10 + 10; // Random growth rate between 10% and 20%
        return new Tree(randomSpecies, randomYear, randomHeight, randomGrowthRate);
    }

}

