package GameRanks.GameRanks.service;

import GameRanks.GameRanks.clientStruct.element.DeveloperStruct;
import GameRanks.GameRanks.serverStruct.ScoreStruct;
import GameRanks.GameRanks.model.Developer;
import GameRanks.GameRanks.repository.DeveloperRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class DeveloperService {
    @Autowired
    private DeveloperRepository developerRepository;
    
    @Autowired
    private ReviewService reviewService;
    
    public Iterable<Developer> getDeveloperList(){
        return developerRepository.findAll();
    }
    
    public Developer getDeveloper(long id){
        return developerRepository.findOne(id);
    }
    
    public Iterable<DeveloperStruct> getDevelopersByScore(Iterable<ScoreStruct> developersToGet){
        List<DeveloperStruct> developers = new ArrayList<>();
        
        for(ScoreStruct developerScore : developersToGet){
            developers.add(new DeveloperStruct(developerRepository.findOne(developerScore.getId()), developerScore.getAvgscore(), null, null));
        }
        
        return reviewService.addDeveloperRankTitle(developers);
    }
    
    public Iterable<Developer> getDevelopersInRangeWithFilter(int start, int amount, String name, boolean ascending){
        return ascending ? developerRepository.getDevelopersInRangeWithFilterAsc(start, amount, name.toLowerCase()) :
                            developerRepository.getDevelopersInRangeWithFilterDesc(start, amount, name.toLowerCase());
    }
}