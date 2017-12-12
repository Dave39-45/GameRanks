package GameRanks.GameRanks.service;

import GameRanks.GameRanks.clientStruct.element.DeveloperStruct;
import GameRanks.GameRanks.clientStruct.element.GameStruct;
import GameRanks.GameRanks.clientStruct.element.PublisherStruct;
import GameRanks.GameRanks.model.Developer;
import GameRanks.GameRanks.serverStruct.ScoreStruct;
import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Publisher;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.repository.ReviewRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private SteamService steamService;
    
    public Iterable<ScoreStruct> objectsToScoreStruct(List<Object[]> rawScores){
        List<ScoreStruct> scores = new ArrayList<>();
        
        for(Object[] obj : rawScores){
            scores.add(new ScoreStruct(obj));
        }
        
        return scores;
    }
    
    public Iterable<ScoreStruct> getGameScoresInRange(int start, int amount){
        return objectsToScoreStruct(reviewRepository.getGameScoresInRange(start, amount));
    }
    
    @Transactional
    public double getAvgScoreForGame(long gameId){
        return reviewRepository.calculateAverageGameScore(gameId);
    }
    
    public Iterable<GameStruct> getScoresForGames(Iterable<Game> games){
        List<GameStruct> gamesWithScores = new ArrayList<>();
        
        for(Game game : games){
            long gameId = game.getId();
            String steamData = game.getSteamId() != null ? steamService.getSteamDataForGame(game.getSteamId()) : null;
            
            gamesWithScores.add(new GameStruct(game, getAvgScoreForGame(gameId), null, steamData, null));
        }
        
        return ReviewService.this.addGameRankTitle(gamesWithScores);
    }
    
    public Iterable<GameStruct> addGameRankTitle(Iterable<GameStruct> gameStructs){
        Iterable<ScoreStruct> topThree = objectsToScoreStruct(reviewRepository.getGameScoresInRange(0, 3));
        List<Long> idInOrder = new ArrayList<>();
        
        for(ScoreStruct st : topThree){
            idInOrder.add(st.getId());
        }
        
        for(GameStruct gameStruct: gameStructs){
            int index = idInOrder.indexOf(gameStruct.getGame().getId());
            
            if(index == 0){
                gameStruct.setRankTitle("gold");
            }
            else if(index == 1){
                gameStruct.setRankTitle("silver");
            }
            else if (index == 2){
                gameStruct.setRankTitle("bronze");
            }
            else{
                gameStruct.setRankTitle("normal");
            }
        }
        
        return gameStructs;
    }
    
    public GameStruct addGameRankTitle(GameStruct gameStruct){
        Iterable<ScoreStruct> topThree = objectsToScoreStruct(reviewRepository.getGameScoresInRange(0, 3));
        List<Long> idInOrder = new ArrayList<>();
        
        for(ScoreStruct st : topThree){
            idInOrder.add(st.getId());
        }
        
        int index = idInOrder.indexOf(gameStruct.getGame().getId());
            
        if(index == 0){
            gameStruct.setRankTitle("gold");
        }
        else if(index == 1){
            gameStruct.setRankTitle("silver");
        }
        else if (index == 2){
            gameStruct.setRankTitle("bronze");
        }
        else{
            gameStruct.setRankTitle("normal");
        }
        
        return gameStruct;
    }
    
    
    
    
    public Iterable<ScoreStruct> getPublisherScoresInRange(int start, int amount){
        return objectsToScoreStruct(reviewRepository.getPublisherScoresInRange(start, amount));
    }
    
    @Transactional
    public double getAvgScoreForPublisher(long publisherId){
        return reviewRepository.calculateAveragePublisherScore(publisherId);
    }
    
    public Iterable<PublisherStruct> getScoresForPublishers(Iterable<Publisher> publishers){
        List<PublisherStruct> publishersWithScores = new ArrayList<>();
        
        for(Publisher publisher : publishers){
            long publisherId = publisher.getId();
            publishersWithScores.add(new PublisherStruct(publisher, getAvgScoreForPublisher(publisherId), null, null));
        }
        
        return addPublisherRankTitle(publishersWithScores);
    }
    
    public Iterable<PublisherStruct> addPublisherRankTitle(List<PublisherStruct> publisherStructs){
        Iterable<ScoreStruct> topThree = objectsToScoreStruct(reviewRepository.getPublisherScoresInRange(0, 3));
        List<Long> idInOrder = new ArrayList<>();
        
        for(ScoreStruct st : topThree){
            idInOrder.add(st.getId());
        }
        
        for(PublisherStruct publisherStruct: publisherStructs){
            int index = idInOrder.indexOf(publisherStruct.getPublisher().getId());
            
            if(index == 0){
                publisherStruct.setRankTitle("gold");
            }
            else if(index == 1){
                publisherStruct.setRankTitle("silver");
            }
            else if (index == 2){
                publisherStruct.setRankTitle("bronze");
            }
            else{
                publisherStruct.setRankTitle("normal");
            }
        }
        
        return publisherStructs;
    }
    
    public PublisherStruct addPublisherRankTitle(PublisherStruct publihserStruct){
        Iterable<ScoreStruct> topThree = objectsToScoreStruct(reviewRepository.getPublisherScoresInRange(0, 3));
        List<Long> idInOrder = new ArrayList<>();
        
        for(ScoreStruct st : topThree){
            idInOrder.add(st.getId());
        }
        
        int index = idInOrder.indexOf(publihserStruct.getPublisher().getId());
            
        if(index == 0){
            publihserStruct.setRankTitle("gold");
        }
        else if(index == 1){
            publihserStruct.setRankTitle("silver");
        }
        else if (index == 2){
            publihserStruct.setRankTitle("bronze");
        }
        else{
            publihserStruct.setRankTitle("normal");
        }
        
        return publihserStruct;
    }
    
    
    
    
    
    public Iterable<ScoreStruct> getDeveloperScoresInRange(int start, int amount){
        return objectsToScoreStruct(reviewRepository.getDeveloperScoresInRange(start, amount));
    }
    
    @Transactional
    public double getAvgScoreForDeveloper(long developerId){
        return reviewRepository.calculateAverageDeveloperScore(developerId);
    }
    
    public Iterable<DeveloperStruct> getScoresForDevelopers(Iterable<Developer> developers){
        List<DeveloperStruct> developersWithScores = new ArrayList<>();
        
        for(Developer developer : developers){
            long developerId = developer.getId();
            developersWithScores.add(new DeveloperStruct(developer, getAvgScoreForDeveloper(developerId), null, null));
        }
        
        return addDeveloperRankTitle(developersWithScores);
    }
    
    public Iterable<DeveloperStruct> addDeveloperRankTitle(List<DeveloperStruct> developerStructs){
        Iterable<ScoreStruct> topThree = objectsToScoreStruct(reviewRepository.getDeveloperScoresInRange(0, 3));
        List<Long> idInOrder = new ArrayList<>();
        
        for(ScoreStruct st : topThree){
            idInOrder.add(st.getId());
        }
        
        for(DeveloperStruct developerStruct: developerStructs){
            int index = idInOrder.indexOf(developerStruct.getDeveloper().getId());
            
            if(index == 0){
                developerStruct.setRankTitle("gold");
            }
            else if(index == 1){
                developerStruct.setRankTitle("silver");
            }
            else if (index == 2){
                developerStruct.setRankTitle("bronze");
            }
            else{
                developerStruct.setRankTitle("normal");
            }
        }
        
        return developerStructs;
    }
    
    public DeveloperStruct addDeveloperRankTitle(DeveloperStruct developerStruct){
        Iterable<ScoreStruct> topThree = objectsToScoreStruct(reviewRepository.getDeveloperScoresInRange(0, 3));
        List<Long> idInOrder = new ArrayList<>();
        
        for(ScoreStruct st : topThree){
            idInOrder.add(st.getId());
        }
        
        int index = idInOrder.indexOf(developerStruct.getDeveloper().getId());
            
        if(index == 0){
            developerStruct.setRankTitle("gold");
        }
        else if(index == 1){
            developerStruct.setRankTitle("silver");
        }
        else if (index == 2){
            developerStruct.setRankTitle("bronze");
        }
        else{
            developerStruct.setRankTitle("normal");
        }
        
        return developerStruct;
    }
    
    
    
    
    
    public List<Object[]> getNumberOfReviewsPerPlatform(Long publisherId, Long developerId){
        Game.Platform[] platforms = Game.Platform.values();
        List<Object[]> results = new ArrayList<>();
        
        for(Game.Platform p : platforms){
            String ps = p.toString();
            Object[] a = {ps, reviewRepository.getNumberOfReviewsPerPlatform(ps, publisherId, developerId)};
            results.add(a);
        }
        
        return results;
    }
    
    public List<Object[]> getAvgScorePerPlatform(Long publisherId, Long developerId){
        Game.Platform[] platforms = Game.Platform.values();
        List<Object[]> results = new ArrayList<>();
        
        for(Game.Platform p : platforms){
            String ps = p.toString();
            Object[] a = {ps, reviewRepository.getAvgScorePerPlatform(ps, publisherId, developerId)};
            results.add(a);
        }
        
        return results;
    }
    
    
    
    
    public Iterable<Review> getReviewsForGame(long gameId){
        return reviewRepository.findAllByGameId(gameId);
    }
    
    public Iterable<Review> getReviewsForUser(long userId){
        return reviewRepository.findAllByUserId(userId);
    }
    
    public boolean hasUserWroteReview(long gameId, long userId){
        return reviewRepository.findByGameIdAndUserId(gameId, userId) != null;
    }
    
    public Review getReview(long reviewId){
        return reviewRepository.findOne(reviewId);
    }
    
    public Review getReview(long gameId, long userId){
        return reviewRepository.findByGameIdAndUserId(gameId, userId);
    }
    
    @Transactional
    public Review createReview(Review review, Game game, User user){
        review.setId(reviewRepository.getLastId() + 1);
        review.setGame(game);
        review.setUser(user);
        review.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        
        return reviewRepository.save(review);
    }
    
    @Transactional
    public boolean updateReview(long userId, long gameId, Review review){
        //return reviewRepository.updateReview(userId, gameId, review) > 0;
        return reviewRepository.updateReview(userId, gameId, review.getScore(), review.getPros(), review.getCons(), review.getReviewText()) > 0;
    }
    
    @Transactional
    public boolean deleteReview(long userId, long gameId){
        return reviewRepository.removeByUserIdAndGameId(userId, gameId) > 0;
    }
}
