package GameRanks.GameRanks.clientStruct.page;

import GameRanks.GameRanks.clientStruct.element.UserPageReviewStruct;
import GameRanks.GameRanks.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPageStruct {
    private User user;
    private Iterable<UserPageReviewStruct> userPageReviewStructs;
}
