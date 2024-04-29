import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Forest implements Serializable {

    private String forestName;
    private ArrayList<Tree> trees = new ArrayList<>();

    public Forest(String name) {
        this.forestName = name;
    }

    public Forest() {
        this.forestName = null;
        this.trees = new ArrayList<>();
    }

    public Forest(ArrayList<Tree> treeList, String forestName) {
        this.forestName = forestName;
        trees = treeList;
    }

    public void printForest() {
        int index;
        double totalHeight = 0.0;


        System.out.println("\n Forest name: " + forestName);
        for (index = 0; index < trees.size(); index++) {
            System.out.printf("%2d %s\n", index, trees.get(index));
            totalHeight += trees.get(index).getHeight();
        }
        System.out.printf("There are %d trees, with an average height of %.2f\n", trees.size(),totalHeight / trees.size());
    }

    public void add() {
        trees.add(Tree.generateRandomTree());
    }

    public void remove(int treeNumber) {
        if (treeNumber >= 0 && treeNumber < trees.size()) {
            trees.remove(treeNumber);
        } else {
            System.out.println("Tree number " + treeNumber + " does not exist");
        }
    }

    public void grow() {
        int index;
        for (index = 0; index < trees.size(); index++) {
            trees.get(index).growForOneYear();
        }
    }

    public void reapTrees(double heightThreshold){
        List<Tree> treesToRemove = new ArrayList<>();
        for (Tree tree : trees) {
            if (tree.getHeight() >= heightThreshold) {
                treesToRemove.add(tree);
            }
        }
        for (Tree tree : treesToRemove) {
            trees.remove(tree); // Remove tall trees from the forest
            System.out.println("Reaping the tall tree  " + tree);
            // Replace with new tree (randomly generated)
            trees.add(Tree.generateRandomTree());
        }
    }

    public static Forest loadForest(String fileName) throws IOException {
        ObjectInputStream fromStream = null;
        Forest loadedForest;

        try {
            // Open a stream to read from the specified file
            fromStream = new ObjectInputStream(new FileInputStream(fileName +".db"));

            // Read the Forest object from the stream
            loadedForest = (Forest) fromStream.readObject();
        } catch (IOException e) {
            // Handle IO exceptions (file not found, etc.)
            System.out.println("Error loading Forest from file: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            // Handle class not found exception
            System.out.println("Error loading Forest: " + e.getMessage());
            return null;
        } finally {
            // Close the input stream
            if (fromStream != null) {
                try {
                    fromStream.close();
                } catch (IOException e) {
                    // Handle closing stream exception
                    System.out.println("Error closing file: " + e.getMessage());
                    return null;
                }
            }
        }
        return loadedForest;
    }
    public static boolean saveForest(String fileName,Forest saveForest) {

        ObjectOutputStream toStream = null;

        try {
            toStream = new ObjectOutputStream(new FileOutputStream(fileName + ".db"));
            toStream.writeObject(saveForest);
            return(true);
        } catch (IOException e) {
            System.out.println("ERROR saving " + e.getMessage());
            return(false);
        } finally {
            if (toStream != null) {
                try {
                    toStream.close();
                } catch (IOException e) {
                    System.out.println("ERROR closing " + e.getMessage());
                    return(false);
                }
            }
        }
    }

    public static Forest readForestFromCSV(String fileName) throws IOException {
        Forest forest = new Forest();
        forest.forestName = fileName;
        BufferedReader br = new BufferedReader(new FileReader(fileName + ".csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 4) {
                Tree.TreeSpecies species = Tree.TreeSpecies.valueOf(data[0].toUpperCase());
                int yearPlanted = Integer.parseInt(data[1]);
                double height = Double.parseDouble(data[2]);
                double growthRate = Double.parseDouble(data[3]);

                Tree newTree = new Tree(species, yearPlanted, height, growthRate);
                forest.trees.add(newTree);
            }
        }
        return(forest);
    }









}