package cs.iastate.edu.cs319;

import javax.swing.ImageIcon;

/**
 * A base model which represents a tile in JSweeper.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public class JTile
{
    private boolean isBomb;
    private boolean visited;
    private Status status;
    private int neighbors;
    private ImageIcon icon;

    /**
     * Makes a new JTile that is not a bomb, not visited, has no status, has 0
     * bomb neighbors, and has a basic covered tile as it's icon image.
     */
    public JTile()
    {
        isBomb = false;
        visited = false;
        status = Status.none;
        neighbors = 0;
        icon = TileIcon.Covered.getIcon();
    }

    /**
     * Returns the IconImage for this tile.
     * 
     * @return
     */
    public ImageIcon getIcon()
    {
        return icon;
    }

    /**
     * Sets the IconImage for this tile.
     * 
     * @param icon
     */
    public void setIcon(ImageIcon icon)
    {
        this.icon = icon;
    }

    /**
     * Returns true if this tile is a bomb, else returns false.
     * 
     * @return
     */
    public boolean isBomb()
    {
        return isBomb;
    }

    /**
     * Sets whether this tile is a bomb.
     * 
     * @param isBomb
     */
    public void setBomb(boolean isBomb)
    {
        this.isBomb = isBomb;
    }

    /**
     * Returns true if this tile has been visited.
     * 
     * @return
     */
    public boolean isVisited()
    {
        return visited;
    }

    /**
     * Sets whether this tile has been visited or not. JTile then changes it's
     * IconImage to be uncovered, trippedMine, or displays the number of
     * neighbors.
     * 
     * @param visited
     */
    public void setVisited(boolean visited)
    {
        this.visited = visited;
        TileIcon iconEnum = null;
        if (isBomb)
        {
            iconEnum = TileIcon.TrippedMine;
        }
        else
        {
            switch (neighbors)
            {
                case 8:
                    iconEnum = TileIcon.EightNeighbors;
                    break;
                case 7:
                    iconEnum = TileIcon.SevenNeighbors;
                    break;
                case 6:
                    iconEnum = TileIcon.SixNeighbors;
                    break;
                case 5:
                    iconEnum = TileIcon.FiveNeighbors;
                    break;
                case 4:
                    iconEnum = TileIcon.FourNeighbors;
                    break;
                case 3:
                    iconEnum = TileIcon.ThreeNeighbors;
                    break;
                case 2:
                    iconEnum = TileIcon.TwoNeighbors;
                    break;
                case 1:
                    iconEnum = TileIcon.OneNeighbor;
                    break;
                default:
                    iconEnum = TileIcon.Uncovered;
            }
        }

        icon = iconEnum.getIcon();
    }

    /**
     * Returns the status of this tile.
     * 
     * @return
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     * Sets the status of this tile.
     * 
     * @param status
     */
    public void setStatus(Status status)
    {
        this.status = status;
        TileIcon iconEnum = null;
        switch (status)
        {
            case flagged:
                iconEnum = TileIcon.Flagged;
                break;
            case questionable:
                iconEnum = TileIcon.Questionable;
                break;
            default:
                iconEnum = TileIcon.Covered;
        }
        icon = iconEnum.getIcon();
    }

    /**
     * Returns the number of neighbors around this tile.
     * 
     * @return
     */
    public int getNeighbors()
    {
        return neighbors;
    }

    /**
     * Sets the number of neighbors around this tile.
     * 
     * @param neighbors
     */
    public void setNeighbors(int neighbors)
    {
        this.neighbors = neighbors;
    }
}
