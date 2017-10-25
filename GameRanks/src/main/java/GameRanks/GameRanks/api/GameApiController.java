package GameRanks.GameRanks.api;

import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.responseStruct.GameStruct;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameApiController {
    @Autowired
    private GameService gameService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Game>> gamesPage(){
        return ResponseEntity.ok(gameService.getGameList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GameStruct> getGame(@PathVariable long id) {
        Game game = gameService.getGame(id);
        Iterable<Review> reviewsForGame = gameService.getReviewForGame(id);
        int avgScore = gameService.getAvgScoreForGame(id);
        
        if(game != null){
            return ResponseEntity.ok(new GameStruct(game, reviewsForGame, avgScore));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PostMapping("/{id}")
    public ResponseEntity<Review> createReview(@PathVariable long id, @RequestBody Review review) {
        Game game = gameService.getGame(id);
        
        if(game != null){
            //Ha mar irt, akkor nem irhat megint
            if(gameService.hasUserWroteReview(id)){
                return ResponseEntity.badRequest().build();
            }

            User user = userService.getUser();
            
            return ResponseEntity.ok(gameService.createReview(review, game, user));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<Review> changeReview(@PathVariable long id, @RequestBody Review review) {
        if(gameService.updateReview(userService.getUser().getId(), id, review)){
            return ResponseEntity.ok(gameService.getReview(review.getId()));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable long id) {
        if(gameService.deleteReview(userService.getUser().getId(), id)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
