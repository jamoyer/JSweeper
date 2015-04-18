package cs.iastate.edu.cs319;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JButton;

/**
 * JFaceButton displays game status via its icon and can reset the game if
 * clicked on.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public class JFaceButton extends JButton implements JFaceButtonListener
{
    /**
     * Constructs the JFaceButton.
     */
    public JFaceButton()
    {
        super();
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorderPainted(false);
        setBorder(null);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setIcon(JFaceButtonIcon.Default.getIcon());
        setPressedIcon(JFaceButtonIcon.Pressed.getIcon());
    }

    @Override
    public void gameEnded()
    {
        setIcon(JFaceButtonIcon.Lose.getIcon());
    }

    @Override
    public void mouseDown()
    {
        setIcon(JFaceButtonIcon.OFace.getIcon());
    }

    @Override
    public void reset()
    {
        setIcon(JFaceButtonIcon.Default.getIcon());
    }

    @Override
    public void gameWon()
    {
        setIcon(JFaceButtonIcon.Win.getIcon());
    }
}
