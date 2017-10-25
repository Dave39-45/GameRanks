package GameRanks.GameRanks.responseStruct;

import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;

public class GameStruct {
    public Game game;
    public Iterable<Review> reviews;
    public int avgScore;
    
    public GameStruct(Game game, Iterable<Review> reviews, int avgScore){
        this.game = game;
        this.reviews = reviews;
        this.avgScore = avgScore;
    }
}
