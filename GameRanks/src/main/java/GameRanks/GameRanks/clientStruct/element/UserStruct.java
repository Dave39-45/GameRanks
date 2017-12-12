package GameRanks.GameRanks.clientStruct.element;

import GameRanks.GameRanks.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStruct {
    private User user;
    private String message;
}
