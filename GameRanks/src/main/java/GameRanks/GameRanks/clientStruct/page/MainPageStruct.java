package GameRanks.GameRanks.clientStruct.page;

import GameRanks.GameRanks.clientStruct.element.DeveloperStruct;
import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.element.PublisherStruct;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainPageStruct {
    Iterable<GameStruct> topThreeGames;
    Iterable<PublisherStruct> topThreePublishers;
    Iterable<DeveloperStruct> topThreeDevelopers;
}
