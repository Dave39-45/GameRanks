package GameRanks.GameRanks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User is not valid")
public class UserNotValidException extends RuntimeException{
    
}
