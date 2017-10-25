package GameRanks.GameRanks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel{
    
    
    //KELL MEG
        //Kapcsolatok mas tablakhoz (pl OneToMany az ertekelesekhez)
    
        //A @JsonIgnore azt jelenti, hogy ha Json -ba rakjuk, akkor ez nem lesz benne
            //Remeljuk, attol meg kodban hozza lehet ferni
    
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessLevel accessLevel;
    
    @OneToMany(targetEntity = Review.class, mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("user")
    @JsonIgnore
    private List<Review> reviews;
    
    public enum AccessLevel{USER, ADMIN}
}
