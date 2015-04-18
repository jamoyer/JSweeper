package cs.iastate.edu.cs319;

/**
 * This listens for any event that can change the JFaceButton.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public interface JFaceButtonListener
{
    /**
     * Changes the JFaceButton icon to the lose face.
     */
    public void gameEnded();

    /**
     * Changes the JFaceButton icon to the O Face.
     */
    public void mouseDown();

    /**
     * Changes the JFaceButton icon to the default face.
     */
    public void reset();

    /**
     * Changes the JFaceButton icon to the win face.
     */
    public void gameWon();
}