package GameRanks.GameRanks.api;

import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.element.Message;
import GameRanks.GameRanks.clientStruct.element.NewReviewStruct;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.ReviewService;
import GameRanks.GameRanks.service.SteamService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameApiController {
    @Autowired
    private GameService gameService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SteamService steamService;
    
    @GetMapping("")
    public ResponseEntity<Iterable<GameStruct>> gamesPage(){
        return ResponseEntity.ok(gameService.getGamesByScore(reviewService.getGameScoresInRange(0, 10)));
    }
    
    @GetMapping("/list")
    public ResponseEntity<Iterable<GameStruct>> nextGamesForList(
            @RequestParam int start, @RequestParam int amount, @RequestParam(required = false) String name,
            @RequestParam(required = false) String platform, @RequestParam(required = false) String genre, @RequestParam(required = false) boolean ascending){
        
        if(start < 0 || amount < 0){
            return ResponseEntity.badRequest().build();
        }
        
        Iterable<GameStruct> filteredGames = reviewService.getScoresForGames(gameService.getGamesInRangeWithFilter(start, amount, (name != null ? name : ""), (platform != null ? platform : ""),
                                                                            (genre != null ? genre : ""), ascending, null, null));
        
        return ResponseEntity.ok(filteredGames);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GameStruct> getGame(@PathVariable long id) {
        Game game = gameService.getGame(id);
        
        if(game != null){
            double avgScore = reviewService.getAvgScoreForGame(id);
            String steamData = game.getSteamId() != null ? steamService.getSteamDataForGame(game.getSteamId()) : null;
            Iterable<Review> reviewsForGame = reviewService.getReviewsForGame(id);
            
            return ResponseEntity.ok(reviewService.addGameRankTitle(new GameStruct(game, avgScore, null, steamData, reviewsForGame)));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PostMapping("/{id}")
    public ResponseEntity<NewReviewStruct> createReview(@PathVariable long id, @RequestBody Review review) {
        Game game = gameService.getGame(id);
        
        if(game != null){
            //Ha mar irt, akkor nem irhat megint
            if(reviewService.hasUserWroteReview(id, userService.getUser().getId())){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "You alredy have a review for this game!"));
            }
            else if(!gameService.isValidPlatform(id, review.getPlatform())){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "Invalid platform!"));
            }
            else if(review.getReviewText().replaceAll("\\s+","").isEmpty()){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "You have to write a review!"));
            }
            else if(review.getPros().replaceAll("\\s+","").isEmpty()){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "You need at least one good thing about the game (PRO)!"));
            }
            else if(review.getCons().replaceAll("\\s+","").isEmpty()){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "You need at least one bad thing about the game (CON)!"));
            }
            else if(review.getScore() < 0 || review.getScore() > 10){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "Invalid score range!"));
            }
            
            try{
                Game.Platform.valueOf(review.getPlatform());
            }
            catch(IllegalArgumentException e){
                return ResponseEntity.badRequest().body(new NewReviewStruct(null, "Invalid platform!"));
            }

            Review newReview = reviewService.createReview(review, game, userService.getUser());
            
            return ResponseEntity.ok(new NewReviewStruct(newReview, "Review created!"));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<NewReviewStruct> changeReview(@PathVariable long id, @RequestBody Review review) {
        if(reviewService.updateReview(userService.getUser().getId(), id, review)){
            return ResponseEntity.ok(new NewReviewStruct(reviewService.getReview(id, userService.getUser().getId()), "Review changed!"));
        }
        else{
            return ResponseEntity.badRequest().body(new NewReviewStruct(null, "Could not update the review!"));
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteReview(@PathVariable long id) {
        if(reviewService.deleteReview(userService.getUser().getId(), id)){
            return ResponseEntity.ok(new Message("Review has been deleted!"));
        }
        else{
            return ResponseEntity.badRequest().body(new Message("Could not delete the review!"));
        }
    }
}
