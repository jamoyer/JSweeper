package cs.iastate.edu.cs319;

import javax.swing.ImageIcon;

/**
 * Holds every possible icon a tile can have.
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public enum TileIcon 
{
	Uncovered("images/BaseTile.png"),
	Covered("images/CoveredTile.png"),
	TrippedMine("images/TrippedMine.png"),
	UntrippedMine("images/UntrippedMine.png"),
	Flagged("images/Flagged.png"),
	Misflagged("images/FalseFlag.png"),
	Questionable("images/Questionable.png"),
	
	OneNeighbor("images/1Neighbor.png"),
	TwoNeighbors("images/2Neighbors.png"),
	ThreeNeighbors("images/3Neighbors.png"),
	FourNeighbors("images/4Neighbors.png"),
	FiveNeighbors("images/5Neighbors.png"),
	SixNeighbors("images/6Neighbors.png"),
	SevenNeighbors("images/7Neighbors.png"),
	EightNeighbors("images/8Neighbors.png");

	private final ImageIcon icon;
	
	private TileIcon(final String fileName)
	{
		this.icon = new ImageIcon(getClass().getResource(fileName));
	}
	
	/**
	 * Returns the ImageIcon that this represents.
	 * @return
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}
}
