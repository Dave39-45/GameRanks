package GameRanks.GameRanks.clientStruct.page;

import GameRanks.GameRanks.clientStruct.element.DeveloperStruct;
import GameRanks.GameRanks.serverStruct.StatisticStruct;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeveloperPageStruct {
    private DeveloperStruct developerStruct;
    private StatisticStruct statisticStruct;
}
