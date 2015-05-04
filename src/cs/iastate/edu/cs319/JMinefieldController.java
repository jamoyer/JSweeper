package cs.iastate.edu.cs319;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

/**
 * This is the controller object for the JMinefield (view) and JMinefieldModel (model).
 *
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
public class JMinefieldController
{

    private final JMinefieldModel model;
    private final JMinefield view;

    private boolean gameOver;
    private boolean leftClickDown = false;
    private boolean rightClickDown = false;
    private final List<JFaceButtonListener> faceButtonListeners = new ArrayList<JFaceButtonListener>();
    private final List<FlagCounterListener> flagCounterListeners = new ArrayList<FlagCounterListener>();
    private final Timer timer;

    /**
     * Overloaded. Uses Beginner Difficulty as the difficulty.
     */
    public JMinefieldController()
    {
        this(Difficulty.Beginner);
    }

    /**
     * Overloaded. Takes in a difficulty and uses all of it's built in parameters for constructing
     * the model.
     *
     * @param difficulty
     */
    public JMinefieldController(Difficulty difficulty)
    {
        this(difficulty, difficulty.getRows(), difficulty.getCols(), difficulty.getMines());
    }

    /**
     * Default constructor. Takes in a difficulty, rows, columns, and mines for constructing the
     * model.
     *
     * @param difficulty
     * @param rows
     * @param cols
     * @param mines
     */
    public JMinefieldController(Difficulty difficulty, int rows, int cols, int mines)
    {
        // build the model then the view then do initialization that is needed.
        model = new JMinefieldModel(difficulty, rows, cols, mines);
        view = new JMinefield(model);
        view.addMouseListener(new MinefieldMouseAdapter());
        timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (model.getTime() < 999)
                {
                    model.setTime(model.getTime() + 1);
                }
            }
        });

        initializeGame();
    }

    /**
     * Adds a JFaceButtonListener to modify the face button.
     *
     * @param listener
     */
    public void addFaceButtonListener(JFaceButtonListener listener)
    {
        faceButtonListeners.add(listener);
    }

    /**
     * Adds a FlagCounterListener to modify the flag counter.
     *
     * @param listener
     */
    public void addFlagCounterListener(FlagCounterListener listener)
    {
        flagCounterListeners.add(listener);
    }

    /**
     * Returns the JMinefieldModel this JMinefieldController manages.
     *
     * @return
     */
    public JMinefieldModel getModel()
    {
        return model;
    }

    /**
     * Returns the JMinefield this JMinefieldController manages.
     *
     * @return
     */
    public JMinefield getView()
    {
        return view;
    }

    /**
     * Initializes the game, reseting counters, reseting tiles, placing mines, and calculating mine
     * neighbors.
     */
    public void initializeGame()
    {
        if (timer.isRunning())
        {
            timer.stop();
        }

        gameOver = false;
        model.setTime(0);
        model.setFlagCount(0);
        model.setNumVisited(0);
        for (final FlagCounterListener fcl : flagCounterListeners)
        {
            fcl.flagCountUpdated(model.getMines() - model.getFlagCount());
        }

        initializeTiles();
        placeMines();
        findNeighbors();
    }

    /*
     * Helper function that resets all the tiles to be new JTiles.
     */
    private void initializeTiles()
    {
        for (int i = 0; i < model.getRowCount(); i++)
        {
            for (int j = 0; j < model.getColumnCount(); j++)
            {
                model.setValueAt(i, j, new JTile());
            }
        }
    }

    /*
     * Helper function that places all the mines in the field randomly.
     */
    private void placeMines()
    {
        // get the total number of mines to be placed and create a Random
        // object.
        int count = model.getMines();
        final Random rand = new Random();

        // while there are more mines to be placed, try to place a mine at a
        // random row and col
        while (count > 0)
        {
            final int row = rand.nextInt(model.getRowCount());
            final int col = rand.nextInt(model.getColumnCount());
            final JTile tile = (JTile) model.getValueAt(row, col);

            // if the tile isn't already a bomb, make it a bomb and decrement the
            // number of bombs left to be placed.
            if (!tile.isBomb())
            {
                count--;
                tile.setBomb(true);
            }
        }
    }

    /*
     * Helper function that finds the number of bombs (neighbors) around all the JTiles.
     */
    private void findNeighbors()
    {
        // loop through all the JTiles in the grid
        for (int i = 0; i < model.getRowCount(); i++)
        {
            for (int j = 0; j < model.getColumnCount(); j++)
            {

                // get the tile
                final JTile tile = (JTile) model.getValueAt(i, j);

                // if the tile is a bomb, it shouldn't have a valid neighbor
                // value and skip this tile.
                if (tile.isBomb())
                {
                    tile.setNeighbors(-1);
                    continue;
                }

                // look at all the tiles adjacent to this one, count how many
                // are bombs.

                // first get the bounds of the adjacent tiles, check for edge
                // cases.
                // edge cases such as, if the left tile would be at col == -1,
                // set col =0 ...
                final int leftBound = j - 1 < 0 ? 0 : j - 1;
                final int rightBound = j + 1 > model.getColumnCount() - 1 ? j : j + 1;
                final int upperBound = i - 1 < 0 ? 0 : i - 1;
                final int lowerBound = i + 1 > model.getRowCount() - 1 ? i : i + 1;

                // next we initialize the number of neighbors to 0
                int neighbors = 0;

                // loop through the neighbors and check for bombs, incrementing
                // neighbors for each bomb
                for (int m = upperBound; m <= lowerBound; m++)
                {
                    for (int n = leftBound; n <= rightBound; n++)
                    {
                        if (((JTile) model.getValueAt(m, n)).isBomb())
                        {
                            neighbors++;
                        }
                    }
                }

                // finally set the tile's neighbor count.
                tile.setNeighbors(neighbors);
            }
        }
    }

    /*
     * Helper function that "visits" a tile.
     */
    private void visitTile(int row, int col) throws GameOverException
    {
        // get the tile at this position
        final JTile selectedTile = (JTile) model.getValueAt(row, col);

        // only visit the tile if it hasn't already been visited.
        if (!selectedTile.isVisited() && selectedTile.getStatus() != Status.flagged)
        {

            // set visited to true, update the total number of visited and fire
            // a table updated event, which updates the view.
            selectedTile.setVisited(true);
            model.setNumVisited(model.getNumVisited() + 1);
            model.fireTableCellUpdated(row, col);

            // check if this tile is a bomb, if it is call loseGame, the game is
            // lost.
            if (selectedTile.isBomb())
            {
                loseGame();
            }

            // check if the game has been won, call winGame, the game is won.
            if (checkForWin())
            {
                winGame();
            }

            // if this tile has 0 neighbors, expand so it shows all nearby tiles
            // that have 0 neighbors.
            if (selectedTile.getNeighbors() == 0)
            {
                visitNeighbors(row, col);
            }
        }
    }

    /*
     * Helper function that recursively "visits" all nearby tiles.
     */
    private void visitNeighbors(int row, int col) throws GameOverException
    {
        final int i = row;
        final int j = col;

        // first get the bounds of the adjacent tiles, check for edge
        // cases.
        // edge cases such as, if the left tile would be at col == -1,
        // set col =0 ...
        final int leftBound = j - 1 < 0 ? 0 : j - 1;
        final int rightBound = j + 1 > model.getColumnCount() - 1 ? j : j + 1;
        final int upperBound = i - 1 < 0 ? 0 : i - 1;
        final int lowerBound = i + 1 > model.getRowCount() - 1 ? i : i + 1;

        // loop through all nearby tiles
        for (int m = upperBound; m <= lowerBound; m++)
        {
            for (int n = leftBound; n <= rightBound; n++)
            {

                // skip the original tile
                if (m == i && n == j)
                {
                    continue;
                }

                // visit the tile at this location.
                visitTile(m, n);
            }
        }
    }

    /*
     * Helper function that checks the conditions needed to have won the game. Returns true if the
     * game is won.
     */
    private boolean checkForWin()
    {

        // we have won if every tile has been visited except for all the mines.
        return model.getRowCount() * model.getColumnCount() - model.getNumVisited() == model.getMines();
    }

    /*
     * Helper function that does all the work that needs to be done when the game is lost.
     */
    private void loseGame() throws GameOverException
    {

        // set the gameOver to true since the game is over.
        gameOver = true;

        // stop the game timer
        timer.stop();

        // show the position of all the bombs
        showBombs();

        // set the JFaceButton to Deadface
        for (final JFaceButtonListener btn : faceButtonListeners)
        {
            btn.gameEnded();
        }

        // throw the GameOverException to halt the game process.
        throw new GameOverException("Failure");
    }

    /*
     * Helper function that does all the work that needs to be done when the game is won.
     */
    private void winGame() throws GameOverException
    {

        // set the gameOver to true since the game is over.
        gameOver = true;

        // stop the game timer
        timer.stop();

        // put flag icons on all the bombs
        flagBombs();

        // set the facebutton to the sunglasses face
        for (final JFaceButtonListener btn : faceButtonListeners)
        {
            btn.gameWon();
        }

        // update the number of flags on the screen to the flag counter
        for (final FlagCounterListener fcl : flagCounterListeners)
        {
            fcl.flagCountUpdated(model.getMines() - model.getFlagCount());
        }

        // write a high score if this isn't a custom game
        if (model.getDifficulty() != Difficulty.Custom)
        {
            HighScoreHandler.writeNewHighScore(model.getDifficulty(), model.getTime());
        }

        // throw the GameOverException to halt the game process.
        throw new GameOverException("Winner!");
    }

    /*
     * This private mouse adapter handles what happens when you click on the JMinefield.
     */
    private class MinefieldMouseAdapter extends MouseAdapter
    {

        // Handles what happens when the mouse is pressed on the JMinefield
        @Override
        public void mousePressed(MouseEvent e)
        {

            // Don't do anything if the game is over.
            if (gameOver)
            {
                return;
            }

            // get which button was pressed
            switch (e.getButton())
            {
                case 1:
                    // left click
                    leftClickDown = true;
                    for (final JFaceButtonListener btn : faceButtonListeners)
                    {
                        btn.mouseDown();
                    }
                    break;
                case 3:
                    // right click
                    rightClickDown = true;
                    break;
                default:
                    break;
            }
        }

        // Handles what happens when the mouse is released on the JMinefield.
        @Override
        public void mouseReleased(MouseEvent e)
        {
            // Don't do anything if the game is over.
            if (gameOver)
            {
                return;
            }

            // get the row and column where the mouse was released
            final int row = view.rowAtPoint(e.getPoint());
            final int col = view.columnAtPoint(e.getPoint());

            // Simultaneous click
            if (leftClickDown && rightClickDown)
            {
                leftClickDown = false;
                rightClickDown = false;

                // call the twoClicks helper function
                try
                {
                    twoClicks(row, col);
                }
                catch (final GameOverException e1)
                {
                    return;
                }

                // reset the JFaceButton to the default smiley face
                for (final JFaceButtonListener btn : faceButtonListeners)
                {
                    btn.reset();
                }
                return;
            }

            // get which button was pressed
            switch (e.getButton())
            {
                case 1:
                    // left click
                    leftClickDown = false;

                    // call the left click helper function
                    leftClick(row, col);
                    break;
                case 3:
                    // right click
                    rightClickDown = false;

                    // call the right click helper function
                    rightClick(row, col);
                    break;
                default:
                    break;
            }

            // update the view
            model.fireTableCellUpdated(row, col);
        }

    }

    /*
     * Helper function that handles left click release events on the JMinefield view
     */
    private void leftClick(int row, int col)
    {
        // Set the default face on the JFaceButton
        for (final JFaceButtonListener btn : faceButtonListeners)
        {
            btn.reset();
        }

        // start the timer if it isnt already running
        // should only happen on the first click of the game.
        if (!timer.isRunning())
        {
            timer.start();
        }

        // visit the tile
        try
        {
            visitTile(row, col);
        }
        catch (final GameOverException e1)
        {
            return;
        }
    }

    /*
     * Helper function that handles right click release events on the JMinefield view
     */
    private void rightClick(int row, int col)
    {

        // get the tile
        final JTile tile = (JTile) model.getValueAt(row, col);

        // dont do anything if this tile has already been visited
        if (tile.isVisited())
        {
            return;
        }

        // toggle the status of the tile from none to flagged to questionable to
        // none
        // updates the flag count as well.
        switch (tile.getStatus())
        {
            case flagged:
                tile.setStatus(Status.questionable);
                model.setFlagCount(model.getFlagCount() - 1);
                break;
            case questionable:
                tile.setStatus(Status.none);
                break;
            default:
                tile.setStatus(Status.flagged);
                model.setFlagCount(model.getFlagCount() + 1);
                break;
        }

        // update the flag count label
        for (final FlagCounterListener fcl : flagCounterListeners)
        {
            fcl.flagCountUpdated(model.getMines() - model.getFlagCount());
        }

    }

    /*
     * Helper function that handles both mouse buttons being held then released.
     */
    private void twoClicks(int row, int col) throws GameOverException
    {

        // get the origin tile, skip if it has not been visited.
        final JTile originTile = (JTile) model.getValueAt(row, col);
        if (!originTile.isVisited())
        {
            return;
        }

        final int i = row;
        final int j = col;

        // first get the bounds of the adjacent tiles, check for edge
        // cases.
        // edge cases such as, if the left tile would be at col == -1,
        // set col =0 ...
        final int leftBound = j - 1 < 0 ? 0 : j - 1;
        final int rightBound = j + 1 > view.getColumnCount() - 1 ? j : j + 1;
        final int upperBound = i - 1 < 0 ? 0 : i - 1;
        final int lowerBound = i + 1 > view.getRowCount() - 1 ? i : i + 1;

        // loops around all origin tile and counts all the adjacent tiles that
        // are flagged
        int numFlags = 0;
        for (int m = upperBound; m <= lowerBound; m++)
        {
            for (int n = leftBound; n <= rightBound; n++)
            {

                // skip the origin tile
                if (m == i && n == j)
                {
                    continue;
                }

                // check if this tile is flagged
                final JTile tile = (JTile) model.getValueAt(m, n);

                if (tile.getStatus() == Status.flagged)
                {
                    numFlags++;
                }
            }
        }

        // if we have the correct number of flags, visit all the adjacent tiles
        if (numFlags == originTile.getNeighbors())
        {
            visitNeighbors(row, col);
        }
    }

    /*
     * Helper function that loops through the grid and puts bomb icons on all the bombs. Should only
     * be called on loseGame()
     */
    private void showBombs()
    {

        // loop through the grid
        for (int i = 0; i < model.getRowCount(); i++)
        {
            for (int j = 0; j < model.getColumnCount(); j++)
            {

                // get an individual tile at this spot
                final JTile tile = (JTile) model.getValueAt(i, j);

                // if something that isn't a bomb was flagged, set the
                // misflagged icon
                if (tile.getStatus() == Status.flagged && !tile.isBomb())
                {
                    tile.setIcon(TileIcon.Misflagged.getIcon());

                    // if something isn't flagged and is a bomb, set the bomb
                    // icon
                }
                else if (tile.getStatus() != Status.flagged && tile.isBomb())
                {

                    // if this happens to be the bomb that we visited, set the
                    // tripped bomb icon
                    if (tile.isVisited())
                    {
                        tile.setIcon(TileIcon.TrippedMine.getIcon());
                    }
                    else
                    {
                        tile.setIcon(TileIcon.UntrippedMine.getIcon());
                    }
                }

                // update the view for this location
                model.fireTableCellUpdated(i, j);
            }
        }
    }

    /*
     * Helper function that loops through the grid and puts flag icons on all the bombs. Should only
     * be called on winGame()
     */
    private void flagBombs()
    {

        // loop through the grid
        for (int i = 0; i < model.getRowCount(); i++)
        {
            for (int j = 0; j < model.getColumnCount(); j++)
            {

                // get an individual tile at this spot
                final JTile tile = (JTile) model.getValueAt(i, j);

                // if it is a bomb and it has not been flagged, flag it and
                // update the flag counter
                if (tile.isBomb() && tile.getStatus() != Status.flagged)
                {
                    model.setFlagCount(model.getFlagCount() + 1);
                    tile.setIcon(TileIcon.Flagged.getIcon());
                }

                // update the view for this location
                model.fireTableCellUpdated(i, j);
            }
        }
    }
}
