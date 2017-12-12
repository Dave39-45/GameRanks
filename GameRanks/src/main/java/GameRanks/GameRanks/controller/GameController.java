package GameRanks.GameRanks.controller;

import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.ReviewService;
import GameRanks.GameRanks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public String gamesPage(){
        Iterable<Game> games = gameService.getGameList();
        
        return "gameList";
    }
    
    @GetMapping("/{id}")
    public String getGame(@PathVariable long id, Model model) {
        Game game = gameService.getGame(id);
        Iterable<Review> reviewsForGame = reviewService.getReviewsForGame(id);
        double avgScore = reviewService.getAvgScoreForGame(id);
        
        if(game != null){
            model.addAttribute("gameName", game.getName());
            model.addAttribute("review", new Review()); //Igy ferhetunk hozza a formban az objektumhoz
            model.addAttribute("gameId", id);
            return "game";
        }
        else{
            return "notFound";
        }
    }
    
    @AccessBy({AccessLevel.USER, AccessLevel.ADMIN})
    @PostMapping("/{id}")
    public String createReview(@PathVariable long id, @RequestBody Review review, Model model) {
        Game game = gameService.getGame(id);
        
        //Ha mar irt, akkor nem irhat megint
        if(reviewService.hasUserWroteReview(id, userService.getUser().getId())){
            //NEM jelenik meg, mert redirect, de am nem is kell majd
            model.addAttribute("alreadyWrote", true);
            return "redirect:/game/" + id;
        }
        
        User user = userService.getUser();
        
        if(game != null){
            reviewService.createReview(review, game, user);
            return "redirect:/game/" + id;
        }
        else{
            return "notFound";
        }
    }
}
