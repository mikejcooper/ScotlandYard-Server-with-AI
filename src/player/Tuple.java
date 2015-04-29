package player;

import scotlandyard.Move;

/**
 * Created by gebruiker on 25/04/2015.
 */
public class Tuple {
    private Move move;
    private Integer rating;

    public Tuple(Move move,int rating){
        this.move = move;
        this.rating = rating;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
