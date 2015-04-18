package cs.iastate.edu.cs319;

/**
 * This exception should be thrown when the game is over to halt the game and do
 * necessary end game functions.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public class GameOverException extends Exception
{
    public GameOverException(String message)
    {
        super(message);
    }
}
