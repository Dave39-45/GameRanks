package GameRanks.GameRanks.serverStruct;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticStruct {
    Iterable<Object[]> numberOfGamesPerGenre;
    Iterable<Object[]> numberOfGamesPerPlatform;
    Iterable<Object[]> numberOfReviewsPerPlatform;
    Iterable<Object[]> avgScorePerPlatform;
}
