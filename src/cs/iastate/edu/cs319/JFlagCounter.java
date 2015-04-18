package cs.iastate.edu.cs319;

/**
 * A JLabel that counts the number of flags left to be placed on the minefield.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public class JFlagCounter extends JSweeperLabel implements FlagCounterListener
{
    @Override
    public void flagCountUpdated(int flagsRemaining)
    {
        updateTextNumeric(flagsRemaining);
    }
}
