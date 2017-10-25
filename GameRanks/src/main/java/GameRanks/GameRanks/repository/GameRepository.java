package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    //Iterable<Game> findAll();
}
