package GameRanks.GameRanks.clientStruct.element;

import GameRanks.GameRanks.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewReviewStruct {
    private Review review;
    private String message;
}
