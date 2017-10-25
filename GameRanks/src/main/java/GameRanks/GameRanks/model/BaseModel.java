package GameRanks.GameRanks.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    //Hasznos az alabbiak miatt - https://stackoverflow.com/questions/10648515/using-version-in-spring-data-project
    @Version
    private long version;
}
