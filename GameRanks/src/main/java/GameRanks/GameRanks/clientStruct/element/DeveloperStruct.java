package GameRanks.GameRanks.clientStruct.element;

import GameRanks.GameRanks.model.Developer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeveloperStruct {
    private Developer developer;
    public double avgScore;
    public String rankTitle;    //gold,silver,bronze,normal
    public Iterable<GameStruct> games;
}
