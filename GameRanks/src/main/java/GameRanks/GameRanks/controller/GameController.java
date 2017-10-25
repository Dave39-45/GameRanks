package GameRanks.GameRanks.controller;

import GameRanks.GameRanks.annotation.AccessBy;
import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.model.User.AccessLevel;
import GameRanks.GameRanks.service.GameService;
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
    
    
    //TODO
        //Ha mar irt egy ertekelest, akkor ne tudjon megegyet!!!
    
        //Csak akkor latszodjon majd a form, ha be van lepve (kesobb)
    
        //Majd informaljuk a klienst, ha mar irt es magint akar
        
        //Ha mar irt, akkor a meglevot modosithatja, vagy torolheti
            //Modositaskor lesz ott egy modify form, amibe bekerulnek az ertekelesenek adatai,
            //ha a modositasra kattint majd ezt elkuldve modositjuk a meglevo ertekelest
    
        //CRUD -ositani
    
    
    @Autowired
    private GameService gameService;
    
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
        Iterable<Review> reviewsForGame = gameService.getReviewForGame(id);
        int avgScore = gameService.getAvgScoreForGame(id);
        
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
        if(gameService.hasUserWroteReview(id)){
            //NEM jelenik meg, mert redirect, de am nem is kell majd
            model.addAttribute("alreadyWrote", true);
            return "redirect:/game/" + id;
        }
        
        User user = userService.getUser();
        
        if(game != null){
            gameService.createReview(review, game, user);
            return "redirect:/game/" + id;
        }
        else{
            return "notFound";
        }
    }
}
