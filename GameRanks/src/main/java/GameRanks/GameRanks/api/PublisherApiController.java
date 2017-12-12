package GameRanks.GameRanks.api;

import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.element.PublisherStruct;
import GameRanks.GameRanks.clientStruct.page.PublisherPageStruct;
import GameRanks.GameRanks.model.Publisher;
import GameRanks.GameRanks.serverStruct.StatisticStruct;
import GameRanks.GameRanks.service.GameService;
import GameRanks.GameRanks.service.PublisherService;
import GameRanks.GameRanks.service.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publisher")
public class PublisherApiController {
    @Autowired
    private PublisherService publisherService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("")
    public ResponseEntity<Iterable<PublisherStruct>> publishersPage(){
        return ResponseEntity.ok(publisherService.getPublishersByScore(reviewService.getPublisherScoresInRange(0, 10)));
    }
    
    @GetMapping("/list")
    public ResponseEntity<Iterable<PublisherStruct>> nextPublishersForList(
            @RequestParam int start, @RequestParam int amount, @RequestParam(required = false) String name, @RequestParam(required = false) boolean ascending){
        
        if(start < 0 || amount < 0){
            return ResponseEntity.badRequest().build();
        }
        
        Iterable<PublisherStruct> filteredPublishers = reviewService.getScoresForPublishers(publisherService.getPublishersInRangeWithFilter(start, amount, (name != null ? name : ""), ascending));
        
        return ResponseEntity.ok(filteredPublishers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PublisherPageStruct> getPublisher(@PathVariable long id) {
        Publisher publisher = publisherService.getPublisher(id);
        
        if(publisher != null){
            double avgScore = reviewService.getAvgScoreForPublisher(id);
            Iterable<GameStruct> games = reviewService.getScoresForGames(gameService.getGamesInRangeWithFilter(0, 10, "", "", "", false, id, null));

            Iterable<Object[]> numberOfGamesPerGenre = gameService.getNumberOfGamesPerGenre(id, null);
            Iterable<Object[]> numberOfGamesPerPlatform = gameService.getNumberOfGamesPerPlatform(id, null);
            Iterable<Object[]> numberOfReviewsPerPlatform = reviewService.getNumberOfReviewsPerPlatform(id, null);
            List<Object[]> avgScorePerPlatform = reviewService.getAvgScorePerPlatform(id, null);
            
            return ResponseEntity.ok(new PublisherPageStruct(reviewService.addPublisherRankTitle(new PublisherStruct(publisher, avgScore, null, games)),
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
                                                                            (genre != null ? genre : ""), ascending, id, null));
        
        return ResponseEntity.ok(filteredGames);
    }
}
