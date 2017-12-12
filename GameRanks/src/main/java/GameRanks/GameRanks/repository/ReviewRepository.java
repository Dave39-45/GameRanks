package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>{
    Iterable<Review> findAllByGameId(long gameId);
    Review findByGameIdAndUserId(long gameId, long userId);
    Iterable<Review> findAllByUserId(long userId);
    
    @Query(value = "SELECT game_id id, AVG(CAST(score AS FLOAT)) avgscore "
                + "FROM REVIEWS "
                + "GROUP BY game_id "
                + "ORDER BY AVG(CAST(score AS FLOAT)) DESC, game_id "
                + "LIMIT :start, :amount", nativeQuery = true)
    List<Object[]> getGameScoresInRange(@Param("start") int start, @Param("amount") int amount);
    
    @Query("SELECT AVG(score) FROM Review WHERE game_id = :gameId")
    double calculateAverageGameScore(@Param("gameId") long gameId);
    
    @Query(value = "SELECT * " 
                + "FROM(" 
                    + "SELECT publisher_id, AVG(CAST(score AS FLOAT)) avgscore "
                    + "FROM games g JOIN reviews r ON (g.id = r.game_id) "
                    + "GROUP BY publisher_id "
                    + "ORDER BY AVG(CAST(score AS FLOAT)) DESC, publisher_id"
                + ")"
                +"LIMIT :start, :amount", nativeQuery = true)
    List<Object[]> getPublisherScoresInRange(@Param("start") int start, @Param("amount") int amount);
    
    @Query(value = "SELECT AVG(CAST(score AS FLOAT)) " +
                    "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                    "WHERE publisher_id = :publisherId", nativeQuery = true)
    double calculateAveragePublisherScore(@Param("publisherId") long publisherId);
    
    @Query(value = "SELECT * " 
                + "FROM(" 
                    + "SELECT developer_id, AVG(CAST(score AS FLOAT)) avgscore "
                    + "FROM games g JOIN reviews r ON (g.id = r.game_id) "
                    + "GROUP BY developer_id "
                    + "ORDER BY AVG(CAST(score AS FLOAT)) DESC, developer_id"
                + ")"
                +"LIMIT :start, :amount", nativeQuery = true)
    List<Object[]> getDeveloperScoresInRange(@Param("start") int start, @Param("amount") int amount);
    
    @Query(value = "SELECT AVG(CAST(score AS FLOAT)) " +
                    "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                    "WHERE developer_id = :developerId", nativeQuery = true)
    double calculateAverageDeveloperScore(@Param("developerId") long developerId);
    
    @Query(value = "SELECT COUNT(*) " +
                    "FROM reviews r JOIN games g ON(r.game_id = g.id) " +
                    "WHERE (:filterPublisher IS NULL OR publisher_id = :filterPublisher) AND (:filterDeveloper IS NULL OR developer_id = :filterDeveloper) " +
                    "AND r.platform = :filterPlatform", nativeQuery = true)
    Integer getNumberOfReviewsPerPlatform(@Param("filterPlatform") String filterPlatform, @Param("filterPublisher") Long filterPublisher, @Param("filterDeveloper") Long filterDeveloper);
    
    @Query(value = "SELECT AVG(CAST(score AS DECIMAL(10,2))) " +
                    "FROM reviews r JOIN games g ON(r.game_id = g.id) " +
                    "WHERE (:filterPublisher IS NULL OR publisher_id = :filterPublisher) AND (:filterDeveloper IS NULL OR developer_id = :filterDeveloper) " +
                    "AND r.platform = :filterPlatform", nativeQuery = true)
    Double getAvgScorePerPlatform(@Param("filterPlatform") String filterPlatform, @Param("filterPublisher") Long filterPublisher, @Param("filterDeveloper") Long filterDeveloper);
    
    /*@Modifying(clearAutomatically = true)
    @Query("UPDATE Review r"
            + " SET r.score = :#{#review.score}, r.pros = :#{#review.pros}, r.cons = :#{#review.cons}, r.reviewText = :#{#review.reviewText}"
            + " WHERE r.user_id = :userId AND r.game_id = :gameId")
    int updateReview(@Param("userId") long userId, @Param("gameId") long gameId, @Param("review") Review review);*/
    
    
    @Query(value = "SELECT MAX(id) FROM reviews", nativeQuery = true)
    long getLastId();
    
    //nativeQuery nelkul nem tudja ellenorizni es elszall
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE Reviews r"
            + " SET r.score = :score, r.pros = :pros, r.cons = :cons, r.review_text = :reviewText"
            + " WHERE r.user_id = :userId AND r.game_id = :gameId")
    int updateReview(@Param("userId") long userId, @Param("gameId") long gameId, @Param("score") int score,
            @Param("pros") String pros, @Param("cons") String cons, @Param("reviewText") String reviewText);
    
    @Modifying(clearAutomatically = true)
    long removeByUserIdAndGameId(long userId, long gameId);
}
