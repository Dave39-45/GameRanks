package GameRanks.GameRanks.service;

import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.element.UserPageReviewStruct;
import GameRanks.GameRanks.serverStruct.ScoreStruct;
import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.repository.GameRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private ReviewService reviewService;
    
    public Iterable<Game> getGameList(){
        return gameRepository.findAll();
    }
    
    public Game getGame(long id){
        return gameRepository.findOne(id);
    }
    
    public boolean isValidPlatform(long id, String platform){
        return gameRepository.isValidPlatform(id, platform).isPresent();
    }
    
    public Iterable<GameStruct> getGamesByScore(Iterable<ScoreStruct> gamesToGet){
        List<GameStruct> games = new ArrayList<>();
        
        for(ScoreStruct gameScore : gamesToGet){
            Game game = gameRepository.findOne(gameScore.getId());
            //String steamData = game.getSteamId() != null ? getSteamDataForGame(game.getSteamId()) : null;
            
            games.add(new GameStruct(game, gameScore.getAvgscore(), null, /*steamData*/null, null));
        }
        
        return reviewService.addGameRankTitle(games);
    }
    
    public Iterable<Game> getGamesInRangeWithFilter(int start, int amount, String name, String platform, String genre, boolean ascending, Long publisherId, Long developerId){
        return ascending ? gameRepository.getGamesInRangeWithFilterAsc(start, amount, name.toLowerCase(), platform, genre.toUpperCase(), publisherId, developerId) :
                            gameRepository.getGamesInRangeWithFilterDesc(start, amount, name.toLowerCase(), platform, genre.toUpperCase(), publisherId, developerId);
    }
    
    public List<Object[]> getNumberOfGamesPerGenre(Long publisherId, Long developerId){
        Game.Genre[] genres = Game.Genre.values();
        List<Object[]> results = new ArrayList<>();
        
        for(Game.Genre g : genres){
            String gs = g.toString();
            int numberOfGames = gameRepository.getNumberOfGamesPerGenre(gs, publisherId, developerId);
            
            if(numberOfGames > 0){
                Object[] a = {gs.substring(0, 1) + gs.substring(1).toLowerCase(), numberOfGames};
                results.add(a);
            }
        }
        
        return results;
    }
    
    public List<Object[]> getNumberOfGamesPerPlatform(Long publisherId, Long developerId){
        Game.Platform[] platforms = Game.Platform.values();
        List<Object[]> results = new ArrayList<>();
        
        for(Game.Platform p : platforms){
            String ps = p.toString();
            Object[] a = {ps, gameRepository.getNumberOfGamesPerPlatform(ps, publisherId, developerId)};
            results.add(a);
        }
        
        return results;
    }
    
    public Iterable<ScoreStruct> objectsToScoreStruct(List<Object[]> rawScores){
        List<ScoreStruct> scores = new ArrayList<>();
        
        for(Object[] obj : rawScores){
            scores.add(new ScoreStruct(obj));
        }
        
        return scores;
    }
    
    public Iterable<UserPageReviewStruct> getGameAndReviewDataForUser(long userId){
        Iterable<Review> reviews = reviewService.getReviewsForUser(userId);
        List<UserPageReviewStruct> structs = new ArrayList<>();
        
        for(Review review : reviews){
            Game game = review.getGame();
            structs.add(new UserPageReviewStruct(review, game.getId(), game.getName(), game.getSmallImage()));
        }
        
        return structs;
    }
}
