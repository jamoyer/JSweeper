package cs.iastate.edu.cs319;

import javax.swing.ImageIcon;

/**
 * An enumeration of all the different icons the JFaceButton can have.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public enum JFaceButtonIcon
{
    Default("images/SmileFace.png"), 
    Lose("images/DeadFace.png"), 
    Win("images/SunglassesFace.png"), 
    OFace("images/OFace.png"), 
    Pressed("images/DownSmiley.png");

    private final ImageIcon icon;

    private JFaceButtonIcon(final String fileName)
    {
        this.icon = new ImageIcon(getClass().getResource(fileName));
    }

    /**
     * Returns this ImageIcon.
     * 
     * @return
     */
    public ImageIcon getIcon()
    {
        return icon;
    }
}