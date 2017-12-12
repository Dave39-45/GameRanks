package GameRanks.GameRanks.api;

import GameRanks.GameRanks.clientStruct.element.DeveloperStruct;
import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.page.MainPageStruct;
import GameRanks.GameRanks.clientStruct.element.PublisherStruct;
import GameRanks.GameRanks.clientStruct.element.UserStruct;
import GameRanks.GameRanks.exception.UserNotValidException;
import GameRanks.GameRanks.model.User;
import static GameRanks.GameRanks.model.User.AccessLevel.USER;
import GameRanks.GameRanks.service.DeveloperService;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.PublisherService;
import GameRanks.GameRanks.service.ReviewService;
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
    @Autowired
    private UserService userService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private PublisherService publisherService;
    
    @Autowired
    private DeveloperService developerService;
    
    @GetMapping("")
    public ResponseEntity<MainPageStruct> mainPage(){
        Iterable<GameStruct> topThreeGames = gameService.getGamesByScore(reviewService.getGameScoresInRange(0, 3));
        Iterable<PublisherStruct> topThreePublishers = publisherService.getPublishersByScore(reviewService.getPublisherScoresInRange(0, 3));
        Iterable<DeveloperStruct> topThreeDevelopers = developerService.getDevelopersByScore(reviewService.getDeveloperScoresInRange(0, 3));
        
        return ResponseEntity.ok(new MainPageStruct(topThreeGames, topThreePublishers, topThreeDevelopers));
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserStruct> login(@RequestBody User user) {
        try { 
            return ResponseEntity.ok(new UserStruct(userService.login(user), null));
        }
        catch (UserNotValidException e) { 
            return ResponseEntity.badRequest().body(new UserStruct(null, "Invalid login informations!"));
        } 
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(/*@RequestBody User user*/){
        userService.logout();
        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    public ResponseEntity<UserStruct> register(@RequestBody User user, @RequestParam String passwordAgain) {
        //Ha nem egyezik meg a ket jelszo, akkor jelezzuk
        if(!user.getPassword().equals(passwordAgain)){
            return ResponseEntity.badRequest().body(new UserStruct(null, "Passwords are diferent!"));
        }
        
        //Ha mar letezik ilyen felhasznalo, akkor jelezzuk
        if(userService.isUserAlreadyExists(user)){
            return ResponseEntity.badRequest().body(new UserStruct(null, "User already exists!"));
        }
        
        user.setAccessLevel(USER);
        return ResponseEntity.ok(new UserStruct(userService.register(user), "Successful registration!"));
    }
}
