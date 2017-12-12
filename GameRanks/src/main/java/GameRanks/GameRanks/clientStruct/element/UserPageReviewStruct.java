package GameRanks.GameRanks.clientStruct.element;

import GameRanks.GameRanks.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPageReviewStruct {
    private Review review;
    private long gameId;
    private String gameName;
    private String gameImage;
}
