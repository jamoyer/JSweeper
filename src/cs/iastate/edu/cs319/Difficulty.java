package cs.iastate.edu.cs319;

/**
 * Represents the difficulty the game is being played at. Beginner : 9 rows, 9
 * columns, and 10 mines. Intermediate : 16 rows, 16 columns, and 40 mines.
 * Expert : 16 rows, 30 columns, and 99 mines. Custom can have any positive
 * number of rows, columns, and mines.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public enum Difficulty
{
    Beginner(9, 9, 10), 
    Intermediate(16, 16, 40), 
    Expert(16, 30, 99), 
    Custom;

    private static final int DEFAULT_ROWS = 0;
    private static final int DEFAULT_COLS = 0;
    private static final int DEFAULT_MINES = 0;

    private final int rows;
    private final int cols;
    private final int mines;

    private Difficulty()
    {
        rows = DEFAULT_ROWS;
        cols = DEFAULT_COLS;
        mines = DEFAULT_MINES;
    }

    private Difficulty(final int rows, final int cols, final int mines)
    {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    /**
     * Returns the number of rows.
     * 
     * @return
     */
    public int getRows()
    {
        return rows;
    }

    /**
     * Returns the number of columns.
     * 
     * @return
     */
    public int getCols()
    {
        return cols;
    }

    /**
     * Returns the number of mines.
     * 
     * @return
     */
    public int getMines()
    {
        return mines;
    }
}
