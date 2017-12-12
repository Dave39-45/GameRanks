package GameRanks.GameRanks.clientStruct.element;

import GameRanks.GameRanks.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublisherStruct {
    private Publisher publisher;
    public double avgScore;
    public String rankTitle;    //gold,silver,bronze,normal
    public Iterable<GameStruct> games;
}
