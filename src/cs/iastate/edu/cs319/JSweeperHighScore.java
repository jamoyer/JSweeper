package cs.iastate.edu.cs319;

/**
 * Represents a High score in JSweeper.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public class JSweeperHighScore
{
    private final int score;
    private final String winner;

    /**
     * Takes a score and a user's name and constructs a new JSweeperHighScore.
     * 
     * @param score
     * @param winner
     */
    public JSweeperHighScore(final int score, final String winner)
    {
        this.score = score;
        this.winner = winner;
    }

    /**
     * Returns the name of the winner.
     * 
     * @return
     */
    public String getWinner()
    {
        return winner;
    }

    /**
     * Returns the score.
     * 
     * @return
     */
    public int getScore()
    {
        return score;
    }
}
