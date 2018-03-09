import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Scoreboard {

    File file;

    
    //Constructor
    public Scoreboard(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        file = new File(name);
    }


    public String[] getHighscores() {
        String[] topFive = new String[10];
        try {
            Scanner scanner = new Scanner(file);
            //Parse through the file and add the names and scores to the String[]
            for (int i = 0; i < 5; i++) {
                if (!scanner.hasNext()) {
                    break;
                }
                topFive[2*i] = scanner.next().toUpperCase();
                topFive[2*i + 1] = scanner.next();
            }
            scanner.close();
            return topFive;
        } catch (java.io.FileNotFoundException e) {
            //Return an empty array, rather than a null array;
            for (int j = 0; j < 5; j++) {
                topFive[2*j] = " ";
                topFive[2*j + 1] = " ";
            }
            return topFive;
        }
    }


    public void addScore(String name, int newScore) {
        //Get the top 5 scores from the file
        String[] scoreList = getHighscores();
        if (name == null) {
            throw new NullPointerException();
        }
        name = name.toUpperCase();
        try {
            //Create a FileWriter object
            FileWriter writeTo = new FileWriter(file);
            for (int i = 0; i < 5; i++) {
                int currScore = Integer.parseInt(scoreList[2*i + 1]);
                if (newScore >= currScore && !(name.equals(scoreList[2*i]))) {
                    writeTo.write(name + '\n');
                    writeTo.write(Integer.toString(newScore) + '\n');
                    newScore = 0;
                }
                //If the name and score are already on the board, skip
                if (newScore >= currScore && name.equals(scoreList[2*i])) {
                    newScore = 0;
                }
                writeTo.write(scoreList[2*i] + '\n');
                writeTo.write(scoreList[2*i + 1] + '\n');
            }
            writeTo.close();
        } catch (java.io.IOException e) {
            return;
        }
        return;
    }

    //Get the top 5 scores as an array with elements of format "Name    Score"
    public String[] getTopFive() {
        String[] topScores = getHighscores();
        String[] topFive = new String[5];
        for (int i = 0; i < 5; i++) {
            //Truncate the name if longer than 7 characters
            if (topScores[2*i].length() > 7) {
                topScores[2*i] = topScores[2*i].substring(0, 7);
            }
            topFive[i] = topScores[2*i] + " " + topScores[2*i + 1];
        }
        
        return topFive;
    }

}