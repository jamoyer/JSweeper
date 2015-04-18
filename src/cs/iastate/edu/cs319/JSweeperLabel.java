package cs.iastate.edu.cs319;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * Super class for the two counter labels in the ui.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public abstract class JSweeperLabel extends JLabel
{
    private static final String INITIAL_VALUE = "000";

    /**
     * Constructs a new JSweeperLabel.
     */
    public JSweeperLabel()
    {
        super(INITIAL_VALUE);
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        setFont(new Font("Consolas", Font.BOLD, 16));
    }

    /**
     * Sets the text in the label to just 3 digits.
     * 
     * @param value
     */
    public void updateTextNumeric(int value)
    {
        setText(String.format("%03d", value));
    }
}
