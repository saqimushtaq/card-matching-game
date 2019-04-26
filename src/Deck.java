import java.util.Random;

public class Deck {

    public static final int CARDS_IN_DECK = 52;

    private final Card[] cards;

    private int nextCardIndex;

    public Deck() {
        
        this.nextCardIndex = 0;
        
        cards = new Card[CARDS_IN_DECK];
        char[] suits = {Card.CLUBS, Card.DIAMONDS, Card.HEARTS, Card.SPADES};
        int pos = 0;
        for (int j = 0; j < suits.length; j++) {
            int value = Card.LOWEST_VALUE;
            for (int k = Card.LOWEST_VALUE; k <= Card.HIGHEST_VALUE; k++) {
                cards[pos] = new Card(suits[j], value);
                value++;
                pos++;
            }
        }
    }
    
    public void shuffle(){
        Random rand = new Random();
        for(int i = CARDS_IN_DECK - 1 ; i >= 1 ; i--){
            Card a = cards[i];
            int randomIndex = rand.nextInt(i);
            Card b = cards[randomIndex];
            cards[i] = b;
            cards[randomIndex] = a;
        }
    }
    
    public Card nextCard(){
        int pos = this.nextCardIndex;
        this.nextCardIndex++;
        return cards[pos];
    }

    @Override
    public String toString() {
        String string = "";
        for(int i = 0 ; i< cards.length ; i++){
            string += "Card " + i + " : " + cards[i].toString() + "\n";
        }
        return string;
    }
    
    
}