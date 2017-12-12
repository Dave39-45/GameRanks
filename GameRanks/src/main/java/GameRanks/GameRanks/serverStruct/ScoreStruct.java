package GameRanks.GameRanks.serverStruct;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreStruct {
    private long id;
    
    private double avgscore;
    
    public ScoreStruct(Object[] values){
        this.id = ((BigInteger)values[0]).longValue();
        this.avgscore = (double)values[1];
    }
}
