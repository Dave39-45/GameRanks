package GameRanks.GameRanks.clientStruct.element;

import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStruct {
    public Game game;
    public double avgScore;
    public String rankTitle;    //gold,silver,bronze,normal
    public String steamData;
    public Iterable<Review> reviews;
}
