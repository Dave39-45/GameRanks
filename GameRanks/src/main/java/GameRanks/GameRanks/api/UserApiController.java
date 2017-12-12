package GameRanks.GameRanks.api;

import org.springframework.web.bind.annotation.RestController;
import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.clientStruct.element.Message;
import GameRanks.GameRanks.clientStruct.element.UserStruct;
import GameRanks.GameRanks.clientStruct.page.UserPageStruct;
import GameRanks.GameRanks.exception.EmailInUseException;
import static GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private GameService gameService;
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @GetMapping("")
    public ResponseEntity<UserPageStruct> user(){
        if (userService.isLoggedIn()) {
            return ResponseEntity.ok(new UserPageStruct(userService.getUser(), gameService.getGameAndReviewDataForUser(userService.getUser().getId())));
        }
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/changeAvatar")
    public ResponseEntity<UserStruct> changeAvatar(@RequestParam String avatar){
        
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(!avatar.equals("") && userService.changeAvatar(avatar)){
            userService.reloadUser();
            return ResponseEntity.ok(new UserStruct(userService.getUser(), "Successful change!"));
        }
        else{
            return ResponseEntity.badRequest().body(new UserStruct(null, "Passwords are diferent!"));
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/changePassword")
    public ResponseEntity<UserStruct> changePassword(@RequestParam String password, @RequestParam String passwordAgain){
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(password.equals(passwordAgain) && userService.changePassword(password)){
            userService.reloadUser();
            return ResponseEntity.ok(new UserStruct(userService.getUser(), "Successful change!"));
        }
        else{
            return ResponseEntity.badRequest().body(new UserStruct(null, "Passwords are diferent!"));
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/changeEmail")
    public ResponseEntity<UserStruct> changeEmail(@RequestParam String email){
        try{
            if(userService.changeEmail(email)){
                userService.reloadUser();
                return ResponseEntity.ok(new UserStruct(userService.getUser(), "Successful change!"));
            }
            else{
                return ResponseEntity.badRequest().body(new UserStruct(null, "Could not change the email!"));
            }
        }
        catch(EmailInUseException e){
            return ResponseEntity.badRequest().body(new UserStruct(null, "Email is already in use!"));
        }
    }
}
