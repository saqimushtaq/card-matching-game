import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Provides the view and controller for the MatchGame
 */
public class MatchGameGUI extends JFrame implements ActionListener {

    /**
     * Width of GUI Window
     */
    public static final int WIDTH = 950;
    /**
     * Height of GUI Window
     */
    public static final int HEIGHT = 560;
    /**
     * Path to (directory for) card images
     */
    public static final String PATH = MatchGameGUI.class.getResource("/cards/").getPath();
    /**
     * Extension of card images
     */
    public static final String EXTENSION = ".png";
    /**
     * Time (milliseconds) that cards that are not a match are displayed
     */
    public static final int DELAY = 1500;
    /**
     * Large bold font used for game instructions
     */
    public static final Font LARGE_BOLD = new Font("Courier", 1, 18);
    /**
     * Normal font used for game statistics
     */
    public static final Font NORMAL = new Font("Courier", 0, 16);

    /**
     * Instructions label
     */
    private JLabel instructionsLabel;
    /**
     * Correct guesses label
     */
    private JLabel correctGuessesLabel;
    /**
     * Total guesses label
     */
    private JLabel totalGuessesLabel;
    /**
     * Average label
     */
    private JLabel averageLabel;
    /**
     * Statistics panel
     */
    private JPanel statisticsPanel;
    /**
     * Information (instructions and statistics) panel
     */
    private JPanel informationPanel;

    /**
     * Card buttons
     */
    private JButton[][] cardButtons;
    /**
     * Panel to display grid of cards
     */
    private JPanel gridPanel;
    /**
     * Easy game button
     */
    private JButton easyGameButton;
    /**
     * Hard game button
     */
    private JButton hardGameButton;
    /**
     * Quit button
     */
    private JButton quitButton;
    /**
     * Panel to hold game buttons
     */
    private JPanel buttonPanel;

    /**
     * MatchGame (model) instance
     */
    private MatchGame matchGame;
    /**
     * Row in grid of first card of pair that is turned over (clicked on)
     */
    private int card1Row;
    /**
     * Column in grid of first card of  that is turned over (clicked on)
     */
    private int card1Col;
    /**
     * Row in grid of second card of pair that is turned over (clicked on)
     */
    private int card2Row;
    /**
     * Column in grid of second card of pair that is turned over (clicked on)
     */
    private int card2Col;
    /**
     * First card has been selected
     */
    private boolean firstCardSelected;
    /**
     * Timer to delay turning over cards that are not a match
     */
    private Timer timer;
    /**
     * If game is being run in testing mode
     */
    private boolean testing;

    /**
     * Creates GUI for playing MatchGame
     *
     * @param testing true if in testing mode, false otherwise
     */
    public MatchGameGUI(boolean testing) {

        super("Match Game");
        this.testing = testing;
        setSize(WIDTH, HEIGHT);
        Container c = getContentPane();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Instructions and statistics
        informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        instructionsLabel = new JLabel
                ("              Welcome to the Match Game -- choose an Easy or Hard Game!");
        instructionsLabel.setFont(LARGE_BOLD);
        informationPanel.add(instructionsLabel, BorderLayout.NORTH);
        statisticsPanel = new JPanel();
        correctGuessesLabel = new JLabel(" Correct Guesses: 0");

        correctGuessesLabel.setFont(NORMAL);
        totalGuessesLabel = new JLabel(" Total Guesses: 0");

        totalGuessesLabel.setFont(NORMAL);
        averageLabel = new JLabel(" Average: ");

        averageLabel.setFont(NORMAL);
        statisticsPanel.add(correctGuessesLabel);
        statisticsPanel.add(totalGuessesLabel);
        statisticsPanel.add(averageLabel);
        informationPanel.add(statisticsPanel, BorderLayout.CENTER);
        c.add(informationPanel, BorderLayout.NORTH);

        //Grid of cards
        gridPanel = new JPanel(new GridLayout(MatchGame.ROWS, MatchGame.COLS));
        cardButtons = new JButton[MatchGame.ROWS][MatchGame.COLS];
        for (int i = 0; i < cardButtons.length; i++) {
            for (int j = 0; j < cardButtons[i].length; j++) {
                JButton button = new JButton();
                button.addActionListener(this);
                button.setIcon(new ImageIcon(PATH + "blank" + EXTENSION));
                button.setEnabled(false);
                cardButtons[i][j] = button;
                gridPanel.add(button);
            }
        }
        c.add(gridPanel, BorderLayout.CENTER);

        //Game play buttons
        buttonPanel = new JPanel();
        easyGameButton = new JButton("EASY GAME");
        easyGameButton.addActionListener(this);
        hardGameButton = new JButton("HARD GAME");
        hardGameButton.addActionListener(this);
        quitButton = new JButton("QUIT");
        quitButton.addActionListener(this);
        buttonPanel.add(easyGameButton);
        buttonPanel.add(hardGameButton);
        buttonPanel.add(quitButton);
        c.add(buttonPanel, BorderLayout.SOUTH);

        //Timer to delay turning cards back over after incorrect guess
        timer = new Timer(DELAY, this);
        timer.addActionListener(this);

        setVisible(true);
    }

    /**
     * Handles game play based on events generated by buttons (card, easy game, hard game, quit)
     * and timer
     *
     * @param e event that controls game play.
     */
    public void actionPerformed(ActionEvent e) {

        //Quit Game
        if (e.getSource() == quitButton) {
            System.exit(1);
        }

        //Start Easy Game
        else if (!timer.isRunning() && e.getSource() == easyGameButton) {
            matchGame = new MatchGame(testing, true);
            for (int i = 0; i < cardButtons.length; i++) {
                for (int j = 0; j < cardButtons[i].length; j++) {
//                    cardButtons[i][j].setIcon (new ImageIcon(PATH + "blank" + EXTENSION));
                    cardButtons[i][j].setIcon(new ImageIcon( new ImageIcon(PATH +
                            "yellow_back" + EXTENSION).getImage().getScaledInstance(80,100,
                            Image.SCALE_DEFAULT)));
                    cardButtons[i][j].setEnabled(true);
                }
            }
            instructionsLabel.setText
                    ("                      Easy Game -- match cards with same value!");
            correctGuessesLabel.setText(" Correct Guesses: 0");
            totalGuessesLabel.setText(" Total Guesses: 0");
            averageLabel.setText(" Average: ");
            firstCardSelected = false;
        }

        //Start Hard Game
        else if (!timer.isRunning() && e.getSource() == hardGameButton) {
            matchGame = new MatchGame(testing, false);
            for (int i = 0; i < cardButtons.length; i++) {
                for (int j = 0; j < cardButtons[i].length; j++) {
                    cardButtons[i][j].setIcon(new ImageIcon( new ImageIcon(PATH +
                            "yellow_back" + EXTENSION).getImage().getScaledInstance(80,100,
                            Image.SCALE_DEFAULT)));
                    cardButtons[i][j].setEnabled(true);
                }
            }
            instructionsLabel.setText
                    ("                  Hard Game -- match cards with same value and color!");
            correctGuessesLabel.setText(" Correct Guesses: 0");
            totalGuessesLabel.setText(" Total Guesses: 0");
            averageLabel.setText(" Average: ");
            firstCardSelected = false;
        }

        //Turn cards back over after incorrect guess    
        else if (timer == e.getSource()) {
            timer.stop();
            firstCardSelected = false;
            cardButtons[card1Row][card1Col].setIcon(new ImageIcon( new ImageIcon(PATH +
                    "yellow_back" + EXTENSION).getImage().getScaledInstance(80,100,
                    Image.SCALE_DEFAULT)));
            cardButtons[card2Row][card2Col].setIcon(new ImageIcon( new ImageIcon(PATH +
                    "yellow_back" + EXTENSION).getImage().getScaledInstance(80,100,
                    Image.SCALE_DEFAULT)));
        }

        //Turn over first card    
        else if (!timer.isRunning() && !firstCardSelected) {
            boolean found = false;
            for (int i = 0; !found && i < cardButtons.length; i++) {
                for (int j = 0; !found && j < cardButtons[i].length; j++) {
                    if (cardButtons[i][j] == e.getSource()) {
                        found = true;
                        if (!matchGame.hasBeenFound(i, j)) {
                            firstCardSelected = true;

                            card1Row = i;
                            card1Col = j;
//                            cardButtons[i][j].setIcon(new ImageIcon(PATH +
//                                    matchGame.getCardName(i, j) + EXTENSION));
                            cardButtons[i][j].setIcon(new ImageIcon( new ImageIcon(PATH +
                                    matchGame.getCardName(i, j) + EXTENSION).getImage().getScaledInstance(80, 100, Image.SCALE_DEFAULT)));


                        }
                    }
                }
            }
        }

        //Turn over second card
        else if (!timer.isRunning() && firstCardSelected) {
            boolean found = false;
            for (int i = 0; !found && i < cardButtons.length; i++) {
                for (int j = 0; !found && j < cardButtons[i].length; j++) {
                    if (cardButtons[i][j] == e.getSource()) {
                        found = true;
                        if (!matchGame.hasBeenFound(i, j) && !(i == card1Row && j == card1Col)) {
                            card2Row = i;
                            card2Col = j;
//                            cardButtons[i][j].setIcon(new ImageIcon(PATH +
//                                    matchGame.getCardName(i, j) + EXTENSION));

                            cardButtons[i][j].setIcon(new ImageIcon( new ImageIcon(PATH +
                                    matchGame.getCardName(i, j) + EXTENSION).getImage().getScaledInstance(80, 100, Image.SCALE_DEFAULT)));

                            //Incorrect guess - leave cards turned over for short time
                            if (!matchGame.isMatch(card1Row, card1Col, card2Row, card2Col))
                                timer.start();
                                //Correct guess - leave cards turned over permanently
                            else {
                                firstCardSelected = false;
                            }
                            //Update statistics
                            correctGuessesLabel.setText(" Correct Guesses: " +
                                    matchGame.getNumberOfCorrectGuesses());
                            totalGuessesLabel.setText(" Total Guesses: " +
                                    matchGame.getNumberOfGuesses());
                            averageLabel.setText(" Average: " +
                                    String.format("%.3g%n",
                                            matchGame.getGuessAverage()));

                        }
                    }
                }
            }
        }
    }

    /**
     * Starts MatchGameGUI in normal mode if no command line argument is provided,
     * in testing mode if single command line argument is -t. Otherwise, provides a
     * usage message
     *
     * @param args command line argument, if args[0] is "-t", game is played in testing mode
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            new MatchGameGUI(false); //Normal game mode
        } else if (args.length == 1 && args[0].equals("-t")) {
            new MatchGameGUI(true);  //Testing mode
        } else {
            System.out.println("Usage: java MatchGameGUI [-t]");
        }
    }

}
        
