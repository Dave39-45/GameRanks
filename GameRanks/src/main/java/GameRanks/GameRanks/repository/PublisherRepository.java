package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long>{
    
}
