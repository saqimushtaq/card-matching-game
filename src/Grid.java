public class Grid {
    
    private int rows;
    private int cols;
    private Card[][] cards;

    public Grid(int rows, int cols) {
        
        if(rows < 1 || cols < 1){
            throw new IllegalArgumentException("Invalid rows / colums");
        }
        
        this.rows = rows;
        this.cols = cols;
        this.cards = new Card[this.rows][this.cols];
    }
    
    public void setCard(int row, int col, Card card){
        if(card == null){
            throw new NullPointerException("Null Card");
        }
        if(row < 0 || row > rows){
            throw new IllegalArgumentException("Invalid row");
        }
        
        if(col < 0 || col > cols){
            throw new IllegalArgumentException("Invalid Column");
        }
        
        cards[row][col] = card;
        
    }
    
    public Card getCard(int row, int col){
        if(row < 0 || row > rows){
            throw new IllegalArgumentException("Invalid row");
        }
        
        if(col < 0 || col > cols){
            throw new IllegalArgumentException("Invalid Column");
        }
       
        return cards[row][col];
    }
    
    public String toString(){
        String string = "";
        for(int i = 0;i<rows; i++){
            for(int j = 0; j<cols ; j++){
                string += cards[i][j].toString() + " ";
            }
            
            string += "\n";
        }
        
        return string;
    }
    
}
