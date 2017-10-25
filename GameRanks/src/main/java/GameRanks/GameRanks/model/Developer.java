package GameRanks.GameRanks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEVELOPERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Developer extends BaseModel{
    @Column(nullable = false, unique = true)
    private String name;
    
    @OneToMany(targetEntity = Game.class, mappedBy = "developer", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("developer")
    @JsonIgnore
    private List<Game> games;
}
