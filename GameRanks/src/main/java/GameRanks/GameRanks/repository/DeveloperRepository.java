package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Developer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long>{
    @Query(value = "SELECT ID, VERSION, NAME, LOGO " +
                    "FROM( " +
                        "SELECT developer_id " +
                        "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                        "GROUP BY developer_id " +
                        "ORDER BY AVG(CAST(score AS FLOAT)) DESC " +
                    ") a JOIN DEVELOPERS b ON (a.developer_id = b.id) " +
                    "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Developer> getDevelopersInRange(@Param("start") int start, @Param("amount") int amount);
    
    @Query(value = "SELECT ID, VERSION, NAME, LOGO " +
                    "FROM( " +
                        "SELECT developer_id " +
                        "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                        "GROUP BY developer_id " +
                        "ORDER BY AVG(CAST(score AS FLOAT)) DESC " +
                    ") a JOIN DEVELOPERS b ON (a.developer_id = b.id) " +
                    "WHERE LOWER(name) LIKE(CONCAT('%', :filterName, '%')) " + 
                    "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Developer> getDevelopersInRangeWithFilterDesc(@Param("start") int start, @Param("amount") int amount, @Param("filterName") String filterName);
    
    @Query(value = "SELECT ID, VERSION, NAME, LOGO " +
                    "FROM( " +
                        "SELECT developer_id " +
                        "FROM games g JOIN reviews r ON (g.id = r.game_id) " +
                        "GROUP BY developer_id " +
                        "ORDER BY AVG(CAST(score AS FLOAT)) " +
                    ") a JOIN DEVELOPERS b ON (a.developer_id = b.id) " +
                    "WHERE LOWER(name) LIKE(CONCAT('%', :filterName, '%')) " + 
                    "LIMIT :start, :amount", nativeQuery = true)
    Iterable<Developer> getDevelopersInRangeWithFilterAsc(@Param("start") int start, @Param("amount") int amount, @Param("filterName") String filterName);
}
