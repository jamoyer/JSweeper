package cs.iastate.edu.cs319;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * A static class which handles High Scores.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public class HighScoreHandler
{

    // This is the name of the high score file.
    private static final String HIGH_SCORES_FILENAME = "highScores.txt";

    /**
     * Tries to read the HIGH_SCORES_FILENAME file and retrieve the high scores
     * into a List of JSWeeperHighScore objects. If no file can be found it
     * returns an empty List.
     * 
     * @return
     */
    public static List<JSweeperHighScore> getHighScores()
    {
        File highScoresFile = new File(HIGH_SCORES_FILENAME);
        Scanner fileScanner = null;
        List<JSweeperHighScore> list = new ArrayList<JSweeperHighScore>();
        try
        {
            fileScanner = new Scanner(highScoresFile);

            // read in all the high scores.
            // high scores will be in a format:
            // line1 "score1 userName1" which will be the beginner highscore
            // line2 "score2 userName2" which will be the intermediate highscore
            // line3 "score3 userName3" which will be the expert highscore
            // ...other possible highscores
            while (fileScanner.hasNextLine())
            {
                int score = fileScanner.nextInt();
                String winner = fileScanner.nextLine();
                JSweeperHighScore hs = new JSweeperHighScore(score, winner);
                list.add(hs);
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e)
        {

        }

        return list;
    }

    /**
     * Displays a pop up menu asking for the user's name. Returns the input.
     * 
     * @return
     */
    public static String getUserName()
    {
        return (String) JOptionPane.showInputDialog(null, "You have Won!\n"
                + "Please enter your name: ", "High Score",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Given a Difficulty, a score, this function check if the user has beaten
     * the current high scores, ask for the user's name if they have, and write
     * the new highscore to HIGH_SCORES_FILENAME.
     * 
     * @param diff
     * @param score
     * @param userName
     */
    public static void writeNewHighScore(final Difficulty diff, final int score)
    {

        // first get the current high scores list
        ArrayList<JSweeperHighScore> scores = (ArrayList<JSweeperHighScore>) HighScoreHandler
                .getHighScores();

        // if there are no highscores, insert some "Nobody" scores into the list
        if (scores.size() == 0)
        {
            scores.add(new JSweeperHighScore(999, "Jenkins"));
            scores.add(new JSweeperHighScore(999, "Peabody"));
            scores.add(new JSweeperHighScore(999, "Jacob"));
        }

        // choose where to insert the new highscore
        int index = -1;
        switch (diff)
        {
            case Beginner:
                index = 0;
                break;
            case Expert:
                index = 2;
                break;
            case Intermediate:
                index = 1;
                break;
            case Custom:
            default:
                return;
        }

        // check to make sure that this really is a highscore, return if it
        // isn't
        // new highscores have to be faster than older ones
        if (scores.get(index).getScore() <= score)
        {
            return;
        }

        // get the user's name and update the list with their score
        scores.set(index, new JSweeperHighScore(score, getUserName()));

        // attempt to make the PrintWriter to the highscores file
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(HIGH_SCORES_FILENAME, "UTF-8");
        }
        catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            System.out.println("Could not write high score.");
            return;
        }

        // overwrite the old scores beginner, intermediate, and expert
        writer.println(scores.get(0).getScore() + " "
                + scores.get(0).getWinner());
        writer.println(scores.get(1).getScore() + " "
                + scores.get(1).getWinner());
        writer.println(scores.get(2).getScore() + " "
                + scores.get(2).getWinner());

        // close the writer.
        writer.close();
    }
}