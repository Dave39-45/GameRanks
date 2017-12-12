package GameRanks.GameRanks.clientStruct.page;

import GameRanks.GameRanks.clientStruct.element.PublisherStruct;
import GameRanks.GameRanks.serverStruct.StatisticStruct;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublisherPageStruct {
    private PublisherStruct publisherStruct;
    private StatisticStruct statisticStruct;
}
