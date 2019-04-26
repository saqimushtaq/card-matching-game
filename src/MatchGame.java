

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author saqi
 */
public class MatchGame {

    public static int ROWS = 4;
    public static int COLS = 13;

    private Deck deck;
    private Grid grid;
    private int numberOfCorrectGuesses;
    private int numberOfGuesses;
    private boolean isEasy;

    public MatchGame(boolean isTesting, boolean isEasy) {
        this.isEasy = isEasy;

        deck = new Deck();

        if (!isTesting) {
            deck.shuffle();
        }

        grid = new Grid(ROWS, COLS);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid.setCard(i, j, deck.nextCard());
            }
        }

    }

    public boolean hasBeenFound(int row, int col) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Invalid Row");
        }
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Invalid Column");
        }
        
        return grid.getCard(row, col).hasBeenFound();
    }

    public String getCardName(int row, int col) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Invalid Row");
        }
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Invalid Column");
        }

        return grid.getCard(row, col).toString();
    }

    public boolean isMatch(int card1Row, int card1Col, int card2Row, int card2Col) {
         if (card1Row < 0 || card1Row >= ROWS || card2Row < 0 || card2Row >= ROWS) {
            throw new IllegalArgumentException("Invalid Row");
        }
        if (card1Col < 0 || card1Col >= COLS || card2Col < 0 || card2Col >= COLS) {
            throw new IllegalArgumentException("Invalid Column");
        }
        Card c1 = grid.getCard(card1Row, card1Col);
        Card c2 = grid.getCard(card2Row, card2Col);
        numberOfGuesses++;
        boolean result = false;
        if(!c2.hasBeenFound()){
            if(isEasy){
                result = c2.hasSameValue(c1);
            }else{
                result = c2.hasSameValueAndColor(c1);
            }   
        }
       if(result){
           numberOfCorrectGuesses++;
           c1.setHasBeenFound(true);
           c2.setHasBeenFound(true);
       }
        return result;
    }

    public int getNumberOfCorrectGuesses() {
        return numberOfCorrectGuesses;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public double getGuessAverage() {
        return (double) getNumberOfCorrectGuesses() / (double) getNumberOfGuesses();
    }
    
    public Deck getDeck(){
        return deck;
    }
    
    public Grid getGrid(){
        return grid;
    }

}
