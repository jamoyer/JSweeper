package cs.iastate.edu.cs319;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
/**
 * This class is the main view which contains the UI elements.
 * @author Matt Clucas, Mike Mead, Jacob Moyer
 *
 */
public class JSweeperUI extends JPanel
{

    private final JMinefieldModel model;
    private JFaceButton faceButton;
    private JFlagCounter flagCounter;

    /**
     * The constructor takes in a model and JMinefield(JTable) to be placed in the UI
     *
     * @param model
     *            the model
     * @param minefield
     *            JTable for the UI
     */
    public JSweeperUI(JMinefieldModel model, JMinefield minefield)
    {
        this.model = model;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(menuPanel());
        add(headerPanel());
        add(bodyPanel());
        add(fieldPanel(minefield));
    }

    /**
     * The Game controller button getter
     *
     * @return game controller button
     */
    public JFaceButton getFace()
    {
        return faceButton;
    }

    /**
     * The flag counter label getter
     *
     * @return flag counter
     */
    public JFlagCounter getFlagCounter()
    {
        return flagCounter;
    }

    /*
     * Creates the gamemenu section of the view
     */
    private JPanel menuPanel()
    {
        final JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        // main menu bar
        final JMenuBar mainMenu = new JMenuBar();

        final JMenu gameMenu = new JMenu("Game");

        // Beginner difficulty button
        final JMenuItem beginnerItem = new JMenuItem("Beginner");
        beginnerItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                model.setDifficulty(Difficulty.Beginner);
            }
        });
        gameMenu.add(beginnerItem);

        // Intermediate difficulty button
        final JMenuItem intermediateItem = new JMenuItem("Intermediate");
        intermediateItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                model.setDifficulty(Difficulty.Intermediate);
            }
        });
        gameMenu.add(intermediateItem);

        // Expert difficulty button
        final JMenuItem expertItem = new JMenuItem("Expert");
        expertItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                model.setDifficulty(Difficulty.Expert);
            }
        });
        gameMenu.add(expertItem);

        // Expert difficulty button
        final JMenuItem customItem = new JMenuItem("Custom");
        customItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                final JPanel custom = new JPanel(new BorderLayout(5, 5));

                final JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
                labels.add(new JLabel("Rows:", SwingConstants.RIGHT));
                labels.add(new JLabel("Columns:", SwingConstants.RIGHT));
                labels.add(new JLabel("Mines:", SwingConstants.RIGHT));
                custom.add(labels, BorderLayout.WEST);

                final JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));

                final JTextField rows = new JTextField();
                controls.add(rows);

                final JTextField cols = new JTextField();
                controls.add(cols);

                final JTextField mines = new JTextField();
                controls.add(mines);

                custom.add(controls, BorderLayout.CENTER);

                // Ask for values with dialog box
                JOptionPane.showMessageDialog(null, custom, "Custom Game", JOptionPane.QUESTION_MESSAGE);

                model.setDifficulty(Difficulty.Custom, Integer.parseInt(rows.getText()), Integer.parseInt(cols.getText()), Integer.parseInt(mines.getText()));
            }
        });
        gameMenu.add(customItem);

        // display high scores button
        final JMenuItem highScores = new JMenuItem("High Scores");
        highScores.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                final ArrayList<JSweeperHighScore> highScores = (ArrayList<JSweeperHighScore>) HighScoreHandler.getHighScores();

                int bs, is, es;
                String bw, iw, ew;
                if (highScores.size() == 0)
                {
                    bs = 999;
                    is = 999;
                    es = 999;
                    bw = "Jenkins";
                    iw = "Peabody";
                    ew = "Jacob";
                }
                else
                {
                    bs = highScores.get(0).getScore();
                    is = highScores.get(1).getScore();
                    es = highScores.get(2).getScore();
                    bw = highScores.get(0).getWinner();
                    iw = highScores.get(1).getWinner();
                    ew = highScores.get(2).getWinner();
                }

                final JPanel highScoresPanel = new JPanel(new BorderLayout(5, 5));

                final JPanel labels = new JPanel(new GridLayout(0, 1, 2, 2));
                labels.add(new JLabel("Beginner: " + bw + " : " + bs, SwingConstants.RIGHT));
                labels.add(new JLabel("Intermediate: " + iw + " : " + is, SwingConstants.RIGHT));
                labels.add(new JLabel("Expert: " + ew + " : " + es, SwingConstants.RIGHT));
                highScoresPanel.add(labels, BorderLayout.WEST);

                JOptionPane.showMessageDialog(null, highScoresPanel, "High Scores", JOptionPane.PLAIN_MESSAGE);
            }
        });
        gameMenu.add(highScores);

        // exit game button
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                System.exit(0);
            }
        });
        gameMenu.add(exitItem);

        mainMenu.add(gameMenu);

        // help submenu with about button
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutItem = new JMenuItem("About JSweeper...");
        aboutItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                JOptionPane.showMessageDialog(new JFrame(), "Created by: Jacob Moyer, Matt Clucas, Mike Mead.\n\nComing soon: Shark mode and Batman mode!");
            }
        });

        helpMenu.add(aboutItem);

        mainMenu.add(helpMenu);

        menuPanel.add(mainMenu);
        return menuPanel;
    }

    /*
     * Heading panel, contains the title of the game
     */
    private JPanel headerPanel()
    {
        final JPanel headerPanel = new JPanel();
        final JLabel heading = new JLabel("Minesweeper");
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        headerPanel.add(heading);
        return headerPanel;
    }

    /*
     * Body panel, contains the three status elements
     */
    private JPanel bodyPanel()
    {
        final JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));

        // //////////////////////////////
        // // JFlagCounter Panel
        final JPanel flagCounterPanel = new JPanel(new FlowLayout());
        flagCounter = new JFlagCounter();
        flagCounterPanel.add(flagCounter);
        bodyPanel.add(flagCounterPanel);

        // //////////////////////////////
        // // JFaceButton Panel
        final JPanel faceButtonPanel = new JPanel(new FlowLayout());
        faceButton = new JFaceButton();
        faceButtonPanel.add(faceButton);
        bodyPanel.add(faceButtonPanel);

        // //////////////////////////////
        // // Timer Panel
        final JPanel timerPanel = new JPanel(new FlowLayout());
        final JTimerLabel timerLabel = new JTimerLabel();
        timerPanel.add(timerLabel);
        final Thread timerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    timerLabel.updateTextNumeric(model.getTime());
                    try
                    {
                        Thread.sleep(500);
                    }
                    catch (final InterruptedException e)
                    {
                        // Should never happen
                        System.out.println("Timer update thread interrupted. Critical Error!");
                        System.exit(-1);
                    }
                }
            }
        });
        timerThread.start();
        bodyPanel.add(timerPanel);

        return bodyPanel;
    }

    // panel to contain the JMinefield
    private JPanel fieldPanel(JMinefield field)
    {
        final JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        fieldPanel.add(field);

        return fieldPanel;
    }
}
