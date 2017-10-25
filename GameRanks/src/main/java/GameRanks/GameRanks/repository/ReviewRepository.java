package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>{
    Iterable<Review> findAllByGameId(long gameId);
    Optional<Review> findByUserId(long userId);
    
    @Query("SELECT AVG(score) FROM Review WHERE game_id = :gameId")
    int calculateAverageGameScore(@Param("gameId") long gameId);
    
    /*@Modifying(clearAutomatically = true)
    @Query("UPDATE Review r"
            + " SET r.score = :#{#review.score}, r.pros = :#{#review.pros}, r.cons = :#{#review.cons}, r.reviewText = :#{#review.reviewText}"
            + " WHERE r.user_id = :userId AND r.game_id = :gameId")
    int updateReview(@Param("userId") long userId, @Param("gameId") long gameId, @Param("review") Review review);*/
    
    //nativeQuery nelkul nem tudja ellenorizni es elszall
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE Review r"
            + " SET r.score = :score, r.pros = :pros, r.cons = :cons, r.reviewText = :reviewText"
            + " WHERE r.user_id = :userId AND r.game_id = :gameId")
    int updateReview(@Param("userId") long userId, @Param("gameId") long gameId, @Param("score") int score,
            @Param("pros") String pros, @Param("cons") String cons, @Param("reviewText") String reviewText);
    
    @Modifying(clearAutomatically = true)
    long removeByUserIdAndGameId(long userId, long gameId);
}
