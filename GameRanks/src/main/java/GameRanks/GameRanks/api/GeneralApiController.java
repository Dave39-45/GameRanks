package GameRanks.GameRanks.api;

import GameRanks.GameRanks.exception.UserNotValidException;
import GameRanks.GameRanks.model.User;
import static GameRanks.GameRanks.model.User.AccessLevel.USER;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GeneralApiController {
    
    
    
    //TODO
        //Az alap oldal meglatogatasakor adjunk vissza egy objektumot, amiben bennevannak a szukseges dolgok (pl. 3 lista a 3 mini tablazathoz)
    
    
    
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public ResponseEntity<Object> mainPage(){
        return ResponseEntity.ok(new Object());
    }
    
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        try { 
            return ResponseEntity.ok(userService.login(user));
        }
        catch (UserNotValidException e) { 
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @PostMapping("/logout")
    public ResponseEntity<User> logout(@RequestBody User user){
        userService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user, @RequestParam String passwordAgain) {
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(!user.getPassword().equals(passwordAgain)){
            return ResponseEntity.badRequest().build();
        }
        
        user.setAccessLevel(USER);
        return ResponseEntity.ok(userService.register(user));
    }
}
