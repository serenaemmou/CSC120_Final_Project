import java.util.*;
import java.io.*;

public class Forestry {

    private static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        Forest myForest;
        int argIndex;
        boolean doNext;
        String forestName;

        System.out.println("Welcome to the Forestry Simulation");
        System.out.println("----------------------------------");

        doNext = true;

        for (argIndex = 0; doNext && argIndex < args.length; argIndex++) {
            System.out.println("Initializing from " + args[argIndex]);

            try {
                forestName = args[argIndex];
                myForest = Forest.readForestFromCSV(forestName);
            } catch (IOException e) {
                System.out.println("Error opening/reading " + e.getMessage());
                System.out.println("Old forest retained" + e.getMessage());

                continue;
            }

            doNext = menu(myForest, forestName);
        }

        System.out.println();
        System.out.println("Exiting the Forestry Simulation");
    }

    private static boolean menu(Forest forest, String fileName) {
        Forest NewForest;
        int number;
        int heightToReap;
        char choice;

        do {
            System.out.print("\n(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
            choice = Character.toUpperCase(keyboard.next().charAt(0));

            while ( choice!='P' && choice != 'A' && choice != 'C' && choice != 'G' && choice != 'R'
                    && choice != 'S' && choice != 'L' && choice != 'X' && choice !='N' ) {

                System.out.print("Invalid menu option, try again\n");
                System.out.print("\n(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
                choice = Character.toUpperCase(keyboard.next().charAt(0));
            }

            switch (choice) {
                case 'P':
                    forest.printForest();
                    break;

                case 'A':
                    forest.add();
                    break;

                case 'G':
                    forest.grow();
                    break;

                case 'R':
                    while (true) {
                        System.out.print("Height to reap from: ");
                        try {
                            heightToReap = keyboard.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("That is not an integer");
                            keyboard.next();
                        }
                    }
                    forest.reapTrees(heightToReap);
                    break;

                case 'C':
                    while (true) {
                        System.out.print("Tree number to cut down: ");
                        try {
                            number = keyboard.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("That is not an integer");
                            keyboard.next();
                        }
                    }
                    forest.remove(number);
                    break;

                case 'S':
                    Forest.saveForest(fileName, forest);
                    break;

                case 'L':
                    System.out.print("Forest name : ");
                    String forestToPick = keyboard.next();
                    try {
                        if(( NewForest = Forest.loadForest(forestToPick)) != null) {
                            forest = NewForest;
                            fileName = forestToPick;
                        }
                    } catch (IOException e) {
                        System.out.println("Error loading " + e.getMessage());
                    }
                    break;

                case 'N':
                    System.out.println("Moving to the next forest");
                    return true;

                case 'X':
                    return false;
            }
        } while (true);
    }

}