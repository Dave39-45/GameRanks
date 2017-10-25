package GameRanks.GameRanks.service;

import GameRanks.GameRanks.model.Game;
import GameRanks.GameRanks.model.Review;
import GameRanks.GameRanks.model.User;
import GameRanks.GameRanks.repository.GameRepository;
import GameRanks.GameRanks.repository.ReviewRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class GameService {
    
    //KELL
        //AUTOWIRED repo
    
        //getGameList
            //Talan a getGameList -nek adhatunk szuresi feltetelt is?
    
        //CRUD
    
    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public Iterable<Game> getGameList(){
        return gameRepository.findAll();
    }
    
    public Game getGame(long id){
        return gameRepository.findOne(id);
    }
    
    public Iterable<Review> getReviewForGame(long gameId){
        return reviewRepository.findAllByGameId(gameId);
    }
    
    public boolean hasUserWroteReview(long userId){
        return reviewRepository.findByUserId(userId).isPresent();
    }
    
    @Transactional
    public int getAvgScoreForGame(long gameId){
        return reviewRepository.calculateAverageGameScore(gameId);
    }
    
    public Review getReview(long reviewId){
        return reviewRepository.findOne(reviewId);
    }
    
    @Transactional
    public Review createReview(Review review, Game game, User user){
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
