package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Game;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    @Query(value = "SELECT id " +
                    "FROM GAMES " +
                    "WHERE id = :id AND PLATFORM LIKE(CONCAT('%', :platform, '%'))", nativeQuery = true)
    Optional<Game> isValidPlatform(@Param("id") long id, @Param("platform") String platform);
    
    @Query(value = "SELECT ID, VERSION, NAME, PUBLISHER_ID, DEVELOPER_ID, RELEASE_DATE, PLATFORM, GENRE, WALLPAPER, LIST_IMAGE, COVER_ART, SMALL_IMAGE, STEAM_ID "
                + "FROM("
                + "SELECT game_id "
                + "FROM REVIEWS "
                + "GROUP BY game_id "
                + "ORDER BY AVG(CAST(score AS FLOAT)) DESC, game_id) a JOIN GAMES b ON (a.game_id = b.id) "
                + "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Game> getGamesInRange(@Param("start") int start, @Param("amount") int amount);
    
    @Query(value = "SELECT ID, VERSION, NAME, PUBLISHER_ID, DEVELOPER_ID, RELEASE_DATE, PLATFORM, GENRE, WALLPAPER, LIST_IMAGE, COVER_ART, SMALL_IMAGE, STEAM_ID "
                + "FROM("
                + "SELECT game_id "
                + "FROM REVIEWS "
                + "GROUP BY game_id "
                + "ORDER BY AVG(CAST(score AS FLOAT)) DESC, game_id) a JOIN GAMES b ON (a.game_id = b.id) "
                + "WHERE LOWER(name) LIKE(CONCAT('%', :filterName, '%')) AND platform LIKE(CONCAT('%', :filterPlatform, '%')) AND genre LIKE(CONCAT('%', :filterGenre, '%')) "
                + "AND (:filterPublisher IS NULL OR publisher_id = :filterPublisher) AND (:filterDeveloper IS NULL OR developer_id = :filterDeveloper) "
                + "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Game> getGamesInRangeWithFilterDesc(@Param("start") int start, @Param("amount") int amount, @Param("filterName") String filterName, @Param("filterPlatform") String filterPlatform,
            @Param("filterGenre") String filterGenre, @Param("filterPublisher") Long filterPublisher, @Param("filterDeveloper") Long filterDeveloper);
    
    @Query(value = "SELECT ID, VERSION, NAME, PUBLISHER_ID, DEVELOPER_ID, RELEASE_DATE, PLATFORM, GENRE, WALLPAPER, LIST_IMAGE, COVER_ART, SMALL_IMAGE, STEAM_ID "
                + "FROM("
                + "SELECT game_id "
                + "FROM REVIEWS "
                + "GROUP BY game_id "
                + "ORDER BY AVG(CAST(score AS FLOAT)), game_id) a JOIN GAMES b ON (a.game_id = b.id) "
                + "WHERE LOWER(name) LIKE(CONCAT('%', :filterName, '%')) AND platform LIKE(CONCAT('%', :filterPlatform, '%')) AND genre LIKE(CONCAT('%', :filterGenre, '%')) "
                + "AND (:filterPublisher IS NULL OR publisher_id = :filterPublisher) AND (:filterDeveloper IS NULL OR developer_id = :filterDeveloper) "
                + "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Game> getGamesInRangeWithFilterAsc(@Param("start") int start, @Param("amount") int amount, @Param("filterName") String filterName, @Param("filterPlatform") String filterPlatform,
            @Param("filterGenre") String filterGenre, @Param("filterPublisher") Long filterPublisher, @Param("filterDeveloper") Long filterDeveloper);
    
    @Query(value = "SELECT COUNT(*) " +
                    "FROM GAMES " +
                    "WHERE (:filterPublisher IS NULL OR publisher_id = :filterPublisher) AND (:filterDeveloper IS NULL OR developer_id = :filterDeveloper) " +
                    "AND genre LIKE(CONCAT('%', :filterGenre, '%')) ", nativeQuery = true)
    Integer getNumberOfGamesPerGenre(@Param("filterGenre") String filterGenre, @Param("filterPublisher") Long filterPublisher, @Param("filterDeveloper") Long filterDeveloper);
    
    @Query(value = "SELECT COUNT(*) " +
                    "FROM GAMES " +
                    "WHERE (:filterPublisher IS NULL OR publisher_id = :filterPublisher) AND (:filterDeveloper IS NULL OR developer_id = :filterDeveloper) " +
                    "AND platform LIKE(CONCAT('%', :filterPlatform, '%')) ", nativeQuery = true)
    Integer getNumberOfGamesPerPlatform(@Param("filterPlatform") String filterPlatform, @Param("filterPublisher") Long filterPublisher, @Param("filterDeveloper") Long filterDeveloper);
}
