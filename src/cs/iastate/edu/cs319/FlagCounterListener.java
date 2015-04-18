package cs.iastate.edu.cs319;

/**
 * Listens for new flags being placed.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public interface FlagCounterListener
{
    /**
     * Updates the flag counter with the given number of flags remaining.
     * 
     * @param flagsRemaining
     */
    public void flagCountUpdated(int flagsRemaining);
}
