package cs.iastate.edu.cs319;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * This class runs the game and contains the main function, it also acts as an
 * administrative controller for the entire game. Smaller tasks like updating
 * and maintaining the JMinesweeperModel and JMinefield are delegated to the
 * JMinefieldController.
 * 
 * @author Matt Clucas, Mike Mead, and Jacob Moyer
 *
 */
@SuppressWarnings("serial")
public class JSweeper extends JFrame implements GameSettingsListener
{
    private JMinefieldController minefieldController;
    private JMinefieldModel model;
    private JSweeperUI view;

    /**
     * Spawns a new thread with the JSweeper and then ends.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    JSweeper frame = new JSweeper("JSweeper");
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Overloaded constructor. Takes a game title and then builds a new frame
     * based on beginner difficulty.
     * 
     * @param title
     */
    public JSweeper(String title)
    {
        this(title, Difficulty.Beginner);
    }

    /**
     * Overloaded constructor. Takes in a title and a difficulty and builds the
     * game based on the difficulty's built in parameters. Exits if difficulty
     * is custom.
     * 
     * @param title
     * @param difficulty
     */
    public JSweeper(String title, Difficulty difficulty)
    {
        this(title, difficulty, difficulty.getRows(), difficulty.getCols(),
                difficulty.getMines());
        if (difficulty == Difficulty.Custom)
        {
            System.exit(-1);
        }
    }

    /**
     * Default constructor. Takes in a title, difficulty, rows, columns, and
     * mines. It first builds the JMinefieldController, which then builds the
     * model and JMinefield view, then this builds the rest of the view and
     * displays the frame.
     * 
     * @param title
     * @param difficulty
     * @param rows
     * @param cols
     * @param mines
     */
    public JSweeper(String title, Difficulty difficulty, int rows, int cols,
            int mines)
    {
        super(title);
        // set up the frame
        setBounds(0, 0, 0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // build the controller, model, and UI, then display the frame.
        buildController(difficulty);
        buildModel();
        buildUI();
        setContentPane(view);
        pack();
    }

    /*
     * Helper function that builds the JMinefieldController
     */
    private void buildController(Difficulty difficulty)
    {
        minefieldController = new JMinefieldController(difficulty);
    }

    /*
     * Helper function that builds the JMinefieldController
     */
    private void buildController(Difficulty difficulty, int rows, int cols,
            int mines)
    {
        minefieldController = new JMinefieldController(difficulty, rows, cols,
                mines);
    }

    /*
     * Helper function that gets the model from the controller.
     */
    private void buildModel()
    {
        model = minefieldController.getModel();
        model.addListener(this);
    }

    /*
     * Helper function that builds the UI.
     */
    private void buildUI()
    {
        // create the overall UI
        view = new JSweeperUI(model, minefieldController.getView());

        // create the face button and add it to the UI
        JFaceButton faceButton = view.getFace();
        minefieldController.addFaceButtonListener(faceButton);

        // make a listener for when the face button is pressed, reseting the
        // game
        faceButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                minefieldController.initializeGame();
                view.getFace().reset();
                minefieldController.getModel().fireTableDataChanged();
            }
        });

        // create the flag counter label
        JFlagCounter flagCounter = view.getFlagCounter();
        flagCounter.updateTextNumeric(model.getMines());
        minefieldController.addFlagCounterListener(flagCounter);
    }

    /*
     * Function that fires when the model's difficulty has changed. This sets up
     * a new game with the new difficulty.
     */
    @Override
    public void difficultyChanged()
    {
        // store the old difficulty
        Difficulty diff = model.getDifficulty();

        // build a new controller
        if (diff == Difficulty.Custom)
        {
            // if its custom we must also store the rows, columns, and mines.
            int rows = model.getRowCount();
            int cols = model.getColumnCount();
            int mines = model.getMines();
            buildController(diff, rows, cols, mines);
        }
        else
        {
            buildController(diff);
        }
        // build new model and ui and redraw the new ui
        buildModel();
        buildUI();
        setContentPane(view);
        pack();
    }
}
