package GameRanks.GameRanks.service;

import GameRanks.GameRanks.model.Developer;
import GameRanks.GameRanks.repository.DeveloperRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class DeveloperService {
    @Autowired
    private DeveloperRepository developerRepository;
    
    public Iterable<Developer> getDeveloperList(){
        return developerRepository.findAll();
    }
    
    public Developer getDeveloper(long id){
        return developerRepository.findOne(id);
    }
}