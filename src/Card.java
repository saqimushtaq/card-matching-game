import java.awt.Color;
import java.util.Objects;

public class Card {

    public static final char CLUBS = 'C';
    public static final char HEARTS = 'H';
    public static final char SPADES = 'S';
    public static final char DIAMONDS = 'D';

    public static final int LOWEST_VALUE = 2;
    public static final int HIGHEST_VALUE = 14;

    private char suit;
    private int value;
    private Color color;
    private boolean hasBeenFound;

    public Card(char suit, int value) {
        switch (suit) {
            case 'C':
                this.suit = Card.CLUBS;
                this.color = Color.BLACK;
                break;
            case 'D':
                this.suit = Card.DIAMONDS;
                this.color = Color.RED;
                break;
            case 'S':
                this.suit = Card.SPADES;
                this.color = Color.BLACK;
                break;
            case 'H':
                this.suit = Card.HEARTS;
                this.color = Color.RED;
                break;
            default:
                throw new IllegalArgumentException("invalid suit");
        }
        //set value
        if (value < Card.LOWEST_VALUE || value > Card.HIGHEST_VALUE) {
            throw new IllegalArgumentException("Invalid value");
        }
        
        this.value = value;
        
        this.hasBeenFound = false;
    }

    public char getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasBeenFound() {
        return hasBeenFound;
    }

    public void setHasBeenFound(boolean found) {
        this.hasBeenFound = found;
    }
    
    public boolean hasSameValue(Card other){
        return other.value == this.value;
    }
    
    public boolean hasSameValueAndColor(Card other){
        return other.value == this.value && other.color.equals(this.color);
    }
    
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.suit != other.suit) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return this.hasBeenFound == other.hasBeenFound;
    }

    public String toString() {
        return  this.value+ "" +  this.suit;
    }
    
    
   

}
