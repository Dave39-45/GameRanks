package GameRanks.GameRanks.repository;

import GameRanks.GameRanks.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.id = :id")
    int updateAvatar(@Param("id") long id, @Param("avatar") String avatar);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    int updatePassword(@Param("id") long id, @Param("password") String password);
    
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    int updateEmail(@Param("id") long id, @Param("email") String email);
}
