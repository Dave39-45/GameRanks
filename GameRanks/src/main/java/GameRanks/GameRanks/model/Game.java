package GameRanks.GameRanks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GAMES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Game extends BaseModel{
    @Column(nullable = false)
    private String name;
    
    @JoinColumn
    @ManyToOne(targetEntity = Publisher.class, optional = false, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("games")
    private Publisher publisher;
    
    @JoinColumn
    @ManyToOne(targetEntity = Developer.class, optional = false, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("games")
    private Developer developer;
    
    @Column(nullable = false)
    private Timestamp releaseDate;
    
    //A jateknal megjeleno kep
    private String image;
    
    //A jatekhoz tartozo mufajok # -el elvalasztva ha tobb van
    @Column(nullable = false)
    private String genre;
    
    @OneToMany(targetEntity = Review.class, mappedBy = "game", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("game")
    @JsonIgnore
    private List<Review> reviews;
    
    public enum Genre{ACTION, ADVENTURE, CASUAL, INDIE, MMO, RACING, RPG, SIMULATION, SPORTS, STRATEGY};
}
