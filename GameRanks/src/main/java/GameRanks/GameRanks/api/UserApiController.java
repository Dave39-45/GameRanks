package GameRanks.GameRanks.api;

import org.springframework.web.bind.annotation.RestController;
import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.exception.EmailInUseException;
import GameRanks.GameRanks.model.User;
import static GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @GetMapping("")
    public ResponseEntity<User> user(){
        if (userService.isLoggedIn()) {
            return ResponseEntity.ok(userService.getUser());
        }
        
        return ResponseEntity.badRequest().build();
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestParam String password, @RequestParam String passwordAgain){
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(password.equals(passwordAgain) && userService.changePassword(password)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/changeEmail")
    public ResponseEntity<User> changeEmail(@RequestParam String email){
        try{
            if(userService.changeEmail(email)){
                return ResponseEntity.ok(userService.getUser());
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }
        catch(EmailInUseException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
