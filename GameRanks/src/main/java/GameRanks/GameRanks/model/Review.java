package GameRanks.GameRanks.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REVIEWS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseModel{
    
    
    //KELL MEG
        //Kapcsolatok mas tablakhoz (pl OneToMany az ertekelesekhez)
    
        //Jo lenne, ha a score -t lehetne intervallummal korlatozni
    
    
    @JoinColumn
    @ManyToOne(targetEntity = Game.class, optional = false, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reviews")
    private Game game;
    
    @JoinColumn
    @ManyToOne(targetEntity = User.class, optional = false, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties({"email", "password", "accessLevel", "reviews"})
    private User user;
    
    @Column(nullable = false)
    private int score;
    
    @Column(nullable = false)
    private String pros;
    
    @Column(nullable = false)
    private String cons;
    
    @Column(nullable = false)
    private String reviewText;
    
    @Column(nullable = false)
    private Timestamp createdOn;
}
