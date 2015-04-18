package cs.iastate.edu.cs319;

/**
 * Listens for game-altering-settings to be changed.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public interface GameSettingsListener
{
    /**
     * Method fires if game difficulty has been changed.
     */
    public void difficultyChanged();
}
