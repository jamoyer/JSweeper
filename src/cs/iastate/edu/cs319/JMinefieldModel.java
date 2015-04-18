package cs.iastate.edu.cs319;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Represents a Minefield.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public class JMinefieldModel extends AbstractTableModel
{

    private JTile[][] grid;
    private int mines;
    private int flagCount;
    private int numVisited;
    private int time;
    private Difficulty difficulty;
    private ArrayList<GameSettingsListener> listeners = new ArrayList<GameSettingsListener>();

    /**
     * Overloaded constructor. Takes a difficulty and creates a JMinefieldModel
     * based off of its built in parameters.
     * 
     * @param difficulty
     */
    public JMinefieldModel(Difficulty difficulty)
    {
        this(difficulty, difficulty.getRows(), difficulty.getCols(), difficulty
                .getMines());
    }

    /**
     * Default constructor. Takes in difficulty, rows, columns, and mines and
     * creates a new JMinefieldModel.
     * 
     * @param difficulty
     * @param rows
     * @param cols
     * @param mines
     */
    public JMinefieldModel(Difficulty difficulty, int rows, int cols, int mines)
    {
        this.flagCount = 0;
        this.numVisited = 0;
        this.time = 0;
        grid = new JTile[rows][cols];
        this.difficulty = difficulty;
        this.mines = mines;
    }

    /**
     * Overloaded method. Takes in a difficulty and reinitializes the minefield
     * with its built in parameters.
     * 
     * @param difficulty
     */
    public void setDifficulty(Difficulty difficulty)
    {
        setDifficulty(difficulty, difficulty.getRows(), difficulty.getCols(),
                difficulty.getMines());
    }

    /**
     * Reinitializes the minefield given a new difficulty, rows, columns, and
     * mines.
     * 
     * @param difficulty
     * @param rows
     * @param cols
     * @param mines
     */
    public void setDifficulty(Difficulty difficulty, int rows, int cols,
            int mines)
    {
        grid = new JTile[rows][cols];
        this.difficulty = difficulty;
        this.mines = mines;
        for (GameSettingsListener listener : listeners)
            listener.difficultyChanged();
    }

    /**
     * Returns the difficulty for this minefield.
     * 
     * @return
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * Adds a listener for when game settings change in the model.
     * 
     * @param newListener
     */
    public void addListener(GameSettingsListener newListener)
    {
        listeners.add(newListener);
    }

    /**
     * Returns the time this game has been running.
     * 
     * @return
     */
    public int getTime()
    {
        return time;
    }

    /**
     * Sets the time this game has been running.
     * 
     * @param time
     */
    public void setTime(int time)
    {
        this.time = time;
    }

    @Override
    public int getColumnCount()
    {
        return grid[0].length;
    }

    @Override
    public int getRowCount()
    {
        return grid.length;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        return grid[row][col];
    }

    /**
     * Sets the JTile at this position.
     * 
     * @param row
     * @param col
     * @param val
     */
    public void setValueAt(int row, int col, JTile val)
    {
        grid[row][col] = val;
    }

    /**
     * Returns the total number of flags that have been placed.
     * 
     * @return
     */
    public int getFlagCount()
    {
        return flagCount;
    }

    /**
     * Returns the number of tiles that have been visited.
     * 
     * @return
     */
    public int getNumVisited()
    {
        return numVisited;
    }

    /**
     * Sets the number of flags that have been placed.
     * 
     * @param flagCount
     */
    public void setFlagCount(int flagCount)
    {
        this.flagCount = flagCount;
    }

    /**
     * Sets the number of tiles that have been visited.
     * 
     * @param numVisited
     */
    public void setNumVisited(int numVisited)
    {
        this.numVisited = numVisited;
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

    /**
     * Sets the number of mines.
     * 
     * @param mines
     */
    public void setMines(int mines)
    {
        this.mines = mines;
    }
}
