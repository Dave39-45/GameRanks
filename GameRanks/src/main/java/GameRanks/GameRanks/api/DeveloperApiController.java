package GameRanks.GameRanks.api;

import GameRanks.GameRanks.clientStruct.element.DeveloperStruct;
import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.page.DeveloperPageStruct;
import GameRanks.GameRanks.model.Developer;
import GameRanks.GameRanks.serverStruct.StatisticStruct;
import GameRanks.GameRanks.service.DeveloperService;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/developer")
public class DeveloperApiController {
    @Autowired
    private DeveloperService developerService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("")
    public ResponseEntity<Iterable<DeveloperStruct>> developersPage(){
        return ResponseEntity.ok(developerService.getDevelopersByScore(reviewService.getDeveloperScoresInRange(0, 10)));
    }
    
    @GetMapping("/list")
    public ResponseEntity<Iterable<DeveloperStruct>> nextDevelopersForList(
            @RequestParam int start, @RequestParam int amount, @RequestParam(required = false) String name, @RequestParam(required = false) boolean ascending){
        
        if(start < 0 || amount < 0){
            return ResponseEntity.badRequest().build();
        }
        
        Iterable<DeveloperStruct> filteredDevelopers = reviewService.getScoresForDevelopers(developerService.getDevelopersInRangeWithFilter(start, amount, (name != null ? name : ""), ascending));
        
        return ResponseEntity.ok(filteredDevelopers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DeveloperPageStruct> getDeveloper(@PathVariable long id) {
        Developer developer = developerService.getDeveloper(id);
        
        if(developer != null){
            double avgScore = reviewService.getAvgScoreForDeveloper(id);
            Iterable<GameStruct> games = reviewService.getScoresForGames(gameService.getGamesInRangeWithFilter(0, 10, "", "", "", false, null, id));

            Iterable<Object[]> numberOfGamesPerGenre = gameService.getNumberOfGamesPerGenre(null, id);
            Iterable<Object[]> numberOfGamesPerPlatform = gameService.getNumberOfGamesPerPlatform(null, id);
            Iterable<Object[]> numberOfReviewsPerPlatform = reviewService.getNumberOfReviewsPerPlatform(null, id);
            Iterable<Object[]> avgScorePerPlatform = reviewService.getAvgScorePerPlatform(null, id);
            
            return ResponseEntity.ok(new DeveloperPageStruct(reviewService.addDeveloperRankTitle(new DeveloperStruct(developer, avgScore, null, games)),
                                    new StatisticStruct(numberOfGamesPerGenre, numberOfGamesPerPlatform, numberOfReviewsPerPlatform, avgScorePerPlatform)));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}/list")
    public ResponseEntity<Iterable<GameStruct>> nextGamesForList(
            @PathVariable long id, @RequestParam int start, @RequestParam int amount, @RequestParam(required = false) String name,
            @RequestParam(required = false) String platform, @RequestParam(required = false) String genre, @RequestParam(required = false) boolean ascending){
        
        if(start < 0 || amount < 0){
            return ResponseEntity.badRequest().build();
        }
        
        Iterable<GameStruct> filteredGames = reviewService.getScoresForGames(gameService.getGamesInRangeWithFilter(start, amount, (name != null ? name : ""), (platform != null ? platform : ""),
                                                                            (genre != null ? genre : ""), ascending, null, id));
        
        return ResponseEntity.ok(filteredGames);
    }
}
