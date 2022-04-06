import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment6Driver {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static void main(String[] args){
//        testGame();
        playGame("moves1.txt");
        System.out.println();
        playGame("moves2.txt");
        System.out.println();
        playGame("movesTest.txt");
    }
    // plays the game. Reads the file, and determines if somebody has won based on play methods.
    private static void playGame(String filename) {
        ArrayList<Integer> plays = new ArrayList<>();
        File file = new File(filename);
        boolean bluesPlay = true;
        HexGame game = new HexGame();
        boolean winner = false;
        int winningPlay = -1;
        try (Scanner input = new Scanner(file)) {
            // TODO: Write some good stuff here
            while (input.hasNextInt()) {
                plays.add(input.nextInt());
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }
        for (Integer play: plays){
            if (bluesPlay){
                if (game.playBlue(play, false)){
                    winner = true;
                    winningPlay = play;
                    break;
                }
                bluesPlay = false;
            }
            else {
                if (game.playRed(play, false)){
                    winner = true;
                    winningPlay = play;
                    break;
                }
                bluesPlay = true;
            }
        }
        if (winner){
            String winnerDinner = "Red";
            if (bluesPlay){
                winnerDinner = "Blue";
            }
            System.out.println(winnerDinner + " wins with move at position " + winningPlay);
            System.out.println();
            printGrid(game);
        }
        else {
            System.out.println("No winner chicken dinner winner");
            System.out.println();
            printGrid(game);
        }
    }

    //
    // TODO: You can use this to compare with the output show in the assignment while working on your code
    private static void testGame() {
        HexGame game = new HexGame(11);

        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

        System.out.println("--- blue ---");
        game.playBlue(1, true);
        game.playBlue(2, true);
        game.playBlue(11, true);
        game.playBlue(12, true);
        game.playBlue(121, true);
        game.playBlue(122 - 11, true);
        game.playBlue(62, true);

        printGrid(game);
      }

        // TODO: Complete this method
    // prints the grid in color
    private static void printGrid(HexGame game) {
        StringBuilder message = new StringBuilder();
        String spacing = " ";
        for (int i = 0; i< game.getRowCount()*game.getRowCount(); i += game.getRowCount()){
            for (int j=1; j<=game.getRowCount(); j++){
                if (game.getHexColor(i+j) == HexGame.Color.BLUE){
                    message.append(ANSI_BLUE + "B " + ANSI_RESET);
                }
                else if (game.getHexColor(i+j) == HexGame.Color.RED) {
                    message.append(ANSI_RED + "R " + ANSI_RESET);
                }
                else{
                    message.append("0 ");
                }
            }
            System.out.print(message);
            System.out.println();
            message = new StringBuilder(spacing);
            spacing += " ";
        }
    }
    // tests for a good disjoint set
    private static void testDisjointSet(){
        DisjointSet disjointSet = new DisjointSet(20);
        disjointSet.union(1,2);
        System.out.println(disjointSet);
        disjointSet.union(0,2);
        System.out.println(disjointSet);
        disjointSet.union(0,1);
        System.out.println(disjointSet);
    }
}
