package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long>{
    @Query(value = "SELECT ID, VERSION, NAME, LOGO " +
                    "FROM( " +
                        "SELECT publisher_id " +
                        "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                        "GROUP BY publisher_id " +
                        "ORDER BY AVG(CAST(score AS FLOAT)) DESC " +
                    ") a JOIN PUBLISHERS b ON (a.publisher_id = b.id) " +
                    "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Publisher> getPublishersInRange(@Param("start") int start, @Param("amount") int amount);
    
    @Query(value = "SELECT ID, VERSION, NAME, LOGO " +
                    "FROM( " +
                        "SELECT publisher_id " +
                        "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                        "GROUP BY publisher_id " +
                        "ORDER BY AVG(CAST(score AS FLOAT)) DESC " +
                    ") a JOIN PUBLISHERS b ON (a.publisher_id = b.id) " +
                    "WHERE LOWER(name) LIKE(CONCAT('%', :filterName, '%')) " + 
                    "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Publisher> getPublishersInRangeWithFilterDesc(@Param("start") int start, @Param("amount") int amount, @Param("filterName") String filterName);
    
    @Query(value = "SELECT ID, VERSION, NAME, LOGO " +
                    "FROM( " +
                        "SELECT publisher_id " +
                        "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                        "GROUP BY publisher_id " +
                        "ORDER BY AVG(CAST(score AS FLOAT)) " +
                    ") a JOIN PUBLISHERS b ON (a.publisher_id = b.id) " +
                    "WHERE LOWER(name) LIKE(CONCAT('%', :filterName, '%')) " + 
                    "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Publisher> getPublishersInRangeWithFilterAsc(@Param("start") int start, @Param("amount") int amount, @Param("filterName") String filterName);
}
