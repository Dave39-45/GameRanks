package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long>{
    
}
